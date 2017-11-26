package com.digistratum.microhost.RestServer.Endpoint;

import com.digistratum.microhost.RestServer.Http.RequestResponse;

public interface Endpoint {
	public RequestResponse handle(RequestResponse request) throws Exception;
}
