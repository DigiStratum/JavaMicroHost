package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionImpl;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPoolImpl;
import com.digistratum.microhost.Database.Mysql.Model.MySqlModelFactory;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Endpoint.Endpoint;
import com.digistratum.microhost.Example.Model.ModelMysqlDatabaseImpl;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Request;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Response;
import com.digistratum.microhost.RestServer.Http.RequestResponse.ResponseImpl;
import com.google.gson.Gson;

import javax.inject.Inject;
import java.util.List;

/**
 * Get a list of mysql databases
 *
 * @todo Move boilerplate mysql operations to abstract base class if possible
 */
public class EndpointDatabases implements Endpoint {
	MySqlConnectionPoolImpl pool;
	MySqlModelFactory mySqlModelFactory;

	/**
	 * Constructor
	 *
	 * @param pool Dependency injected database connection pool
	 * @param mySqlModelFactory
	 */
	@Inject
	public EndpointDatabases(MySqlConnectionPoolImpl pool, MySqlModelFactory mySqlModelFactory) {
		this.pool = pool;
		this.mySqlModelFactory = mySqlModelFactory;
	}

	@Override
	public Response handle(Request request) throws MHException {
		try (MySqlConnectionImpl conn = pool.getConnection()) {
			ModelMysqlDatabaseImpl modelMysqlDatabase = mySqlModelFactory.newModel(ModelMysqlDatabaseImpl.class);
			List<ModelMysqlDatabaseImpl> databases = modelMysqlDatabase.getDatabases(conn);
			Gson gson = new Gson();
			String responseBody = gson.toJson(databases);
			return new ResponseImpl(
					200,
					responseBody
			);
		}
	}
}
