package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Endpoint.EndpointImpl;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Request;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Response;

public class EndpointHello extends EndpointImpl {
	@Override
	public Response handle(Request request) throws MHException {
		String name = "World";
		return htmlResponse200(
				"<html><body>" +
				"Hello, " + name + "!<br/>" +
				"<form><input type=\"text\" name=\"name\" value=\""+ name + "\"/>" +
				"<input type=\"submit\" name=\"Say Hello!\"/><br/>" +
				"</body></html>"
		);
	}
}
