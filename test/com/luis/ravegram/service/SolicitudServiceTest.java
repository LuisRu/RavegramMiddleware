package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.EventoDTO;
import com.luis.ravegram.model.SolicitudDTO;
import com.luis.ravegram.service.impl.SolicitudServiceImpl;

public class SolicitudServiceTest {
	
	private SolicitudService solicitudService = null;
	private SolicitudDTO solicitud = null;

	public SolicitudServiceTest() {
		solicitudService = new SolicitudServiceImpl();
		solicitud = new SolicitudDTO();
		
	}

	public void leerLista(List<SolicitudDTO> a) {
		for (SolicitudDTO solicitud : a) {
			System.out.println(solicitud.getNombreUsuario()+" "+solicitud.getNombreEvento()+" "+solicitud.getNombreEstado());
		}
	}

	public void testSolicitudDeUsuarioAEvento() {
		System.out.println("Testing solicitud de usuario a evento...");
		try {
			solicitud.setIdUsuario(1L);
			solicitud.setIdEvento(2L);
			solicitudService.solicitudDeUsuarioAEvento(solicitud); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	
	public void testSolicitudDeEventoAUsuario() {
		System.out.println("Testing solicitud de evento a usuario...");
		try {
			solicitud.setIdUsuario(10L);
			solicitud.setIdEvento(2L);
			solicitudService.solicitudDeEventoAUsuario(solicitud); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public void testAceptarSolicitud() {
		System.out.println("Testing aceptar solicitud...");
		try {
			solicitudService.aceptarSolicitud(solicitudService.findByIdUsuarioIdEvento(1L,2L));
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public void testHistorialSolicitudes() {
		System.out.println("Testing historial solicitudes...");
		try {
			leerLista(solicitudService.historialSolicitudes(1L));
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public void testFindByIdUsuarioIdEvento() {
		System.out.println("Testing find by IdUsuario y IdEvento...");
		try {
			System.out.println(solicitudService.findByIdUsuarioIdEvento(1L,2L)); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public void testSolicitudesAMisEventosPendientesDeAprobar() {
		System.out.println("Testing find solicitudes a mis eventos pendientes de aprobar...");
		try {
			leerLista(solicitudService.solicitudesAMisEventosPendientes(2L,0D,0D)); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public void testInvitacioneAEventosPendientesDeAprobar() {
		System.out.println("Testing find invitaciones a eventos pendientes de aprobar...");
		try {
			leerLista(solicitudService.invitacionesAEventosPendientesDeAprobar(1L)); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	

	public void testUpdate() {
		System.out.println("Testing update...");
		try {
			SolicitudDTO solicitud = solicitudService.findByIdUsuarioIdEvento(1L, 2L);
			solicitud.setIdTipoEstado(2L);
			solicitudService.update(solicitud); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public void testUpdateEstadoSolicitudesEvento() {
		System.out.println("Testing find invitaciones a eventos pendientes de aprobar...");
		try {
			solicitudService.updateEstadoSolicitudesEvento(2L, 3L); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public static void main(String args[]) {
		SolicitudServiceTest test = new SolicitudServiceTest();
//		test.testSolicitudDeUsuarioAEvento();
//		test.testSolicitudDeEventoAUsuario();
//		test.testAceptarSolicitud();
//		test.testHistorialSolicitudes();
//		test.testFindByIdUsuarioIdEvento();
test.testSolicitudesAMisEventosPendientesDeAprobar();
//		test.testInvitacioneAEventosPendientesDeAprobar();
//		test.testUpdate();
	//	test.testUpdateEstadoSolicitudesEvento();
	}

}
