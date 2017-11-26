package com.digistratum.microhost.Database.Mysql.Connection;

import com.digistratum.microhost.Config.ConfigImpl;
import com.digistratum.microhost.Exception.MHDatabaseException;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPoolFactory;

import java.sql.Connection;

/**
 * ref: http://www.rndblog.com/how-to-set-up-a-mysql-connection-pool-in-java/
 * ref: https://commons.apache.org/proper/commons-pool/api-1.6/org/apache/commons/pool/ObjectPool.html#returnObject(T)
 */
public class MySqlConnectionPoolImpl implements MySqlConnectionPool {
	protected ConfigImpl configImpl;
	protected ObjectPool pool;

	/**
	 * Constructor
	 *
	 * @param configImpl Configuration is dependency-injected
	 */
	public MySqlConnectionPoolImpl(ConfigImpl configImpl) {
		this.configImpl = configImpl;
		init();
	}

	@Override
	public void init() {
		String host = configImpl.get("microhost.db.host", "localhost");
		Integer port = configImpl.get("microhost.db.port", 3306);
		String name = configImpl.get("microhost.db.name", "mysql");
		String user = configImpl.get("microhost.db.user", "root");
		String pass = configImpl.get("microhost.db.pass", "");

		PoolableObjectFactory mySqlPoolableObjectFactory;
		mySqlPoolableObjectFactory = new MySqlPoolableObjectFactory(
				host, port, name, user, pass
		);
		GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
		poolConfig.maxActive = configImpl.get("microhost.db.pool.maxActive", 10);
		poolConfig.testOnBorrow = configImpl.get("microhost.db.pool.testOnBorrow", true);
		poolConfig.testWhileIdle = configImpl.get("microhost.db.pool.testWhileIdle", true);;
		poolConfig.timeBetweenEvictionRunsMillis = configImpl.get("microhost.db.pool.timeBetweenEvictionRunsMillis", 10000);
		poolConfig.minEvictableIdleTimeMillis = configImpl.get("microhost.db.pool.minEvictableIdleTimeMillis", 60000);

		GenericObjectPoolFactory genericObjectPoolFactory;
		genericObjectPoolFactory = new GenericObjectPoolFactory(
				mySqlPoolableObjectFactory,
				poolConfig
		);

		pool = genericObjectPoolFactory.createPool();
	}

	@Override
	public MySqlConnectionImpl getConnection() throws MHDatabaseException {
		try {
			Connection conn = (Connection) pool.borrowObject();
			return new MySqlConnectionImpl(this, conn);
		} catch (Exception e) {
			throw new MHDatabaseException("Failed to borrow a connection from the pool", e);
		}
	}

	@Override
	public void returnConnection(Connection conn) throws MHDatabaseException {
		if (null == conn) return;
		try {
			pool.returnObject(conn);
		} catch (Exception e) {
			throw new MHDatabaseException("Failed to return a connection to the pool", e);
		}
	}
}
