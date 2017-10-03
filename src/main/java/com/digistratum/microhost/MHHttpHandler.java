package com.digistratum.microhost;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

import static javax.imageio.ImageIO.read;

/**
 *
 *
 * ref: http://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/package-summary.html
 */
public class MHHttpHandler implements HttpHandler {
	// RequestMethod, <URIregex, HandlerMethodName>
	protected Map<String, Map<Pattern, String>> requestMap;

	/**
	 * Default Constructor
	 */
	public MHHttpHandler() {
		// ref: https://www.javatpoint.com/java-regex
		requestMap = new HashMap<>();
	}

	/**
	 * Add a mapping for the requestMethod/URI to a named requestHandler method
	 *
	 * @param requestMethod HTTP request method (e.g. get, post, etc)
	 * @param requestUriPattern Regex pattern to use to match a given request URI
	 * @param requestHandler The method in THIS class to call if the URI regex pattern matches
	 */
	protected void setRequestHandler(String requestMethod, String requestUriPattern, String requestHandler) {
		Map<Pattern, String> methodMap = requestMap.get(requestMethod);
		boolean allNew = (null == methodMap);
		if (allNew) {
			methodMap = new HashMap<>();
		}
		methodMap.put(Pattern.compile(requestUriPattern), requestHandler);
		if (allNew) {
			requestMap.put(requestMethod, methodMap);
		}
	}

	public void handle(HttpExchange t) throws IOException {

		// Get the URI for this request
		String requestUri = t.getRequestURI().toString();
		String requestMethod = t.getRequestMethod().toLowerCase();

		// Does the requested URI match anything in our methodMap?
		Map<Pattern, String> methodMap = requestMap.get(requestMethod);
		if (null == methodMap) {
			// Do a 404 response!
			errorDocument404(t);
			return;
		}
		// ref: https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
		Iterator it = methodMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			Pattern pattern = (Pattern) entry.getKey();
			if (pattern.matcher(requestUri).matches()) {
				// Found a match! This method will handle the request!
				String requestHandler = (String) entry.getValue();
				try {
					// TODO: Extract some useful bits from the URI and pass as arguments
					this.getClass().getMethod(requestHandler).invoke(t);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				break;
			}
		}

		// No mapping was found for this URI - do a 404 response!
		errorDocument404(t);
/*
		InputStream is = t.getRequestBody();
		read(is); // .. read the request body
		String response = "You requested <b>" + requestUri + "</b>";
		Headers responseHeaders = t.getResponseHeaders();
		responseHeaders.add("Content-Type", "text/html");
		t.sendResponseHeaders(200, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
		*/
	}

	protected void errorDocument404(HttpExchange t) throws IOException {
		InputStream is = t.getRequestBody();
		read(is); // .. read the request body
		String response = "404 Not Found";
		t.sendResponseHeaders(404, response.length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}
