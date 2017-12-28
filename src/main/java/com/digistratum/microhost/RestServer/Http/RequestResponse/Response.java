package com.digistratum.microhost.RestServer.Http.RequestResponse;

/**
 * A representation of a response that we have formulated based on a request we received
 *
 * @todo: Implement builder pattern here so that we don't need to pass everything into a single
 * call to one of the constructors...
 */
public interface Response extends RequestResponse {
	/**
	 * Get the value of the Http response code
	 *
	 * @return Integer code for this response (may be null!)
	 */
	Integer getCode();
}
