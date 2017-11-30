package com.digistratum.microhost.RestServer;

import com.digistratum.microhost.Config.Config;
import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public class RestServerModule {
	@Provides
	RestServer provideRestServer(Config config) {
		return new RestServerImpl(config);
	}
}
