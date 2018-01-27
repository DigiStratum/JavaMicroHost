package com.digistratum.microhost.RestServer.JsonApi.DynamicClass;

import java.net.URL;
import java.util.List;

/**
 * TODO: Add support for forcing which links are required (for validation) and which are optional
 * (allowed at all) vs. unrestricted (current model)
 */
public class Links extends DynamicClass {

	/**
	 * Default Constructor
	 */
	public Links() {
		super();
	}

	/**
	 * Alternative Constructor
	 *
	 * @param requiredKeys
	 * @param optionalKeys
	 */
	public Links(List<String> requiredKeys, List<String> optionalKeys) {
		super(requiredKeys, optionalKeys);
	}

	/**
	 * Add/set a generic named link to the set
	 *
	 * @param name String name of the link to set
	 * @param link URL instance to be stored as the named link
	 *
	 * @return this for chaining...
	 */
	public Links set(String name, URL link) {
		set(name, link.toString());
		return this;
	}

	/**
	 * Add pagination link for first page of the paged result set
	 *
	 * @param link URL that the link should point to to get the first page
	 *
	 * @return this for chaining...
	 */
	public Links addPaginationLinkFirst(URL link) {
		return set("first", link);
	}

	/**
	 * Add pagination link for last page of the paged result set
	 *
	 * @param link URL that the link should point to to get the page
	 *
	 * @return this for chaining...
	 */
	public Links addPaginationLinkLast(URL link) {
		return set("last", link);
	}

	/**
	 * Add pagination link for next page of the paged result set
	 *
	 * @param link URL that the link should point to to get the page
	 *
	 * @return this for chaining...
	 */
	public Links addPaginationLinkNext(URL link) {
		return set("next", link);
	}

	/**
	 * Add pagination link for previous page of the paged result set
	 *
	 * @param link URL that the link should point to to get the page
	 *
	 * @return Links (this) for chaining
	 */
	public Links addPaginationLinkPrev(URL link) {
		return set("prev", link);
	}
}
