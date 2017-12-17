package com.digistratum.microhost.RestServer.Endpoint;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Http.Headers.Headers;
import com.digistratum.microhost.RestServer.Http.Headers.HeadersImpl;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Request;
import com.digistratum.microhost.RestServer.Http.RequestResponse.Response;
import com.digistratum.microhost.RestServer.Http.RequestResponse.ResponseImpl;
import com.google.gson.Gson;

abstract public class EndpointImpl implements Endpoint {
	protected Gson gson;

	/**
	 * Constructor
	 */
	protected EndpointImpl() {
		gson = new Gson();
	}

	@Override
	/**
	 * Default implementation
	 */
	public Response handle(Request request) throws MHException {
		return null;
	}

	/**
	 * Provide a JSON data response with 200 OK status code
	 *
	 * @param data Object that we want to JSON-encode for the response
	 *
	 * @return Response instance populated with the data and status code
	 *
	 * @throws MHException for any errors
	 */
	protected Response jsonResponse200(Object data) throws MHException {
		Headers responseHeaders = new HeadersImpl();
		responseHeaders.set("Content-Type", "application/json");
		String responseBody = gson.toJson(data);
		return new ResponseImpl(
				200,
				responseHeaders,
				responseBody
		);
	}

	/**
	 * Provide a TEXT response with 200 OK status code
	 *
	 * @param text String that we want to return for the response
	 *
	 * @return Response instance populated with the text and status code
	 *
	 * @throws MHException for any errors
	 */
	protected Response textResponse200(String text) throws MHException {
		Headers responseHeaders = new HeadersImpl();
		responseHeaders.set("Content-Type", "text/plain");
		return new ResponseImpl(
				200,
				text
		);
	}
}
