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
	 * Default constructor
	 *
	 * @throws IOException
	 */
	public Server() throws IOException {
		this(54321, 10);
	}

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
	}

	public void addControllerContext(Controller ctrl, String ctx) throws MHException {
		if ((null == ctrl) || (null == ctx) || ctx.isEmpty()) {
			throw new MHException("Attempted to add invalid controller context for: '" + ctx + "'");
		}
		controllerMap.put(ctx, ctrl);
		server.createContext(ctx, ctrl);
	}

	public void start() { server.start(); }

	/**
	 * Server stopper
	 */
	public void stop() {
		server.stop(0);
	}
}
