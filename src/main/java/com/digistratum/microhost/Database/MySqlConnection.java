package com.digistratum.microhost.Database;

import com.digistratum.microhost.Exception.MHDatabaseException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ref: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
 */
public class MySqlConnection implements AutoCloseable {
	final static Logger log = Logger.getLogger(MySqlConnection.class);

	protected Connection conn;
	protected MySqlConnectionPool pool;

	/**
	 * Constructor
	 *
	 * @param pool MySqlConnection pool to which we should return our connection
	 * @param conn Connection JDBC connection that we will wrap
	 *
	 * @throws MHDatabaseException
	 */
	public MySqlConnection(MySqlConnectionPool pool, Connection conn) throws MHDatabaseException {
		this.pool = pool;
		this.conn = conn;
	}

	/**
	 * Execute some SQL as a prepared statement
	 *
	 * @param sql
	 */
	public void query(String sql) throws MHDatabaseException {
		try {
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery(sql);
			while (res.next()) {

			}
		} catch (SQLException e) {
			String msg = "query failed: " + sql;
			log.error(msg, e);
			throw new MHDatabaseException(msg, e);
		}
	}

	/**
	 * Close out this connection (AutoCloseable!)
	 *
	 * We do so by returning our database connection back to the pool from whence it came.
	 */
	@Override
	public void close() {
		try {
			pool.returnConnection(conn);
		} catch (MHDatabaseException e) {
			log.error("Failed to return DB connection to pool", e);
			// null out our working bits to guarantee nobody can abuse us
			conn = null;
			pool = null;
		}
	}
}
