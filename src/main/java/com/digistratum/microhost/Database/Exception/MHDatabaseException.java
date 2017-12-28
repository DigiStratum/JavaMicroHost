package com.digistratum.microhost.Database.Exception;

import com.digistratum.microhost.Exception.MHException;

public class MHDatabaseException extends MHException {
	public MHDatabaseException(String message) { super(message); }
	public MHDatabaseException(String message, Throwable cause) { super(message, cause); }
	public MHDatabaseException(Throwable cause) { super(cause); }
	public MHDatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
