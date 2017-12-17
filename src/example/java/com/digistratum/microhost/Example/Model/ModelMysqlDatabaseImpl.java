package com.digistratum.microhost.Example.Model;

import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnection;
import com.digistratum.microhost.Database.Mysql.Model.MysqlModelImpl;
import com.digistratum.microhost.Exception.MHException;

import java.util.List;

public class ModelMysqlDatabaseImpl extends MysqlModelImpl {
	public String Database;

	public ModelMysqlDatabaseImpl() {
		super();
	}

	/**
	 * Get a list of databases from our database server connection
	 *
	 * @return List of ModelMysqlDatabaseImpl's
	 *
	 * @throws MHException for any errors
	 */
	public List<ModelMysqlDatabaseImpl> getDatabases(MySqlConnection conn) throws MHException {
		return conn.query(
			ModelMysqlDatabaseImpl.class,
			"show databases;"
		);
	}
}
