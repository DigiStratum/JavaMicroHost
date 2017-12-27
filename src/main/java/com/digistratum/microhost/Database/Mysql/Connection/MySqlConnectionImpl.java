package com.digistratum.microhost.Database.Mysql.Connection;

import com.digistratum.microhost.Exception.MHDatabaseException;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ref: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
 * ref: https://docs.oracle.com/javase/tutorial/jdbc/basics/retrieving.html
 * ref: https://www.mkyong.com/jdbc/jdbc-preparestatement-example-select-list-of-the-records/
 */
public class MySqlConnectionImpl implements MySqlConnection {
	final static Logger log = Logger.getLogger(MySqlConnectionImpl.class);

	private class MHPreparedStatement {
		int hash;
		int paramCount;
		String sql;
		PreparedStatement ps;
	}

	protected Connection conn;
	protected MySqlConnectionPoolImpl pool;
	protected Boolean inTransaction = false;

	protected List<MHPreparedStatement> preparedStatements = new ArrayList<>();

	/**
	 * Constructor
	 *
	 * @param pool MySqlConnectionImpl pool to which we should return our connection
	 * @param conn Connection JDBC connection that we will wrap
	 *
	 * @throws MHDatabaseException on errors
	 */
	public MySqlConnectionImpl(MySqlConnectionPoolImpl pool, Connection conn) throws MHDatabaseException {
		this.pool = pool;
		this.conn = conn;
	}

	@Override
	public void query(String sql) throws MHDatabaseException {
		try (
				Statement st = conn.createStatement()
		) {
			st.executeQuery(sql);
		} catch (SQLException e) {
			String msg = "query failed: " + sql;
			log.error(msg, e);
			throw new MHDatabaseException(msg, e);
		}
	}

	/**
	 * A simple, quick hash for the supplied string
	 *
	 * We'll use this to make a quick-reference for prepared statements, etc.
	 *
	 * ref: https://stackoverflow.com/questions/2624192/good-hash-function-for-strings
	 *
	 * @param str Input string that we want to hash
	 *
	 * @return integer hash of the supplied string
	 */
	protected int simpleHash(String str) {
		int hash = 7;
		for (int i = 0; i < str.length(); i++) {
			hash = hash * 31 + str.charAt(i);
		}
		return hash;
	}

	/**
	 * Find a prepared statement by hash in our set of prepared statements
	 *
	 * @param hash Int hash ID for the prepared statement's SQL that we are after
	 *
	 * @return MHPreparedStatement instance if found, else null
	 */
	private MHPreparedStatement findPreparedStatement(int hash) {
		for (MHPreparedStatement mhps : preparedStatements) {
			if (mhps.hash == hash) return mhps;
		}
		return null;
	}

	/**
	 * Determine whether we have a prepared statement with the matching hash in our set of prepared statements
	 *
	 * @param hash Int hash ID for the prepared statement's SQL that we are after
	 *
	 * @return boolean true if we do have one, else false
	 */
	private Boolean containsPreparedStatement(int hash) {
		return (null != findPreparedStatement(hash));
	}

	@Override
	public Integer prepare(String sql, int paramCount) throws MHDatabaseException {

		// The hash for this SQL query is...
		MHPreparedStatement mhps = new MHPreparedStatement();
		mhps.hash = simpleHash(sql);
		mhps.paramCount = paramCount;
		mhps.sql = sql;

		// If it is already in our set of prepared statements, then nothing more is needed
		if (containsPreparedStatement(mhps.hash)) return mhps.hash;

		// Prepare a statement with the id "ps" + hash
		try {
			mhps.ps = conn.prepareStatement(sql);
		} catch (SQLException e) {
			throw new MHDatabaseException("Failed to prepare statement for SQL: " + sql, e);
		}

		// Add this has to the collection of prepared statement ID's so we can clean up later
		preparedStatements.add(mhps);

		return mhps.hash;
	}

	@Override
	public void deallocatePrepare(Integer hash) throws MHDatabaseException {

		// If it is not in our set of prepared statements, then nothing is needed
		MHPreparedStatement mhps = findPreparedStatement(hash);
		if (null == mhps) return;

		// Deallocate prepare a statement with the id "ps" + hash
		try {
			mhps.ps.close();
		} catch (SQLException e) {
			throw new MHDatabaseException("Failed to close prepared statement", e);
		}

		// Knock this one out of our collection of prepared statements
		preparedStatements.remove(mhps);
	}

	/**
	 * If there are any prepared statements, deallocate each one
	 *
	 * This ensures that there is no leftover cruft connected with this MySQL connection
	 * when it is returned to the connection pool for some other thread/process to receive.
	 *
	 * @throws MHDatabaseException on errors
	 */
	protected void deallocateAllPreparedStatements() throws MHDatabaseException {

		// If there aren't any, then there's nothing to do
		if (preparedStatements.isEmpty()) return;

		// For each prepared statement ID that we have...
		for (MHPreparedStatement mhps : preparedStatements) {

			// Deallocate it
			deallocatePrepare(mhps.hash);
		}
	}

	@Override
	public <T> List<T> queryPrepared(Class<T> type, Integer sqlHash, Object... params) throws MHDatabaseException {
		MHPreparedStatement mhps = findPreparedStatement(sqlHash);
		if (null == mhps) {
			throw new MHDatabaseException("No such prepared statement for ID: " + sqlHash);
		}
		// ref: https://dev.mysql.com/doc/refman/5.7/en/execute.html
		// ref: http://www.java2s.com/Code/Java/Language-Basics/JavavarargsIteratingOverVariableLengthArgumentLists.htm
		// Add all the params as individual parameters here... (and properly escape them!)
		if (params.length != mhps.paramCount) {
			throw new MHDatabaseException("Prepared statement (" + sqlHash + ") requires " + mhps.paramCount + " params, " + params.length + " provided.");
		}

		// Iterate over the params supplied
		for (int i = 0; i < params.length; i++) {
			Object param = params[i];

			try {
				// ref: https://coderanch.com/t/404450/java/type-object
				// TODO: Add proper support for setNull(), setBytes(), set***Stream(), setURL(), addBatch()
				// Note: skip setObject(), setRef(), setArray(), setRowId(), setNString(), setN***Stream(), setSQLXML()
				// ref: https://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html#setInt(int,%20int)
				if (param instanceof Boolean) {
					mhps.ps.setBoolean(i + 1, (Boolean) param);
				}
				else if (param instanceof Byte) {
					mhps.ps.setByte(i + 1, (Byte) param);
				}
				else if (param instanceof Short) {
					mhps.ps.setShort(i + 1, (Short) param);
				}
				else if (param instanceof Integer) {
					mhps.ps.setInt(i + 1, (Integer) param);
				}
				else if (param instanceof Long) {
					mhps.ps.setLong(i + 1, (Long) param);
				}
				else if (param instanceof Float) {
					mhps.ps.setFloat(i + 1, (Float) param);
				}
				else if (param instanceof Double) {
					mhps.ps.setDouble(i + 1, (Double) param);
				}
				else if (param instanceof BigDecimal) {
					mhps.ps.setBigDecimal(i + 1, (BigDecimal) param);
				}
				else if (param instanceof String) {
					mhps.ps.setString(i + 1, (String) param);
				}
				else if (param instanceof Date) {
					mhps.ps.setDate(i + 1, (Date) param);
				}
				else if (param instanceof Time) {
					mhps.ps.setTime(i + 1, (Time) param);
				}
				else if (param instanceof Timestamp) {
					mhps.ps.setTimestamp(i + 1, (Timestamp) param);
				}
				else if (param instanceof Blob) {
					mhps.ps.setBlob(i + 1, (Blob) param);
				}
				else if (param instanceof Clob) {
					mhps.ps.setClob(i + 1, (Clob) param);
				}
			} catch (SQLException e) {
				String msg = "Prepared statement set***() param failed for param " + i;
				log.error(msg, e);
				throw new MHDatabaseException(msg, e);
			}
		}

		try (
				ResultSet rs = mhps.ps.executeQuery()
		) {
			return getQueryResultSet(type, rs);
		} catch (SQLException e) {
			String msg = "Prepared statement executeQuery failed: " + mhps.sql;
			log.error(msg, e);
			throw new MHDatabaseException(msg, e);
		}
	}

	@Override
	public <T> List<T> queryPrepared(Class<T> type, String sql, Object... params) throws MHDatabaseException {
		// The hash for this SQL query is...
		Integer sqlHash = simpleHash(sql);
		return queryPrepared(type, sqlHash, params);
	}

	@Override
	public <T> List<T> query(Class<T> type, String sql) throws MHDatabaseException {
		try (
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql)
		) {
			return getQueryResultSet(type, rs);
		} catch (SQLException e) {
			String msg = "query failed: " + sql;
			log.error(msg, e);
			throw new MHDatabaseException(msg, e);
		}
	}

	/**
	 * Convert the supplied MySQL query Result Set into a List of T objects
	 *
	 * This does all our ORM-style mapping from MySQL columns to POJO properties.
	 *
	 * @param type The generic type for the POJO matching our result set
	 * @param rs MySQL result set to convert
	 *
	 * @return List<T> collection of objects converted from the result set
	 *
	 * @throws MHDatabaseException for any errors
	 */
	protected <T> List<T> getQueryResultSet(Class<T> type, ResultSet rs) throws MHDatabaseException {
		List<T> results = new ArrayList<>();
		try {
			// Advance to the first result row, and return the empty set if there isn't one
			if (! rs.next()) return results;

			// Make sure the type that we are working with has a field for every result column
			// Todo: consider caching the map and result of checks with a hash of the query
			// Reflection ref: http://tutorials.jenkov.com/java-reflection/fields.html
			ResultSetMetaData rsmd = rs.getMetaData();
			String[] columnNames = getResultSetColumnNames(rsmd);
			String[] columnTypes = getResultSetColumnTypes(rsmd);
			for (int col = 0; col < columnNames.length; col++) {
				Field field = type.getField(columnNames[col]);

				// Do we have this field in our target?
				if (null == field) {
					String msg = "Result contains a column name mismatch on: '" + columnNames[col] + "'";
					log.error(msg);
					throw new Exception(msg);
				}

				// Does this field's type match the map?
				Class fieldType = field.getType();
				if (! fieldType.getSimpleName().equals(columnTypes[col])) {
					String msg = "Result contains a column type mismatch on: '" + columnNames[col] +
							"'; class: '" + fieldType.getName() +
							"'; result: '" + columnTypes[col] + "'";
					log.error(msg);
					throw new Exception(msg);
				}
			}

			// Capture data from each result row, starting with this first one we already have
			do {
				T result = type.newInstance();

				// Set the properties of result which match the map
				for (int col = 0; col < columnNames.length; col++) {
					Field field = type.getField(columnNames[col]);
					if ("String".equals(columnTypes[col])) {
						field.set(result, rs.getString(1 + col));
					} else if ("Boolean".equals(columnTypes[col])) {
						field.set(result, rs.getBoolean(1 + col));
					} else if ("Integer".equals(columnTypes[col])) {
						field.set(result, rs.getInt(1 + col));
					} else if ("Double".equals(columnTypes[col])) {
						field.set(result, rs.getDouble(1 + col));
					}
				}
				results.add(result);

			} while (rs.next());

			return results;
		} catch (Exception e) {
			String msg = "Instantiation failed: " + type.getName();
			log.error(msg, e);
			throw new MHDatabaseException(msg, e);
		}
	}

	/**
	 * Get a set of column names for a given ResultSet(MetaData)
	 *
	 * No accommodation/mercy is provided for queries returning multiple columns with the same name.
	 *
	 * @param rsmd ResultSetMetaData that we want to examine
	 *
	 * @return String[] of column names
	 *
	 * @throws MHDatabaseException on errors
	 */
	protected String[] getResultSetColumnNames(ResultSetMetaData rsmd) throws MHDatabaseException {
		int num;
		try {
			num = rsmd.getColumnCount();
			//log.debug("Database result set column count = " + num);
			String[] columnNames = new String[num];
			for (int col = 0; col < num; col++) {
				// Column indicators start numbering from 1, but array index is 0-based!
				columnNames[col] = rsmd.getColumnLabel(1 + col);
			}
			return columnNames;
		} catch (SQLException e) {
			String msg = "Query result processing failed";
			log.error(msg, e);
			throw new MHDatabaseException(msg, e);
		}
	}

	/**
	 * Get a set of column names for a given ResultSet(MetaData)
	 *
	 * No accommodation/mercy is provided for queries returning multiple columns with the same name.
	 *
	 * @param rsmd ResultSetMetaData that we want to examine
	 *
	 * @return Map of column name -> type
	 *
	 * @throws MHDatabaseException on errors
	 */
	protected String[] getResultSetColumnTypes(ResultSetMetaData rsmd) throws MHDatabaseException {
		int num;
		try {
			num = rsmd.getColumnCount();
			String[] columnTypes = new String[num];
			for (int col = 0; col < num; col++) {
				// Column indicators start numbering from 1...
				String name = rsmd.getColumnLabel(1 + col);
				String type;
				switch (rsmd.getColumnType(1 + col)) {

					// Capture as Integers
					case Types.NULL:
					case Types.BINARY:
					case Types.DECIMAL:
					case Types.INTEGER:
					case Types.SMALLINT:
					case Types.TINYINT:
					case Types.BIGINT: type = "Integer"; break;

					// Capture as Boolean
					case Types.BOOLEAN: type = "Boolean"; break;

					// Capture as Doubles
					case Types.FLOAT:
					case Types.REAL:
					case Types.DOUBLE: type = "Double"; break;

					// Capture as Strings...
					case Types.TIME:
					case Types.TIME_WITH_TIMEZONE:
					case Types.TIMESTAMP:
					case Types.TIMESTAMP_WITH_TIMEZONE:
					case Types.DATE:
					case Types.NVARCHAR:
					case Types.VARCHAR:
					case Types.VARBINARY:
					case Types.LONGNVARCHAR:
					case Types.LONGVARCHAR:
					case Types.LONGVARBINARY:
					case Types.BLOB:
					case Types.CLOB:
					case Types.CHAR: type = "String"; break;

					default:
						log.warn("Dropping column '" + name + "' with unsupported data type: '" + rsmd.getColumnTypeName(col) + "'");
						continue;
				}
				columnTypes[col] = type;
			}
			return columnTypes;
		} catch (SQLException e) {
			String msg = "Query result processing failed";
			log.error(msg, e);
			throw new MHDatabaseException(msg, e);
		}
	}

	/**
	 * Close out this connection (AutoCloseable!)
	 *
	 * We do so by returning our database connection back to the pool from whence it came. If we are in
	 * the middle of a transaction, rollback to get out of it before returning the connection to the pool
	 */
	@Override
	public void close() {
		try {
			// If we are in a transaction, roll back
			if (inTransaction) rollback();

			// If there are any prepared statements defined, deallocate them
			deallocateAllPreparedStatements();

			// Return the connection to the pool
			pool.returnConnection(conn);
		} catch (MHDatabaseException e) {
			log.error("Failed to return DB connection to pool", e);
			// null out our working bits to guarantee nobody can abuse us
			conn = null;
			pool = null;
			inTransaction = false;
		}
	}

	@Override
	public void start() throws MHDatabaseException {
		if (inTransaction) {
			String msg = "Transaction already started, cannot start again";
			log.error(msg);
			throw new MHDatabaseException(msg);
		}

		// Make sure we got a successful transaction start...
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			String msg = "Failed to set auto commit to start transaction";
			log.error(msg);
			throw new MHDatabaseException(msg, e);
		}

		// ... THEN set our state to match
		inTransaction = true;
	}

	@Override
	public void commit() throws MHDatabaseException {
		if (! inTransaction) {
			String msg = "Transaction not started, cannot commit";
			log.error(msg);
			throw new MHDatabaseException(msg);
		}

		// Clear our state so that no matter what happens next we can start a new transaction again...
		inTransaction = false;

		// ... THEN try to commit!
		try {
			conn.commit();
		} catch (SQLException e) {
			String msg = "Failed to commit transaction";
			log.error(msg);
			throw new MHDatabaseException(msg, e);
		}

		// Restore auto-commit
		try {
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			String msg = "Failed to set auto commit to commit transaction";
			log.error(msg);
			throw new MHDatabaseException(msg, e);
		}
	}

	@Override
	public void rollback() throws MHDatabaseException {
		if (! inTransaction) {
			String msg = "Transaction not started, cannot roll back";
			log.error(msg);
			throw new MHDatabaseException(msg);
		}

		// Clear our state so that no matter what happens next we can start a new transaction again...
		inTransaction = false;

		// ... THEN try to roll back!
		try {
			conn.rollback();
		} catch (SQLException e) {
			String msg = "Failed to roll back transaction";
			log.error(msg);
			throw new MHDatabaseException(msg, e);
		}

		// Restore auto-commit
		try {
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			String msg = "Failed to set auto commit to roll back transaction";
			log.error(msg);
			throw new MHDatabaseException(msg, e);
		}
	}
}
