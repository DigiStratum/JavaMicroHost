package com.digistratum.microhost.Example.Service;

import com.digistratum.microhost.Database.ModelFactory;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnection;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionPool;
import com.digistratum.microhost.Example.Model.ModelMysqlDatabaseImpl;
import com.digistratum.microhost.Exception.MHException;

import javax.inject.Inject;
import java.util.List;

/**
 * Application Service Layer
 *
 * This sits between our RESTful API interface and the data model for separation of concerns
 */
public class ServiceExample {
	protected MySqlConnectionPool pool;
	protected ModelFactory modelFactory;

	/**
	 * Constructor
	 *
	 * @param pool Dependency injected database connection pool
	 * @param modelFactory ModelFactory instance which can create new Model instances as requested
	 */
	@Inject
	public ServiceExample(MySqlConnectionPool pool, ModelFactory modelFactory) {
		this.pool = pool;
		this.modelFactory = modelFactory;
	}

	/**
	 *
	 * @return
	 * @throws MHException
	 */
	public List<ModelMysqlDatabaseImpl> getDatabases() throws MHException {
		try (MySqlConnection conn = pool.getConnection()) {
			ModelMysqlDatabaseImpl modelMysqlDatabase = modelFactory.newModel(ModelMysqlDatabaseImpl.class);
			return modelMysqlDatabase.getDatabases(conn);
		} catch (Exception e) {
			throw new MHException("Error handling request", e);
		}
	}
}
