package com.digistratum.microhost.Http;

import com.digistratum.microhost.Exception.MHException;

import java.util.HashMap;
import java.util.Map;

public class RequestResponseImpl implements RequestResponse {
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
	public RequestResponseImpl(String requestMethod, String requestUri, Map<String, String> requestHeaders) throws MHException {
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
	public RequestResponseImpl(String requestMethod, String requestUri, Map<String, String> requestHeaders, String requestBody) throws MHException {

		// This is a REQUEST
		type = Type.request;

		// Check the request method
		if ((null == requestMethod) || requestMethod.isEmpty()) {
			throw new MHException("RequestResponseImpl() - supplied request method was empty");
		}
		// TODO: Check that the request method is one which we support
		method = requestMethod;

		// Check the URI
		if ((null == requestUri) || requestUri.isEmpty()) {
			throw new MHException("RequestResponseImpl() - supplied URI was empty");
		}
		uri = requestUri;

		// Check the headers
		if (null == requestHeaders) {
			throw new MHException("RequestResponseImpl() - headers were not supplied");
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
	public RequestResponseImpl(int responseCode, Map<String, String> responseHeaders) throws MHException {
		this(responseCode, responseHeaders, null);
	}

	/**
	 * Headerless Response Constructor
	 *
	 * @param responseCode Integer HTTP response code
	 * @param responseBody HTTP response body (optional)
	 */
	public RequestResponseImpl(int responseCode, String responseBody) throws MHException {
		this(responseCode, new HashMap<String, String>(), responseBody);
	}

	/**
	 * Headerless, Bodyless Response Constructor (code-only)
	 *
	 * @param responseCode Integer HTTP response code
	 */
	public RequestResponseImpl(int responseCode) throws MHException {
		this(responseCode, new HashMap<String, String>(), null);
	}

	/**
	 * Response Constructor
	 *
	 * @param responseCode Integer HTTP response code
	 * @param responseHeaders Map<name, value> HTTP response headers
	 * @param responseBody HTTP response body (optional)
	 */
	public RequestResponseImpl(int responseCode, Map<String, String> responseHeaders, String responseBody) throws MHException {

		// This is a RESPONSE
		type = Type.response;

		// TODO: check that the code is one which we support
		code = responseCode;

		// Check the headers
		if (null == responseHeaders) {
			throw new MHException("RequestResponseImpl() - headers were not supplied");
		}
		headers = responseHeaders;

		// Grab the body (empty is valid fo requests such as with POST)
		body = responseBody;
	}

	@Override
	public boolean isRequest() {
		return (type == Type.request);
	}

	@Override
	public boolean isResponse() {
		return (type == Type.response);
	}

	@Override
	public boolean hasHeader(String name) {
		return headers.containsKey(name);
	}

	@Override
	public String getHeader(String name) {
		return headers.get(name);
	}

	@Override
	public Map<String, String> getHeaders() {
		return headers;
	}

	@Override
	public String getBody() {
		return body;
	}

	@Override
	public String getRequestMethod() {
		return method;
	}

	@Override
	public String getUri() {
		return uri;
	}

	@Override
	public Integer getCode() {
		return code;
	}
}
