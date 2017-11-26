package com.digistratum.microhost.Endpoint;

import com.digistratum.microhost.Http.RequestResponseImpl;

public interface Endpoint {
	public RequestResponseImpl handle(RequestResponseImpl request) throws Exception;
}
