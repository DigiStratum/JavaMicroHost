package com.digistratum.microhost;

import com.digistratum.microhost.Controller.ControllerMicroHost;
import com.digistratum.microhost.Exception.MHException;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * BASIC:
 * @todo Database connection pool
 *  >>> Hibernate?
 *  ref: https://www.jumpingbean.co.za/hibernate-4.0.1-pom.xml
 *  ref: https://www.tutorialspoint.com/hibernate/hibernate_examples.htm
 *  ref: https://examples.javacodegeeks.com/enterprise-java/hibernate/hibernate-annotations-example/
 *  ref: https://www.mkyong.com/hibernate/hibernate-native-sql-queries-examples/
 *
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
		try {

			// Read in configuration properties
			String userDir = System.getProperty("user.dir");
			String propsFile = userDir + "/MicroHost.properties";
			Config config = new Config(propsFile);

			// Stand up a new HttpServer
			log.info("MicroHost HTTP Server starting...");
			final Server server = new Server(config);

			// Set up default controller for microhost context endpoints
			if ("on".equals(config.get("microhost.context.microhost", "off"))) {
				server.addControllerContext(new ControllerMicroHost(), "/microhost");
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
