package com.digistratum.microhost.Database.Mysql.Connection;

import com.digistratum.microhost.Exception.MHDatabaseException;

import java.util.List;

public interface MySqlConnection extends AutoCloseable {

	/**
	 * Execute some SQL as a normal statement
	 *
	 * This is for queries which we don't expect or want any results from.
	 *
	 * @param sql String SQL statement to execute
	 *
	 * @throws MHDatabaseException on errors
	 */
	public void query(String sql) throws MHDatabaseException;

	/**
	 * Execute some SQL as a normal statement
	 *
	 * ref: http://www.java2novice.com/java-generics/simple-class/
	 *
	 * @param sql String SQL statement to execute
	 *
	 * @return List<T> Generic typed list result
	 *
	 * @throws MHDatabaseException on errors
	 */
	public <T> List<T> query(Class<T> type, String sql) throws MHDatabaseException;

	/**
	 * Start a transaction
	 *
	 * ref: https://dev.mysql.com/doc/refman/5.7/en/commit.html
	 *
	 * @throws MHDatabaseException on errors
	 */
	public void start() throws MHDatabaseException;

	/**
	 * Commit the transaction
	 *
	 * @throws MHDatabaseException on errors
	 */
	public void commit() throws MHDatabaseException;

	/**
	 * Roll back (stop/abort) the transaction
	 *
	 * @throws MHDatabaseException on errors
	 */
	public void rollback() throws MHDatabaseException;
}
