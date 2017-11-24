package com.digistratum.microhost.Example.Model;

import com.digistratum.microhost.Database.Mysql.MySqlConnection;
import com.digistratum.microhost.Database.Mysql.MysqlModel;

import java.util.List;

public class ModelMysqlDatabase extends MysqlModel {
	public String Database;

	/**
	 *
	 * @param conn
	 */
	public ModelMysqlDatabase(MySqlConnection conn) {
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
	public List<ModelMysqlDatabase> getDatabases() throws Exception {
		return conn.query(
			ModelMysqlDatabase.class,
			"show databases;"
		);
	}
}
