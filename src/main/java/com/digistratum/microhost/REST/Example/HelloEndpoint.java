package com.digistratum.microhost.REST.Example;

import com.digistratum.microhost.Endpoint.Endpoint;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RequestResponse;

public class HelloEndpoint implements Endpoint {
	public RequestResponse handle(RequestResponse request) throws MHException {
		return new RequestResponse(200,"Hello, World!");
	}
}
