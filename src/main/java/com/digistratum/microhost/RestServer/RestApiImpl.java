package com.digistratum.microhost.RestServer;

import com.digistratum.Process.RunnableProcessImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RestApiImpl extends RunnableProcessImpl {
	protected RestServer server;

	@Inject
	public RestApiImpl(RestServer server) {
		this.server = server;
	}

	@Override
	public void run() {
		log.info("MicroHost HTTP RestApi starting...");

		// Register a shut-down hook so that we can clean up our business
		// ref: https://stackoverflow.com/questions/2921945/useful-example-of-a-shutdown-hook-in-java
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			log.info("MicroHost HTTP RestApi stopping...");
			server.stop();
		}));

		super.run();
	}
}
