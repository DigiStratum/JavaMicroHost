package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.Config.Config;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPool;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPoolImpl;
import com.digistratum.microhost.Database.Mysql.Model.MySqlModelFactory;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.RestServer;
import com.digistratum.microhost.RestServer.RestServerSetterUpper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RestServerSetterUpperExampleImpl implements RestServerSetterUpper {
	protected Config config;
	protected MySqlConnectionPool pool;
	protected MySqlModelFactory mySqlModelFactory;

	@Inject
	public RestServerSetterUpperExampleImpl(Config config, MySqlConnectionPool pool, MySqlModelFactory mySqlModelFactory) {
		this.config = config;
		this.pool = pool;
	}

	@Override
	public void addContexts(RestServer restServer) throws MHException {

		// Add the /example context if enabled via configuration
		if ("on".equals(config.get("microhost.context.example", "off"))) {
			restServer.addControllerContext(
					new ControllerExampleImpl((MySqlConnectionPoolImpl) pool, mySqlModelFactory),
					"/example"
			);
		}
	}
}
