package com.digistratum.microhost.RestServer.Endpoint;

import com.digistratum.microhost.RestServer.Http.Headers.HeadersImpl;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Request;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Response;
import com.digistratum.microhost.RestServer.Http.RequestResponse.ResponseImpl;

public class EndpointStatusImpl implements Endpoint {
	@Override
	public Response handle(Request request) throws Exception {
		HeadersImpl responseHeaders = new HeadersImpl();
		responseHeaders.set("content-type", "application/json");
		return new ResponseImpl(
				200,
				responseHeaders,
				"{\"status\": \"UP\"}"
		);
	}
}
