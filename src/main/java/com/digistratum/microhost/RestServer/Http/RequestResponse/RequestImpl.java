package com.digistratum.microhost.RestServer.Http.RequestResponse;

import com.digistratum.microhost.Exception.MHException;
import com.digistratum.microhost.RestServer.Http.Headers.Headers;
import com.digistratum.microhost.RestServer.Http.Headers.HeadersImpl;
import com.digistratum.microhost.RestServer.Http.HttpSpec;
import com.digistratum.microhost.RestServer.Http.MimeTypes;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RequestImpl extends RequestResponseImpl implements Request {
	final static Logger log = Logger.getLogger(RequestImpl.class);
	/**
	 * Bodyless RequestImpl Constructor
	 *
	 * @param requestMethod String representation of HTTP request method (e.g. get, post, delete, etc.)
	 * @param requestUri HTTP request URI string
	 * @param requestHeaders Headers implementation instance to hold HTTP request headers
	 */
	public RequestImpl(String requestMethod, String requestUri, Headers requestHeaders) throws MHException {
		this(requestMethod, requestUri, requestHeaders, null, null);
	}

	/**
	 * RequestImpl Constructor
	 *
	 * @param requestMethod String representation of HTTP request method (e.g. get, post, delete, etc.)
	 * @param requestUri HTTP request URI string
	 * @param requestHeaders Headers implementation instance to hold HTTP request headers
	 * @param requestBody HTTP request body (optional)
	 */
	public RequestImpl(String requestMethod, String requestUri, Headers requestHeaders, String requestBody, String queryString) throws MHException {

		// This is a REQUEST
		type = Type.request;

		// Check the request method
		if ((null == requestMethod) || requestMethod.isEmpty()) {
			throw new MHException("RequestResponseImpl() - supplied request method was empty");
		}
		// TODO: Check that the request method is one which we support
		method = requestMethod;

		// Check the URI
		if ((null == requestUri) || requestUri.isEmpty()) {
			throw new MHException("RequestResponseImpl() - supplied URI was empty");
		}
		uri = requestUri;

		// Check the headers
		if (null == requestHeaders) {
			throw new MHException("RequestResponseImpl() - headers were not supplied");
		}
		headers = (HeadersImpl) requestHeaders;

		// Grab the raw body (empty is valid fo requests such as with GET)
		body = requestBody;

		// If this request is one which supports a body
		if (HttpSpec.getBodyMethods().contains(method.toUpperCase())) {

			// And if the body is form encoded...
			if (requestHeaders.has(HttpSpec.HEADER_CONTENT_TYPE)) {
				String contentTypeHeader = requestHeaders.get(HttpSpec.HEADER_CONTENT_TYPE).toLowerCase();
				if (contentTypeHeader.equals(MimeTypes.APPLICATION_X_WWW_FORM_URLENCODED.toLowerCase())) {

					// Split the body into separate params
					bodyParams = splitEncodedString(body);
				}
			}
		}

		// Capture the query params
		this.queryString = queryString;
		queryParams = splitEncodedString(this.queryString);
	}

	/**
	 * Split the supplied encoded string into name/value pairs, etc.
	 *
	 * The same encoding method is used for POST/PUT/PATCH data as in a GET query string
	 *
	 * ref: https://stackoverflow.com/questions/13592236/parse-a-uri-string-into-name-value-collection
	 *
	 * @param queryString String Query String fragment of the URL
	 *
	 * @return Map of parameter names to a list of values for each one
	 *
	 * @throws MHException For any errors
	 */
	protected Map<String, List<String>> splitEncodedString(String queryString) throws MHException {
		final Map<String, List<String>> queryPairs = new LinkedHashMap<>();
		if ((null == queryString) || queryString.isEmpty()) return queryPairs;
		final String[] pairs = queryString.split("&");
		try {
			for (String pair : pairs) {
				final int idx = pair.indexOf("=");
				final String key = (idx > 0) ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
				if (! queryPairs.containsKey(key)) {
					queryPairs.put(key, new LinkedList<>());
				}
				final String value = ((idx > 0) && (pair.length() > (idx + 1))) ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
				queryPairs.get(key).add(value);
				//log.debug("Adding query string param: " + key + "=" + value);
			}
		} catch (UnsupportedEncodingException e) {
			throw new MHException("Failed to decode encoded string fragment", e);
		}
		return queryPairs;
	}

	@Override
	public String getRequestMethod() {
		return method;
	}


	@Override
	public String getUri() {
		return uri;
	}

	@Override
	public Boolean hasQueryParam(String name) {
		if (null == queryParams) return false;
		return queryParams.containsKey(name);
	}

	@Override
	public List<String> getQueryParam(String name) {
		return (hasQueryParam(name)) ? queryParams.get(name) : null;
	}

	@Override
	public String getQueryString() {
		return queryString;
	}

	@Override
	public Boolean hasBodyParam(String name) {
		if (null == bodyParams) return false;
		return bodyParams.containsKey(name);
	}

	@Override
	public List<String> getBodyParam(String name) {
		return (hasBodyParam(name)) ? bodyParams.get(name) : null;
	}
}
