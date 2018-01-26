package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.Json.Json;
import com.digistratum.microhost.Json.JsonSerializeable;

import java.util.ArrayList;
import java.util.List;

/**
 * JsonApi - Resources
 *
 * A collection of zero or more Resource objects
 */
public class Resources implements JsonSerializeable {
	protected List<Resource> resources;

	protected boolean forceCollection = false;
	protected boolean nullable = true;

	protected Json json;

	/**
	 * Default constructor
	 */
	public Resources() {
		resources = new ArrayList<>();
	}

	/**
	 * Force the JSON output to be a collection even if there are zero or one
	 *
	 * Note: default value is false and the behavior is to automatically set the JSON output to be a
	 * collection if there are two or more resources, and be a single object for only one resource,
	 * and null for an empty set. When true, the empty set will return an empty set, and one or more
	 * resources will be added to a collection rather than just a single object for one resource.
	 *
	 * @param value boolean; true to force a collection result
	 *
	 * @Return this for chaining...
	 */
	public Resources setForceCollection(boolean value) {
		forceCollection = value;
		return this;
	}

	/**
	 * Force the JSON output to be null for empty set
	 *
	 * @param value boolean; false to force the collection to return an empty set instead of null
	 *
	 * @Return this for chaining...
	 */
	public Resources setNullable(boolean value) {
		nullable = value;
		return this;
	}

	/**
	 * Add a resource to the collection
	 *
	 * @param resource Resource instance that we want to add
	 *
	 * @Return this for chaining...
	 */
	public Resources addResource(Resource resource) {
		resources.add(resource);
		return this;
	}

	// TODO: Perhaps this is generally useful as part of JsonBuilder/JsonClass?
	@Override
	public String toJson() {

		// Empty set? Return a null if we are nullable
		if (resources.isEmpty() && (nullable)) return "null";

		if (null == json) json = new Json();

		// Set of one? // Return only a single resource if we are NOT forcing a collection
		if ((resources.size() == 1) && (! forceCollection)) {
			json.setVerbose(true);
			return json.toJson(resources.get(0));
		}

		// Everything else is represented as a collection of resource objects (including empty set)
		return json.toJson(resources);
	}
}
