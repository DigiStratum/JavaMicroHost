package com.digistratum.microhost.Controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public interface Controller extends HttpHandler {
	/**
	 * Handle a given HTTP request
	 *
	 * This comes from the HttpHandler interface; we never call this method directly
	 * as it is called automatically by the HttpServer's context handler.
	 *
	 * @param t HttpExchange which we are given with to work
	 *
	 * @throws IOException If anything goes wrong which HttpServer should know about
	 */
	@Override
	public void handle(HttpExchange t) throws IOException;
}
