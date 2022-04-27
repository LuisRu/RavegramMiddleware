package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.model.TipoEstablecimiento;
import com.luis.ravegram.model.TipoEstadoEvento;
import com.luis.ravegram.model.TipoMusica;
import com.luis.ravegram.model.TipoTematica;
import com.luis.ravegram.service.impl.TipoEstablecimientoServiceImpl;
import com.luis.ravegram.service.impl.TipoEstadoEventoServiceImpl;
import com.luis.ravegram.service.impl.TipoMusicaServiceImpl;
import com.luis.ravegram.service.impl.TipoTematicaServiceImpl;

public class TiposServiceTest {
	
	private TipoEstablecimientoService tipoEstablecimientoService = null;
	private TipoEstadoEventoService tipoEstadoEventoService = null;
	private TipoMusicaService tipoMusicaService = null;
	private TipoTematicaService tipoTematicaService = null;

	public TiposServiceTest() {
		tipoEstablecimientoService = new TipoEstablecimientoServiceImpl();
		tipoEstadoEventoService = new TipoEstadoEventoServiceImpl();
		tipoMusicaService = new TipoMusicaServiceImpl();
		tipoTematicaService = new TipoTematicaServiceImpl();
	}
	
	
	public void leerListaTipoEstablecimiento(List<TipoEstablecimiento> tiposEstablecimiento ) {
		for (TipoEstablecimiento es : tiposEstablecimiento) {
			System.out.println("Resultado: "+es.getNombre());
		}
	}
	
	public void leerListaTipoEstadoEvento(List<TipoEstadoEvento> tiposEstadosEventos ) {
		for (TipoEstadoEvento es : tiposEstadosEventos) {
			System.out.println("Resultado: "+es.getNombre());
		}
	}
	
	public void leerListaTipoMusica(List<TipoMusica> tiposMusica ) {
		for (TipoMusica es : tiposMusica) {
			System.out.println("Resultado: "+es.getNombre());
		}
	}
	
	public void leerListaTipoTematica(List<TipoTematica> tiposTematica ) {
		for (TipoTematica es : tiposTematica) {
			System.out.println("Resultado: "+es.getNombre());
		}
	}
	
	
	
	public void testTipoEstablecimientoFindAll() {
		System.out.println("Testing testTipoEstablecimientoFindAll....");
		try {
			leerListaTipoEstablecimiento(tipoEstablecimientoService.findAll());
		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}	
	}
	
	public void testTipoEstadoEventoFindAll() {
		System.out.println("Testing testTipoEstadoEventoFindAll....");
		try {
			leerListaTipoEstadoEvento(tipoEstadoEventoService.findAll());
		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}	
	}
	
	public void testTipoMusicaFindAll() {
		System.out.println("Testing testTipoMusicaFindAll....");
		try {
			leerListaTipoMusica(tipoMusicaService.findAll());
		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}	
	}
	
	public void testTipoTematicaFindAll() {
		System.out.println("Testing testTipoTematicaFindAll....");
		try {
			leerListaTipoTematica(tipoTematicaService.findAll());
		}catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}	
	}

	
	
	
	public static void main(String args[]) {
		TiposServiceTest test = new TiposServiceTest();
		test.testTipoEstablecimientoFindAll();
		test.testTipoEstadoEventoFindAll();
		test.testTipoMusicaFindAll();
		test.testTipoTematicaFindAll();
		
	}

}
