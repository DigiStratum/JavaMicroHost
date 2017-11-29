package com.digistratum.microhost.RestServer;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Exception.MHException;
import dagger.Module;
import dagger.Provides;

@Module
public class RestServerModule {
	@Provides
	RestServer provideRestServer(Config config) {
		try {
			return new RestServerImpl(config);
		} catch (MHException e) {
			throw new RuntimeException("Failed to provide a RestServer!", e);
		}
	}
}
