package com.digistratum.microhost.Endpoint;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.Http.RequestResponseImpl;

import java.util.HashMap;
import java.util.Map;

public class EndpointStatusImpl implements Endpoint {
	@Override
	public RequestResponseImpl handle(RequestResponseImpl request) throws MHException {
		Map<String, String> responseHeaders = new HashMap<>();
		responseHeaders.put("content-type", "application/json");
		return new RequestResponseImpl(200,responseHeaders,"{\"status\": \"UP\"}");
	}
}
