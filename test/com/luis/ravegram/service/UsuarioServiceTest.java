package com.luis.ravegram.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.luis.ravegram.exception.InvalidUserOrPasswordException;
import com.luis.ravegram.exception.MailException;
import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.exception.UserAlreadyExistsException;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.model.criteria.UsuarioCriteria;
import com.luis.ravegram.service.impl.UsuarioServiceImpl;

public class UsuarioServiceTest {

	private UsuarioService usuarioService = null;

	public UsuarioServiceTest() {
		usuarioService = new UsuarioServiceImpl();
	}

	public void leerLista(List<UsuarioDTO> a) {
		for (UsuarioDTO usuario : a) {
			System.out.println(usuario.getUserName());
		}
	}


	public void testFindById() {
		System.out.println("Testing testFindById..");
		UsuarioDTO usuario = null;
		try {
			usuario = usuarioService.findById(2L);
			System.out.println(usuario.getUserName());
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}

	
	public void testFindByIds() {
		System.out.println("Testing testFindByIds..");
		List<Long> idsUsuarios = new ArrayList<Long>();
		idsUsuarios.add(1L);
		idsUsuarios.add(4L);
		idsUsuarios.add(3L);
	
		
		try {
			System.out.println(usuarioService.findByIds(idsUsuarios)); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}


	public void testFindSeguidoresNoAceptadoEvento() {
		System.out.println("Testing FindSeguidoresNoAceptadoEvento...");
		try {
			leerLista(usuarioService.findSeguidoresNoAceptadoEvento(2L,1L));
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}

	public void testSeguidosIds() {
		System.out.println("Testing testSeguidosIds...");
		try {
			for (Long id : usuarioService.findSeguidosIds(1L)) {
				System.out.println(id);
			}
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	
	public void testFindByEmail() {
		System.out.println("Testing find by email...");
		try {
			System.out.println(usuarioService.findByEmail("luisrubidoloureiro@hotmail.com")); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public void testFindSeguidores() {
		System.out.println("Testing testFindSeguidores...");
		try {
			leerLista(usuarioService.findSeguidores(2L));
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public void testFindSeguidos() {
		System.out.println("Testing testFindSeguidos...");
		try {
			leerLista(usuarioService.findSeguidos(2L));
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}




	public void testFindByCriteria() {
		System.out.println("Testing find by criteria...");
		UsuarioCriteria uc = new UsuarioCriteria();
		int startIndex = 1;
		int pageSize = 5;
		Results<UsuarioDTO> results = null;
		try {
			results = usuarioService.findByCriteria(uc, startIndex, pageSize);
			leerLista(results.getData());
		}catch (Exception se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}




	public void testLogin() {
		System.out.println("Testing login...");

		try {
			UsuarioDTO usuario = usuarioService.login("luis.jorr@gmail.com", "aefea1998");	
			System.out.println("Bienvenido "+usuario.getUserName());	 
		}catch (InvalidUserOrPasswordException iupe) {
			iupe.printStackTrace();
		}catch (ServiceException se) {
			se.printStackTrace();
		}
	}




	public void testSignUp() {
		System.out.println("Testing signUp...");
		UsuarioDTO usuario = new UsuarioDTO();
		Calendar c = Calendar.getInstance();		
		c.set(Calendar.YEAR, c.get(Calendar.YEAR)-20);

		long num = System.currentTimeMillis();
		usuario.setUserName("manuela"+num);
		usuario.setEmail(usuario.getUserName()+"@gmail.com");
		usuario.setContrasena("abc12345");
		usuario.setFechaNacimiento(c.getTime());
		usuario.setLatitud(1d);
		usuario.setLongitud(1d);
		usuario.setTelefono("692311234");
		usuario.setBiografia("Hola esto es un test");
		System.out.println("Creando usuario "+usuario.getEmail());
		try {

			usuarioService.signUp(usuario);

		} catch (UserAlreadyExistsException uae) {		
			System.out.println("El usuario ya existe.");
			uae.printStackTrace();
		} catch (MailException me) {
			System.out.println("Ha habido un problema al enviar el e-mail.");
			me.printStackTrace();
		} catch (ServiceException se) {
			System.out.println(se.getMessage());
			se.printStackTrace();
		}
	}


	public void testUpdate() {
		System.out.println("Testing update...");
		UsuarioDTO usuario = new UsuarioDTO();
		UsuarioDTO usuarioSesion = new UsuarioDTO();
		Calendar c = Calendar.getInstance();		
		c.set(Calendar.YEAR, c.get(Calendar.YEAR)-20);

		long num = System.currentTimeMillis();
		try {
			usuario.setUserName("manuela"+num);
			usuario.setEmail(usuario.getUserName()+"@g23434mail.com");
			usuario.setContrasena("abc12345");
			usuario.setFechaNacimiento(c.getTime());
			usuario.setLatitud(1d);
			usuario.setLongitud(1d);
			usuario.setTelefono("692311234");
			usuario.setBiografia("Hola esto es un test");
			usuario.setId(1L);
			
			usuarioSesion.setUserName("manuela"+num);
			usuarioSesion.setEmail(usuario.getUserName()+"@g23434mail.com");
			usuarioSesion.setContrasena("abc12345");
			usuarioSesion.setFechaNacimiento(c.getTime());
			usuarioSesion.setLatitud(1d);
			usuarioSesion.setLongitud(1d);
			usuarioSesion.setTelefono("692311234");
			usuarioSesion.setBiografia("Hola esto es un test");
			usuarioSesion.setId(1L);
			
			usuarioService.update(usuario,usuarioSesion);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void testUpdateEstado() {
		System.out.println("Testing update estado...");
		try {
			usuarioService.updateEstado(1L,2);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void testUpdateUbicacion() {
		System.out.println("Testing update ubicacion...");
		try {
			usuarioService.updateUbicacion(40.000D, 40.000D, 1L);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}







	public static void main(String args[]) {
		UsuarioServiceTest test = new UsuarioServiceTest();
		test.testFindById();
//		test.testFindByIds();
//		test.testFindSeguidoresNoAceptadoEvento();
//		test.testSeguidosIds();
//		test.testFindByEmail();
//		test.testFindSeguidores();
//		test.testFindSeguidos();
//		test.testFindByCriteria();
//		//test.testLogin();
		//test.testSignUp();
		//test.testUpdate();
		//test.testUpdateEstado();
		//test.testUpdateUbicacion();



	}
}
