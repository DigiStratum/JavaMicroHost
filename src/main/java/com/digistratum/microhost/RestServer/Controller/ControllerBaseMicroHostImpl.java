package com.digistratum.microhost.RestServer.Controller;

import com.digistratum.microhost.RestServer.Endpoint.EndpointStatusImpl;

public class ControllerBaseMicroHostImpl extends ControllerBaseImpl {
	public ControllerBaseMicroHostImpl() {
		super();
	}

	@Override
	public void mapEndpoints() {
		// Respond to http://localhost:54321/microhost/status
		mapEndpoint(
				"get",
				"^/microhost/status$",
				new EndpointStatusImpl()
		);
	}
}
