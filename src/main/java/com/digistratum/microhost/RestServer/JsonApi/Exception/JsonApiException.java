package com.digistratum.microhost.RestServer.JsonApi.Exception;

public class JsonApiException extends Exception {
	public JsonApiException(String message) { super(message); }
	public JsonApiException(String message, Throwable cause) { super(message, cause); }
	public JsonApiException(Throwable cause) { super(cause); }
	public JsonApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
