package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.Establecimiento;
import com.luis.ravegram.model.EstablecimientoCriteria;
import com.luis.ravegram.service.impl.EstablecimientoServiceImpl;

public class EstablecimientoServiceTest {
	
	private EstablecimientoService establecimientoService = null;

	public EstablecimientoServiceTest() {
		establecimientoService = new EstablecimientoServiceImpl();
		
	}
	
	
	public void leerLista(List<Establecimiento> e) {
		for (Establecimiento es : e) {
			System.out.println(es.getNombre());
		}
	}
	
	public void testFindById() {
		System.out.println("Testing find by id....");
		try {
			System.out.println(establecimientoService.findById(1L)); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public void testFindLocalidad() {
		System.out.println("Testing find by localidad....");
		try {
			System.out.println(establecimientoService.findByLocalidad(1L)); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	public void testFindCriteria() {
		System.out.println("Testing find by criteria....");
		EstablecimientoCriteria ec = new EstablecimientoCriteria();
		ec.setTipoEstablecimiento(5);
		ec.setDistancia(10);
		try {
			leerLista(establecimientoService.findByCriteria(ec, 43.659751, -8.055048)); 
		}catch (ServiceException se) {
			System.out.println("Error");
			se.printStackTrace();
		}	
	}
	
	
	
	
	public static void main(String args[]) {
		EstablecimientoServiceTest test = new EstablecimientoServiceTest();
//		test.testFindById();
//		test.testFindLocalidad();
		test.testFindCriteria();
		
	}

}
