package com.digistratum.microhost.Http;

import com.digistratum.microhost.Exception.MHException;

import java.util.HashMap;
import java.util.Map;

public interface RequestResponse {

	/**
	 * Check whether this instance represents a REQUEST
	 *
	 * @return boolean true if it does, else false (if it's a RESPONSE!)
	 */
	public boolean isRequest();

	/**
	 * Check whether this instance represents a RESPONSE
	 *
	 * @return boolean true if it does, else false (if it's a REQUEST!)
	 */
	public boolean isResponse();

	/**
	 * Check whether we have a specifically named header
	 *
	 * @param name String name of the header we want to check for
	 *
	 * @return boolean true if the named header is defined, else false
	 */
	public boolean hasHeader(String name);

	/**
	 * Get the value of the specifically named header
	 *
	 * Note that casing is not enforced here, but all lower-case is recommended to avoid inconsistency.
	 *
	 * @param name String name of the header we want the value for
	 *
	 * @return String value of the header (may be null, especially if undefined)
	 */
	public String getHeader(String name);

	/**
	 * Get the entire collection of headers
	 *
	 * @return Header map
	 */
	public Map<String, String> getHeaders();

	/**
	 * Get the value of the body
	 *
	 * @return String body (may be null!)
	 */
	public String getBody();

	/**
	 * Get the value of the method
	 *
	 * @return String method (may be null!)
	 */
	public String getRequestMethod();

	/**
	 * Get the value of the URI
	 *
	 * @return String URI (may be null!)
	 */
	public String getUri();

	/**
	 * Get the value of the status code
	 *
	 * @return Integer status code (may be null!)
	 */
	public Integer getCode();
}
