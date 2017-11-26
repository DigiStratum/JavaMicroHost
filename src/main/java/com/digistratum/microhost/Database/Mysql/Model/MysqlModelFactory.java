package com.digistratum.microhost.Database.Mysql.Model;

import com.digistratum.microhost.Database.ModelFactory;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionImpl;

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
	 * @throws Exception
	 */
	public <T extends MysqlModelImpl> T newModel(Class<T> modelClass, MySqlConnectionImpl conn) throws Exception {
		try {
			return modelClass.getConstructor(MySqlConnectionImpl.class).newInstance(conn);
		} catch (InstantiationException|IllegalAccessException e) {
			e.printStackTrace();
			throw new Exception("Failed to instantiate a newModel of type '" + modelClass.getCanonicalName() + "'");
		}
	}
}
