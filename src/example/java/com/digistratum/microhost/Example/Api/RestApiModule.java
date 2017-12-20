package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Config.ConfigImpl;
import com.digistratum.microhost.Database.ModelFactory;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPool;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPoolImpl;
import com.digistratum.microhost.Database.Mysql.Model.MySqlModelFactory;
import com.digistratum.microhost.Example.Service.ServiceExample;
import com.digistratum.microhost.Process.MHRunnable;
import com.digistratum.microhost.RestServer.*;

import com.digistratum.microhost.RestServer.Http.HttpServerFactory;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
/**
 * Dagger module for DI of all our Singleton
 */
public class RestApiModule {

	@Provides
	@Singleton
	Config provideConfig() {
		//System.out.println("providing real config to someone!");
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
	RestServer provideRestServer(Config config, HttpServerFactory serverFactory, RestServerSetterUpper setterUpper) {
		RestServer restServer = new RestServerImpl(config, serverFactory);
		setterUpper.addContexts(restServer);
		return restServer;
	}

	@Provides
	@Singleton
	RestServerSetterUpper provideRestServerSetterUpper(Config config, ServiceExample service) {
		return new RestServerSetterUpperExampleImpl(config, service);
	}

	@Provides
	@Singleton
	ModelFactory provideModelFactory() {
		return new MySqlModelFactory();
	}

	@Provides
	@Singleton
	MHRunnable provideRestApi(RestServer server) {
		return new RestApiImpl(server);
	}

	@Provides
	@Singleton
	ServiceExample provideService(MySqlConnectionPool connectionPool, ModelFactory modelFactory) {
		return new ServiceExample(connectionPool, modelFactory);
	}

	@Provides
	@Singleton
	HttpServerFactory provideHttpServerFactory() {
		return new HttpServerFactory();
	}
}
