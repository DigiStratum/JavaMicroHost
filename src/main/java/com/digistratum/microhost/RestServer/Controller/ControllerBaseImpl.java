package com.digistratum.microhost.RestServer.Controller;

import com.digistratum.microhost.RestServer.Endpoint.Endpoint;
import com.digistratum.microhost.RestServer.Endpoint.EndpointErrorDocumentImpl;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Http.Headers.HeadersImpl;
import com.digistratum.microhost.RestServer.Http.HttpSpec;
import com.digistratum.microhost.RestServer.Http.RequestResponse.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
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

	List<String> supportedRequestMethods;
	/**
	 * Default Constructor
	 */
	public ControllerBaseImpl() {

		// Declare our supported request methods
		supportedRequestMethods = new ArrayList<>();
		String[] srm = {"get","post","put","delete","head","options","patch"};
		supportedRequestMethods.addAll(Arrays.asList(srm));

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
		if ((null == requestMethod) || requestMethod.isEmpty()) return false;
		return supportedRequestMethods.contains(requestMethod);
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
		int[] errorCodes = {
				HttpSpec.HTTP_STATUS_400_BAD_REQUEST, HttpSpec.HTTP_STATUS_401_UNAUTHORIZED,
				HttpSpec.HTTP_STATUS_403_FORBIDDEN, HttpSpec.HTTP_STATUS_404_NOT_FOUND,
				HttpSpec.HTTP_STATUS_405_METHOD_NOT_ALLOWED, HttpSpec.HTTP_STATUS_406_NOT_ACCEPTABLE,
				HttpSpec.HTTP_STATUS_409_CONFLICT, HttpSpec.HTTP_STATUS_410_GONE,
				HttpSpec.HTTP_STATUS_411_LENGTH_REQUIRED, HttpSpec.HTTP_STATUS_412_PRECONDITION_FAILED,
				HttpSpec.HTTP_STATUS_413_REQUEST_ENTITY_TOO_LARGE, HttpSpec.HTTP_STATUS_415_UNSUPPORTED_MEDIA_TYPE,
				HttpSpec.HTTP_STATUS_416_REQUESTED_RANGE_NOT_SATISFIED, HttpSpec.HTTP_STATUS_417_EXPECTATION_FAILED,
				HttpSpec.HTTP_STATUS_428_PRECONDITION_REQUIRED, HttpSpec.HTTP_STATUS_429_TOO_MANY_REQUESTS,
				HttpSpec.HTTP_STATUS_500_INTERNAL_SERVER_ERROR, HttpSpec.HTTP_STATUS_501_NOT_IMPLEMENTED,
				HttpSpec.HTTP_STATUS_502_BAD_GATEWAY, HttpSpec.HTTP_STATUS_503_SERVICE_UNAVAILABLE,
				HttpSpec.HTTP_STATUS_504_GATEWAY_TIMEOUT
		};
		for (int errorCode : errorCodes) {
			errorMap.put(
					errorCode,
					new EndpointErrorDocumentImpl(
							errorCode,
							errorCode + " " + HttpSpec.getStatusDescription(errorCode)
					)
			);
		}
	}
}
