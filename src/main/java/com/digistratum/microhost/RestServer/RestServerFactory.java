package com.digistratum.microhost.RestServer;

import com.digistratum.microhost.Config.ConfigImpl;

import java.io.IOException;

/**
 * @todo - get rid of this... obviously has  no value with no implementation
 */
public class RestServerFactory {
	public RestServerImpl createServer(ConfigImpl configImpl) throws IOException {
		return new RestServerImpl(configImpl);
	}
}
