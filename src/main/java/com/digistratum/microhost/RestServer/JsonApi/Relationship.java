package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.Json.Exception.JsonException;
import com.digistratum.microhost.Json.Json;
import com.digistratum.microhost.Json.JsonSerializeable;
import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Links;
import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Meta;
import com.digistratum.microhost.Validation.Validatable;

public class Relationship implements JsonSerializeable, Validatable {
	protected Properties properties;
	protected Json json;

	/**
	 * Our properties data structure
	 */
	protected class Properties {
		/**
		 * A links object containing at least one of the following:
		 *
		 * "self": a link for the relationship itself (a “relationship link”). This link allows the
		 * client to directly manipulate the relationship. For example, removing an author through
		 * an article’s relationship URL would disconnect the person from the article without
		 * deleting the people resource itself. When fetched successfully, this link returns the
		 * linkage for the related resources as its primary data. (See Fetching Relationships.)
		 *
		 * "related": a related resource link
		 */
		public Links links;

		/**
		 * Resource linkage
		 */
		public Resources data;

		/**
		 * A meta object that contains non-standard meta-information about the relationship.
		 */
		public Meta meta;
	}

	/**
	 * Constructor
	 */
	public Relationship () {
		properties = new Properties();
	}

	/**
	 * Setter for Links
	 *
	 * @param links Links instance to set against our properties
	 *
	 * @return this for chaining...
	 */
	public Relationship setLinks(Links links) {
		properties.links = links;
		return this;
	}

	/**
	 * Setter for Meta
	 *
	 * @param meta Meta instance to set against our properties
	 *
	 * @return this for chaining...
	 */
	public Relationship setMeta(Meta meta) {
		properties.meta = meta;
		return this;
	}

	/**
	 * Getter for Meta
	 *
	 * @return Meta instance which we have
	 */
	public Meta getMeta() {
		return properties.meta;
	}

	/**
	 * Setter for Data
	 *
	 * @param data Resources instance to set against our properties
	 *
	 * @return this for chaining...
	 */
	public Relationship setData(Resources data) {
		properties.data = data;
		return this;
	}

	@Override
	public String toJson() {
		if (! isValid()) {
			throw new JsonException("Relationship instance is invalid - cannot serialize to JSON");
		}
		if (null == json) json = new Json();
		return json.toJson(properties);
	}

	@Override
	public boolean isValid() {
		// A “relationship object” MUST contain at least one of the following:
		if ((null == properties.links) && (null == properties.meta) && (null == properties.data)) return false;

		// If a links property is set...
		if (null != properties.links) {
			// it must at least one of the following links:
			if (! (properties.links.has("self") || properties.links.has("related"))) return false;
		}

		return true;
	}
}
