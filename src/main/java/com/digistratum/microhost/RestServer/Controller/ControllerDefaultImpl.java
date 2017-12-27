package com.digistratum.microhost.RestServer.Controller;

import com.digistratum.microhost.RestServer.Endpoint.EndpointErrorDocumentImpl;
import com.digistratum.microhost.RestServer.Http.HttpSpec;

/**
 * ControllerDefaultImpl controller for default responses from the server base
 */
public class ControllerDefaultImpl extends ControllerBaseImpl {

	public ControllerDefaultImpl() {
		super();
	}

	public void mapEndpoints() {
		// Respond to anything
		String[] methods = {"get","post","put","delete","head","options","patch"};
		this.mapEndpoint(
				methods,
				".*",
				new EndpointErrorDocumentImpl(
						HttpSpec.HTTP_STATUS_404_NOT_FOUND,
						HttpSpec.getStatusDescription(HttpSpec.HTTP_STATUS_404_NOT_FOUND)
				)
		);
	}
}