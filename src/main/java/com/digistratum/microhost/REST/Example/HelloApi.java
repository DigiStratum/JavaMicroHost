package com.digistratum.microhost.REST.Example;

import com.digistratum.microhost.Controller.Controller;
import com.digistratum.microhost.Exception.MHException;

/**
 * HelloApi example controller
 */
public class HelloApi extends Controller {
	public HelloApi() throws MHException {
		super();
		// Respond to http://localhost:54321/hello
		this.mapEndpoint("get", "^/hello$", new HelloEndpoint());
	}
}
