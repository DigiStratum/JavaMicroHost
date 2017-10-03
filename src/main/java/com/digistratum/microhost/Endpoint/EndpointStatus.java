package com.digistratum.microhost.Endpoint;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RequestResponse;

import java.util.HashMap;
import java.util.Map;

public class EndpointStatus implements Endpoint {
	@Override
	public RequestResponse handle(RequestResponse request) throws MHException {
		Map<String, String> responseHeaders = new HashMap<>();
		responseHeaders.put("content-type", "application/json");
		return new RequestResponse(200,responseHeaders,"{\"status\": \"UP\"}");
	}
}
