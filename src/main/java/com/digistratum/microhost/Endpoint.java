package com.digistratum.microhost;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface Endpoint {
	public void handleRequest(HttpExchange t) throws IOException;
}
