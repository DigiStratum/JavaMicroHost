package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Config.ConfigImpl;
import com.digistratum.microhost.Database.ModelFactory;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPool;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPoolImpl;
import com.digistratum.microhost.Database.Mysql.Model.MySqlModelFactory;
import com.digistratum.microhost.Example.Service.ServiceExample;
import com.digistratum.microhost.RestServer.*;

import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class RestApiModule {

	@Provides
	@Singleton
	Config provideConfig() {
		System.out.println("providing real config to someone!");
		// Read in configuration properties
		String userDir = System.getProperty("user.dir");
		String propsFile = userDir + "/RestApi.properties";
		return new ConfigImpl(propsFile);
	}

	@Provides
	@Singleton
	MySqlConnectionPool provideMySqlConnectionPool(Config config) {
		return new MySqlConnectionPoolImpl(config);
	}

	@Provides
	@Singleton
	RestServer provideRestServer(Config config) {
		return new RestServerImpl(config);
	}

	@Provides
	@Singleton
	RestServerSetterUpper provideRestServersetterUpper(Config config, ServiceExample service) {
		return new RestServerSetterUpperExampleImpl(config, service);
	}

	@Provides
	@Singleton
	ModelFactory provideModelFactory() {
		return new MySqlModelFactory();
	}

	@Provides
	@Singleton
	RestApi provideRestApi(Config config, MySqlConnectionPool pool, RestServer server, RestServerSetterUpper setterUpper) {
		return new RestApiImpl(config, pool, server, setterUpper);
	}

	@Provides
	@Singleton
	ServiceExample provideService(MySqlConnectionPool connectionPool, ModelFactory modelFactory) {
		return new ServiceExample(connectionPool, modelFactory);
	}
}
