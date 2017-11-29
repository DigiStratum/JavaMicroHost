package com.digistratum.microhost.Database.Mysql.Connection;

import com.digistratum.microhost.Config.Config;
import dagger.Module;
import dagger.Provides;

@Module
public class MySqlConnectionPoolModule {
	@Provides
	MySqlConnectionPool provideMySqlConnectionPool(Config config) {
		return new MySqlConnectionPoolImpl(config);
	}
}