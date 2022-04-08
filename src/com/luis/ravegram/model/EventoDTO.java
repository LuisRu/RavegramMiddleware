package com.luis.ravegram.model;

import java.util.Date;

import com.luis.ravegram.dao.util.AbstractValueObject;

public class EventoDTO extends AbstractValueObject{

	private Long id;
	private String nombre;
	private String descripcion;
	private Date fechaHora;
	private Integer numAsistentes;
	private Date edadeDesde;
	private Date edadHasta;
	private Boolean publicoPrivado;
	private Double latitud;
	private Double longitud;
	private String calle;
	private String zip;
	private Long idUsuario;
	private String nombreUsuarioCreador;
	private Long idTipoTematica;
	private String tipoTematica;
	private Long idTipoEstablecimiento;
	private String tipoEstablecimiento;
	private Long idLocalidad;
	private String localidad;
	private Long idEstablecimiento;
	private String establecimiento;
	private Long idTipoEstadoEvento;
	private String tipoEstadoEvento;
	private Long idTipoMusica;
	private String tipoMusica;
	private Double distanciaKm;
	
	public EventoDTO() {
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Integer getNumAsistentes() {
		return numAsistentes;
	}

	public void setNumAsistentes(Integer numAsistentes) {
		this.numAsistentes = numAsistentes;
	}

	public Date getEdadeDesde() {
		return edadeDesde;
	}

	public void setEdadeDesde(Date edadeDesde) {
		this.edadeDesde = edadeDesde;
	}

	public Date getEdadHasta() {
		return edadHasta;
	}

	public void setEdadHasta(Date edadHasta) {
		this.edadHasta = edadHasta;
	}

	public Boolean getPublicoPrivado() {
		return publicoPrivado;
	}

	public void setPublicoPrivado(Boolean publicoPrivado) {
		this.publicoPrivado = publicoPrivado;
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

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuarioCreador() {
		return nombreUsuarioCreador;
	}

	public void setNombreUsuarioCreador(String nombreUsuarioCreador) {
		this.nombreUsuarioCreador = nombreUsuarioCreador;
	}

	public Long getIdTipoTematica() {
		return idTipoTematica;
	}

	public void setIdTipoTematica(Long idTipoTematica) {
		this.idTipoTematica = idTipoTematica;
	}

	public String getTipoTematica() {
		return tipoTematica;
	}

	public void setTipoTematica(String tipoTematica) {
		this.tipoTematica = tipoTematica;
	}

	public Long getIdTipoEstablecimiento() {
		return idTipoEstablecimiento;
	}

	public void setIdTipoEstablecimiento(Long idTipoEstablecimiento) {
		this.idTipoEstablecimiento = idTipoEstablecimiento;
	}

	public String getTipoEstablecimiento() {
		return tipoEstablecimiento;
	}

	public void setTipoEstablecimiento(String tipoEstablecimiento) {
		this.tipoEstablecimiento = tipoEstablecimiento;
	}

	public Long getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(Long idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public Long getIdEstablecimiento() {
		return idEstablecimiento;
	}

	public void setIdEstablecimiento(Long idEstablecimiento) {
		this.idEstablecimiento = idEstablecimiento;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public Long getIdTipoEstadoEvento() {
		return idTipoEstadoEvento;
	}

	public void setIdTipoEstadoEvento(Long idTipoEstadoEvento) {
		this.idTipoEstadoEvento = idTipoEstadoEvento;
	}

	public String getTipoEstadoEvento() {
		return tipoEstadoEvento;
	}

	public void setTipoEstadoEvento(String tipoEstadoEvento) {
		this.tipoEstadoEvento = tipoEstadoEvento;
	}

	public Long getIdTipoMusica() {
		return idTipoMusica;
	}

	public void setIdTipoMusica(Long idTipoMusica) {
		this.idTipoMusica = idTipoMusica;
	}

	public String getTipoMusica() {
		return tipoMusica;
	}

	public void setTipoMusica(String tipoMusica) {
		this.tipoMusica = tipoMusica;
	}

	public Double getDistanciaKm() {
		return distanciaKm;
	}

	public void setDistanciaKm(Double distanciaKm) {
		this.distanciaKm = distanciaKm;
	}

		


}
