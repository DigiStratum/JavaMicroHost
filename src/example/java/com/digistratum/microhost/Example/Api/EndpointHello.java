package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.RestServer.Endpoint.Endpoint;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Request;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Response;
import com.digistratum.microhost.RestServer.Http.RequestResponse.ResponseImpl;

public class EndpointHello implements Endpoint {
	public Response handle(Request request) throws MHException {
		return new ResponseImpl(
			200,
			"Hello, World!"
		);
	}
}
