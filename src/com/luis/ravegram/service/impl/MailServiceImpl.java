package com.luis.ravegram.service.impl;

import org.apache.commons.mail.DefaultAuthenticator;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.exception.MailException;
import com.luis.ravegram.service.MailService;

public class MailServiceImpl implements MailService {

	

	private static Logger logger = LogManager.getLogger(MailServiceImpl.class);
	
	public MailServiceImpl() {
	}

	@Override
	public void sendEmail(String from, String subject, String text, String...to) throws MailException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("Sending from "+from+" to "+to+"...");
			}
			Email email = new SimpleEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator(from, "Cedeira1998"));
			email.setSSLOnConnect(true);
			email.setFrom(from);
			email.setSubject(subject);
			email.setMsg(text);
			email.addTo(to);
			email.send();
			logger.debug("Email sent");	
		} catch (EmailException e) {
			logger.error("Sending from "+from+" to "+to+"...", e);
			throw new MailException(e.getMessage(), e);
		}
		
	}
}
