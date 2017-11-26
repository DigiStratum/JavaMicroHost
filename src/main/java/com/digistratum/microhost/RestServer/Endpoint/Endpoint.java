package com.digistratum.microhost.RestServer.Endpoint;

import com.digistratum.microhost.RestServer.Http.RequestResponse.RequestResponse;

public interface Endpoint {

	/**
	 * Caller is handing the provided request to us for handling
	 *
	 * @param request RequestResponse instance with HTTP request info
	 *
	 * @throws Exception for any errors
	 */
	public RequestResponse handle(RequestResponse request) throws Exception;
}
