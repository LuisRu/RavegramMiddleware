package com.luis.ravegram.exception;

public class InvalidUserOrPasswordException extends ServiceException {

	public InvalidUserOrPasswordException(String email) {
		super(email);
	}

}
