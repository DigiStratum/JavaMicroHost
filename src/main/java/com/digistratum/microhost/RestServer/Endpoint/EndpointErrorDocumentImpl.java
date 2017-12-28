package com.digistratum.microhost.RestServer.Endpoint;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Request;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Response;

public class EndpointErrorDocumentImpl extends EndpointImpl {
	Integer code;
	String message;

	/**
	 * Constructor
	 *
	 * @param code Integer HTTP Status code we want to return
	 * @param message String message that we want to output into the error doc
	 */
	public EndpointErrorDocumentImpl(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public Response handle(Request request) throws MHException {
		return htmlResponse("<h1>" + message + "</h1>", code);
	}
}
