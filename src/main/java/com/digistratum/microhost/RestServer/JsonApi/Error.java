package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.Json.Json;
import com.digistratum.microhost.Json.JsonSerializeable;
import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Links;
import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Meta;

public class Error implements JsonSerializeable {
	protected Properties properties;
	protected Json json;

	/**
	 * Our properties data structure
	 */
	protected class Properties {
		/**
		 * A unique identifier for this particular occurrence of the problem.
		 */
		public String id;

		/**
		 * A links object containing the following members:
		 * "about": a link that leads to further details about this particular occurrence of the problem.
		 */
		public Links links;

		/**
		 * The HTTP status code applicable to this problem, expressed as a string value.
		 */
		public String status;

		/**
		 * An application-specific error code, expressed as a string value.
		 */
		public String code;

		/**
		 * A short, human-readable summary of the problem that SHOULD NOT change from occurrence to
		 * occurrence of the problem, except for purposes of localization.
		 */
		public String title;

		/**
		 * A human-readable explanation specific to this occurrence of the problem. Like title, this
		 * field’s value can be localized.
		 */
		public String detail;

		/**
		 * An object containing references to the source of the error, optionally including any of
		 * the following members:
		 *
		 * "pointer": a JSON Pointer [RFC6901] to the associated entity in the request document
		 * [e.g. "/data" for a primary data object, or "/data/attributes/title" for a specific
		 * attribute].
		 *
		 * "parameter": a string indicating which URI query parameter caused the error.
		 */
		public ErrorSource source;

		/**
		 * A meta object containing non-standard meta-information about the error.
		 */
		public Meta meta;
	}

	/**
	 * Constructor
	 */
	public Error () {
		properties = new Properties();
	}

	/**
	 * Setter for ID
	 *
	 * @param id String ID to set against our properties
	 *
	 * @return this for chaining...
	 */
	public Error setId(String id) {
		properties.id = id;
		return this;
	}

	/**
	 * Setter for Links
	 *
	 * @param links Links instance to set against our properties
	 *
	 * @return this for chaining...
	 */
	public Error setLinks(Links links) {
		properties.links = links;
		return this;
	}

	/**
	 * Setter for Status
	 *
	 * todo: Force this to be a valid, supported HTTP status code
	 *
	 * @param status String ID to set against our properties
	 *
	 * @return this for chaining...
	 */
	public Error setStatus(String status) {
		properties.status = status;
		return this;
	}

	/**
	 * Setter for Code
	 *
	 * @param code String Code to set against our properties
	 *
	 * @return this for chaining...
	 */
	public Error setCode(String code) {
		properties.code = code;
		return this;
	}

	/**
	 * Setter for Title
	 *
	 * @param title String Title to set against our properties
	 *
	 * @return this for chaining...
	 */
	public Error setTitle(String title) {
		properties.title = title;
		return this;
	}

	/**
	 * Setter for Detail
	 *
	 * @param detail String Detail to set against our properties
	 *
	 * @return this for chaining...
	 */
	public Error setDetail(String detail) {
		properties.detail = detail;
		return this;
	}

	/**
	 * Setter for Source
	 *
	 * @param source Source instance to set against our properties
	 *
	 * @return this for chaining...
	 */
	public Error setSource(ErrorSource source) {
		properties.source = source;
		return this;
	}

	/**
	 * Setter for Meta
	 *
	 * @param meta Meta instance to set against our properties
	 *
	 * @return this for chaining...
	 */
	public Error setMeta(Meta meta) {
		properties.meta = meta;
		return this;
	}

	@Override
	public String toJson() {
		if (null == json) json = new Json();
		//jsonBuilder.setVerbose(true);
		return json.toJson(properties);
	}
}
