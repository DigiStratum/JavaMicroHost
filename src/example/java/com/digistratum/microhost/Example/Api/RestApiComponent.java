package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPool;
import com.digistratum.microhost.Example.Model.RestApi;
import com.digistratum.microhost.RestServer.RestServer;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {RestApiModule.class})
public interface RestApiComponent {
	Config getConfig();
	MySqlConnectionPool getMySqlConnectionPool();
	RestServer getRestServer();
	RestApi getRestApi();
}
