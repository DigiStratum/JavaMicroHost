package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.RestServer.JsonApi.Exception.JsonApiException;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * ref: https://stackoverflow.com/questions/12832554/how-to-add-attributes-dynamically-for-java-object
 *
 * TODO: Add support for other JSON-primitive data types (number, boolean, array (of string/number/boolean), etc)
 */
public abstract class DynamicClass {
	private Map<String, Object> properties = new HashMap<>();

	/**
	 * Get the named property's value
	 *
	 * @param key String name of the property we want to get the value for
	 *
	 * @return String property value or null
	 */
	public String get(String key) {
		return (String) properties.get(key);
	}

	/**
	 * Set the named property's value
	 *
	 * @param key String name of the property we want to set the value for
	 * @param value  String property value
	 */
	public void set(String key, String value) {
		properties.put(key, value);
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(properties);
	}
}
