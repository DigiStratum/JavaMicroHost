package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Example.Service.ServiceExample;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.RestServer;
import com.digistratum.microhost.RestServer.RestServerSetterUpper;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RestServerSetterUpperExampleImpl implements RestServerSetterUpper {
	protected final static Logger log = Logger.getLogger(RestServerSetterUpperExampleImpl.class);
	protected Config config;
	protected ServiceExample service;

	@Inject
	public RestServerSetterUpperExampleImpl(Config config, ServiceExample service) {
		this.config = config;
		this.service = service;
	}

	@Override
	public void addContexts(RestServer restServer) {

		// Add the /example context if enabled via configuration
		if ("on".equals(config.get("microhost.context.example", "off"))) {
			try {
				restServer.addControllerContext(
						new ControllerExampleImpl(service),
						"/example"
				);
			} catch (MHException e) {
				// Swallow it - no exceptions in our DI layer
			}
		}
		else {
			log.info("Skipping context: /example");
		}
	}
}
