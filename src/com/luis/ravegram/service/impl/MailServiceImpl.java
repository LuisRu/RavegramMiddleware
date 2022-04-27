package com.luis.ravegram.service.impl;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.util.ConfigurationManager;
import com.luis.ravegram.exception.MailException;
import com.luis.ravegram.service.MailService;

public class MailServiceImpl implements MailService {

	private static final String CFGM_PFX = "service.mail.";
    private static final String SERVER = CFGM_PFX + "server";
    private static final String PORT = CFGM_PFX + "port";
    private static final String ACCOUNT = CFGM_PFX + "account";
    private static final String PASSWORD = CFGM_PFX + "password";
    
    public static final String WEB_RAVEGRAM_PROPERTIES = "ravegram-config";
    
    ConfigurationManager cfgM = ConfigurationManager.getInstance();

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
			email.setHostName((cfgM.getParameter(WEB_RAVEGRAM_PROPERTIES, SERVER)));
			email.setSmtpPort(Integer.valueOf(cfgM.getParameter(WEB_RAVEGRAM_PROPERTIES, PORT)));
			email.setAuthenticator(new DefaultAuthenticator(cfgM.getParameter(WEB_RAVEGRAM_PROPERTIES, ACCOUNT), cfgM.getParameter(WEB_RAVEGRAM_PROPERTIES, PASSWORD)));
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
