package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.TipoEstadoEvento;
import com.luis.ravegram.service.impl.TipoEstadoEventoServiceImpl;


public class TipoEstadoEventoServiceTest {

	private TipoEstadoEventoService tipoEstadoEventoService = null;

	public TipoEstadoEventoServiceTest() {
		tipoEstadoEventoService = new TipoEstadoEventoServiceImpl();
	}
	
	public void leerLista(List<TipoEstadoEvento> tee) {
		for (TipoEstadoEvento estado : tee) {
			System.out.println(estado.getNombre());
		}
	}

	

	public void testFindAll() {
		System.out.println("Testing find all...");
		List<TipoEstadoEvento> estadosEventos = null;
		try {
			estadosEventos = tipoEstadoEventoService.findAll(); 
			leerLista(estadosEventos);
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public static void main(String args[]) {
		TipoEstadoEventoServiceTest test = new TipoEstadoEventoServiceTest();
		test.testFindAll();
	}


}
