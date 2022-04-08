package com.luis.ravegram.service;

import java.util.Date;
import java.util.List;

import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.EventoCriteria;
import com.luis.ravegram.model.EventoDTO;
import com.luis.ravegram.service.impl.EventoServiceImpl;

public class EventoServiceTest {

	private EventoService eventoService = null;
	private EventoDTO evento = null;

	public EventoServiceTest() {
		eventoService = new EventoServiceImpl();
	}
	
	public void leerLista(List<EventoDTO> a) {
		for (EventoDTO evento : a) {
			System.out.println(evento.getNombre());
		}
	}

	public void testFindiById() {
		System.out.println("Testing findById...");
		try {
			System.out.println(eventoService.findById(6L)); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public void testFindiBySeguidos() {
		System.out.println("Testing findBySeguidos...");
		try {
			System.out.println(eventoService.findyBySeguidos(1L,42.610069, -7.764830)); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public void testFindiByPendientes() {
		System.out.println("Testing findyPendientes...");
		try {
			System.out.println(eventoService.findByPendientes(1L,42.610069, -7.764830)); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public void testFindiByCreador() {
		System.out.println("Testing findByCreador...");
		try {
			leerLista(eventoService.findByCreador(1L));
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public void testFindiByEstablecimiento() {
		System.out.println("Testing findByEstablecimiento...");
		try {
			leerLista(eventoService.findByEstablecimiento(4L));
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public void testFindiByCriteria() {
		System.out.println("Testing findByCriteria...");
		EventoCriteria ec = new EventoCriteria();
		ec.setPublicPrivado(true);
		ec.setTipoEstablecimiento(1);
//		ec.setDistancia(10);
		try {
			leerLista(eventoService.findByCriteria(ec,1L,42.610069, -7.764830));
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	
	public void testCreate() {
		System.out.println("Testing create...");
		evento = new EventoDTO();
		long num = System.currentTimeMillis();
		evento.setNombre("Prueba"+num);
		evento.setDescripcion("Esto es una prueba");
		evento.setFechaHora((new Date()));
		evento.setPublicoPrivado(false);
		evento.setLatitud(2.324234);
		evento.setLongitud(22.45325);
		evento.setCalle("Calle Prueba");
		evento.setZip("12345");
		evento.setIdUsuario(1L);
		evento.setIdTipoEstablecimiento(1L);
		evento.setIdLocalidad(1L);
		evento.setIdTipoEstadoEvento(1L);
		try {
			eventoService.create(evento);
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public void testUpdate() {
		System.out.println("Testing update...");
		evento = new EventoDTO();
		long num = System.currentTimeMillis();
		try {
			evento = eventoService.findById(1L);
			evento.setNombre("Prueba"+num);
			System.out.println(eventoService.update(evento)); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public void testUpdateEstado() {
		System.out.println("Testing update estado...");
		evento = new EventoDTO();
		try {
			System.out.println(eventoService.updateEstado(1l,2l)); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	
	public void testEliminadoEventoConEmail() {
		System.out.println("Testing eliminado evento con email y cambiando solicitudes...");
		try {
			eventoService.eliminadoEventoConEmail(1L); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	
	public static void main(String args[]) {
		EventoServiceTest test = new EventoServiceTest();
		test.testFindiById();
		//test.testFindiBySeguidos();
		//test.testFindiByPendientes();
		//test.testFindiByCreador();
		//test.testFindiByEstablecimiento();
		//test.testFindiByCriteria();
		//test.testCreate();
		//test.testUpdate();
		//test.testUpdateEstado();
		//test.testEliminadoEventoConEmail();
	}
}
