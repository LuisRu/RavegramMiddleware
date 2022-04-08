package com.luis.ravegram.model;

import java.util.Date;

public class EventoCriteria {
	
	private Boolean publicPrivado;
	private Date fecha;
	private Integer tipoEstablecimiento;
	private Date edadMax;
	private Date edadMin;
	private Integer distancia;
	private	Integer tipoTematica;
	private Integer tipoMusica;
	private Integer tipoEstadoEvento;
	

	public EventoCriteria() {
	}


	public Boolean getPublicPrivado() {
		return publicPrivado;
	}


	public void setPublicPrivado(Boolean publicPrivado) {
		this.publicPrivado = publicPrivado;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public Integer getTipoEstablecimiento() {
		return tipoEstablecimiento;
	}


	public void setTipoEstablecimiento(Integer tipoEstablecimiento) {
		this.tipoEstablecimiento = tipoEstablecimiento;
	}


	public Date getEdadMax() {
		return edadMax;
	}


	public void setEdadMax(Date edadMax) {
		this.edadMax = edadMax;
	}


	public Date getEdadMin() {
		return edadMin;
	}


	public void setEdadMin(Date edadMin) {
		this.edadMin = edadMin;
	}


	public Integer getTipoTematica() {
		return tipoTematica;
	}


	public void setTipoTematica(Integer tipoTematica) {
		this.tipoTematica = tipoTematica;
	}


	public Integer getTipoMusica() {
		return tipoMusica;
	}


	public void setTipoMusica(Integer tipoMusica) {
		this.tipoMusica = tipoMusica;
	}


	public Integer getTipoEstadoEvento() {
		return tipoEstadoEvento;
	}


	public void setTipoEstadoEvento(Integer tipoEstadoEvento) {
		this.tipoEstadoEvento = tipoEstadoEvento;
	}


	public Integer getDistancia() {
		return distancia;
	}


	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}
	
}
