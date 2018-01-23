package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.Json.JsonBuilder;
import com.digistratum.microhost.Json.JsonClass;

/**
 * JsonApi - Resource class
 */
public class Resource implements JsonClass {
	protected Properties properties;
	protected JsonBuilder jsonBuilder;

	protected class Properties {
		// Required
		public String id; // Not required if client is requestng new resource creation
		public String type;

		// Optional
		public Attributes attributes;
		public Relationships relationships;
		public Links links;
		public Meta meta;
	}

	/**
	 * Constructor
	 *
	 * TODO: enforce validity of id/type
	 *
	 * @param id String unique identifier for this resource (within type)
	 * @param type String type of this resource
	 */
	public Resource(String id, String type) {
		properties = new Properties();
		properties.id = id;
		properties.type = type;
	}

	/**
	 * Setter for attributes
	 *
	 * @param attributes Attributes instance to set against our properties
	 */
	public void setAttributes(Attributes attributes) {
		properties.attributes = attributes;
	}

	/**
	 * Setter for relationships
	 *
	 * @param relationships Relationships instance to set against our properties
	 */
	public void setRelationships(Relationships relationships) {
		properties.relationships = relationships;
	}

	/**
	 * Setter for Links
	 *
	 * @param links Links instance to set against our properties
	 */
	public void setLinks(Links links) {
		properties.links = links;
	}

	/**
	 * Setter for Meta
	 *
	 * @param meta Meta instance to set against our properties
	 */
	public void setMeta(Meta meta) {
		properties.meta = meta;
	}

	@Override
	public String toJson() {
		if (null == jsonBuilder) jsonBuilder = new JsonBuilder();
		//jsonBuilder.setVerbose(true);
		return jsonBuilder.toJson(properties);
	}
}
