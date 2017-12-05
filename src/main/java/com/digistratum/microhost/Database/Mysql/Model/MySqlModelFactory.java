package com.digistratum.microhost.Database.Mysql.Model;

import com.digistratum.microhost.Database.Model;
import com.digistratum.microhost.Database.ModelFactory;
import com.digistratum.microhost.Database.Mysql.Connection.MySqlConnectionImpl;
import com.digistratum.microhost.Exception.MHDatabaseException;
import com.digistratum.microhost.Exception.MHException;

/**
 * MysqlModelImpl Factory
 */
public class MySqlModelFactory implements ModelFactory {

	@Override
	public <T extends Model> T newModel(Class<T> modelClass) throws MHException {
		try {
			return modelClass.getConstructor(MySqlConnectionImpl.class).newInstance();
		} catch (Exception e) {
			throw new MHDatabaseException("Failed to instantiate a new Model of type '" + modelClass.getCanonicalName() + "'", e);
		}
	}
}
