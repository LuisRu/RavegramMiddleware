package com.luis.ravegram.service;

import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.service.impl.UsuarioSigueServiceImpl;

public class UsuarioSigueServiceTest {

	private UsuarioSigueService usuarioSigueService = null;

	public UsuarioSigueServiceTest() {
		usuarioSigueService = new UsuarioSigueServiceImpl();
	}

	public void testUnFollow() {
		System.out.println("Testing delete...");
		try {
			usuarioSigueService.unFollow(1, 3); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}

	public void testFollow() {
		System.out.println("Testing create...");
		try {
			usuarioSigueService.follow(1, 3); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public static void main(String args[]) {
		UsuarioSigueServiceTest test = new UsuarioSigueServiceTest();
		test.testUnFollow();
		test.testFollow();
	}


}
