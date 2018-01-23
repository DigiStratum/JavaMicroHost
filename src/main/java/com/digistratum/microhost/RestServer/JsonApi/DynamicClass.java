package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.Json.JsonClass;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * ref: https://stackoverflow.com/questions/12832554/how-to-add-attributes-dynamically-for-java-object
 *
 * TODO: Add support for other JSON-primitive data types (number, boolean, array (of string/number/boolean), etc)
 */
public abstract class DynamicClass implements JsonClass {
	protected Map<String, Object> properties = new HashMap<>();

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

	/**
	 * Check whether we have a value for the named property
	 *
	 * @param key String name of the property we want to check
	 *
	 * @return boolean true if the named property is in our set, else false
	 */
	public boolean has(String key) {
		return properties.containsKey(key);
	}

	@Override
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(properties);
	}
}
