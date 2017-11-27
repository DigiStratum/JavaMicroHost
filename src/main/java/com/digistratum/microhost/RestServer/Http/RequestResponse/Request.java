package com.digistratum.microhost.RestServer.Http.RequestResponse;

import com.digistratum.microhost.RestServer.Http.Headers.Headers;

/**
 * An immutable representation of the request which has come in to our service
 */
public interface Request extends RequestResponse {

	/**
	 * Get the value of the method
	 *
	 * @return String method (may be null!)
	 */
	public String getRequestMethod();
}
