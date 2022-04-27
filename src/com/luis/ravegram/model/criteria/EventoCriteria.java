package com.luis.ravegram.model.criteria;

import java.util.Date;

public class EventoCriteria {
	
	private Double latitudBuscador;
	private Double longitudBuscador;
	private Integer distancia;
	private Long id;
	private Long idBuscador;
	private Long idCreador;
	private Long idEstablecimiento;
	private Date fecha;
	private Boolean publicPrivado;
	private Long tipoEstablecimiento;
	private	Long tipoTematica;
	private Long tipoMusica;
	private Long tipoEstadoEvento;
	private Boolean descartarInteractuados;
	private Boolean descartarNoAceptado;
	private String orderBy;
	
	public EventoCriteria() {
	}

	
	public Double getLatitudBuscador() {
		return latitudBuscador;
	}

	public void setLatitudBuscador(Double latitudBuscador) {
		this.latitudBuscador = latitudBuscador;
	}

	public Double getLongitudBuscador() {
		return longitudBuscador;
	}

	public void setLongitudBuscador(Double longitudBuscador) {
		this.longitudBuscador = longitudBuscador;
	}
	
	public Integer getDistancia() {
		return distancia;
	}

	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdBuscador() {
		return idBuscador;
	}

	public void setIdBuscador(Long idBuscador) {
		this.idBuscador = idBuscador;
	}

	public Long getIdCreador() {
		return idCreador;
	}

	public void setIdCreador(Long idCreador) {
		this.idCreador = idCreador;
	}

	public Long getIdEstablecimiento() {
		return idEstablecimiento;
	}

	public void setIdEstablecimiento(Long idEstablecimiento) {
		this.idEstablecimiento = idEstablecimiento;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Boolean getPublicPrivado() {
		return publicPrivado;
	}

	public void setPublicPrivado(Boolean publicPrivado) {
		this.publicPrivado = publicPrivado;
	}

	public Long getTipoEstablecimiento() {
		return tipoEstablecimiento;
	}

	public void setTipoEstablecimiento(Long tipoEstablecimiento) {
		this.tipoEstablecimiento = tipoEstablecimiento;
	}

	public Long getTipoTematica() {
		return tipoTematica;
	}

	public void setTipoTematica(Long tipoTematica) {
		this.tipoTematica = tipoTematica;
	}

	public Long getTipoMusica() {
		return tipoMusica;
	}

	public void setTipoMusica(Long tipoMusica) {
		this.tipoMusica = tipoMusica;
	}

	public Long getTipoEstadoEvento() {
		return tipoEstadoEvento;
	}

	public void setTipoEstadoEvento(Long tipoEstadoEvento) {
		this.tipoEstadoEvento = tipoEstadoEvento;
	}

	public Boolean getDescartarInteractuados() {
		return descartarInteractuados;
	}

	/**
	 * Para descartar los interactuados debemos setear obligatoriamente en idBuscador el id 
	 * Introduzca true para activarlo
	 */
	public void setDescartarInteractuados(Boolean descartarInteractuados) {
		this.descartarInteractuados = descartarInteractuados;
	}


	public Boolean getDescartarNoAceptado() {
		return descartarNoAceptado;
	}

	/**
	 * Para mostrar solo los eventos a los que estoy aceptado, debemos setear obligatoriamente en idBuscador el id 
	 * Introduzca true para activarlo
	 */
	public void setDescartarNoAceptado(Boolean descartarNoAceptado) {
		this.descartarNoAceptado = descartarNoAceptado;
	}


	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * Opciones
	 * NOMBRE-ASC
	 * NOMBRE-DESC
	 * FECHA-ASC
	 * FECHA-DESC
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}


	


	
}
