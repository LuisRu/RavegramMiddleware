package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.model.EventoDTO;
import com.luis.ravegram.model.UsuarioEventoPuntuaDTO;
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
	
	public void testFindByEvento(){
		System.out.println("Testing findyByEvento...");
		try {
			leerLista(puntuacionService.findByEvento(2l));	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testHistorialPuntuaciones(){
		System.out.println("Testing historial puntuaciones...");
		try {
			leerLista(puntuacionService.historialPuntuaciones(2l));	
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
			puntuacion.setIdUsuario(2L);
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
		//test.testUpdate();
		test.testCreate();
//		test.testFindByEvento();
//		test.testHistorialPuntuaciones();
		
		}

	
	
}
