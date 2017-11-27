package com.digistratum.microhost.RestServer.Http.RequestResponse;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Http.Headers.Headers;
import com.digistratum.microhost.RestServer.Http.Headers.HeadersImpl;

public class RequestImpl extends RequestResponseImpl implements Request {

	/**
	 * Bodyless RequestImpl Constructor
	 *
	 * @param requestMethod String representation of HTTP request method (e.g. get, post, delete, etc.)
	 * @param requestUri HTTP request URI string
	 * @param requestHeaders Headers implementation instance to hold HTTP request headers
	 */
	public RequestImpl(String requestMethod, String requestUri, Headers requestHeaders) throws MHException {
		this(requestMethod, requestUri, requestHeaders, null);
	}

	/**
	 * RequestImpl Constructor
	 *
	 * @param requestMethod String representation of HTTP request method (e.g. get, post, delete, etc.)
	 * @param requestUri HTTP request URI string
	 * @param requestHeaders Headers implementation instance to hold HTTP request headers
	 * @param requestBody HTTP request body (optional)
	 */
	public RequestImpl(String requestMethod, String requestUri, Headers requestHeaders, String requestBody) throws MHException {

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
		headers = (HeadersImpl) requestHeaders;

		// Grab the body (empty is valid fo requests such as with GET)
		body = requestBody;
	}

	@Override
	public String getRequestMethod() {
		return method;
	}
}
