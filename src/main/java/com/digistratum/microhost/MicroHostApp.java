package com.digistratum.microhost;

import com.digistratum.microhost.Process.MHRunnable;

/**
 * TODO Add support for receiving, starting, and monitoring multiple MHRunnable's
 */
abstract public class MicroHostApp {

	/**
	 * Constructor
	 *
	 * This should be called from the application main() with an instance of the RestApi; we will
	 * put the RestApi into its own thread and let it run while we monitor. This gives us the
	 * ability to potentially spawn more threads and/or perform additional work as needed while the
	 * RestApi service does its job.
	 *
	 * @param mHRunnable RestApi RestApi instance
	 */
	public MicroHostApp(MHRunnable mHRunnable) {
		// Start and monitor...
		Thread t = new Thread(mHRunnable, "MHRunnable Instance");
		t.start();
		while (mHRunnable.isRunning()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// Restore the interrupted status
				Thread.currentThread().interrupt();
			}
		}
	}
}
