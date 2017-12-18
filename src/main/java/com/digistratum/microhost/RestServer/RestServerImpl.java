package com.digistratum.microhost.RestServer;

import com.digistratum.microhost.RestServer.Controller.Controller;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.RestServer.Controller.ControllerBaseMicroHostImpl;
import com.digistratum.microhost.RestServer.Http.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class RestServerImpl implements RestServer {
	protected final static Logger log = Logger.getLogger(RestServerImpl.class);

	protected final static Integer DEFAULT_PORT = 54321;
	protected final static Integer DEFAULT_THREADS = 10;
	protected final static String DEFAULT_ENDPOINTS = "off";
	protected final static String DEFAULT_CONTEXT = "/microhost";

	protected HttpServer server;
	protected Map<String, Controller> controllerMap;

	/**
	 * Configuration-injected constructor
	 *
	 * Use this to automatically configure the service by way of configuration data.
	 *
	 * @param config Config instance (DI)
	 * @param serverFactory an HttpServerFactory instance we can use to get a new HttpServer instance
	 */
	@Inject
	public RestServerImpl(Config config, HttpServerFactory serverFactory) {
		this(
				config.get("microhost.port",DEFAULT_PORT),
				config.get("microhost.threads",DEFAULT_THREADS),
				serverFactory
		);

		// Add our own default controllers as needed
		// Set up default controller for microhost context endpoints
		if ("on".equals(config.get("microhost.context.microhost", DEFAULT_ENDPOINTS))) {
			try {
				addControllerContext(new ControllerBaseMicroHostImpl(), DEFAULT_CONTEXT);
			} catch (MHException e) {
				// Swallow it - no exceptions are permitted from our Constructors (thanks Dagger!)
			}
		}
	}

	/**
	 * Parametric constructor
	 *
	 * Use this to programmatically configure the service rather than using configuration data.
	 *
	 * @param port Listening port for our MicroHost HTTP service
	 * @param threadPoolSize Count of threads for our pool (concurrency limit)
	 */
	public RestServerImpl(int port, int threadPoolSize, HttpServerFactory serverFactory)  {
		try {
			server = serverFactory.getInstance(port);

			// ref: http://docs.oracle.com/javase/1.5.0/docs/api/java/util/concurrent/Executors.html#newFixedThreadPool(int)
			server.setExecutor(Executors.newFixedThreadPool(threadPoolSize));

			controllerMap = new HashMap<>();

			// Start the MicroHost HttpServer; Note that we can still add/remove
			// controller contexts on-the-fly while the server is running
			server.start();
		} catch (MHException e) {
			// No exceptions from constructors (thanks, Daggger!)
			log.error("Failed to create new HttpServer", e);
		}
	}

	@Override
	public void addControllerContext(Controller ctrl, String ctx) throws MHException {

		// If the controller or context are bogus...
		if ((null == ctrl) || (null == ctx) || ctx.isEmpty()) {
			String msg = "addControllerContext() - Attempted to add invalid controller for context: '" + ctx + "'";
			log.error(msg);
			throw new MHException(msg);
		}

		// If the context is already defined, it must be removed before we attempt to replace it
		if (hasContext(ctx)) {
			String msg = "addControllerContext() - Attempted to add duplicate controller for context: '" + ctx + "'";
			log.error(msg);
			throw new MHException(msg);
		}

		// Add it!
		controllerMap.put(ctx, ctrl);
		server.createContext(ctx, ctrl);
	}

	@Override
	public void removeContext(String ctx) throws MHException {
		if (! hasContext(ctx)) {
			throw new MHException("removeContext() - Attempted to remove undefined context: '" + ctx + "'");
		}
		server.removeContext(ctx);
		controllerMap.remove(ctx);
	}

	@Override
	public boolean hasContext(String ctx) throws MHException {
		if ((null == ctx) || ctx.isEmpty()) {
			String msg = "hasContext() - Attempted to check invalid context";
			log.error(msg);
			throw new MHException(msg);
		}
		return controllerMap.containsKey(ctx);
	}

	@Override
	public void stop() {
		server.stop(0);
	}
}
