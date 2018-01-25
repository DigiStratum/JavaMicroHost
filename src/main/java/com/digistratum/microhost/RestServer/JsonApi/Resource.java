package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.Json.JsonBuilder;
import com.digistratum.microhost.Json.JsonClass;
import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Attributes;
import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Links;
import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Meta;

/**
 * JsonApi - Resource Object
 *
 * “Resource objects” appear in a JSON API document to represent resources.
 */
public class Resource implements JsonClass {
	protected Properties properties;
	protected JsonBuilder jsonBuilder;

	/**
	 * Our properties data structure
	 */
	protected class Properties {

		/**
		 * The unique ID for this Resource within the set of Resources of this type;
		 * Not required (null) if client is requestng new resource creation
		 */
		public String id;

		/**
		 * The type of this resource
		 */
		public String type;

		/**
		 * An attributes object representing some of the resource’s data.
		 */
		public Attributes attributes;

		/**
		 * A relationships object describing relationships
		 * between the resource and other JSON API resources.
		 */
		public Relationships relationships;

		/**
		 * A links object containing links related to the resource.
		 */
		public Links links;

		/**
		 * A meta object containing non-standard meta-information about a
		 * resource that can not be represented as an attribute or relationship.
		 */
		public Meta meta;

		/**
		 * Constructor
		 *
		 * This is to prevent us from instantiating a set of
		 * properties without providing the minimum required fields.
		 *
		 * @param id
		 * @param type
		 */
		public Properties (String id, String type) {
			this.id = id;
			this.type = type;
		}
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
		properties = new Properties(id, type);
	}

	/**
	 * Setter for attributes
	 *
	 * @param attributes Attributes instance to set against our properties
	 *
	 * @return this for chaining...
	 */
	public Resource setAttributes(Attributes attributes) {
		properties.attributes = attributes;
		return this;
	}

	/**
	 * Setter for relationships
	 *
	 * @param relationships Relationships instance to set against our properties
	 *
	 * @return this for chaining...
	 */
	public Resource setRelationships(Relationships relationships) {
		properties.relationships = relationships;
		return this;
	}

	/**
	 * Setter for Links
	 *
	 * @param links Links instance to set against our properties
	 *
	 * @return this for chaining...
	 */
	public Resource setLinks(Links links) {
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
	public Resource setMeta(Meta meta) {
		properties.meta = meta;
		return this;
	}

	@Override
	public String toJson() {
		if (null == jsonBuilder) jsonBuilder = new JsonBuilder();
		//jsonBuilder.setVerbose(true);
		return jsonBuilder.toJson(properties);
	}
}
