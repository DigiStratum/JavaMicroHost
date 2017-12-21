package com.digistratum.microhost.RestServer.Endpoint;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Http.Headers.Headers;
import com.digistratum.microhost.RestServer.Http.Headers.HeadersImpl;
import com.digistratum.microhost.RestServer.Http.HttpSpec;
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
	 * @return Response instance populated with the data, status code, and mime type
	 *
	 * @throws MHException for any errors
	 */
	protected Response jsonResponse200(Object data) throws MHException {
		Headers responseHeaders = new HeadersImpl();
		responseHeaders.set("Content-Type", "application/json");
		String responseBody = gson.toJson(data);
		return new ResponseImpl(
				HttpSpec.HTTP_STATUS_200_OK,
				responseHeaders,
				responseBody
		);
	}

	/**
	 * Provide a TEXT response with 200 OK status code
	 *
	 * @param text String that we want to return for the response
	 *
	 * @return Response instance populated with the text, status code, and mime type
	 *
	 * @throws MHException for any errors
	 */
	protected Response textResponse200(String text) throws MHException {
		Headers responseHeaders = new HeadersImpl();
		responseHeaders.set("Content-Type", "text/plain");
		return new ResponseImpl(
				HttpSpec.HTTP_STATUS_200_OK,
				responseHeaders,
				text
		);
	}

	/**
	 * Provide an HTML response with the supplied status code
	 *
	 * @param html String that we want to return for the response
	 *
	 * @return Response instance populated with the html, status code, and mime type
	 *
	 * @throws MHException for any errors
	 */
	protected Response htmlResponse(String html, int code) throws MHException {
		Headers responseHeaders = new HeadersImpl();
		responseHeaders.set("Content-Type", "text/html");
		return new ResponseImpl(
				code,
				responseHeaders,
				html
		);
	}

	/**
	 * Provide an HTML response with 200 OK status code
	 *
	 * @param html String that we want to return for the response
	 *
	 * @return Response instance populated with the html, status code, and mime type
	 *
	 * @throws MHException for any errors
	 */
	protected Response htmlResponse200(String html) throws MHException {
		return htmlResponse(html, HttpSpec.HTTP_STATUS_200_OK);
	}
}
