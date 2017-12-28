package com.digistratum.microhost.RestServer.Http;

import java.util.LinkedList;
import java.util.List;

/**
 * ref: https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
 */
public class HttpSpec {

	// Methods
	public static final String HTTP_METHOD_GET = "GET";
	public static final String HTTP_METHOD_POST = "POST";
	public static final String HTTP_METHOD_PUT = "PUT";
	public static final String HTTP_METHOD_PATCH = "PATCH";
	public static final String HTTP_METHOD_DELETE = "DELETE";
	public static final String HTTP_METHOD_OPTIONS = "OPTIONS";
	public static final String HTTP_METHOD_HEAD = "HEAD";

	// Informational
	public static final int HTTP_STATUS_100_CONTINUE = 100;

	// Success
	public static final int HTTP_STATUS_200_OK = 200;
	public static final int HTTP_STATUS_201_CREATED = 201;
	public static final int HTTP_STATUS_202_ACCEPTED = 202;
	public static final int HTTP_STATUS_204_NO_CONTENT = 204;
	public static final int HTTP_STATUS_206_PARTIAL_CONTENT = 206;

	// Redirection
	public static final int HTTP_STATUS_301_MOVED_PERMANENTLY = 301;
	public static final int HTTP_STATUS_302_FOUND = 302;
	public static final int HTTP_STATUS_304_NOT_MODIFIED = 304;
	public static final int HTTP_STATUS_307_TEMPORARY_REDIRECT = 307;
	public static final int HTTP_STATUS_308_PERMANENT_REDIRECT = 308;

	// Client Errors
	public static final int HTTP_STATUS_400_BAD_REQUEST = 400;
	public static final int HTTP_STATUS_401_UNAUTHORIZED = 401;
	public static final int HTTP_STATUS_403_FORBIDDEN = 403;
	public static final int HTTP_STATUS_404_NOT_FOUND = 404;
	public static final int HTTP_STATUS_405_METHOD_NOT_ALLOWED = 405;
	public static final int HTTP_STATUS_406_NOT_ACCEPTABLE = 406;
	public static final int HTTP_STATUS_409_CONFLICT = 409;
	public static final int HTTP_STATUS_410_GONE = 410;
	public static final int HTTP_STATUS_411_LENGTH_REQUIRED = 411;
	public static final int HTTP_STATUS_412_PRECONDITION_FAILED = 412;
	public static final int HTTP_STATUS_413_REQUEST_ENTITY_TOO_LARGE = 413;
	public static final int HTTP_STATUS_415_UNSUPPORTED_MEDIA_TYPE = 415;
	public static final int HTTP_STATUS_416_REQUESTED_RANGE_NOT_SATISFIED = 416;
	public static final int HTTP_STATUS_417_EXPECTATION_FAILED = 417;
	public static final int HTTP_STATUS_428_PRECONDITION_REQUIRED = 428;
	public static final int HTTP_STATUS_429_TOO_MANY_REQUESTS = 429;

	// Server Errors
	public static final int HTTP_STATUS_500_INTERNAL_SERVER_ERROR = 500;
	public static final int HTTP_STATUS_501_NOT_IMPLEMENTED = 501;
	public static final int HTTP_STATUS_502_BAD_GATEWAY = 502;
	public static final int HTTP_STATUS_503_SERVICE_UNAVAILABLE = 503;
	public static final int HTTP_STATUS_504_GATEWAY_TIMEOUT = 504;

	// Headers
	public static final String HEADER_CONTENT_TYPE = "Content-Type";

	/**
	 * Get the list of request methods which support a body payload
	 *
	 * @return List of request method strings
	 */
	public static List<String> getBodyMethods() {
		List<String> methods = new LinkedList<>();
		methods.add(HTTP_METHOD_POST);
		methods.add(HTTP_METHOD_PUT);
		methods.add(HTTP_METHOD_PATCH);
		return methods;
	}

	/**
	 * Get a short description for the specified HTTP status code
	 *
	 * @param statusCode Int HTTP status code from our list of supported codes
	 *
	 * @return String brief description for this error code
	 */
	public static String getStatusDescription(int statusCode) {
		switch (statusCode) {
			case HTTP_STATUS_100_CONTINUE: return "Continue";
			case HTTP_STATUS_200_OK: return "OK";
			case HTTP_STATUS_201_CREATED: return "Created";
			case HTTP_STATUS_202_ACCEPTED: return "Accepted";
			case HTTP_STATUS_204_NO_CONTENT: return "No Content";
			case HTTP_STATUS_206_PARTIAL_CONTENT: return "Partial Content";
			case HTTP_STATUS_301_MOVED_PERMANENTLY: return "Moved Permanently";
			case HTTP_STATUS_302_FOUND: return "Found";
			case HTTP_STATUS_304_NOT_MODIFIED: return "Not Modified";
			case HTTP_STATUS_307_TEMPORARY_REDIRECT: return "Temporary Redirect";
			case HTTP_STATUS_308_PERMANENT_REDIRECT: return "Permanent Redirect";
			case HTTP_STATUS_400_BAD_REQUEST: return "Bad Request";
			case HTTP_STATUS_401_UNAUTHORIZED: return "Unauthorized";
			case HTTP_STATUS_403_FORBIDDEN: return "Forbidden";
			case HTTP_STATUS_404_NOT_FOUND: return "Not Found";
			case HTTP_STATUS_405_METHOD_NOT_ALLOWED: return "Method Not Allowed";
			case HTTP_STATUS_406_NOT_ACCEPTABLE: return "Not Acceptable";
			case HTTP_STATUS_409_CONFLICT: return "Conflict";
			case HTTP_STATUS_410_GONE: return "Gone";
			case HTTP_STATUS_411_LENGTH_REQUIRED: return "Length Required";
			case HTTP_STATUS_412_PRECONDITION_FAILED: return "Precondition Failed";
			case HTTP_STATUS_413_REQUEST_ENTITY_TOO_LARGE: return "Request Entity Too Large";
			case HTTP_STATUS_415_UNSUPPORTED_MEDIA_TYPE: return "Unsupported Media Type";
			case HTTP_STATUS_416_REQUESTED_RANGE_NOT_SATISFIED: return "Requested Range Not Satisfied";
			case HTTP_STATUS_417_EXPECTATION_FAILED: return "Expectation Failed";
			case HTTP_STATUS_428_PRECONDITION_REQUIRED: return "Precondition Required";
			case HTTP_STATUS_429_TOO_MANY_REQUESTS: return "Too Many Requests";
			case HTTP_STATUS_500_INTERNAL_SERVER_ERROR: return "Internal Server Error";
			case HTTP_STATUS_501_NOT_IMPLEMENTED: return "Not Implemented";
			case HTTP_STATUS_502_BAD_GATEWAY: return "Bad Gateway";
			case HTTP_STATUS_503_SERVICE_UNAVAILABLE: return "Service Unavailable";
			case HTTP_STATUS_504_GATEWAY_TIMEOUT: return "Gateway Timeout";
		}
		return null;
	}
}
