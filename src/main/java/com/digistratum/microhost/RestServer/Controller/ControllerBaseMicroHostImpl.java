package com.digistratum.microhost.RestServer.Controller;

import com.digistratum.microhost.RestServer.Endpoint.EndpointStatusImpl;
import com.digistratum.microhost.Exception.MHException;

public class ControllerBaseMicroHostImpl extends ControllerBaseImpl {
	public ControllerBaseMicroHostImpl() {
		super();
		// Respond to http://localhost:54321/microhost/status
		mapEndpoint(
				"get",
				"^/microhost/status$",
				new EndpointStatusImpl()
		);
	}
}
