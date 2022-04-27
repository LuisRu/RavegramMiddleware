package com.luis.ravegram.model.criteria;

public class EstablecimientoCriteria {
	

	
	private Integer distancia;
	private Double latitud;
	private Double longitud;
	private Integer id;
	private Integer idTipoEstablecimiento;
	private Integer IdLocalidad;
	private String orderBy;

	public EstablecimientoCriteria() {
	}

	
	public Integer getDistancia() {
		return distancia;
	}

	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getIdTipoEstablecimiento() {
		return idTipoEstablecimiento;
	}

	public void setIdTipoEstablecimiento(Integer idTipoEstablecimiento) {
		this.idTipoEstablecimiento = idTipoEstablecimiento;
	}



	public Integer getIdLocalidad() {
		return IdLocalidad;
	}

	public void setIdLocalidad(Integer idLocalidad) {
		IdLocalidad = idLocalidad;
	}


	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * Opciones
	 * AFORO-ASC
	 * AFORO-DESC 
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
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

}
