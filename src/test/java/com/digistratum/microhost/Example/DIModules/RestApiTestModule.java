package com.digistratum.microhost.Example.DIModules;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Config.ConfigImpl;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPool;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPoolImpl;
import com.digistratum.microhost.RestServer.RestServer;
import com.digistratum.microhost.RestServer.RestServerImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

import static org.mockito.Mockito.mock;

@Module
public class RestApiTestModule {
	@Provides
	@Singleton
	static Config provideConfig() {
		System.out.println("providing mock config to someone!");
		return mock(ConfigImpl.class);
	}

	@Provides
	@Singleton
	static MySqlConnectionPool provideMySqlConnectionPool() {
		System.out.println("Providing mock MySqlConnectionPool");
		return mock(MySqlConnectionPoolImpl.class);
	}

	@Provides
	@Singleton
	static RestServer provideRestServer() {
		System.out.println("Providing mock RestServer");
		return mock(RestServerImpl.class);
	}
}
