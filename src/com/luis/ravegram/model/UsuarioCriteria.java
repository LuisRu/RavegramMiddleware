package com.luis.ravegram.model;

import java.util.Date;

public class UsuarioCriteria {
	
	
	private Long idBuscador;
	private Date edadDesde;
	private Date edadHasta;
	private Integer distanciaKm;
	private String busqueda;

	public UsuarioCriteria() {
	}

	public Date getEdadDesde() {
		return edadDesde;
	}

	public void setEdadDesde(Date edadDesde) {
		this.edadDesde = edadDesde;
	}

	public Date getEdadHasta() {
		return edadHasta;
	}

	public void setEdadHasta(Date edadHasta) {
		this.edadHasta = edadHasta;
	}

	public Integer getDistanciaKm() {
		return distanciaKm;
	}

	public void setDistanciaKm(Integer distanciaKm) {
		this.distanciaKm = distanciaKm;
	}

	public Long getIdBuscador() {
		return idBuscador;
	}

	public void setIdBuscador(Long idBuscador) {
		this.idBuscador = idBuscador;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

}
