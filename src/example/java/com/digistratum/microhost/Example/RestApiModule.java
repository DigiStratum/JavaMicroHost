package com.digistratum.microhost.Example;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Config.ConfigImpl;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPoolModule;
import com.digistratum.microhost.Example.RestApi;
import com.digistratum.microhost.RestServer.RestServerModule;
import dagger.Module;
import dagger.Provides;

@Module(
		injects = RestApi.class,
		includes = {
				RestServerModule.class,
				MySqlConnectionPoolModule.class
		}
)
public class RestApiModule {
	@Provides
	Config provideConfig() {
		// Read in configuration properties
		String userDir = System.getProperty("user.dir");
		String propsFile = userDir + "/RestApi.properties";
		return new ConfigImpl(propsFile);
	}
}
