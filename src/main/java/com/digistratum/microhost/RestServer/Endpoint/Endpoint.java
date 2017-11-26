package com.digistratum.microhost.RestServer.Endpoint;

import com.digistratum.microhost.RestServer.Http.RequestResponseImpl;

public interface Endpoint {
	public RequestResponseImpl handle(RequestResponseImpl request) throws Exception;
}
