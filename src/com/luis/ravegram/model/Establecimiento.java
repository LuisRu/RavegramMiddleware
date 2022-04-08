package com.luis.ravegram.model;

import com.luis.ravegram.dao.util.AbstractValueObject;

public class Establecimiento extends AbstractValueObject {

	private Long id;
	private String nombre;
	private String calle;
	private String zip;
	private Integer aforo;
	private Long idTipoEstablecimiento;
	private String nombreTipoEstablecimiento;
	private Long idLocalidad;
	private String nombreLocalidad;
	private Double latitud;
	private Double longitud;
	private Double distanciaKm;
	
	public Establecimiento() {
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

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Integer getAforo() {
		return aforo;
	}

	public void setAforo(Integer aforo) {
		this.aforo = aforo;
	}

	public Long getIdTipoEstablecimiento() {
		return idTipoEstablecimiento;
	}

	public void setIdTipoEstablecimiento(Long idTipoEstablecimiento) {
		this.idTipoEstablecimiento = idTipoEstablecimiento;
	}

	public String getNombreTipoEstablecimiento() {
		return nombreTipoEstablecimiento;
	}

	public void setNombreTipoEstablecimiento(String nombreTipoEstablecimiento) {
		this.nombreTipoEstablecimiento = nombreTipoEstablecimiento;
	}

	public Long getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(Long idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public String getNombreLocalidad() {
		return nombreLocalidad;
	}

	public void setNombreLocalidad(String nombreLocalidad) {
		this.nombreLocalidad = nombreLocalidad;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Double getDistanciaKm() {
		return distanciaKm;
	}

	public void setDistanciaKm(Double distanciaKm) {
		this.distanciaKm = distanciaKm;
	}
	
	

}
