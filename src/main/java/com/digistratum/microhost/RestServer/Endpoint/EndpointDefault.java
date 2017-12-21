package com.digistratum.microhost.RestServer.Endpoint;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Request;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Response;

public class EndpointDefault extends EndpointImpl {
	@Override
	public Response handle(Request request) throws MHException {
		return textResponse404();
	}
}
