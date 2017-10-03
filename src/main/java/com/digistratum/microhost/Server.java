package com.digistratum.microhost;

import com.digistratum.microhost.Controller.Controller;
import com.digistratum.microhost.Exception.MHException;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class Server {
	HttpServer server;
	Map<String, Controller> controllerMap;

	/**
	 * Configuration-injected constructor
	 *
	 * @param config
	 * @throws IOException
	 */
	public Server(Config config) throws IOException {
		this(
				Integer.parseInt(config.get("port","54321")),
				Integer.parseInt(config.get("threads","10"))
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
	public Server(int port, int threadPoolSize) throws IOException {
		server = HttpServer.create(new InetSocketAddress(port), 0);

		// ref: http://docs.oracle.com/javase/1.5.0/docs/api/java/util/concurrent/Executors.html#newFixedThreadPool(int)
		server.setExecutor(Executors.newFixedThreadPool(threadPoolSize));

		controllerMap = new HashMap<>();

		// Start the MicroHost HttpServer; Note that we can still add/remove
		// controller contexts on-the-fly while the server is running
		server.start();
	}

	/**
	 * Add a controller context to this server
	 *
	 * A controller is responsible for all request URIs relative to the context path
	 *
	 * @param ctrl Controller instance to do the work
	 * @param ctx String context base URI to map this controller to
	 *
	 * @throws MHException
	 */
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

	/**
	 * Remove a context which is already defined
	 *
	 * @param ctx String context base URI to which this controller is mapped
	 *
	 * @throws MHException
	 */
	public void removeContext(String ctx) throws MHException {
		if (! hasContext(ctx)) {
			throw new MHException("Attempted to remove undefined context: '" + ctx + "'");
		}
		server.removeContext(ctx);
		controllerMap.remove(ctx);
	}

	/**
	 * Check whether the specified context is already defined
	 *
	 * Note that this does not evaluate the URI to see if some other less specific context maps to this...
	 *
	 * @param ctx String context base URI which we want to check
	 *
	 * @return boolean true if the context is defined, else false
	 */
	public boolean hasContext(String ctx) {
		return controllerMap.containsKey(ctx);
	}

	/**
	 * Server stopper
	 */
	public void stop() {
		server.stop(0);
	}
}
