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
	public Headers getHeaders();

	/**
	 * Get the value of the body
	 *
	 * @return String body (may be null!)
	 */
	public String getBody();


	/**
	 * Get the value of the URI
	 *
	 * @return String URI (may be null!)
	 */
	public String getUri();
}
