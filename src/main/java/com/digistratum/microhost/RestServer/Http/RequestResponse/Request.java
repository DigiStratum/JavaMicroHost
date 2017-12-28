package com.digistratum.microhost.RestServer.Http.RequestResponse;

import java.util.List;

/**
 * An immutable representation of the request which has come in to our service
 */
public interface Request extends RequestResponse {

	/**
	 * Get the value of the method
	 *
	 * @return String method (may be null!)
	 */
	String getRequestMethod();

	/**
	 * Get the URI for this request (without the query string!)
	 *
	 * @return String URI (may be null!)
	 */
	String getUri();

	/**
	 * Get the query string, if any
	 *
	 * @return String contents of the query string (may be null or empty)
	 */
	String getQueryString();

	/**
	 * Check if we have the named request query string param
	 *
	 * @param name String name of the query param that we want
	 *
	 * @return Boolean true if we have the named query param, else false
	 */
	Boolean hasQueryParam(String name);

	/**
	 * Get the named request query string param
	 *
	 * @param name String name of the query param that we want
	 *
	 * @return List of string values for this param; may be null, usually a single value, may have multiples
	 */
	List<String> getQueryParam(String name);

	/**
	 * Check if we have the named request body string param
	 *
	 * @param name String name of the body param that we want
	 *
	 * @return Boolean true if we have the named body param, else false
	 */
	Boolean hasBodyParam(String name);

	/**
	 * Get the named request body string param
	 *
	 * @param name String name of the body param that we want
	 *
	 * @return List of string values for this param; may be null, usually a single value, may have multiples
	 */
	List<String> getBodyParam(String name);
}
