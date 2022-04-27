package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.model.Localidad;
import com.luis.ravegram.service.impl.LocalidadServiceImpl;

public class LocalidServiceTest {
	
	private LocalidadService localidadService = null;

	public LocalidServiceTest() {
		localidadService = new LocalidadServiceImpl();
		
	}
	
	
	public void leerLista(List<Localidad> localidades) {
		for (Localidad es : localidades) {
			System.out.println("Resultado: "+es.getNombre());
		}
	}
	
	
	
	public void testFindByProvincia() {
		System.out.println("Testing testFindByProvincia....");
		try {
			leerLista(localidadService.findByProvincia(1L));
		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}	
	}

	
	
	
	public static void main(String args[]) {
		LocalidServiceTest test = new LocalidServiceTest();
		test.testFindByProvincia();
		
	}

}
