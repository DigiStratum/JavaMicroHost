package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.Json.Json;
import com.digistratum.microhost.Json.JsonSerializeable;
import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Links;
import com.digistratum.microhost.RestServer.JsonApi.DynamicClass.Meta;
import com.digistratum.microhost.RestServer.JsonApi.Exception.JsonApiException;
import com.digistratum.microhost.Validation.Validatable;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * todo: can we use a builder pattern?
 */
public class Document implements JsonSerializeable, Validatable {
	protected Properties properties;
	protected Json json;

	/**
	 * Our set of properties
	 */
	public class Properties {

		// At least one of data, errors, or meta must be present to be valid.
		// The members data and errors MUST NOT coexist in the same document.

		/**
		 * The document's "primary data"; it will be null, or one or many Resources
		 */
		public Resources data;

		/**
		 * An array of error objects
		 */
		public List<Error> errors;

		/**
		 * A meta object that contains non-standard meta-information.
		 */
		public Meta meta;

		// These are optional

		/**
		 * An object describing the server's implementation
		 */
		public JsonApi jsonapi;

		/**
		 * The top-level links object MAY contain the following members:
		 *
		 * "self": the link that generated the current response document.
		 * "related": a related resource link when the primary data represents a resource relationship.
		 * Pagination links for the primary data ("first", "last", "prev", "next")
		 */
		public Links links;

		/**
		 * An array of resource objects that are related to the
		 * primary data and/or each other ("included resources").
		 */
		public Resources included;
	}

	/**
	 * Constructor
	 */
	public Document() {
		properties = new Properties();
	}

	/**
	 * Set the jsonapi version info for this document
	 *
	 * @param version@return this for chaining...
	 */
	public Document setJsonApiVersion(String version) {
		properties.jsonapi = new JsonApi(version);
		return this;
	}

	/**
	 * Add/set a link into our links collection
	 *
	 * Note that this will instantiate the links collection if a valid link is being provided to set
	 *
	 * @param name String name of the link to set
	 * @param link URL instance of the link we want to save
	 *
	 * @return this for chaining...
	 */
	public Document setLink(String name, URL link) {
		if ((null == link) || (null == name)) {
			throw new JsonApiException("Link cannot be null");
		}
		String[] optionalLinks = new String[] {"self", "related", "first", "last", "prev", "next"};
		if (null == properties.links) {
			properties.links = new Links(null, Arrays.asList(optionalLinks));
		}
		properties.links.set(name, link);
		return this;
	}

	/**
	 * Add a resource to the primary data
	 *
	 * @param resource Resource instance to add to the primary data for our response
	 *
	 * @return this for chaining...
	 */
	public Document addResource(Resource resource) {
		if (null == resource) {
			throw new JsonApiException("Resource cannot be null");
		}
		if (null != properties.errors) {
			throw new JsonApiException("Primary resource data cannot coexist with errors");
		}

		// If we have no primary data yet, then we need to initialize an empty set
		if (null == properties.data) {
			properties.data = new Resources();
		}
		properties.data.addResource(resource);
		return this;

	}

	/**
	 * Include a supplemental resource with this document's primary data
	 *
	 * Note that included property is intentionally null before including any resources
	 * so that it will be removed from the resulting JSON if no resources are added.
	 *
	 * @param resource Resource instance to include
	 *
	 * @return this for chaining...
	 */
	public Document includeResource(Resource resource) {
		if (null == properties.data) {
			throw new JsonApiException("Cannot include resources without primary data");
		}
		if (null == resource) {
			throw new JsonApiException("Resource cannot be null");
		}

		// If we have no included resources yet, then we need to initialize an empty set
		if (null == properties.included) {
			properties.included = new Resources();
			properties.included.setForceCollection(true);
		}
		properties.included.addResource(resource);
		return this;
	}

	/**
	 * Set some name/value pair into the Metadata
	 *
	 * @param name String name of the Metadata we want to set
	 * @param value String value for the named Metadata we want to set
	 *
	 * @return this for chaining...
	 */
	public Document setMetadata(String name, String value) {
		if (null == properties.meta) properties.meta = new Meta();
		properties.meta.set(name, value);
		return this;
	}

	/**
	 * Add an Error to the errors output
	 *
	 * @param error Error instance to add
	 *
	 * @return this for chaining...
	 */
	public Document addError(Error error) {
		if (null != properties.data) {
			throw new JsonApiException("Errors cannot coexist with primary resource data");
		}
		if (null == properties.errors) properties.errors = new ArrayList<>();
		properties.errors.add(error);
		return this;
	}

	@Override
	public String toJson() {
		if (! isValid()) {
			throw new JsonApiException("Invalid state, cannot serialize");
		}
		if (null == json) json = new Json();
		//json.setVerbose(true);
		return json.toJson(properties);
	}

	@Override
	public boolean isValid() {

		// At least one element must be non-null; non = error
		if ((null == properties.data) && (null == properties.errors) && (null == properties.meta)) return false;

		// Only data OR errors can be non-null; BOTH = error
		if ((null != properties.data) && (null != properties.errors)) return false;

		// Included may only be non-null if data is non-null
		if ((null == properties.data) && (null != properties.included)) return false;

		return true;
	}
}
