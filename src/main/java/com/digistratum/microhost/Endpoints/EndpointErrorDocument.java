package com.digistratum.microhost.Endpoints;

import com.digistratum.microhost.Endpoint;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static javax.imageio.ImageIO.read;

public class EndpointErrorDocument implements Endpoint {
	Integer code;
	String message;

	/**
	 * Constructor
	 *
	 * @param code
	 * @param message
	 */
	public EndpointErrorDocument(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * Request handler
	 *
	 * @param t
	 * @throws IOException
	 */
	public void handleRequest(HttpExchange t) throws IOException {
		InputStream is = t.getRequestBody();
		read(is); // .. read the request body
		t.sendResponseHeaders(code, message.length());
		OutputStream os = t.getResponseBody();
		os.write(message.getBytes());
		os.close();
	}
}
