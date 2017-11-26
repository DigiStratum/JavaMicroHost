package com.digistratum.microhost.Database.Mysql.Connection;

import com.digistratum.microhost.Config.ConfigImpl;

/**
 * @todo Get rid of this factory if possible in favor of DI/interface model
 */
public class MySqlConnectionPoolFactory {
	public MySqlConnectionPoolImpl createMySqlConnectionPool(ConfigImpl configImpl) {
		return new MySqlConnectionPoolImpl(configImpl);
	}
}
