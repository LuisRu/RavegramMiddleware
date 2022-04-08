package com.luis.ravegram.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import com.luis.ravegram.exception.InvalidUserOrPasswordException;
import com.luis.ravegram.exception.MailException;
import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.exception.UserAlreadyExistsException;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioCriteria;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.service.impl.EventoServiceImpl;
import com.luis.ravegram.service.impl.UsuarioServiceImpl;

public class UsuarioServiceTest {

	private UsuarioService usuarioService = null;
	private EventoService eventoService = null;
	private UsuarioDTO usuario = null;

	public UsuarioServiceTest() {
		usuarioService = new UsuarioServiceImpl();
		eventoService = new EventoServiceImpl();
	}

	public void leerLista(List<UsuarioDTO> a) {
		for (UsuarioDTO usuario : a) {
			System.out.println(usuario.getUserName());
		}
	}


	public void testFindById() {
		System.out.println("Testing find by ID...");
		try {
			System.out.println(usuarioService.findById(8L)); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}


	public void testFindByIds() {
		System.out.println("Testing find by Ids...");
		try {
			List<Long> idsSeguidores = new ArrayList<Long>();
			for (UsuarioDTO usuario : usuarioService.findSeguidores(1L)) {
				idsSeguidores.add(usuario.getId());
			}
			for (Long long1 : idsSeguidores) {
				System.out.println(long1);
			}
			leerLista(usuarioService.findByIds(idsSeguidores));  
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}



	public void testFindAsistentes() {
		System.out.println("Testing findAsistes...");
		try {
			leerLista(usuarioService.findAsistentes(1L));
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}


	public void testFindSeguidores() {
		System.out.println("Testing findSeguidores...");
		try {
			leerLista(usuarioService.findSeguidores(1L));
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}

	public void testFindSeguidos() {
		System.out.println("Testing findSeguidores...");
		try {
			leerLista(usuarioService.findSeguidos(1L));
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}



	public void testFindSeguidosIds() {
		System.out.println("Testing FindSeguidosIds...");
		try {
			Set<Long> usuariosIds = null;
			usuariosIds=usuarioService.findSeguidosIds(1L);
			for (Long ids:usuariosIds) {
				System.out.println(ids);
			}
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}

	public void testFindSeguidosMutuamente() {
		System.out.println("Testing find seguidos mutuamente...");
		try {
			leerLista(usuarioService.findSeguidosMutuamente(1L));
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}

	public void testFindByCriteria() {
		System.out.println("Testing find by criteria...");
		UsuarioCriteria uc = new UsuarioCriteria();
		UsuarioDTO usuario = null;
		double latitud=42.611796d;
		double longitud=-7.773983d;
		try {
			System.out.println(usuario);
			uc.setIdBuscador(1L);
			uc.setBusqueda("l");
			int startIndex = 1;
			int pageSize = 5;
			Results<UsuarioDTO> results = null;
		
			do {
				results = usuarioService.findByCriteria(uc,startIndex,pageSize);
 
				System.out.println("Encontrados "+results.getTotal()+" resultados");
				System.out.println("Mostrando del "+startIndex+" al "+(startIndex+results.getData().size()-1));
				startIndex = startIndex+results.getData().size();
				for (UsuarioDTO u: results.getData()) {
					System.out.println("\t"+u);
				}
				
			} while (startIndex<=results.getTotal());
			
			
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
		usuario = new UsuarioDTO();
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




//	public void testCompartir() {
//		System.out.println("Testing compartir...");
//		List<Long> idsAmigos = new ArrayList<Long>();
//		idsAmigos.add(18L);
//		idsAmigos.add(1L);
//		idsAmigos.add(3L);
//		idsAmigos.add(4L);
//		idsAmigos.add(5L);
//		try {
//			eventoService.compartir(2L, 2L, idsAmigos);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}


	public void testUpdate() {
		System.out.println("Testing update...");
		Calendar c = Calendar.getInstance();		
		c.set(Calendar.YEAR, c.get(Calendar.YEAR),c.get(Calendar.HOUR));	
		try {
			usuario = usuarioService.findById(1L);
			usuario.setBiografia("Prueba dia");
			usuarioService.update(usuario);
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
		//test.testFindById();
		//test.testFindByIds();
		//test.testFindAsistentes();
		//test.testFindSeguidores();
//		test.testFindSeguidos();
//		test.testFindSeguidsosIds();
		//test.testFindSeguidosMutuamente();
		//test.testFindByEmail();
		test.testFindByCriteria();
		//test.testLogin();
		//test.testSignUp();
		//test.testCompartir();
		//test.testUpdate();
		//test.testUpdateEstado();
		//test.testUpdateUbicacion();



	}
}
