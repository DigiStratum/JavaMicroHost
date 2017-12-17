package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Example.Service.ServiceExample;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.RestServer;
import com.digistratum.microhost.RestServer.RestServerSetterUpper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RestServerSetterUpperExampleImpl implements RestServerSetterUpper {
	protected Config config;
	protected ServiceExample service;

	@Inject
	public RestServerSetterUpperExampleImpl(Config config, ServiceExample service) {
		this.config = config;
		this.service = service;
	}

	@Override
	public void addContexts(RestServer restServer) throws MHException {

		// Add the /example context if enabled via configuration
		if ("on".equals(config.get("microhost.context.example", "off"))) {
			restServer.addControllerContext(
					new ControllerExampleImpl(service),
					"/example"
			);
		}
	}
}