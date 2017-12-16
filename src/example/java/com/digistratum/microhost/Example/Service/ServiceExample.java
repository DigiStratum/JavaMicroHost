package com.digistratum.microhost.Example.Service;

import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnection;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPool;
import com.digistratum.microhost.Database.Mysql.Model.MySqlModelFactory;
import com.digistratum.microhost.Example.Model.ModelMysqlDatabaseImpl;
import com.digistratum.microhost.Exception.MHException;

import javax.inject.Inject;
import java.util.List;

public class ServiceExample {
	protected MySqlConnectionPool pool;
	protected MySqlModelFactory mySqlModelFactory;

	/**
	 * Constructor
	 *
	 * @param pool Dependency injected database connection pool
	 * @param mySqlModelFactory My
	 */
	@Inject
	ServiceExample(MySqlConnectionPool pool, MySqlModelFactory mySqlModelFactory) {
		this.pool = pool;
		this.mySqlModelFactory = mySqlModelFactory;
	}

	public List<ModelMysqlDatabaseImpl> getDatabases() throws MHException {
		try (MySqlConnection conn = pool.getConnection()) {
			ModelMysqlDatabaseImpl modelMysqlDatabase = mySqlModelFactory.newModel(ModelMysqlDatabaseImpl.class);
			return modelMysqlDatabase.getDatabases(conn);
		} catch (Exception e) {
			throw new MHException("Error handling request", e);
		}

	}
}
