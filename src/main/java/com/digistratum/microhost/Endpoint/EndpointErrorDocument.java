package com.digistratum.microhost.Endpoint;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RequestResponse;

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
		return new RequestResponse(code, "<h1>" + message + "</h1>");
	}
}
