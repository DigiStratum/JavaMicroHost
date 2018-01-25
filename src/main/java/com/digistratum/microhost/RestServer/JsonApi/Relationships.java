package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.Json.JsonBuilder;
import com.digistratum.microhost.Json.JsonClass;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class Relationships implements JsonClass {
	protected Map<String, Relationship> relationships = new HashMap<>();
	protected JsonBuilder jsonBuilder;

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
	 *
	 * @return this for chaining...
	 */
	public Relationships set(String key, Relationship value) {
		relationships.put(key, value);
		return this;
	}

	@Override
	public String toJson() {
		if (null == jsonBuilder) jsonBuilder = new JsonBuilder();
		//jsonBuilder.setVerbose(true);
		return jsonBuilder.toJson(relationships);
	}
}
