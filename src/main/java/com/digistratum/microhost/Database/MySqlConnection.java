package com.digistratum.microhost.Database;

import com.digistratum.microhost.Exception.MHDatabaseException;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ref: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
 * ref: https://docs.oracle.com/javase/tutorial/jdbc/basics/retrieving.html
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
	 * ref: http://www.java2novice.com/java-generics/simple-class/
	 *
	 * @param sql
	 */
	public <T> List<T> query(Class<T> type, String sql) throws Exception {
		try (
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
		) {
			List<T> results = new ArrayList<>();

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
				if (! fieldType.getName().equals(columnTypes[col])) {
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
						field.set(result, rs.getString(col));
					} else if ("Boolean".equals(columnTypes[col])) {
						field.set(result, rs.getBoolean(col));
					} else if ("Integer".equals(columnTypes[col])) {
						field.set(result, rs.getInt(col));
					} else if ("Double".equals(columnTypes[col])) {
						field.set(result, rs.getDouble(col));
					}
				}
				results.add(result);

			} while (rs.next());

			return results;
		} catch (SQLException e) {
			String msg = "query failed: " + sql;
			log.error(msg, e);
			throw new MHDatabaseException(msg, e);
		} catch (IllegalAccessException|InstantiationException e) {
			String msg = "Generic instantiation failed: " + type.getName();
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
	 */
	protected String[] getResultSetColumnNames(ResultSetMetaData rsmd) throws SQLException {
		int num = rsmd.getColumnCount();
		String[] columnNames = new String[num];
		for (int col = 0; col < num; col++) {
			columnNames[col] = rsmd.getColumnLabel(col);
		}
		return columnNames;
	}

	/**
	 * Get a set of column names for a given ResultSet(MetaData)
	 *
	 * No accommodation/mercy is provided for queries returning multiple columns with the same name.
	 *
	 * @param rsmd ResultSetMetaData that we want to examine
	 *
	 * @return Map of column name -> type
	 */
	protected String[] getResultSetColumnTypes(ResultSetMetaData rsmd) throws SQLException {
		int num = rsmd.getColumnCount();
		String[] columnTypes = new String[num];
		for (int col = 0; col < num; col++) {
			String name = rsmd.getColumnLabel(col);
			String type = null;
			switch (rsmd.getColumnType(col)) {

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
