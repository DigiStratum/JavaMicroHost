package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.Json.JsonClass;
import com.google.gson.Gson;

public class JsonApi implements JsonClass {
	protected Properties properties;

	/**
	 * Default Constructor
	 */
	public JsonApi() {
		properties = new Properties();
	}

	/**
	 * A JSON serializable class with all our properties without exposing access
	 */
	protected class Properties {
		String version;
		Meta meta;
	}

	/**
	 * Set the JsonApi version
	 *
	 * TODO: What version of JsonApi specification do we support? (min is 1.0)
	 *
	 * @param version String version
	 *
	 * @return JsonApi (this) for chaining
	 */
	public JsonApi setVersion(String version) {
		properties.version = version;
		return this;
	}

	/**
	 * Set the JsonApi Metadata
	 *
	 * @param meta Meta instance with JsonApi metadata
	 *
	 * @return JsonApi (this) for chaining
	 */
	public JsonApi setMeta(Meta meta) {
		properties.meta = meta;
		return this;
	}

	@Override
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(properties);
	}
}
