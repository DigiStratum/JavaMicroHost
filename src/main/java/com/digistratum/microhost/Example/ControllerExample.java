package com.digistratum.microhost.Example;

import com.digistratum.microhost.Controller.Controller;
import com.digistratum.microhost.Database.Mysql.MySqlConnectionPool;
import com.digistratum.microhost.Database.Mysql.MysqlModelFactory;
import com.digistratum.microhost.Exception.MHException;

/**
 * ControllerExample example controller
 */
public class ControllerExample extends Controller {

	public ControllerExample(MySqlConnectionPool pool) throws MHException {
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
