package com.digistratum.microhost.Example;

import com.digistratum.microhost.Config.ConfigFactory;
import com.digistratum.microhost.Config.ConfigImpl;
import com.digistratum.microhost.Controller.ControllerBaseMicroHostImpl;
import com.digistratum.microhost.Example.Api.ControllerBaseImplExample;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPoolImpl;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPoolFactory;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.RestServerFactory;
import com.digistratum.microhost.RestServer.RestServerImpl;
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
		ConfigFactory mhConfigFactory = new ConfigFactory();
		MySqlConnectionPoolFactory mySqlConnectionPoolFactory = new MySqlConnectionPoolFactory();
		RestServerFactory restServerFactory = new RestServerFactory();

		microHost.run(mhConfigFactory, mySqlConnectionPoolFactory, restServerFactory);
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
	 * @param mhConfigFactory Object instance of ConfigFactory to retrieve configuration data
	 * @param mySqlConnectionPoolFactory Object instance of MySqlConnectionPoolFactory to get connected to a database
	 * @param restServerFactory Object instance of RestServerFactory to stand up our restful server/controller host
	 */
	protected void run(ConfigFactory mhConfigFactory, MySqlConnectionPoolFactory mySqlConnectionPoolFactory, RestServerFactory restServerFactory) {
		try {

			// Read in configuration properties
			String userDir = System.getProperty("user.dir");
			String propsFile = userDir + "/MicroHost.properties";
			ConfigImpl configImpl = mhConfigFactory.createMHConfig(propsFile);

			// Set up database connection pool
			MySqlConnectionPoolImpl pool = mySqlConnectionPoolFactory.createMySqlConnectionPool(configImpl);

			// Stand up a new HttpServer
			log.info("MicroHost HTTP RestServerImpl starting...");
			final RestServerImpl server = restServerFactory.createServer(configImpl);

			// Set up default controller for microhost context endpoints
			if ("on".equals(configImpl.get("microhost.context.microhost", "off"))) {
				server.addControllerContext(new ControllerBaseMicroHostImpl(), "/microhost");
			}
			if ("on".equals(configImpl.get("microhost.context.example", "off"))) {
				server.addControllerContext(new ControllerBaseImplExample(pool), "/example");
			}
			log.info("started!");

			// Register a shut-down hook so that we can clean up our business
			// ref: https://stackoverflow.com/questions/2921945/useful-example-of-a-shutdown-hook-in-java
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					log.info("MicroHost HTTP RestServerImpl stopping...");
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
			log.error("MicroHost HTTP RestServerImpl failed to initialize", e);
		} catch (InterruptedException e) {
			log.error("MicroHost HTTP RestServerImpl was interrupted", e);
		} catch (MHException e) {
			log.error("MicroHost HTTP RestServerImpl failed", e);
		}
	}
}
