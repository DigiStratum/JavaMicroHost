package com.digistratum.microhost.RestServer;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Exception.MHException;
import dagger.Module;
import dagger.Provides;

@Module
public class RestServerModule {
	@Provides
	RestServer provideRestServer(Config config) throws MHException {
		return new RestServerImpl(config);
	}
}
