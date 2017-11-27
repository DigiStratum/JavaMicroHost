package com.digistratum.microhost.RestServer.Http.Headers;

import java.util.Set;

/**
 * A managed collection of name:value HTTP headers
 */
public interface Headers {

	// todo: add support for rendering a given header to its final form
	// todo: add support for decomposing a provided header to a name:value form

	/**
	 * Check whether we have a specifically named header
	 *
	 * @param name String name of the header we want to check for
	 *
	 * @return boolean true if the named header is defined, else false
	 */
	public boolean has(String name);

	/**
	 * Get the value of the specifically named header
	 *
	 * Note that casing is not enforced here, but all lower-case is recommended to avoid inconsistency.
	 *
	 * @param name String name of the header we want the value for
	 *
	 * @return String value of the header (may be null, especially if undefined)
	 */
	public String get(String name);

	/**
	 * Set the value of the specifically named header to te supplied value
	 *
	 * @param name String name of the header we want the value for
	 * @param value String value to set the header to
	 */
	public void set(String name, String value);

	/**
	 * Get a set  of named headers
	 *
	 * @return Set<String> of header names which we have
	 */
	public Set<String> list();
}
