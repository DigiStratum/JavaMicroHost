package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionImpl;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPoolImpl;
import com.digistratum.microhost.Database.Mysql.Model.MysqlModelFactory;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Endpoint.Endpoint;
import com.digistratum.microhost.Example.Model.ModelMysqlDatabaseImpl;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Request;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Response;
import com.digistratum.microhost.RestServer.Http.RequestResponse.ResponseImpl;
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

	@Override
	public Response handle(Request request) throws MHException {
		try (MySqlConnectionImpl conn = pool.getConnection()) {
			ModelMysqlDatabaseImpl modelMysqlDatabase = mysqlModelFactory.newModel(ModelMysqlDatabaseImpl.class, conn);
			List<ModelMysqlDatabaseImpl> databases = modelMysqlDatabase.getDatabases();
			Gson gson = new Gson();
			String responseBody = gson.toJson(databases);
			return new ResponseImpl(
					200,
					responseBody
			);
		}
	}
}
