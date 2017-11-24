package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.Database.Mysql.MySqlConnection;
import com.digistratum.microhost.Database.Mysql.MySqlConnectionPool;
import com.digistratum.microhost.Database.Mysql.MysqlModelFactory;
import com.digistratum.microhost.Endpoint.Endpoint;
import com.digistratum.microhost.Example.Model.ModelMysqlDatabase;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RequestResponse;
import com.google.gson.Gson;

import java.util.List;

/**
 * Get a list of mysql databases
 */
public class EndpointDatabases implements Endpoint {
	MySqlConnectionPool pool;
	MysqlModelFactory mysqlModelFactory;

	/**
	 * Constructor
	 *
	 * @param pool Dependency injected database connection pool
	 * @param mysqlModelFactory
	 */
	public EndpointDatabases(MySqlConnectionPool pool, MysqlModelFactory mysqlModelFactory) {
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
	public RequestResponse handle(RequestResponse request) throws Exception {
		try (MySqlConnection conn = pool.getConnection()) {
			ModelMysqlDatabase modelMysqlDatabase = mysqlModelFactory.newModel(ModelMysqlDatabase.class, conn);
			List<ModelMysqlDatabase> databases = modelMysqlDatabase.getDatabases();
			Gson gson = new Gson();
			String responseBody = gson.toJson(databases);
			return new RequestResponse(200, responseBody);
		}
	}
}
