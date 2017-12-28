package com.digistratum.microhost.RestServer.JsonApi;

public class Document {

	// At least one of the following is required
	ResourceLinkage data;
	Error[] errors;
	Meta meta;

	// These are optional
	JsonApi jsonapi;
	Links links;
	Resource[] included;

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
}
