package com.digistratum.microhost.Process;

public interface MHRunnable extends Runnable {

	/**
	 * Determine from the outside whether we are running
	 *
	 * @return boolean true if we are running, else false
	 */
	public boolean isRunning();

	/**
	 * Stop the main loop from running any longer
	 */
	public void stop();
}
