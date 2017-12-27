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
	void query(String sql) throws MHDatabaseException;

	/**
	 * Execute some SQL as a normal statement
	 *
	 * ref: http://www.java2novice.com/java-generics/simple-class/
	 *
	 * @param type Object type which the result set must map to
	 * @param sql String SQL statement to execute
	 *
	 * @return List<T> Generic typed list result
	 *
	 * @throws MHDatabaseException on errors
	 */
	<T> List<T> query(Class<T> type, String sql) throws MHDatabaseException;

	/**
	 * Prepare a statement with the supplied SQL
	 *
	 * Note that this primitive implementation cannot tolerate the use of single-quotes within the supplied SQL, so
	 * we will need some way to convert or escape those in the future. For now this character will not work here and
	 * will likely manifest itself as a SQL syntax error.
	 *
	 * @param sql String SQL we want to use for our prepared statement
	 * @param paramCount int count of params required to be passed into this sql query
	 *
	 * @return Integer prepared statement ID
	 *
	 * @throws MHDatabaseException on errors
	 */
	Integer prepare(String sql, int paramCount) throws MHDatabaseException;

	/**
	 * Deallocate a previously prepared statement
	 *
	 * Note that this is not necessary for most simple cases as this will automatically be deallocated when the
	 * connection is closed, but it would be useful if there is a lot of prepared statement manipulation taking place
	 * within a single connection and we wish to clean up after ourselves on-the-fly to prevent accumulation of garbage.
	 *
	 * @param sqlHash Integer ID of the prepared statement to deallocate (as returned from prepare() method
	 *
	 * @throws MHDatabaseException on errors
	 */
	void deallocatePrepare(Integer sqlHash) throws MHDatabaseException;

	/**
	 * Execute a prepared statement query (by prepared statement ID)
	 *
	 * @param type Object type which the result set must map to
	 * @param sqlHash Integer ID of the prepared statement to execute (as returned from prepare() method
	 * @param params Object Varargs to use for prepared statement query parameters
	 *
	 * @return List<T> Generic typed list result
	 *
	 * @throws MHDatabaseException on errors
	 */
	<T> List<T> queryPrepared(Class<T> type, Integer sqlHash, Object... params) throws MHDatabaseException;

	/**
	 * Execure a prepared statement query (by SQL)
	 *
	 * This is a convenience method so that the caller needn't keep track of the prepared statement ID's, but
	 * the SQL supplied must resolve to an already prepared statement - if it doesn't, then this is an error.
	 *
	 * @param type Object type which the result set must map to
	 * @param sql String SQL statement to execute
	 * @param params Object Varargs to use for prepared statement query parameters
	 *
	 * @return List<T> Generic typed list result
	 *
	 * @throws MHDatabaseException
	 */
	<T> List<T> queryPrepared(Class<T> type, String sql, Object... params) throws MHDatabaseException;

	/**
	 * Start a transaction
	 *
	 * ref: https://dev.mysql.com/doc/refman/5.7/en/commit.html
	 * ref: https://docs.oracle.com/javase/tutorial/jdbc/basics/transactions.html
	 *
	 * @throws MHDatabaseException on errors
	 */
	void start() throws MHDatabaseException;

	/**
	 * Commit the transaction
	 *
	 * @throws MHDatabaseException on errors
	 */
	void commit() throws MHDatabaseException;

	/**
	 * Roll back (stop/abort) the transaction
	 *
	 * @throws MHDatabaseException on errors
	 */
	void rollback() throws MHDatabaseException;
}
