package com.digistratum.microhost.Example;

import com.digistratum.microhost.Database.MySqlConnection;
import com.digistratum.microhost.Database.MySqlConnectionPool;
import com.digistratum.microhost.Endpoint.Endpoint;
import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RequestResponse;

import java.sql.Connection;

/**
 * Get a list of mysql databases
 */
public class EndpointDatabases implements Endpoint {
	MySqlConnectionPool pool;

	/**
	 * Constructor
	 *
	 * @param pool Dependency injected database connection pool
	 */
	public EndpointDatabases(MySqlConnectionPool pool) {
		this.pool = pool;
	}

	/**
	 *
	 * @param request
	 * @return
	 * @throws MHException
	 */
	@Override
	public RequestResponse handle(RequestResponse request) throws MHException {
		try (MySqlConnection conn = new MySqlConnection(pool)) {
			return new RequestResponse(200, "Hello, World!");
		}
	}
}
