package com.digistratum.microhost.RestServer.Endpoint;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Http.Headers.HeadersImpl;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Request;
import com.digistratum.microhost.RestServer.Http.RequestResponse.RequestResponseImpl;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Response;
import com.digistratum.microhost.RestServer.Http.RequestResponse.ResponseImpl;

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
	public Response handle(Request request) throws MHException {
		HeadersImpl responseHeaders = new HeadersImpl();
		responseHeaders.set("content-type", "text/html");
		return new ResponseImpl(
				code,
				responseHeaders,
				"<h1>" + message + "</h1>"
		);
	}
}
