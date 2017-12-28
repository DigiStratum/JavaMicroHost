package com.digistratum.microhost.Database.Mysql.Connection;

import com.digistratum.microhost.Database.Exception.MHDatabaseException;

import java.sql.Connection;

public interface MySqlConnectionPool {
	/**
	 * Initialize the connection pool
	 *
	 * todo - refactor this to get rid of an init(0 stage/call if possible
	 */
	void init();

	/**
	 * Get a wrapped connection from the pool
	 *
	 * @return MySqlConnection instance wrapping a Connection from the pool
	 *
	 * @throws MHDatabaseException if something goes sideways...
	 */
	MySqlConnection getConnection() throws MHDatabaseException;

	/**
	 * Return a connection to the pool
	 *
	 * @param conn Connection instance to be returned to the pool
	 *
	 * todo Make this symetrical so that the MySqlConnection which the consumer GETs can be PUT back here
	 *
	 * @throws MHDatabaseException if something goes sideways...
	 */
	void returnConnection(Connection conn) throws MHDatabaseException;
}
