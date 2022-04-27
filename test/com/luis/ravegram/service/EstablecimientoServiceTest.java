package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.model.EstablecimientoDTO;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.criteria.EstablecimientoCriteria;
import com.luis.ravegram.service.impl.EstablecimientoServiceImpl;

public class EstablecimientoServiceTest {
	
	private EstablecimientoService establecimientoService = null;

	public EstablecimientoServiceTest() {
		establecimientoService = new EstablecimientoServiceImpl();
		
	}
	
	
	public void leerLista(Results<EstablecimientoDTO> results) {
		List<EstablecimientoDTO> establecimientos = results.getData();
		for (EstablecimientoDTO es : establecimientos) {
			System.out.println("Resultado: "+es.getNombre());
		}
	}
	
	
	public void testFindById() {
		System.out.println("Testing testFindById....");
		EstablecimientoDTO establecimiento = null;
		try {
			establecimiento = establecimientoService.findById(2L);
			System.out.println(establecimiento.getNombre());
		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}	
	}
	

	public void testFindCriteria() {
		System.out.println("Testing find by criteria....");
		EstablecimientoCriteria ec = new EstablecimientoCriteria();
		try {
			ec.setOrderBy("AFORO-ASC");
			ec.setIdTipoEstablecimiento(6);
			ec.setIdLocalidad(4);
			leerLista(establecimientoService.findByCriteria(ec,1,10));
	
		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}	
	}
	
	
	public void testCreate() {
		System.out.println("Testing create....");
		EstablecimientoDTO establecimiento = new EstablecimientoDTO();
		try {
			establecimiento.setNombre("Prueba");
			establecimiento.setCalle("Prueba calle");
			establecimiento.setZip("15350");
			establecimiento.setAforo(100);
			establecimiento.setIdTipoEstablecimiento(3L);
			establecimiento.setIdLocalidad(3L);
			establecimiento.setLatitud(20.234567);
			establecimiento.setLongitud(212.234567);
			
			establecimientoService.create(establecimiento);
			 
		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}	
	}
	
	public void testUpdate() {
		System.out.println("Testing update....");
		EstablecimientoDTO establecimiento = new EstablecimientoDTO();
		try {
			establecimiento.setNombre("Prueba3874983748");
			establecimiento.setCalle("Prueba calle");
			establecimiento.setZip("15350");
			establecimiento.setAforo(100);
			establecimiento.setIdTipoEstablecimiento(3L);
			establecimiento.setIdLocalidad(3L);
			establecimiento.setLatitud(20.234567);
			establecimiento.setLongitud(212.234567);
			establecimiento.setId(12l);
			
			establecimientoService.update(establecimiento);
			 
		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}	
	}
	
	

	
	
	
	
	public static void main(String args[]) {
		EstablecimientoServiceTest test = new EstablecimientoServiceTest();
		test.testFindById();
		//test.testFindCriteria();
		//test.testCreate();
		//test.testUpdate();
		
	}

}
