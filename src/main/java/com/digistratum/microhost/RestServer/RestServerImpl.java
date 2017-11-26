package com.digistratum.microhost.RestServer;

import com.digistratum.microhost.RestServer.Controller.Controller;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.Config.ConfigImpl;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class RestServerImpl implements RestServer {
	HttpServer server;
	Map<String, Controller> controllerMap;

	/**
	 * Configuration-injected constructor
	 *
	 * @param configImpl
	 * @throws IOException
	 */
	public RestServerImpl(ConfigImpl configImpl) throws IOException {
		this(
				Integer.parseInt(configImpl.get("microhost.port","54321")),
				Integer.parseInt(configImpl.get("microhost.threads","10"))
		);
	}

	/**
	 * Parametric constructor
	 *
	 * @param port Listening port for our MicroHost HTTP service
	 * @param threadPoolSize Count of threads for our pool (concurrency limit)
	 *
	 * @throws IOException
	 */
	public RestServerImpl(int port, int threadPoolSize) throws IOException {
		server = HttpServer.create(new InetSocketAddress(port), 0);

		// ref: http://docs.oracle.com/javase/1.5.0/docs/api/java/util/concurrent/Executors.html#newFixedThreadPool(int)
		server.setExecutor(Executors.newFixedThreadPool(threadPoolSize));

		controllerMap = new HashMap<>();

		// Start the MicroHost HttpServer; Note that we can still add/remove
		// controller contexts on-the-fly while the server is running
		server.start();
	}

	@Override
	public void addControllerContext(Controller ctrl, String ctx) throws MHException {

		// If the controller or context are bogus...
		if ((null == ctrl) || (null == ctx) || ctx.isEmpty()) {
			throw new MHException("Attempted to add invalid controller for context: '" + ctx + "'");
		}

		// If the context is already defined, it must be removed before we attempt to replace it
		if (hasContext(ctx)) {
			throw new MHException("Attempted to add duplicate controller for context: '" + ctx + "'");
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
