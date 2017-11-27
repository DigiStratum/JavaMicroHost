package com.digistratum.microhost.RestServer.Http.RequestResponse;

import com.digistratum.microhost.RestServer.Http.Headers.Headers;
import com.digistratum.microhost.RestServer.Http.Headers.HeadersImpl;

/**
 * Base class for data & capabilities common to both HTTP requests and responses
 */
public abstract class RequestResponseImpl implements RequestResponse {
	protected enum Type {request, response };
	protected Type type;

	protected HeadersImpl headers;
	protected String body = null;
	protected String method = null;
	protected String uri = null;
	protected Integer code = null;

	@Override
	public Headers getHeaders() {
		return headers;
	}

	@Override
	public String getBody() {
		return body;
	}

	@Override
	public String getUri() {
		return uri;
	}
}
