package com.digistratum.microhost.Endpoint;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.Http.RequestResponseImpl;

import java.util.HashMap;
import java.util.Map;

public class EndpointErrorDocumentImpl implements Endpoint {
	Integer code;
	String message;

	/**
	 * Constructor
	 *
	 * @param code
	 * @param message
	 */
	public EndpointErrorDocumentImpl(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public RequestResponseImpl handle(RequestResponseImpl request) throws MHException {
		Map<String, String> responseHeaders = new HashMap<>();
		responseHeaders.put("content-type", "text/html");
		return new RequestResponseImpl(
				code,
				responseHeaders,
				"<h1>" + message + "</h1>"
		);
	}
}
