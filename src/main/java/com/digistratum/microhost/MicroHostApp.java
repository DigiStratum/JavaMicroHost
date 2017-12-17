package com.digistratum.microhost;

import com.digistratum.microhost.RestServer.RestApi;

public class MicroHostApp {

	/**
	 * Constructor
	 *
	 * This should be called from the application main() with an instance of the RestApi; we will
	 * put the RestApi into its own thread and let it run while we monitor. This gives us the
	 * ability to potentially spawn more threads and/or perform additional work as needed while the
	 * RestApi service does its job.
	 *
	 * @param restApi RestApi RestApi instance
	 */
	public MicroHostApp(RestApi restApi) {
		// Start and monitor...
		Thread t = new Thread(restApi, "JUnit: RestApiImpl Instance");
		t.start();
		while (restApi.isRunning()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// Restore the interrupted status
				Thread.currentThread().interrupt();
			}
		}
	}
}
