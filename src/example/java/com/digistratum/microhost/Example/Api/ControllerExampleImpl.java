package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.Example.Service.ServiceExample;
import com.digistratum.microhost.RestServer.Controller.ControllerBaseImpl;

import javax.inject.Inject;

/**
 * ControllerBaseImplExample example controller
 */
class ControllerExampleImpl extends ControllerBaseImpl {

	@Inject
	ControllerExampleImpl(ServiceExample service) {
		super();

		// Respond to http://localhost:54321/hello
		this.mapEndpoint(
				"get",
				"^/example/hello(.*?)$",
				new EndpointHello()
		);

		// Respond to http://localhost:54321/databases
		this.mapEndpoint(
				"get",
				"^/example/databases$",
				new EndpointDatabases(service)
		);
	}
}
