package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.Json.JsonClass;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * todo: Add support for using this class to build up a JsonApi Document; can we use a builder pattern?
 */
public class Document implements JsonClass {

	// At least one of the following is required
	protected ResourceLinkage data;
	protected Error[] errors;
	protected Meta meta;

	// These are optional
	protected JsonApi jsonapi;
	protected Links links;
	protected List<Resource> included;

	/**
	 * Check whether this is a valid JsonApi document
	 *
	 * @return boolean true if so, else false
	 */
	public boolean validate() {

		// At least one element must be non-null; non = error
		if ((null == data) && (null == errors) && (null == meta)) return false;

		// Only data OR errors can be non-null; BOTH = error
		if ((null != data) && (null != errors)) return false;

		// Included may only be non-null if data is non-null
		if ((null == data) && (null != included)) return false;

		// TODO: Check the links; may include "self", "related", or pagination data

		return true;
	}

	/**
	 * Set the jsonapi version info for this document
	 *
	 * @param jsonApi JsonApi instance
	 *
	 * @return Document (this) for chaining
	 */
	public Document setJsonApi(JsonApi jsonApi) {
		jsonapi = jsonApi;
		return this;
	}

	/**
	 * Set the links for this document
	 *
	 * @param links Links instance
	 *
	 * @return Document (this) for chaining
	 */
	public Document setLinks(Links links) {
		this.links = links;
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
	 * @return Document (this) for chaining
	 */
	public Document includeResource(Resource resource) {
		// If we have no resources yet, then we need to initialize an empty set
		if (null == included) included = new ArrayList<>();
		included.add(resource);
		return this;
	}

	@Override
	public String toJson() {
		Gson gson = new Gson();
		return "";
	}
}
