package com.digistratum.microhost.Exception;
@SuppressWarnings("serial")

public class MHDatabaseException extends MHException {
	public MHDatabaseException(String message) { super(message); }
	public MHDatabaseException(String message, Throwable cause) { super(message, cause); }
	public MHDatabaseException(Throwable cause) { super(cause); }
	public MHDatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
