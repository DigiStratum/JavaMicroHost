package com.digistratum.microhost;

import java.io.IOException;

/**
 * BASIC:
 * @todo Configuration data from properties file
 * @todo Log output (log4j)
 * @todo Database connection pool
 * @todo URL context mapping for different endpoint handler methods
 *
 * INTERMEDIATE:
 * @todo Built-in endpoint to reflect health/status
 * @todo Abstract base class(es) for RESTful API, controller, endpoint
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

			// Stand up a new HttpServer
			System.out.print("MicroHost HTTP Server starting...");
			final MHHttpServer mhHttpServer = new MHHttpServer();
			System.out.println(" started!");

			// Register a shut-down hook so that we can clean up our business
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					System.out.print("MicroHost HTTP Server stopping...");
					mhHttpServer.stop();
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
		}
	}
}
