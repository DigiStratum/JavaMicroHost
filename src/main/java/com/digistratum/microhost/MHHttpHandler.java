package com.digistratum.microhost;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static javax.imageio.ImageIO.read;

/**
 *
 *
 * ref: http://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/package-summary.html
 */
public class MHHttpHandler implements HttpHandler {
	public void handle(HttpExchange t) throws IOException {
		InputStream is = t.getRequestBody();
		read(is); // .. read the request body
		String response = "This is the response";
		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}
