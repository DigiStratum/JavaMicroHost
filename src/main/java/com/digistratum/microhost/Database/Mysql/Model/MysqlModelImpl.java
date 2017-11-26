package com.digistratum.microhost.Database.Mysql.Model;

import com.digistratum.microhost.Database.Model;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionImpl;

/**
 * A Mysql Model base class
 */
public abstract class MysqlModelImpl implements Model {
	protected MySqlConnectionImpl conn;

	/**
	 *
	 * @param conn
	 */
	public MysqlModelImpl(MySqlConnectionImpl conn) {
		this.conn =  conn;
	}
}
