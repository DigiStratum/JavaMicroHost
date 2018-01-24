package com.digistratum.microhost.RestServer.JsonApi;

import java.net.URL;

/**
 * TODO: Add support for forcing which links are required (for validation) and which are optional
 * (allowed at all) vs. unrestricted (current model)
 */
public class Links extends DynamicClass {

	/**
	 * Add pagination link for first page of the paged result set
	 *
	 * @param link URL that the link should point to to get the first page
	 *
	 * @return Links (this) for chaining
	 */
	public Links addPaginationLinkFirst(URL link) {
		set("first", link.toString());
		return this;
	}

	/**
	 * Add pagination link for last page of the paged result set
	 *
	 * @param link URL that the link should point to to get the page
	 *
	 * @return Links (this) for chaining
	 */
	public Links addPaginationLinkLast(URL link) {
		set("last", link.toString());
		return this;
	}

	/**
	 * Add pagination link for next page of the paged result set
	 *
	 * @param link URL that the link should point to to get the page
	 *
	 * @return Links (this) for chaining
	 */
	public Links addPaginationLinkNext(URL link) {
		set("next", link.toString());
		return this;
	}

	/**
	 * Add pagination link for previous page of the paged result set
	 *
	 * @param link URL that the link should point to to get the page
	 *
	 * @return Links (this) for chaining
	 */
	public Links addPaginationLinkPrev(URL link) {
		set("prev", link.toString());
		return this;
	}
}
