package com.luis.ravegram.model;

import com.luis.ravegram.dao.util.AbstractValueObject;

public class TipoEstadoEvento extends AbstractValueObject {

	private Long id;
	private String nombre;
	
	public TipoEstadoEvento() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

}
