package com.digistratum.microhost.RestServer.JsonApi;

/**
 * Because a ResourceLinkage can be either an array of resources or a single resource,
 * we must provide his abstraction so that we can select which type it is to be...
 */
public interface ResourceLinkage {

	/**
	 * Get the JSON representation for this thing
	 *
	 * @return String json representing this type of resource linkage
	 */
	String toString();
}
