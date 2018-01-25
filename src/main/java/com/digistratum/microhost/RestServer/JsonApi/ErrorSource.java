package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.Json.JsonBuilder;
import com.digistratum.microhost.Json.JsonClass;

/**
 * JsonApi - Source
 *
 * An object containing references to the source of the error, should one occur.
 */
public class ErrorSource implements JsonClass {
	protected Properties properties;
	protected JsonBuilder jsonBuilder;

	/**
	 * Our properties data structure
	 */
	protected class Properties {
		/**
		 * A JSON Pointer [RFC6901] to the associated entity in the request document [e.g. "/data"
		 * for a primary data object, or "/data/attributes/title" for a specific attribute].
		 */
		public JsonPointer pointer;

		/**
		 * A string indicating which URI query parameter caused the error.
		 */
		public String parameter;
	}

	/**
	 * Constructor
	 */
	public ErrorSource() {
		properties = new Properties();
	}

	/**
	 * Setter for Pointer
	 *
	 * @param pointer JsonPointer instance to set against our properties
	 *
	 * @return This instance for chaining...
	 */
	public ErrorSource setPointer(JsonPointer pointer) {
		properties.pointer = pointer;
		return this;
	}

	/**
	 * Setter for Parameter
	 *
	 * @param parameter String to set against our parameter
	 *
	 * @return This instance for chaining...
	 */
	public ErrorSource setParameter(String parameter) {
		properties.parameter = parameter;
		return this;
	}

	@Override
	public String toJson() {
		if (null == jsonBuilder) jsonBuilder = new JsonBuilder();
		return jsonBuilder.toJson(properties);
	}
}
