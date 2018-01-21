package com.digistratum.microhost.RestServer.JsonApi;

import com.digistratum.microhost.Json.JsonClass;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ResourceLinkageMany implements ResourceLinkage, JsonClass {
	List<Resource> resources;

	/**
	 * Default constructor
	 */
	public ResourceLinkageMany() {
		resources = new ArrayList<>();
	}

	/**
	 * Add a resource to the collection
	 *
	 * @param resource Resource instance that we want to add
	 */
	public void addResource(Resource resource) {
		resources.add(resource);
	}

	@Override
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(resources);
	}
}
