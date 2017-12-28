package com.digistratum.microhost.RestServer.Http.RequestResponse;

import com.digistratum.microhost.RestServer.Http.Headers.Headers;

/**
 * Capabilities common to both requests and responses
 */
public interface RequestResponse {
	/**
	 * Get the entire collection of headers
	 *
	 * @return Headers implementation instance
	 */
	Headers getHeaders();

	/**
	 * Get the value of the body
	 *
	 * @return String body (may be null!)
	 */
	String getBody();


	/**
	 * Get the value of the URI
	 *
	 * @return String URI (may be null!)
	 */
	String getUri();
}
