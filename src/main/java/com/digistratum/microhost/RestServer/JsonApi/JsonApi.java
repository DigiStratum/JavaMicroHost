package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.Json.Json;
import com.digistratum.microhost.Json.JsonSerializeable;
import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Meta;

public class JsonApi implements JsonSerializeable {
	protected Properties properties;
	protected Json json;

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
		public String version;
		public Meta meta;
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
		if (null == json) json = new Json();
		// jsonBuilder.setVerbose(true);
		return json.toJson(properties);
	}
}
