package com.digistratum.microhost.RestServer.JsonApi;

import com.google.gson.Gson;

public class ResourceLinkageOne implements ResourceLinkage {
	Resource resource;

	/**
	 * Default Constructor; leaves resource null (intentionally)
	 */
	public ResourceLinkageOne() {}

	/**
	 * Constructor initialized with a Resource
	 *
	 * @param resource Resource instance that we want to set
	 */
	public ResourceLinkageOne(Resource resource) {
		setResource(resource);
	}

	/**
	 * Set the resource to the one provided
	 *
	 * @param resource Resource instance that we want to set
	 */
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(resource);
	}
}
