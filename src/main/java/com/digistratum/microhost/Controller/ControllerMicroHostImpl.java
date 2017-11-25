package com.digistratum.microhost.Controller;

import com.digistratum.microhost.Endpoint.EndpointStatus;
import com.digistratum.microhost.Exception.MHException;

public class ControllerMicroHostImpl extends ControllerBaseImpl {
	public ControllerMicroHostImpl() throws MHException {
		super();
		// Respond to http://localhost:54321/microhost/status
		mapEndpoint(
				"get",
				"^/microhost/status$",
				new EndpointStatus()
		);
	}
}
