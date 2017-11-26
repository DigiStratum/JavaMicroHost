package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionImpl;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPoolImpl;
import com.digistratum.microhost.Database.Mysql.Model.MysqlModelFactory;
import com.digistratum.microhost.Endpoint.Endpoint;
import com.digistratum.microhost.Example.Model.ModelMysqlDatabaseImpl;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.Http.RequestResponseImpl;
import com.google.gson.Gson;

import java.util.List;

/**
 * Get a list of mysql databases
 */
public class EndpointDatabases implements Endpoint {
	MySqlConnectionPoolImpl pool;
	MysqlModelFactory mysqlModelFactory;

	/**
	 * Constructor
	 *
	 * @param pool Dependency injected database connection pool
	 * @param mysqlModelFactory
	 */
	public EndpointDatabases(MySqlConnectionPoolImpl pool, MysqlModelFactory mysqlModelFactory) {
		this.pool = pool;
		this.mysqlModelFactory = mysqlModelFactory;
	}

	/**
	 *
	 * @param request
	 * @return
	 * @throws MHException
	 */
	@Override
	public RequestResponseImpl handle(RequestResponseImpl request) throws Exception {
		try (MySqlConnectionImpl conn = pool.getConnection()) {
			ModelMysqlDatabaseImpl modelMysqlDatabase = mysqlModelFactory.newModel(ModelMysqlDatabaseImpl.class, conn);
			List<ModelMysqlDatabaseImpl> databases = modelMysqlDatabase.getDatabases();
			Gson gson = new Gson();
			String responseBody = gson.toJson(databases);
			return new RequestResponseImpl(200, responseBody);
		}
	}
}
