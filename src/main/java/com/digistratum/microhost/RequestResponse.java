package com.digistratum.microhost;

import com.digistratum.microhost.Exception.MHException;

import java.util.HashMap;
import java.util.Map;

public class RequestResponse {
	private enum Type {request, response };
	private Type type;

	private Map<String, String> headers;
	private String body = null;
	private String method = null;
	private String uri = null;
	private Integer code = null;

	/**
	 * Bodyless Request Constructor
	 *
	 * @param requestMethod String representation of HTTP request method (e.g. get, post, delete, etc.)
	 * @param requestUri HTTP request URI string
	 * @param requestHeaders Map<name, value> HTTP request headers
	 */
	public RequestResponse(String requestMethod, String requestUri, Map<String, String> requestHeaders) throws MHException {
		this(requestMethod, requestUri, requestHeaders, null);
	}

	/**
	 * Request Constructor
	 *
	 * @param requestMethod String representation of HTTP request method (e.g. get, post, delete, etc.)
	 * @param requestUri HTTP request URI string
	 * @param requestHeaders Map<name, value> HTTP request headers
	 * @param requestBody HTTP request body (optional)
	 */
	public RequestResponse(String requestMethod, String requestUri, Map<String, String> requestHeaders, String requestBody) throws MHException {

		// This is a REQUEST
		type = Type.request;

		// Check the request method
		if ((null == requestMethod) || requestMethod.isEmpty()) {
			throw new MHException("RequestResponse() - supplied request method was empty");
		}
		// TODO: Check that the request method is one which we support
		method = requestMethod;

		// Check the URI
		if ((null == requestUri) || requestUri.isEmpty()) {
			throw new MHException("RequestResponse() - supplied URI was empty");
		}
		uri = requestUri;

		// Check the headers
		if (null == requestHeaders) {
			throw new MHException("RequestResponse() - headers were not supplied");
		}
		headers = requestHeaders;

		// Grab the body (empty is valid fo requests such as with GET)
		body = requestBody;
	}

	/**
	 * Bodyless Response Constructor
	 *
	 * @param responseCode Integer HTTP response code
	 * @param responseHeaders Map<name, value> HTTP response headers
	 */
	public RequestResponse(int responseCode, Map<String, String> responseHeaders) throws MHException {
		this(responseCode, responseHeaders, null);
	}

	/**
	 * Headerless Response Constructor
	 *
	 * @param responseCode Integer HTTP response code
	 * @param responseBody HTTP response body (optional)
	 */
	public RequestResponse(int responseCode, String responseBody) throws MHException {
		this(responseCode, new HashMap<String, String>(), responseBody);
	}

	/**
	 * Headerless, Bodyless Response Constructor (code-only)
	 *
	 * @param responseCode Integer HTTP response code
	 */
	public RequestResponse(int responseCode) throws MHException {
		this(responseCode, new HashMap<String, String>(), null);
	}

	/**
	 * Response Constructor
	 *
	 * @param responseCode Integer HTTP response code
	 * @param responseHeaders Map<name, value> HTTP response headers
	 * @param responseBody HTTP response body (optional)
	 */
	public RequestResponse(int responseCode, Map<String, String> responseHeaders, String responseBody) throws MHException {

		// This is a RESPONSE
		type = Type.response;

		// TODO: check that the code is one which we support
		code = responseCode;

		// Check the headers
		if (null == responseHeaders) {
			throw new MHException("RequestResponse() - headers were not supplied");
		}
		headers = responseHeaders;

		// Grab the body (empty is valid fo requests such as with POST)
		body = responseBody;
	}

	/**
	 * Check whether this instance represents a REQUEST
	 *
	 * @return boolean true if it does, else false (if it's a RESPONSE!)
	 */
	public boolean isRequest() {
		return (type == Type.request);
	}

	/**
	 * Check whether this instance represents a RESPONSE
	 *
	 * @return boolean true if it does, else false (if it's a REQUEST!)
	 */
	public boolean isResponse() {
		return (type == Type.response);
	}

	/**
	 * Check whether we have a specifically named header
	 *
	 * @param name String name of the header we want to check for
	 *
	 * @return boolean true if the named header is defined, else false
	 */
	public boolean hasHeader(String name) {
		return headers.containsKey(name);
	}

	/**
	 * Get the value of the specifically named header
	 *
	 * Note that casing is not enforced here, but all lower-case is recommended to avoid inconsistency.
	 *
	 * @param name String name of the header we want the value for
	 *
	 * @return String value of the header (may be null, especially if undefined)
	 */
	public String getHeader(String name) {
		return headers.get(name);
	}

	/**
	 * Get the entire collection of headers
	 *
	 * @return Header map
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * Get the value of the body
	 *
	 * @return String body (may be null!)
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Get the value of the method
	 *
	 * @return String method (may be null!)
	 */
	public String getRequestMethod() {
		return method;
	}

	/**
	 * Get the value of the URI
	 *
	 * @return String URI (may be null!)
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * Get the value of the status code
	 *
	 * @return Integer status code (may be null!)
	 */
	public Integer getCode() {
		return code;
	}
}
