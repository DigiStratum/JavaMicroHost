package com.digistratum.microhost.Database;

import com.digistratum.microhost.Exception.MHDatabaseException;
import com.digistratum.microhost.MHConfig;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPoolFactory;
import org.apache.commons.pool.impl.GenericObjectPool.Config;

import java.sql.Connection;

/**
 * ref: http://www.rndblog.com/how-to-set-up-a-mysql-connection-pool-in-java/
 */
public class MySqlConnectionPool {
	protected MHConfig config;
	protected ObjectPool pool;

	/**
	 * Constructor
	 *
	 * @param config Configuration is dependency-injected
	 */
	public MySqlConnectionPool(MHConfig config) {
		this.config = config;
		init();
	}

	/**
	 * Initialize the connection pool
	 */
	public void init() {
		String host = config.get("microhost.db.host", "localhost");
		Integer port = config.get("microhost.db.port", 3306);
		String name = config.get("microhost.db.name", "mysql");
		String user = config.get("microhost.db.user", "root");
		String pass = config.get("microhost.db.pass", "");

		PoolableObjectFactory mySqlPoolableObjectFactory;
		mySqlPoolableObjectFactory = new MySqlPoolableObjectFactory(
				host, port, name, user, pass
		);
		Config poolConfig = new GenericObjectPool.Config();
		poolConfig.maxActive = config.get("microhost.db.pool.maxActive", 10);
		poolConfig.testOnBorrow = config.get("microhost.db.pool.testOnBorrow", true);
		poolConfig.testWhileIdle = config.get("microhost.db.pool.testWhileIdle", true);;
		poolConfig.timeBetweenEvictionRunsMillis = config.get("microhost.db.pool.timeBetweenEvictionRunsMillis", 10000);
		poolConfig.minEvictableIdleTimeMillis = config.get("microhost.db.pool.minEvictableIdleTimeMillis", 60000);

		GenericObjectPoolFactory genericObjectPoolFactory;
		genericObjectPoolFactory = new GenericObjectPoolFactory(
				mySqlPoolableObjectFactory,
				poolConfig
		);

		pool = genericObjectPoolFactory.createPool();
	}

	/**
	 * Get a connection from the pool
	 *
	 * @return Connection instance from the pool
	 *
	 * @throws MHDatabaseException if something goes sideways...
	 */
	public Connection getConnection() throws MHDatabaseException {
		try {
			return (Connection) pool.borrowObject();
		} catch (Exception e) {
			throw new MHDatabaseException("Failed to borrow a connection from the pool", e);
		}
	}

	/**
	 * Return a connection to the pool
	 *
	 * @todo How can we lease these out and then return known good instances instead of accepting whatever gets passed to us?
	 *
	 * @param conn Connection instance to be returned to the pool
	 *
	 * @throws MHDatabaseException if something goes sideways...
	 */
	public void returnConnection(Connection conn) throws MHDatabaseException {
		if (null == conn) return;
		try {
			pool.returnObject(conn);
		} catch (Exception e) {
			throw new MHDatabaseException("Failed to return a connection to the pool", e);
		}
	}
}
