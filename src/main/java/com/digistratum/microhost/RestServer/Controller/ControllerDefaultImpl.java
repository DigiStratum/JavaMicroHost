package com.digistratum.microhost.RestServer.Controller;

import com.digistratum.microhost.RestServer.Endpoint.EndpointDefault;

/**
 * ControllerDefaultImpl controller for default responses from the server base
 */
public class ControllerDefaultImpl extends ControllerBaseImpl {

	public ControllerDefaultImpl() {
		super();

		// Respond to anything
		String[] methods = {"get","post","put","delete","head","options","patch"};
		this.mapEndpoint(
				methods,
				".*",
				new EndpointDefault()
		);
	}
}