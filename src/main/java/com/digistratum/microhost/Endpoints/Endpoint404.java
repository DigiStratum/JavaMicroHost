package com.digistratum.microhost.Endpoints;

import com.digistratum.microhost.Endpoint;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static javax.imageio.ImageIO.read;

public class Endpoint404 implements Endpoint {
	public void handleRequest(HttpExchange t) throws IOException {
		InputStream is = t.getRequestBody();
		read(is); // .. read the request body
		String response = "404 Not Found";
		t.sendResponseHeaders(404, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}
