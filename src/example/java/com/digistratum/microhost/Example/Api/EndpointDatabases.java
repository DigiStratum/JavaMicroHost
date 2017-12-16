package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnection;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPool;
import com.digistratum.microhost.Database.Mysql.Model.MySqlModelFactory;
import com.digistratum.microhost.Example.Service.ServiceExample;
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
 */
class EndpointDatabases implements Endpoint {
	protected ServiceExample service;
	protected Gson gson;

	/**
	 * Constructor
	 */
	@Inject
	EndpointDatabases(ServiceExample service) {
		this.service = service;
		gson = new Gson();
	}

	@Override
	public Response handle(Request request) throws MHException {
		List<ModelMysqlDatabaseImpl> databases = service.getDatabases();
		String responseBody = gson.toJson(databases);
		return new ResponseImpl(
				200,
				responseBody
		);
	}
}
