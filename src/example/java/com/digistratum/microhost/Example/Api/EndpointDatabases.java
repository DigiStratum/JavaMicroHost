package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.Example.Service.ServiceExample;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Endpoint.EndpointImpl;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Request;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Response;

import javax.inject.Inject;

/**
 * Get a list of mysql databases
 */
class EndpointDatabases extends EndpointImpl {
	protected ServiceExample service;

	/**
	 * Constructor
	 */
	@Inject
	EndpointDatabases(ServiceExample service) {
		this.service = service;
	}

	@Override
	public Response handle(Request request) throws MHException {
		return jsonResponse200(service.getDatabases());
	}
}
