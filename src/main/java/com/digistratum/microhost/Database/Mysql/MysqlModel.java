package com.digistratum.microhost.Database.Mysql;

import com.digistratum.microhost.Database.DbModel;

import java.util.List;

/**
 * A Mysql DbModel
 */
public class MysqlModel extends DbModel {
	protected MySqlConnection conn;

	/**
	 *
	 * @param conn
	 */
	public MysqlModel(MySqlConnection conn) {
		this.conn =  conn;
	}
}
