package com.digistratum.microhost.Example;

import com.digistratum.microhost.Example.Api.ControllerExample;
import com.digistratum.microhost.Controller.ControllerMicroHost;
import com.digistratum.microhost.Database.Mysql.MySqlConnectionPool;
import com.digistratum.microhost.Database.Mysql.MySqlConnectionPoolFactory;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.MHConfig;
import com.digistratum.microhost.MHConfigFactory;
import com.digistratum.microhost.Server;
import com.digistratum.microhost.ServerFactory;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * BASIC
 * @todo separate example from reusable classes; build separate jars for them
 * @todo bring in Dagger for dependency injection; get rid of factories
 * @todo 90+% unit test coverage
 * @todo Set up inversion of control (IoC) using interface/implementation for better structure, DI, testability, DI, etc.
 *
 * INTERMEDIATE:
 * @todo Built-in support for common requirements like authentication, CORS, OPTIONS/HEAD responses
 *
 * ADVANCED:
 * @todo Register service with registry service
 */
public class MicroHost {
	protected final static Logger log = Logger.getLogger(MicroHost.class);
	protected boolean amRunning = false;

	/*
	 * Application entry point
	 */
	public static void main(String[] args) {
		MicroHost microHost = new MicroHost();

		// @todo: get rid of these factories, use interfaces/implementations and dependency injection framework instead
		MHConfigFactory mhConfigFactory = new MHConfigFactory();
		MySqlConnectionPoolFactory mySqlConnectionPoolFactory = new MySqlConnectionPoolFactory();
		ServerFactory serverFactory = new ServerFactory();

		microHost.run(mhConfigFactory, mySqlConnectionPoolFactory, serverFactory);
	}

	/**
	 * Determine from the outside whether we are running
	 *
	 * @return boolean true if we are running, else false
	 */
	public boolean isRunning() {
		return amRunning;
	}

	/**
	 * Stop the main loop from running any longer
	 */
	public void stop() {
		log.info("Stop requested!");
		amRunning = false;
	}

	/**
	 * Run the main loop
	 *
	 * @param mhConfigFactory Object instance of MHConfigFactory to retrieve configuration data
	 * @param mySqlConnectionPoolFactory Object instance of MySqlConnectionPoolFactory to get connected to a database
	 * @param serverFactory Object instance of ServerFactory to stand up our restful server/controller host
	 */
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
			amRunning = true;
			while (amRunning) {
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
