package com.digistratum.microhost.RestServer.Http;

import com.digistratum.microhost.Exception.MHException;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Factory wrapper to get an HttpServer
 *
 * Necessary to satisfy DI, testability, and prevent spawning an actual HttpServer in a test context
 * due to the fact that a static method is used to create a new instance.
 *
 * @todo Add support for binding to a specific local IP address instead of just ANY ipaddress.
 */
public class HttpServerFactory {
	public static final int BACKLOG_DEFAULT = 0;

	/**
	 * Get a new instance of HttpServer
	 *
	 * @param port Integer INET port that was want to bind our HttpServer to
	 *
	 * @return HttpServer instance which is ready to start
	 *
	 * @throws MHException for any errors
	 */
	public HttpServer getInstance(int port) throws MHException {
		return getInstance(port, BACKLOG_DEFAULT);
	}

	/**
	 * Get a new instance of HttpServer
	 *
	 * @param port Integer INET port that was want to bind our HttpServer to
	 * @param backlog integer count for request queue backlog (<=0 for system default)
	 *
	 * @return HttpServer instance which is ready to start
	 *
	 * @throws MHException for any errors
	 */
	public HttpServer getInstance(int port, int backlog) throws MHException {
		try {
			return HttpServer.create(new InetSocketAddress(port), backlog);
		} catch (IOException e) {
			throw new MHException("Failed to create HttpServer instance", e);
		}
	}
}
