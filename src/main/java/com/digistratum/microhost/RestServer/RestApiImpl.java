package com.digistratum.microhost.RestServer;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPool;
import org.apache.log4j.Logger;

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
public class RestApiImpl implements RestApi, Runnable {
	// ref: http://www.java67.com/2015/07/how-to-stop-thread-in-java-example.html
	private volatile boolean amRunning = false;
	protected final static Logger log = Logger.getLogger(RestApiImpl.class);

	protected Config config;
	protected MySqlConnectionPool pool;
	protected RestServer server;
	protected RestServerSetterUpper restServerSetterUpper;

	@Inject
	public RestApiImpl(Config config, MySqlConnectionPool pool, RestServer server, RestServerSetterUpper restServerSetterUpper) {
		this.config = config;
		this.pool = pool;
		this.server = server;
		this.restServerSetterUpper = restServerSetterUpper;
	}

	@Override
	public boolean isRunning() {
		return amRunning;
	}

	@Override
	public void stop() {

		// Don't do the work of stopping if we are not running
		if (! amRunning) return;
		log.info("Stop requested!");
		amRunning = false;

	}

	@Override
	public void run() {
		amRunning = true;
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

		// Do-nothing run loop
		while (amRunning) {
			log.info("Running...");
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
