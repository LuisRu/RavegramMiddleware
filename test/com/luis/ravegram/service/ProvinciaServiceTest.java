package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.model.Provincia;
import com.luis.ravegram.service.impl.ProvinciaServiceImpl;

public class ProvinciaServiceTest {
	
	private ProvinciaService provinciaService = null;

	public ProvinciaServiceTest() {
		provinciaService = new ProvinciaServiceImpl();
		
	}
	
	
	public void leerLista(List<Provincia> provincias) {
		for (Provincia es : provincias) {
			System.out.println("Resultado: "+es.getNombre());
		}
	}
	
	
	
	public void testFindAll() {
		System.out.println("Testing findAll....");
		try {
			leerLista(provinciaService.findAll());
		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}	
	}

	
	
	
	public static void main(String args[]) {
		ProvinciaServiceTest test = new ProvinciaServiceTest();
		test.testFindAll();
		
	}

}
