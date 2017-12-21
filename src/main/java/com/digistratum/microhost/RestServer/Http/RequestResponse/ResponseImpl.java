package com.digistratum.microhost.RestServer.Http.RequestResponse;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Http.Headers.Headers;
import com.digistratum.microhost.RestServer.Http.Headers.HeadersImpl;
import com.digistratum.microhost.RestServer.Http.HttpSpec;
import org.apache.log4j.Logger;

public class ResponseImpl extends RequestResponseImpl implements Response {
	final static Logger log = Logger.getLogger(ResponseImpl.class);

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
	 *
	 * @throws MHException if any parameters are unacceptable
	 */
	public ResponseImpl(int responseCode, Headers responseHeaders, String responseBody) {

		// Check that the code is one which we support (but use it anyway!)
		if (null == HttpSpec.getStatusDescription(responseCode)) {
			log.warn("Unsupported response code: " + responseCode);
		}
		code = responseCode;

		// This is a RESPONSE
		type = RequestResponseImpl.Type.response;

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
