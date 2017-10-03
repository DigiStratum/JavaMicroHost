package com.digistratum.microhost;

import com.digistratum.microhost.Controller.ControllerMicroHost;
import com.digistratum.microhost.Exception.MHException;

import java.io.IOException;

/**
 * BASIC:
 * @todo Log output (log4j)
 * @todo Isolate all IOExceptions to exclusively code required to interface with HttpServer*
 * @todo Database connection pool
 *
 * INTERMEDIATE:
 * @todo Built-in support for common requirements like authentication, CORS, OPTIONS/HEAD responses
 *
 * ADVANCED:
 * @todo Register with service registry server
 */
public class MicroHost {

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
			System.out.print("MicroHost HTTP Server starting...");
			final Server server = new Server(config);

			// Set up default controller for microhost context endpoints
			if ("on".equals(config.get("microhost.context.microhost", "off"))) {
				server.addControllerContext(new ControllerMicroHost(), "/microhost");
			}
			System.out.println(" started!");

			// Register a shut-down hook so that we can clean up our business
			// ref: https://stackoverflow.com/questions/2921945/useful-example-of-a-shutdown-hook-in-java
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					System.out.print("MicroHost HTTP Server stopping...");
					server.stop();
					System.out.println(" stopped!");
				}
			});

			// Do-nothing run loop
			while (true) {
				Thread.sleep(1000);
			}

		} catch (IOException e) {
			System.out.println("\nMicroHost HTTP Server failed to initialize : " + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("\nMicroHost HTTP Server was interrupted: " + e.getMessage());
		} catch (MHException e) {
			System.out.println("\nMicroHost HTTP Server failed: " + e.getMessage());
		}
	}
}
