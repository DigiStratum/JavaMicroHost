package com.digistratum.microhost.RestServer;

import com.digistratum.microhost.RestServer.Controller.Controller;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.RestServer.Controller.ControllerBaseMicroHostImpl;
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
	HttpServer server;
	Map<String, Controller> controllerMap;

	/**
	 * Configuration-injected constructor
	 *
	 * Use this to automatically configure the service by way of configuration data.
	 *
	 * @param config Config instance (DI)
	 */
	@Inject
	public RestServerImpl(Config config) {
		this(
				Integer.parseInt(config.get("microhost.port","54321")),
				Integer.parseInt(config.get("microhost.threads","10"))
		);

		// Add our own default controllers as needed
		// Set up default controller for microhost context endpoints
		if ("on".equals(config.get("microhost.context.microhost", "off"))) {
			addControllerContext(new ControllerBaseMicroHostImpl(), "/microhost");
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
	public RestServerImpl(int port, int threadPoolSize)  {
		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);

			// ref: http://docs.oracle.com/javase/1.5.0/docs/api/java/util/concurrent/Executors.html#newFixedThreadPool(int)
			server.setExecutor(Executors.newFixedThreadPool(threadPoolSize));

			controllerMap = new HashMap<>();

			// Start the MicroHost HttpServer; Note that we can still add/remove
			// controller contexts on-the-fly while the server is running
			server.start();
		} catch (IOException e) {
			log.error("Failed to create new HttpServer", e);
		}
	}

	@Override
	public void addControllerContext(Controller ctrl, String ctx) {

		// If the controller or context are bogus...
		if ((null == ctrl) || (null == ctx) || ctx.isEmpty()) {
			log.error("addControllerContext() - Attempted to add invalid controller for context: '" + ctx + "'");
		}

		// If the context is already defined, it must be removed before we attempt to replace it
		if (hasContext(ctx)) {
			log.error("addControllerContext() - Attempted to add duplicate controller for context: '" + ctx + "'");
		}

		// Add it!
		controllerMap.put(ctx, ctrl);
		server.createContext(ctx, ctrl);
	}

	@Override
	public void removeContext(String ctx) throws MHException {
		if (! hasContext(ctx)) {
			throw new MHException("Attempted to remove undefined context: '" + ctx + "'");
		}
		server.removeContext(ctx);
		controllerMap.remove(ctx);
	}

	@Override
	public boolean hasContext(String ctx) {
		return controllerMap.containsKey(ctx);
	}

	@Override
	public void stop() {
		server.stop(0);
	}
}
