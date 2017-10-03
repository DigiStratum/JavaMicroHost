package com.digistratum.microhost.Controller;

import com.digistratum.microhost.Endpoint.EndpointStatus;

public class ControllerMicroHost extends Controller {
	public ControllerMicroHost() {
		super();
		// Respond to http://localhost:54321/microhost/status
		this.setRequestHandler("get", "^/microhost/status$", new EndpointStatus());
	}
}
