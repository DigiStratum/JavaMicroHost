package com.digistratum.microhost.RestServer.Endpoint;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Request;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Response;
import com.digistratum.microhost.RestServer.Http.RequestResponse.ResponseImpl;
import com.google.gson.Gson;

abstract public class EndpointImpl implements Endpoint {
	protected Gson gson;

	/**
	 * Constructor
	 */
	protected EndpointImpl() {
		gson = new Gson();
	}

	@Override
	/**
	 * Default implementation
	 */
	public Response handle(Request request) throws MHException {
		return null;
	}

	public Response jsonResponse200(Object data) throws MHException {
		String responseBody = gson.toJson(data);
		return new ResponseImpl(
				200,
				responseBody
		);
	}
}
