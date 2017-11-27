package com.digistratum.microhost.Example.Api;

import com.digistratum.microhost.RestServer.Controller.ControllerBaseImpl;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPoolImpl;
import com.digistratum.microhost.Database.Mysql.Model.MysqlModelFactory;
import com.digistratum.microhost.Exception.MHException;

/**
 * ControllerBaseImplExample example controller
 */
public class ControllerBaseExampleImpl extends ControllerBaseImpl {

	public ControllerBaseExampleImpl(MySqlConnectionPoolImpl pool) throws MHException {
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
				new EndpointDatabases(pool, new MysqlModelFactory())
		);
	}
}
