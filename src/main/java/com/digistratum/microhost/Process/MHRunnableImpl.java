package com.digistratum.microhost.Process;

import org.apache.log4j.Logger;

public class MHRunnableImpl implements MHRunnable {
	// ref: http://www.java67.com/2015/07/how-to-stop-thread-in-java-example.html
	private volatile boolean amRunning = false;
	protected final static Logger log = Logger.getLogger(MHRunnableImpl.class);

	@Override
	public boolean isRunning() {
		return amRunning;
	}

	@Override
	public void stop() {
		// Don't do the work of stopping if we are not running
		if (! amRunning) return;
		amRunning = false;
	}

	@Override
	public void run() {
		// Do-nothing run loop
		while (amRunning) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// Restore the interrupted status
				Thread.currentThread().interrupt();
			}
		}
		stop();
	}
}
