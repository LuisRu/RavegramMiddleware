package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioEventoPuntuaDTO;
import com.luis.ravegram.model.criteria.PuntuacionCriteria;
import com.luis.ravegram.service.impl.PuntuacionServiceImpl;

public class PuntuacionServiceTest {
	
	private PuntuacionService puntuacionService = null;
	private UsuarioEventoPuntuaDTO puntuacion = null;

	public PuntuacionServiceTest() {
		puntuacionService = new PuntuacionServiceImpl();
		
	}
	
	public void leerLista(List<UsuarioEventoPuntuaDTO> a) {
		for (UsuarioEventoPuntuaDTO valoracion : a) {
			System.out.println(valoracion.getNombreUsuario());
		}
	}
	
	
	public void testFindByCriteria(){
		System.out.println("Testing  testFindByCriteria...");
		Results<UsuarioEventoPuntuaDTO> results = null;
		PuntuacionCriteria uep = new PuntuacionCriteria();
		try {
			uep.setIdEvento(1L);
			results = puntuacionService.findByCriteria(uep,1,10);
			leerLista(results.getData());	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void testFindByEventosUsuario(){
		System.out.println("Testing testFindByEventosUsuario...");
		Results<UsuarioEventoPuntuaDTO> results = null;
		try {
			results = puntuacionService.findByEventosUsuario(2l,1,10);
			leerLista(results.getData());	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testUpdate(){
		System.out.println("Testing update...");
		Long num = System.currentTimeMillis();
		try {
			puntuacionService.update("Ahora si"+num, 2, 1L,2L);	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testCreate(){
		System.out.println("Testing create...");
		puntuacion = new UsuarioEventoPuntuaDTO();
		try {
			puntuacion.setIdUsuario(10L);
			puntuacion.setIdEvento(2L);
			puntuacion.setComentario("guay1");
			puntuacion.setValoracion(5);
			puntuacionService.create(puntuacion);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		PuntuacionServiceTest test = new PuntuacionServiceTest();
		test.testFindByCriteria();
		test.testFindByEventosUsuario();
		test.testCreate();
		test.testUpdate();
		
		}

	
	
}
