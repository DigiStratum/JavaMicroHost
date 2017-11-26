package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.Endpoint.Endpoint;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.Http.RequestResponseImpl;

public class EndpointHello implements Endpoint {
	public RequestResponseImpl handle(RequestResponseImpl request) throws MHException {
		return new RequestResponseImpl(200,"Hello, World!");
	}
}
