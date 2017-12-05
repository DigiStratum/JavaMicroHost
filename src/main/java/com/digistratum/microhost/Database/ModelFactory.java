package com.digistratum.microhost.Database;

import com.digistratum.microhost.Exception.MHException;

public interface ModelFactory {

	/**
	 * Generic Model Maker
	 *
	 * @return Object instance of modelClass type
	 *
	 * @throws MHException
	 */
	public <T extends Model> T newModel(Class<T> modelClass) throws MHException;
}
