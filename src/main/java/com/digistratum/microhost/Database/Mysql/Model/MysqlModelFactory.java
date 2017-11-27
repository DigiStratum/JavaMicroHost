package com.digistratum.microhost.Database.Mysql.Model;

import com.digistratum.microhost.Database.ModelFactory;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionImpl;
import com.digistratum.microhost.Exception.MHDatabaseException;

import java.lang.reflect.InvocationTargetException;

/**
 * MysqlModelImpl Factory
 */
public class MysqlModelFactory implements ModelFactory {

	/**
	 * Generic Mysql Model Maker
	 *
	 * @param modelClass Class Which MysqlModelImpl  class to  we want a new instance of?
	 *
	 * @return Object instance of modelClass type
	 *
	 * @throws MHDatabaseException
	 */
	public <T extends MysqlModelImpl> T newModel(Class<T> modelClass, MySqlConnectionImpl conn) throws MHDatabaseException {
		try {
			return modelClass.getConstructor(MySqlConnectionImpl.class).newInstance(conn);
		} catch (Exception e) {
			throw new MHDatabaseException("Failed to instantiate a newModel of type '" + modelClass.getCanonicalName() + "'", e);
		}
	}
}
