package com.digistratum.microhost.RestServer.Controller;

import com.digistratum.microhost.RestServer.Endpoint.EndpointDefault;

/**
 * ControllerDefaultImpl controller for default responses from the server base
 */
public class ControllerDefaultImpl extends ControllerBaseImpl {

	public ControllerDefaultImpl() {
		super();

		// Respond to anything
		//  TODO: Make a mapEndpointMultiMethod() which takes a list of methods for the same endpoint
		this.mapEndpoint(
				"get",
				".*",
				new EndpointDefault()
		);

		this.mapEndpoint(
				"put",
				".*",
				new EndpointDefault()
		);

		this.mapEndpoint(
				"post",
				".*",
				new EndpointDefault()
		);

		this.mapEndpoint(
				"delete",
				".*",
				new EndpointDefault()
		);

		this.mapEndpoint(
				"head",
				".*",
				new EndpointDefault()
		);

		this.mapEndpoint(
				"options",
				".*",
				new EndpointDefault()
		);

		this.mapEndpoint(
				"patch",
				".*",
				new EndpointDefault()
		);
	}
}