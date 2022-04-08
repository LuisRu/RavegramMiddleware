package com.luis.ravegram.exception;

public class UserAlreadyExistsException extends ServiceException {

	public UserAlreadyExistsException(String email) {
		super(email);
	}

}
