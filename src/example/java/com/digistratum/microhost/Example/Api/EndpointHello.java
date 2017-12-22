package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Endpoint.EndpointImpl;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Request;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Response;
import org.apache.log4j.Logger;

import java.util.List;

public class EndpointHello extends EndpointImpl {
	final static Logger log = Logger.getLogger(EndpointHello.class);

	@Override
	public Response handle(Request request) throws MHException {
		//log.debug("REQUEST URI: " + request.getUri());
		//log.debug("REQUEST QueryString: " + request.getQueryString());
		String name = "World";
		if (request.hasQueryParam("name")) {
			List<String> paramValue = request.getQueryParam("name");
			if (null != paramValue) {
				if (! paramValue.isEmpty()) {
					name = paramValue.get(0);
				}
			}
		}
		return htmlResponse200(
				"<html><body>" +
				"Hello, " + name + "!<br/>" +
				"<form><input type=\"text\" name=\"name\" value=\""+ name + "\"/>" +
				"<input type=\"submit\" value=\"Say Hello!\"/><br/>" +
				"</body></html>"
		);
	}
}
