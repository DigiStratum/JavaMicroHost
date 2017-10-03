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

	public RequestResponse handle(RequestResponse request) throws MHException {
		Map<String, String> responseHeaders = new HashMap<>();
		// TODO: check if we really need this content-length header... may be duplicate from default output in MHHttpHandler
		responseHeaders.put("content-length", Integer.toString(message.length()));
		return new RequestResponse(code, responseHeaders, message);
	}
}
