package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.Json.JsonBuilder;
import com.digistratum.microhost.Json.JsonClass;
import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Meta;

public class JsonApi implements JsonClass {
	protected Properties properties;
	protected JsonBuilder jsonBuilder;

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
		if (null == jsonBuilder) jsonBuilder = new JsonBuilder();
		// jsonBuilder.setVerbose(true);
		return jsonBuilder.toJson(properties);
	}
}
