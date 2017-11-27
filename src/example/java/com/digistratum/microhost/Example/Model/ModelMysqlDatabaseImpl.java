package com.digistratum.microhost.Example.Model;

import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionImpl;
import com.digistratum.microhost.Database.Mysql.Model.MysqlModelImpl;
import com.digistratum.microhost.Exception.MHException;

import java.util.List;

public class ModelMysqlDatabaseImpl extends MysqlModelImpl {
	public String Database;

	/**
	 *
	 * @param conn
	 */
	public ModelMysqlDatabaseImpl(MySqlConnectionImpl conn) {
		super(conn);
	}

	/*
	show databases;
+--------------------+
| Database           |
+--------------------+
| information_schema |
| microhost_example  |
| mysql              |
| performance_schema |
| sys                |
+--------------------+
	 */

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public List<ModelMysqlDatabaseImpl> getDatabases() throws MHException {
		return conn.query(
			ModelMysqlDatabaseImpl.class,
			"show databases;"
		);
	}
}
