package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.RestServer.Controller.ControllerBaseImpl;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPoolImpl;
import com.digistratum.microhost.Database.Mysql.Model.MySqlModelFactory;
import com.digistratum.microhost.Exception.MHException;

import javax.inject.Inject;

/**
 * ControllerBaseImplExample example controller
 */
public class ControllerExampleImpl extends ControllerBaseImpl {

	@Inject
	public ControllerExampleImpl(MySqlConnectionPoolImpl pool, MySqlModelFactory mySqlModelFactory) throws MHException {
		super();

		// Respond to http://localhost:54321/hello
		this.mapEndpoint(
				"get",
				"^/example/hello$",
				new EndpointHello()
		);

		// Respond to http://localhost:54321/databases
		this.mapEndpoint(
				"get",
				"^/example/databases$",
				new EndpointDatabases(pool, mySqlModelFactory)
		);
	}
}
