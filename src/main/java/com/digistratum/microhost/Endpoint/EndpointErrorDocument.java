package com.digistratum.microhost.Endpoint;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RequestResponse;

import java.util.HashMap;
import java.util.Map;

public class EndpointErrorDocument implements Endpoint {
	Integer code;
	String message;

	/**
	 * Constructor
	 *
	 * @param code
	 * @param message
	 */
	public EndpointErrorDocument(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public RequestResponse handle(RequestResponse request) throws MHException {
		Map<String, String> responseHeaders = new HashMap<>();
		responseHeaders.put("content-type", "text/html");
		return new RequestResponse(
				code,
				responseHeaders,
				"<h1>" + message + "</h1>"
		);
	}
}
