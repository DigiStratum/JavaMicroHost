package com.digistratum.microhost.Example.Config;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Config.ConfigImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class ConfigModule {

	@Provides
	Config provideConfig() {
		// Read in configuration properties
		String userDir = System.getProperty("user.dir");
		String propsFile = userDir + "/MicroHost.properties";
		return new ConfigImpl(propsFile);
	}
}
