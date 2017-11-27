package com.digistratum.microhost.Database.Mysql.Connection;

import com.digistratum.microhost.Exception.MHDatabaseException;

import java.util.List;

public interface MySqlConnection {
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
}
