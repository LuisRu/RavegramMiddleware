package com.luis.ravegram.model;

import java.util.Date;

import com.luis.ravegram.dao.util.AbstractValueObject;

public class SolicitudDTO extends AbstractValueObject {

	private Long idUsuario;
	private String nombreUsuario;
	private Long idEvento;
	private String nombreEvento;
	private Date fecha;
	private Long idTipoEstado;
	private String nombreEstado;
	
	
	public SolicitudDTO() {
	}


	public Long getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}


	public String getNombreUsuario() {
		return nombreUsuario;
	}


	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}


	public Long getIdEvento() {
		return idEvento;
	}


	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}


	public String getNombreEvento() {
		return nombreEvento;
	}


	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public Long getIdTipoEstado() {
		return idTipoEstado;
	}


	public void setIdTipoEstado(Long idTipoEstado) {
		this.idTipoEstado = idTipoEstado;
	}


	public String getNombreEstado() {
		return nombreEstado;
	}


	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

}
