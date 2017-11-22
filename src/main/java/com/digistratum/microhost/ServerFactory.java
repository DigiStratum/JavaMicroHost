package com.digistratum.microhost;

import java.io.IOException;

public class ServerFactory {
	public Server createServer(MHConfig config) throws IOException {
		return new Server(config);
	}
}
