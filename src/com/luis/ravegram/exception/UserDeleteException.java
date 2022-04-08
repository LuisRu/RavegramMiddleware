package com.luis.ravegram.exception;

public class UserDeleteException extends ServiceException {

	public UserDeleteException(String email) {
		super(email);
	}

}
