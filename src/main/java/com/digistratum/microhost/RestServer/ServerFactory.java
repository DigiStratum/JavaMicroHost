package com.digistratum.microhost.RestServer;

import com.digistratum.microhost.Config.ConfigImpl;

import java.io.IOException;

public class ServerFactory {
	public RestServerImpl createServer(ConfigImpl configImpl) throws IOException {
		return new RestServerImpl(configImpl);
	}
}
