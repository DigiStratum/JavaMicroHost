package com.digistratum.microhost.RestServer.Controller;

import com.digistratum.microhost.RestServer.Endpoint.Endpoint;
import com.digistratum.microhost.RestServer.Endpoint.EndpointErrorDocumentImpl;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Http.Headers.HeadersImpl;
import com.digistratum.microhost.RestServer.Http.RequestResponse.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

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
public abstract class ControllerBaseImpl implements Controller {
	final static Logger log = Logger.getLogger(ControllerBaseImpl.class);

	// MAP: RequestMethod, <URIregex, Endpoint>
	protected Map<String, Map<Pattern, Endpoint>> requestMap;

	// MAP: ErrorCode, Endpoint
	protected Map<Integer, Endpoint> errorMap;

	/**
	 * Default Constructor
	 */
	public ControllerBaseImpl() {
		// ref: https://www.javatpoint.com/java-regex
		requestMap = new HashMap<>();
		mapErrors();
	}

	/**
	 * Note that IOException is required by HttpHandler which we are implementing
	 *
	 * @throws IOException
	 */
	@Override
	public void handle(HttpExchange t) throws IOException {
		String logRequest = t.getRequestMethod() + " " + t.getRequestURI();

		Endpoint endpoint = getEndpoint(t);

		// TODO: Extract some useful bits from the URI and pass as arguments to request

		// Convert the HttpExchange into a RequestResponseImpl
		Request request;
		try {
			request = fromHttpExchange(t);
		} catch (Exception e) {
			String msg = logRequest + " 500 - Error converting RequestResponse";
			log.error(msg, e);
			throw new IOException(msg, e);
		}

		// Get a response from the endpoint
		ResponseImpl response;
		try {
			response = (ResponseImpl) endpoint.handle(request);
		} catch (Exception e) {
			String msg = logRequest + " 500 - Error handling RequestResponse";
			log.error(msg, e);
			throw new IOException(msg, e);
		}

		// Send the RequestResponse out
		try {
			sendResponse(t, response);
		} catch (Exception e) {
			String msg = logRequest + " 500 - Error sending response";
			log.error(msg, e);
			throw new IOException(msg, e);
		}

		// Log the request method, URI, response code, and body length
		// INFO GET /health 200 - 54
		log.info(logRequest + " " + response.getCode() + " - " + response.getBody().length());
	}

	/**
	 * Add a mapping for all the requestMethods for this URI to a named requestHandler method
	 *
	 * @param requestMethods String array of HTTP request methods (e.g. get, post, etc)
	 * @param requestUriPattern Regex pattern to use to match a given request URI
	 * @param endpoint Endpoint instance which will handle requests matching the URI
	 */
	protected void mapEndpoint(String[] requestMethods, String requestUriPattern, Endpoint endpoint) {
		for (String requestMethod : requestMethods) mapEndpoint(requestMethod, requestUriPattern, endpoint);
	}

	/**
	 * Add a mapping for the requestMethod/URI to a named requestHandler method
	 *
	 * Note: Throw no exceptions here since this is called from constructors of subclasses.
	 *
	 * @param requestMethod HTTP request method (e.g. get, post, etc)
	 * @param requestUriPattern Regex pattern to use to match a given request URI
	 * @param endpoint Endpoint instance which will handle requests matching the URI
	 */
	protected void mapEndpoint(String requestMethod, String requestUriPattern, Endpoint endpoint) {

		// Check our arguments
		if (! isValidMethod(requestMethod)) {
			log.error("mapEndpoint() - Invalid request method supplied");
			return;
		}
		if ((null == requestUriPattern) || requestUriPattern.isEmpty()) {
			log.error("mapEndpoint() - Invalid request URI pattern supplied");
			return;
		}
		if ((null == endpoint)) {
			log.error("mapEndpoint() - Invalid endpoint supplied");
			return;
		}

		// Add this endpoint to the method map for the specified request method and URI pattern
		Map<Pattern, Endpoint> methodMap = requestMap.get(requestMethod);
		boolean allNew = (null == methodMap);
		if (allNew) {
			methodMap = new HashMap<>();
		}
		methodMap.put(Pattern.compile(requestUriPattern), endpoint);
		if (allNew) {
			requestMap.put(requestMethod, methodMap);
		}
	}

	/**
	 * Is the specified request method valid?
	 *
	 * @param requestMethod String request method we are interested in
	 *
	 * @return boolean true if the method is valid, else false
	 */
	protected boolean isValidMethod(String requestMethod) {
		if ((null == requestMethod) || requestMethod.isEmpty()) {
			return false;
		}
		// TODO make sure that requestMethod is one which we support
		return true;
	}

	/**
	 * Get an Endpoint instance to handle the supplied request
	 *
	 * @param t HttpExchange instance that we're working with
	 *
	 * @return Endpoint instance to handle the request (may be an ErrorDocument!)
	 */
	protected Endpoint getEndpoint(HttpExchange t) {
		// Get the URI for this request
		String requestUri = t.getRequestURI().toString();
		String requestMethod = t.getRequestMethod().toLowerCase();

		// Does the request method match anything in our methodMap?
		Map<Pattern, Endpoint> methodMap = requestMap.get(requestMethod);
		if (null == methodMap) {
			// Nope: issue a 404 response!
			return errorMap.get(404);
		}

		// Does the request URI match any of our endpoints' URI regex's in our map?
		// ref: https://stackoverflow.com/questions/1066589/iterate-through-a-hashmap
		Iterator it = methodMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			Pattern pattern = (Pattern) entry.getKey();
			// ref: https://www.javatpoint.com/java-regex
			if (pattern.matcher(requestUri).matches()) {
				// Found a match; this endpoint will handle the request!
				return (Endpoint) entry.getValue();
			}
		}
		return errorMap.get(404);
	}

	/**
	 * Convert an HttpExchange into a RequestResponse
	 *
	 * @param t HttpExchange instance that we're working with
	 *
	 * @return RequestResponse instance populated with details from the HttpExchange
	 *
	 * @throws MHException If anything goes sideways...
	 */
	protected Request fromHttpExchange(HttpExchange t) throws MHException {

		// Convert the HttpExchange headers to RequestResponse headers
		Headers originalRequestHeaders = t.getRequestHeaders();
		HeadersImpl requestHeaders = new HeadersImpl();
		for (String name : originalRequestHeaders.keySet()) {
			requestHeaders.set(name, originalRequestHeaders.getFirst(name));
		}

		// Convert the HttpExchange into a RequestResponse
		InputStream is = t.getRequestBody();
		String requestBody = null;
		try {
			requestBody = IOUtils.toString(is, StandardCharsets.UTF_8);
			is.close();
		} catch (IOException e) {
			throw new MHException("Internal error reading request body", e);
		}


		return new RequestImpl(
				t.getRequestMethod(),
				t.getRequestURI().toString(),
				requestHeaders,
				requestBody
		);
	}

	/**
	 * Sent a response to the waiting client using HttpExchange and RequestResponse
	 *
	 * @param t HttpExchange instance that we're working with; it has the output stream
	 * @param response Response instance with all our response data in it
	 *
	 * @throws MHException if anything goes sideways...
	 */
	protected void sendResponse(HttpExchange t, Response response) throws MHException {

		// Send the RequestResponse out (headers via HttpExchange, body via output stream)
		Headers actualHeaders = t.getResponseHeaders();
		HeadersImpl desiredHeaders = (HeadersImpl) response.getHeaders();
		for (String name : desiredHeaders.list()) {
			actualHeaders.add(name, desiredHeaders.get(name));
		}

		try {
			t.sendResponseHeaders(response.getCode(), response.getBody().length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBody().getBytes());
			os.close();
		} catch (IOException e) {
			throw new MHException("Internal error reading request body", e);
		}
	}

	/**
	 * Map a bunch of Error Document endpoints
	 */
	protected void mapErrors() {
		errorMap = new HashMap<>();
		errorMap.put(400, new EndpointErrorDocumentImpl(400, "400 Bad RequestImpl"));
		errorMap.put(401, new EndpointErrorDocumentImpl(401, "401 Unauthorized"));
		errorMap.put(403, new EndpointErrorDocumentImpl(403, "403 Forbidden"));
		errorMap.put(404, new EndpointErrorDocumentImpl(404, "404 Not Found"));
		errorMap.put(405, new EndpointErrorDocumentImpl(405, "405 Method Not Allowed"));
		errorMap.put(406, new EndpointErrorDocumentImpl(406, "406 Not Acceptable"));
		errorMap.put(409, new EndpointErrorDocumentImpl(409, "409 Conflict"));
		errorMap.put(410, new EndpointErrorDocumentImpl(410, "410 Gone"));
		errorMap.put(411, new EndpointErrorDocumentImpl(411, "411 Length Required"));
		errorMap.put(412, new EndpointErrorDocumentImpl(412, "412 Precondition Failed"));
		errorMap.put(413, new EndpointErrorDocumentImpl(413, "413 RequestImpl Entity Too Large"));
		errorMap.put(415, new EndpointErrorDocumentImpl(415, "415 Unsupported Media Type"));
		errorMap.put(416, new EndpointErrorDocumentImpl(416, "416 Requested Range Not Satisfiable"));
		errorMap.put(417, new EndpointErrorDocumentImpl(417, "417 Expectation Failed"));
		errorMap.put(428, new EndpointErrorDocumentImpl(428, "428 Precondition Required"));
		errorMap.put(429, new EndpointErrorDocumentImpl(429, "429 Too Many Requests"));
		errorMap.put(500, new EndpointErrorDocumentImpl(500, "500 Internal RestServerImpl Error"));
		errorMap.put(501, new EndpointErrorDocumentImpl(501, "501 Not Implemented"));
		errorMap.put(502, new EndpointErrorDocumentImpl(502, "502 Bad Gateway"));
		errorMap.put(503, new EndpointErrorDocumentImpl(503, "503 Service Unavailable"));
		errorMap.put(504, new EndpointErrorDocumentImpl(504, "504 Gateway Timeout"));
		errorMap.put(598, new EndpointErrorDocumentImpl(598, "598 Network Read Timeout Error"));
		errorMap.put(599, new EndpointErrorDocumentImpl(599, "599 Network Connect Timeout Error"));
	}
}
