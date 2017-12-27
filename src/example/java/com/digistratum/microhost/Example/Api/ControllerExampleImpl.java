package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.Example.Service.ServiceExample;
import com.digistratum.microhost.RestServer.Controller.ControllerBaseImpl;

import javax.inject.Inject;

/**
 * ControllerBaseImplExample example controller
 */
class ControllerExampleImpl extends ControllerBaseImpl {
	ServiceExample service;

	@Inject
	ControllerExampleImpl(ServiceExample service) {
		super();
		this.service = service;
	}

	@Override
	public void mapEndpoints() {
		// Respond to http://localhost:54321/hello
		this.mapEndpoint(
				"get",
				"/hello",
				new EndpointHello()
		);

		// Respond to http://localhost:54321/databases
		this.mapEndpoint(
				"get",
				"/databases",
				new EndpointDatabases(service)
		);
	}
}
