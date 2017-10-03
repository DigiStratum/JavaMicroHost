package com.digistratum.microhost.Endpoint;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RequestResponse;

public interface Endpoint {
	public RequestResponse handle(RequestResponse request) throws MHException;
}
