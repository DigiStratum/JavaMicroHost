package com.digistratum.microhost.RestServer;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPool;
import com.digistratum.microhost.Example.Api.RestApiModule;
import com.digistratum.microhost.Process.MHRunnable;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {RestApiModule.class})
public interface RestApiComponent {
	Config getConfig();
	MHRunnable getRestApi();
}
