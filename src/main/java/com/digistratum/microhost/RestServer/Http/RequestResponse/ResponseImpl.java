package com.digistratum.microhost.RestServer.Http.RequestResponse;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Http.Headers.Headers;
import com.digistratum.microhost.RestServer.Http.Headers.HeadersImpl;

public class ResponseImpl extends RequestResponseImpl implements Response {

	/**
	 * Bodyless Response Constructor
	 *
	 * @param responseCode Integer HTTP response code
	 * @param responseHeaders Headers implementation instance to hold HTTP response headers
	 */
	public ResponseImpl(int responseCode, Headers responseHeaders) throws MHException {
		this(responseCode, responseHeaders, null);
	}

	/**
	 * Headerless Response Constructor
	 *
	 * @param responseCode Integer HTTP response code
	 * @param responseBody HTTP response body (optional)
	 */
	public ResponseImpl(int responseCode, String responseBody) throws MHException {
		this(responseCode, null, responseBody);
	}

	/**
	 * Headerless, Bodyless Response Constructor (code-only)
	 *
	 * @param responseCode Integer HTTP response code
	 */
	public ResponseImpl(int responseCode) throws MHException {
		this(responseCode, null, null);
	}

	/**
	 * Response Constructor
	 *
	 * @param responseCode Integer HTTP response code
	 * @param responseHeaders Map<name, value> HTTP response headers
	 * @param responseBody HTTP response body (optional)
	 */
	public ResponseImpl(int responseCode, Headers responseHeaders, String responseBody) {

		// This is a RESPONSE
		type = RequestResponseImpl.Type.response;

		// TODO: check that the code is one which we support
		code = responseCode;

		// Check the headers
		headers = (null == responseHeaders) ? new HeadersImpl() : (HeadersImpl) responseHeaders;

		// Grab the body (empty is valid fo requests such as with POST)
		body = responseBody;
	}

	@Override
	public Integer getCode() {
		return code;
	}
}
