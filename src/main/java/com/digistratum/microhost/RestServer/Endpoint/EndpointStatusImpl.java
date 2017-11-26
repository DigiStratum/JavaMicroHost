package com.digistratum.microhost.RestServer.Endpoint;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Http.RequestResponse.RequestResponse;
import com.digistratum.microhost.RestServer.Http.RequestResponse.RequestResponseImpl;

import java.util.HashMap;
import java.util.Map;

public class EndpointStatusImpl implements Endpoint {
	@Override
	public RequestResponse handle(RequestResponse request) throws MHException {
		Map<String, String> responseHeaders = new HashMap<>();
		responseHeaders.put("content-type", "application/json");
		return new RequestResponseImpl(200,responseHeaders,"{\"status\": \"UP\"}");
	}
}
