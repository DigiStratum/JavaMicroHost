package com.digistratum.microhost.Database;

import java.sql.DriverManager;
import org.apache.commons.pool.BasePoolableObjectFactory;

public class MySqlPoolableObjectFactory extends BasePoolableObjectFactory {
	private String host;
	private int port;
	private String schema;
	private String user;
	private String password;

	/**
	 * Parametric Constructor
	 *
	 * @param host String hostname of the MySQL server
	 * @param port int port of the MySQL server
	 * @param schema String name of the database we want to connect to
	 * @param user String username MySQL credential
	 * @param password String password MySQL credential
	 */
	public MySqlPoolableObjectFactory(String host, int port, String schema, String user, String password) {
		this.host = host;
		this.port = port;
		this.schema = schema;
		this.user = user;
		this.password = password;
	}

	/**
	 * Object maker for MySQL database connections
	 *
	 * @return Object representation of a MySQL database connection
	 *
	 * @throws Exception
	 */
	@Override
	public Object makeObject() throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String url = "jdbc:mysql://" + host + ":" + port + "/"
				+ schema + "?autoReconnectForPools=true";
		return DriverManager.getConnection(url, user, password);
	}
}
