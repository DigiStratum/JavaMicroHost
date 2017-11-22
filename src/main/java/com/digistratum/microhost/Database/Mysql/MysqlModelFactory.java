package com.digistratum.microhost.Database.Mysql;

import com.digistratum.microhost.Database.DbModelFactory;

/**
 * MysqlModel Factory
 */
public class MysqlModelFactory extends DbModelFactory {

	/**
	 * Generic Mysql Model Maker
	 *
	 * @param modelClass Class Which MysqlModel  class to  we want a new instance of?
	 *
	 * @return Object instance of modelClass type
	 *
	 * @throws Exception
	 */
	public <T extends MysqlModel> T newModel(Class<T> modelClass, MySqlConnection conn) throws Exception {
		try {
			return modelClass.getConstructor(MySqlConnection.class).newInstance(conn);
		} catch (InstantiationException|IllegalAccessException e) {
			e.printStackTrace();
			throw new Exception("Failed to instantiate a newModel of type '" + modelClass.getCanonicalName() + "'");
		}
	}
}
