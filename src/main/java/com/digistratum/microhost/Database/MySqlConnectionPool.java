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
 * ref: https://commons.apache.org/proper/commons-pool/api-1.6/org/apache/commons/pool/ObjectPool.html#returnObject(T)
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
	 * Get a wrapped connection from the pool
	 *
	 * @return MySqlConnection instance wrapping a Connection from the pool
	 *
	 * @throws MHDatabaseException if something goes sideways...
	 */
	public MySqlConnection getConnection() throws MHDatabaseException {
		try {
			Connection conn = (Connection) pool.borrowObject();
			return new MySqlConnection(this, conn);
		} catch (Exception e) {
			throw new MHDatabaseException("Failed to borrow a connection from the pool", e);
		}
	}

	/**
	 * Return a connection to the pool
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
