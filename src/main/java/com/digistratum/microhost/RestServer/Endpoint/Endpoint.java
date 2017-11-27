package com.digistratum.microhost.RestServer.Endpoint;

import com.digistratum.microhost.RestServer.Http.RequestResponse.Request;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Response;

public interface Endpoint {

	/**
	 * Caller is handing the provided request to us for handling
	 *
	 * @param request RequestResponse instance with HTTP request info
	 *
	 * @return Response instance populated with the results of the endpoint's work
	 *
	 * @throws Exception for any errors
	 */
	public Response handle(Request request) throws Exception;
}
