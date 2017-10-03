package com.digistratum.microhost;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class MHHttpServer {
	HttpServer server;

	/**
	 * Default constructor
	 *
	 * @throws IOException
	 */
	public MHHttpServer() throws IOException {
		this(54321, 10, "/");
	}

	/**
	 * Parametric constructor
	 *
	 * @param port Listening port for our MicroHost HTTP service
	 * @param threadPoolSize Count of threads for our pool (concurrency limit)
	 * @param context Mapping which we should handle requests for
	 *
	 * @throws IOException
	 */
	public MHHttpServer(int port, int threadPoolSize, String context) throws IOException {
		server = HttpServer.create(new InetSocketAddress(port), 0);
		server.createContext(context, new MHHttpHandler());
		// ref: http://docs.oracle.com/javase/1.5.0/docs/api/java/util/concurrent/Executors.html#newFixedThreadPool(int)
		server.setExecutor(Executors.newFixedThreadPool(threadPoolSize));
		server.start();
	}

	/**
	 * Server stopper
	 */
	public void stop() {
		server.stop(0);
	}
}
