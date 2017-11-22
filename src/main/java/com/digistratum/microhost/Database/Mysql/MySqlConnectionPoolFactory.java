package com.digistratum.microhost.Database.Mysql;

import com.digistratum.microhost.MHConfig;

public class MySqlConnectionPoolFactory {
	public MySqlConnectionPool createMySqlConnectionPool(MHConfig config) {
		return new MySqlConnectionPool(config);
	}
}
