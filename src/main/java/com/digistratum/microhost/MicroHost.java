package com.digistratum.microhost;

import com.digistratum.microhost.Controller.ControllerMicroHost;
import com.digistratum.microhost.Database.Mysql.MySqlConnectionPool;
import com.digistratum.microhost.Database.Mysql.MySqlConnectionPoolFactory;
import com.digistratum.microhost.Example.ControllerExample;
import com.digistratum.microhost.Exception.MHException;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * INTERMEDIATE:
 * @todo Built-in support for common requirements like authentication, CORS, OPTIONS/HEAD responses
 *
 * ADVANCED:
 * @todo Register with service registry server
 */
public class MicroHost {
	final static Logger log = Logger.getLogger(MicroHost.class);

	/*
	 * Application entry point
	 */
	public static void main(String[] args) {
		MicroHost microHost = new MicroHost();
		MHConfigFactory mhConfigFactory = new MHConfigFactory();
		MySqlConnectionPoolFactory mySqlConnectionPoolFactory = new MySqlConnectionPoolFactory();
		ServerFactory serverFactory = new ServerFactory();
		microHost.run(mhConfigFactory, mySqlConnectionPoolFactory, serverFactory);
	}

	protected void run(MHConfigFactory mhConfigFactory, MySqlConnectionPoolFactory mySqlConnectionPoolFactory, ServerFactory serverFactory) {
		try {

			// Read in configuration properties
			String userDir = System.getProperty("user.dir");
			String propsFile = userDir + "/MicroHost.properties";
			MHConfig config = mhConfigFactory.createMHConfig(propsFile);

			// Set up database connection pool
			MySqlConnectionPool pool = mySqlConnectionPoolFactory.createMySqlConnectionPool(config);

			// Stand up a new HttpServer
			log.info("MicroHost HTTP Server starting...");
			final Server server = serverFactory.createServer(config);

			// Set up default controller for microhost context endpoints
			if ("on".equals(config.get("microhost.context.microhost", "off"))) {
				server.addControllerContext(new ControllerMicroHost(), "/microhost");
			}
			if ("on".equals(config.get("microhost.context.example", "off"))) {
				server.addControllerContext(new ControllerExample(pool), "/example");
			}
			log.info("started!");

			// Register a shut-down hook so that we can clean up our business
			// ref: https://stackoverflow.com/questions/2921945/useful-example-of-a-shutdown-hook-in-java
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					log.info("MicroHost HTTP Server stopping...");
					server.stop();
					log.info(" stopped!");
				}
			});

			// Do-nothing run loop
			while (true) {
				Thread.sleep(1000);
			}

		} catch (IOException e) {
			log.error("MicroHost HTTP Server failed to initialize", e);
		} catch (InterruptedException e) {
			log.error("MicroHost HTTP Server was interrupted", e);
		} catch (MHException e) {
			log.error("MicroHost HTTP Server failed", e);
		}
	}
}
