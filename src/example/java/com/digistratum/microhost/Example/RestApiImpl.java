package com.digistratum.microhost.Example;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPool;
import com.digistratum.microhost.Example.Api.ControllerExampleImpl;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPoolImpl;
import com.digistratum.microhost.Example.Model.RestApi;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.RestServer;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * BASIC
 * @todo separate example from reusable classes; build separate jars for them
 * @todo bring in Dagger for dependency injection; get rid of factories
 * @todo 90+% unit test coverage
 * @todo Set up inversion of control (IoC) using interface/implementation for better structure, DI, testability, DI, etc.
 * @todo Make the microhost classes package-private in order to prevent consumers from trying to use them directly (when possible)
 *
 * INTERMEDIATE:
 * @todo Built-in support for common requirements like authentication, CORS, OPTIONS/HEAD responses
 *
 * ADVANCED:
 * @todo Register service with registry service
 */
@Singleton
public class RestApiImpl implements RestApi, Runnable {
	protected final static Logger log = Logger.getLogger(RestApiImpl.class);
	protected boolean amRunning = false;

	Config config;
	RestServer restServer;
	MySqlConnectionPool pool;

	@Inject
	public RestApiImpl(Config config, MySqlConnectionPool pool, RestServer restServer) {
		this.config = config;
		this.pool = pool;
		this.restServer = restServer;
	}

	@Override
	public boolean isRunning() {
		return amRunning;
	}

	@Override
	public void stop() {
		log.info("Stop requested!");
		amRunning = false;
	}

	@Override
	public void run() {
		amRunning = true;
System.out.println("running...");

		try {
			if ("on".equals(config.get("microhost.context.example", "off"))) {
				restServer.addControllerContext(
						new ControllerExampleImpl((MySqlConnectionPoolImpl) pool),
						"/example"
				);
			}
			log.info("started!");

			// Register a shut-down hook so that we can clean up our business
			// ref: https://stackoverflow.com/questions/2921945/useful-example-of-a-shutdown-hook-in-java
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					log.info("MicroHost HTTP RestServerImpl stopping...");
					restServer.stop();
					log.info(" stopped!");
				}
			});

			// Do-nothing run loop
			amRunning = true;
			while (amRunning) {
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			log.error("MicroHost HTTP RestServerImpl was interrupted", e);
		} catch (MHException e) {
			log.error("MicroHost HTTP RestServerImpl failed", e);
		}
	}
}
