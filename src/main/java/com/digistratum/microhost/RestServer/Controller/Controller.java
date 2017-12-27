package com.digistratum.microhost.RestServer.Controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public interface Controller extends HttpHandler {

	/**
	 * Set the context for this controller to which all endpoints are relative
	 *
	 * @param context String URI path for context which this controller will be mapped to
	 */
	public void setContext(String context);

	/**
	 * Cause the controller to map all its own endpoints which it supplies
	 *
	 * This should be invoked by the owner of the controller after it is instantiated and has a context set.
	 */
	public void mapEndpoints();

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
