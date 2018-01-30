package com.digistratum.microhost.App;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
/**
 * Dagger module for DI of all our Singletons
 */
public class MicroHostModule {
	@Provides
	@Singleton
	MicroHostApp provideMicroHostApp() {
		return new MicroHostApp();
	}
}
