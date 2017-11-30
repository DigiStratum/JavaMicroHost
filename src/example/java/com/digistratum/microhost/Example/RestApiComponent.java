package com.digistratum.microhost.Example;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPool;
import com.digistratum.microhost.RestServer.RestServer;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {Config.class, MySqlConnectionPool.class, RestServer.class})
public interface RestApiComponent {
	Config getConfig();
	MySqlConnectionPool getMySqlConnectionPool();
	RestServer getRestServer();
}
