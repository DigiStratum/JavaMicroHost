package com.digistratum.microhost.RestServer.Controller;

import com.digistratum.Database.Mysql.Connection.MySqlConnectionImpl;
import com.digistratum.Database.Mysql.Connection.MySqlConnectionPoolImpl;
import com.digistratum.Database.Mysql.Model.MySqlModelFactory;
import com.digistratum.Database.Exception.MHDatabaseException;
import com.sun.net.httpserver.HttpExchange;

import javax.inject.Inject;
import java.io.IOException;

public class ControllerMySqlImpl extends ControllerBaseImpl {
	MySqlConnectionPoolImpl pool;
	MySqlModelFactory mySqlModelFactory;

	@Inject
	public ControllerMySqlImpl(MySqlConnectionPoolImpl pool, MySqlModelFactory mySqlModelFactory) {
		super();
		this.pool = pool;
		this.mySqlModelFactory = mySqlModelFactory;
	}

	@Override
	public void mapEndpoints() {
	}

	@Override
	public void handle(HttpExchange t) throws IOException {
		try (MySqlConnectionImpl conn = pool.getConnection()) {
			// FIXME: Pass conn into the endpoint doing the actual handling...
			super.handle(t);
		} catch (MHDatabaseException e) {
			throw new IOException("Failed to get MySQL database connection from pool", e);
		}
	}
}
