package com.digistratum.microhost.Controller;

import com.digistratum.microhost.Endpoint.Endpoint;
import com.digistratum.microhost.Endpoint.EndpointErrorDocument;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RequestResponse;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * ref: http://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/package-summary.html
 */
public abstract class Controller implements HttpHandler {
	// RequestMethod, <URIregex, Endpoint>
	protected Map<String, Map<Pattern, Endpoint>> requestMap;
	protected Map<Integer, Endpoint> errorMap;

	/**
	 * Default Constructor
	 */
	public Controller() {
		// ref: https://www.javatpoint.com/java-regex
		requestMap = new HashMap<>();
		errorMap = new HashMap<>();
		errorMap.put(400, new EndpointErrorDocument(400, "400 Bad Request"));
		errorMap.put(401, new EndpointErrorDocument(401, "401 Unauthorized"));
		errorMap.put(403, new EndpointErrorDocument(403, "403 Forbidden"));
		errorMap.put(404, new EndpointErrorDocument(404, "404 Not Found"));
		errorMap.put(405, new EndpointErrorDocument(405, "405 Method Not Allowed"));
		errorMap.put(406, new EndpointErrorDocument(406, "406 Not Acceptable"));
		errorMap.put(409, new EndpointErrorDocument(409, "409 Conflict"));
		errorMap.put(410, new EndpointErrorDocument(410, "410 Gone"));
		errorMap.put(411, new EndpointErrorDocument(411, "411 Length Required"));
		errorMap.put(412, new EndpointErrorDocument(412, "412 Precondition Failed"));
		errorMap.put(413, new EndpointErrorDocument(413, "413 Request Entity Too Large"));
		errorMap.put(415, new EndpointErrorDocument(415, "415 Unsupported Media Type"));
		errorMap.put(416, new EndpointErrorDocument(416, "416 Requested Range Not Satisfiable"));
		errorMap.put(417, new EndpointErrorDocument(417, "417 Expectation Failed"));
		errorMap.put(428, new EndpointErrorDocument(428, "428 Precondition Required"));
		errorMap.put(429, new EndpointErrorDocument(429, "429 Too Many Requests"));
		errorMap.put(500, new EndpointErrorDocument(500, "500 Internal Server Error"));
		errorMap.put(501, new EndpointErrorDocument(501, "501 Not Implemented"));
		errorMap.put(502, new EndpointErrorDocument(502, "502 Bad Gateway"));
		errorMap.put(503, new EndpointErrorDocument(503, "503 Service Unavailable"));
		errorMap.put(504, new EndpointErrorDocument(504, "504 Gateway Timeout"));
		errorMap.put(598, new EndpointErrorDocument(598, "598 Network Read Timeout Error"));
		errorMap.put(599, new EndpointErrorDocument(599, "599 Network Connect Timeout Error"));
	}

	/**
	 * Add a mapping for the requestMethod/URI to a named requestHandler method
	 *
	 * @param requestMethod HTTP request method (e.g. get, post, etc)
	 * @param requestUriPattern Regex pattern to use to match a given request URI
	 * @param requestHandler The method in THIS class to call if the URI regex pattern matches
	 */
	protected void setRequestHandler(String requestMethod, String requestUriPattern, Endpoint requestHandler) {
		Map<Pattern, Endpoint> methodMap = requestMap.get(requestMethod);
		boolean allNew = (null == methodMap);
		if (allNew) {
			methodMap = new HashMap<>();
		}
		methodMap.put(Pattern.compile(requestUriPattern), requestHandler);
		if (allNew) {
			requestMap.put(requestMethod, methodMap);
		}
	}

	/**
	 * Handle a given HTTP request
	 *
	 * @param t HttpExchange which we are given with to work
	 *
	 * @throws IOException
	 */
	public void handle(HttpExchange t) throws IOException {

		Endpoint endpoint = getEndpoint(t);

		// TODO: Extract some useful bits from the URI and pass as arguments to request

		// Convert the HttpExchange into a RequestResponse
		RequestResponse request = fromHttpExchange(t);

		// Get a response from the endpoint
		RequestResponse response;
		try {
			response = endpoint.handle(request);
		} catch (MHException e) {
			String msg = "Error handling RequestResponse: " + e.getMessage();
			System.out.println(msg);
			throw new IOException(msg);
		}

		// Send the RequestResponse out
		sendResponse(t, response);
	}

	protected Endpoint getEndpoint(HttpExchange t) {
		// Get the URI for this request
		String requestUri = t.getRequestURI().toString();
		String requestMethod = t.getRequestMethod().toLowerCase();

		// Does the requested URI match anything in our methodMap?
		Map<Pattern, Endpoint> methodMap = requestMap.get(requestMethod);
		if (null == methodMap) {
			// Do a 404 response!
			return errorMap.get(404);
		}

		// ref: https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
		Iterator it = methodMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			Pattern pattern = (Pattern) entry.getKey();
			if (pattern.matcher(requestUri).matches()) {
				// Found a match; this endpoint will handle the request!
				return (Endpoint) entry.getValue();
			}
		}
		return errorMap.get(404);
	}

	protected RequestResponse fromHttpExchange(HttpExchange t) throws IOException {
		// Convert the HttpExchange headers to RequestResponse headers
		Headers originalRequestHeaders = t.getRequestHeaders();
		Map<String, String> requestHeaders = new HashMap<>();
		for (String name : originalRequestHeaders.keySet()) {
			requestHeaders.put(name, originalRequestHeaders.getFirst(name));
		}

		// Convert the HttpExchange into a RequestResponse
		RequestResponse request;
		InputStream is = t.getRequestBody();
		String requestBody = IOUtils.toString(is, StandardCharsets.UTF_8);
		is.close();
		try {
			return new RequestResponse(
					t.getRequestMethod(),
					t.getRequestURI().toString(),
					requestHeaders,
					requestBody
			);
		} catch (MHException e) {
			throw new IOException("Error converting HttpExchange to RequestResponse", e);
		}
	}

	protected void sendResponse(HttpExchange t, RequestResponse response) throws IOException {
		// Send the RequestResponse out (headers via HttpExchange, body via output stream)
		Headers responseHeaders = t.getResponseHeaders();
		for (String name : response.getHeaders().keySet()) {
			responseHeaders.add(name, response.getHeader(name));
		}
		t.sendResponseHeaders(response.getCode(), response.getBody().length());
		OutputStream os = t.getResponseBody();
		os.write(response.getBody().getBytes());
		os.close();
	}
}
