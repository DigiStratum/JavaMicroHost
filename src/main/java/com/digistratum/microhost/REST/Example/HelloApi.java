package com.digistratum.microhost.REST.Example;

import com.digistratum.microhost.Controller.Controller;

/**
 * HelloApi example controller
 */
public class HelloApi extends Controller {
	public HelloApi() {
		super();
		// Respond to http://localhost:54321/hello
		this.setRequestHandler("get", "^/hello$", new HelloEndpoint());
	}
}
