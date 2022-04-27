package com.luis.ravegram.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.EventoDTO;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.criteria.EventoCriteria;
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


	public void testFindById() {
		System.out.println("Testing FindById...");
		EventoDTO evento = null;
		try {
			evento =  eventoService.findById(2L, 20.234567D, 20.234567D);
			System.out.println(evento.getNombre());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testFindBySeguidosDisponibles() {
		System.out.println("Testing FindBySeguidosDisponibles...");
		try {
			eventoService.findBySeguidosDisponibles(1L, 20.234567D, 20.234567D, 1L, 10);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testFindByCriteria() {
		System.out.println("Testing find by criteria...");
		EventoCriteria ec = new EventoCriteria();
		Results<EventoDTO> results = null;
		try {
			ec.setDescartarInteractuados(true);
			ec.setIdBuscador(1L);
			ec.setLatitudBuscador(20.234567D);
			ec.setLongitudBuscador(20.234567D);
			results = eventoService.findByCriteria(ec,1,10);
			leerLista(results.getData());
		}catch (Exception e) {
			e.printStackTrace();
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
		
		List<Long> idsAsistentes = new ArrayList<Long>();
		idsAsistentes.add(2L);
		idsAsistentes.add(3L);
		try {
			eventoService.create(evento,idsAsistentes);
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}

	public void testUpdate() {
		System.out.println("Testing update...");
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
		evento.setId(1L);
		try {
			evento.setNombre("Prueba"+num);

		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}	
	}

	public void testUpdateEstado() {
		System.out.println("Testing update estado...");
		try {

			System.out.println(eventoService.updateEstado(1l,2l));
			for (int i = 0; i<10; i++) {
				long t0 = System.currentTimeMillis();
				System.out.println(eventoService.updateEstado(1l,2l));
				long t1 = System.currentTimeMillis();

				System.out.println("UpdateEstado: "+(t1-t0));
			}
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}

	

	public void testCompartir() {
		System.out.println("Testing compartir...");
		try {
			List<Long> listaAmigos = new ArrayList<Long>();
			listaAmigos.add(12L);
			eventoService.compartir(1l, 3l,20.000D, 20.0000D, listaAmigos);
			
		}catch (Exception se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}



	public static void main(String args[]) {
		EventoServiceTest test = new EventoServiceTest();
		//		test.testFindBySeguidosDisponibles();
		//		test.testFindByCriteria();
			//test.testCreate();
		//		test.testUpdate();
		//test.testUpdateEstado();
		test.testCompartir();
	}
}
