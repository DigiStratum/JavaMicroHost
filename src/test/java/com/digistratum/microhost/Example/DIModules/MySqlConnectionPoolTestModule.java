package com.digistratum.microhost.Example.DIModules;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPool;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPoolImpl;
import dagger.Module;
import dagger.Provides;

import javax.inject.Inject;

import static org.mockito.Mockito.mock;

@Module(complete = false, library = true)
public class MySqlConnectionPoolTestModule {
	@Provides
	MySqlConnectionPool provideMySqlConnectionPool(Config config) {
		System.out.println("Providing mock MySqlConnectionPool");
		return mock(MySqlConnectionPoolImpl.class);
	}
}
