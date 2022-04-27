package com.luis.ravegram.model.criteria;

import java.util.Date;

public class UsuarioCriteria {
	
	private Long id;
	private Long idEvento;
	private Long tipoEstadoSolicitud;
	private Long idBuscador;
	private Date edadDesde;
	private Date edadHasta;
	private Integer distanciaKm;
	private String busqueda;
	private String orderBy;

	public UsuarioCriteria() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdEvento() {
		return idEvento;
	}
	/**
	 * Hace referencia a la tabla solicitudes 
	 * acompañado de tipoEstadoSolicitud sacamos 
	 * los asistentes a un evento
	 * 
	 */
	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public Long getTipoEstadoSolicitud() {
		return tipoEstadoSolicitud;
	}

	public void setTipoEstadoSolicitud(Long tipoEstadoSolicitud) {
		this.tipoEstadoSolicitud = tipoEstadoSolicitud;
	}

	public Long getIdBuscador() {
		return idBuscador;
	}

	/**
	 * Se utiliza para mostrar resultados diferentes al id del buscador
	 * @param idBuscador
	 */
	public void setIdBuscador(Long idBuscador) {
		this.idBuscador = idBuscador;
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

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}



	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * Opciones
	 * EDAD-ASC
	 * EDAD-DESC
	 * @param orderBy
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	

}
