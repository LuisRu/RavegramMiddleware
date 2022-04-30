package com.luis.ravegram.service;

import java.util.ArrayList;
import java.util.List;

import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.SolicitudDTO;
import com.luis.ravegram.model.criteria.SolicitudCriteria;
import com.luis.ravegram.service.impl.SolicitudServiceImpl;

public class SolicitudServiceTest {
	
	private SolicitudService solicitudService = null;

	public SolicitudServiceTest() {
		solicitudService = new SolicitudServiceImpl();
		
	}

	public void leerLista(List<SolicitudDTO> a) {
		for (SolicitudDTO solicitud : a) {
			System.out.println(solicitud.getNombreUsuario()+" "+solicitud.getNombreEvento()+" "+solicitud.getNombreEstado());
		}
	}
	
	
	public void testFindByUsuarioEvento() {
		System.out.println("Testing testFindByUsuarioEvento...");
		SolicitudDTO solicitud = null;
		try {
			solicitud = solicitudService.findByUsuarioEvento(2L,2L);
			System.out.println(solicitud.getNombreEvento());
		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}	
	}
			 
			
	public void testUsuarioSolicita() {
		System.out.println("Testing testUsuarioSolicita...");
		try {
			solicitudService.usuarioSolicita(1L,2L); 
		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}	
	}
	
	
	public void testEventoInvita() {
		System.out.println("Testing testEventoInvita...");
		try {
			solicitudService.eventoInvita(2L,2L);
		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}	
	}
	
	public void testAnadirUsuarios() {
		System.out.println("Testing testAnadirUsuarios...");
		try {
			List<Long> idsUsuarios = new ArrayList<Long>();
			idsUsuarios.add(1L);
			idsUsuarios.add(2L);
			
		
			solicitudService.anadirUsuarios(2l, idsUsuarios); 
		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}	
	}
	
	public void testFindByCriteria() {
		System.out.println("Testing testFindByCriteria...");
		try {
			SolicitudCriteria sc = new SolicitudCriteria();
			sc.setIdEvento(1L);
			sc.setIdUsuario(1L);
			leerLista(solicitudService.findByCriteria(sc));
		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}	
	}
	
	public void testFindSolicitudesPendientes() {
		System.out.println("Testing testFindSolicitudesPendientes...");
		try {
			leerLista(solicitudService.findSolicitudesPendientes(1l));
		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}	
	}
	
	public void testFindInvitacionesPendientes() {
		System.out.println("Testing testFindInvitacionesPendientes...");
		try {
			leerLista(solicitudService.findInvitacionesPendientes(2l)); 
		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}	
	}
	
	
	
	
	

	public void testUpdate() {
		System.out.println("Testing update...");
		try {
			SolicitudCriteria sc = new SolicitudCriteria();
			sc.setIdEvento(2L);
			sc.setIdUsuario(2L);
			List<SolicitudDTO> solicitudes = solicitudService.findByCriteria(sc);
			SolicitudDTO solicitud = solicitudes.get(0);
			solicitud.setIdTipoEstado(2L);
			solicitudService.update(solicitud); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	
	

	
	public static void main(String args[]) {
		SolicitudServiceTest test = new SolicitudServiceTest();
		//test.testFindByUsuarioEvento();
		//test.testUsuarioSolicita();
	//	test.testEventoInvita();
//		test.testAnadirUsuarios();
	//	test.testFindByCriteria();
		//test.testFindSolicitudesPendientes();
		test.testFindInvitacionesPendientes();
//		test.testUpdate();
	}

}
