package com.luis.ravegram.model;

import com.luis.ravegram.dao.util.AbstractValueObject;

public class Localidad extends AbstractValueObject {

	private Long id;
	private String nombre;
	private String nombreProvincia;
	
	public Localidad() {
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

	public String getNombreProvincia() {
		return nombreProvincia;
	}

	public void setNombreProvincia(String nombreProvincia) {
		this.nombreProvincia = nombreProvincia;
	}

	
}
