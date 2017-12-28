package com.digistratum.microhost.Database.Mysql.Connection;

import java.sql.DriverManager;

import com.digistratum.microhost.Database.Exception.MHDatabaseException;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.log4j.Logger;

/**
 * todo Get rid of this factory if possible in favor of DI/interface model
 */
public class MySqlPoolableObjectFactory extends BasePoolableObjectFactory {
	final static Logger log = Logger.getLogger(MySqlPoolableObjectFactory.class);

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
	 * @throws MHDatabaseException for errors
	 */
	@Override
	public Object makeObject() throws MHDatabaseException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://" + host + ":" + port + "/"
					+ schema + "?autoReconnectForPools=true";
			return DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			String msg = "JDBC MySql Connection failed";
			log.error(msg, e);
			throw new MHDatabaseException(msg, e);
		}
	}
}
