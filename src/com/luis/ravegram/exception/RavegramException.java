package com.luis.ravegram.exception;

public class RavegramException extends Exception {

	public RavegramException() {
		super();
	}

	
	public RavegramException(String message) {
		super(message);
	}
	
	
	public RavegramException(Throwable cause) {
		super(cause);
	}
	
	
	public RavegramException(String message, Throwable cause) {
		super(message, cause);
	}
}
