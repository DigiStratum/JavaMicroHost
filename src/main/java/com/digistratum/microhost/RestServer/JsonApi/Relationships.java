package com.digistratum.microhost.RestServer.JsonApi;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Relationships {
	protected Map<String, Relationship> relationships = new HashMap<>();

	/**
	 * Get the named relationship's value
	 *
	 * @param key String name of the relationship we want to get the value for
	 *
	 * @return Relationship value or null
	 */
	public Relationship get(String key) {
		return (Relationship) relationships.get(key);
	}

	/**
	 * Set the named relationship's value
	 *
	 * @param key String name of the relationship we want to set the value for
	 * @param value  Relationship instance to store
	 */
	public void set(String key, Relationship value) {
		relationships.put(key, value);
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(relationships);
	}
}
