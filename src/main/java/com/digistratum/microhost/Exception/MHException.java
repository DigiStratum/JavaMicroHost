package com.digistratum.microhost.Exception;

public class MHException extends Exception {
	public MHException(String message) { super(message); }
	public MHException(String message, Throwable cause) { super(message, cause); }
	public MHException(Throwable cause) { super(cause); }
	public MHException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
