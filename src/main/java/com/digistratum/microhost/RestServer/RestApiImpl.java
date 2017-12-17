package com.digistratum.microhost.RestServer;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPool;
import com.digistratum.microhost.Process.MHRunnableImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * BASIC
 * @todo move this class back to library out of example, and make example extend it to add controllers for setup
 * @todo build separate jars for example/library
 * @todo 90+% unit test coverage
 *
 * INTERMEDIATE:
 * @todo Built-in support for common requirements like authentication, CORS, OPTIONS/HEAD responses
 *
 * ADVANCED:
 * @todo Register service with registry service
 */
@Singleton
public class RestApiImpl extends MHRunnableImpl {
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
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				log.info("MicroHost HTTP RestApi stopping...");
				server.stop();
			}
		});

		super.run();
	}
}
