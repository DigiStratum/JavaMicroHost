package com.digistratum.microhost.RestServer.Http.RequestResponse;

import com.digistratum.microhost.RestServer.Http.Headers.Headers;
import com.digistratum.microhost.RestServer.Http.Headers.HeadersImpl;

import java.util.List;
import java.util.Map;

/**
 * Base class for data & capabilities common to both HTTP requests and responses
 */
public abstract class RequestResponseImpl implements RequestResponse {
	protected enum Type {request, response }
	protected Type type;

	protected HeadersImpl headers;
	protected String body = null;
	protected String method = null;
	protected String uri = null;
	protected Integer code = null;
	protected String queryString = null;
	protected Map<String, List<String>> queryParams = null;
	protected Map<String, List<String>> bodyParams = null;


	@Override
	public Headers getHeaders() {
		return headers;
	}

	@Override
	public String getBody() {
		return body;
	}
}
