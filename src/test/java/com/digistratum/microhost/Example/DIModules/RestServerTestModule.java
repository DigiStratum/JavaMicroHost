package com.digistratum.microhost.Example.DIModules;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.RestServer.RestServer;
import com.digistratum.microhost.RestServer.RestServerImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;

import static org.mockito.Mockito.mock;

@Module(complete = false, library = true)
public class RestServerTestModule {
	@Provides
	RestServer provideRestServer(Config config) {
		System.out.println("Providing mock RestServer");
		return mock(RestServerImpl.class);
	}
}
