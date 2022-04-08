package com.luis.ravegram.service;

import com.luis.ravegram.exception.MailException;

public interface MailService {

	public void sendEmail(String from, String subject, String text, String...to)
		throws MailException;
}
