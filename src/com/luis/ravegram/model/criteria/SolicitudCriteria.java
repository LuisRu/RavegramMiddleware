package com.luis.ravegram.model.criteria;

public class SolicitudCriteria {
	
	private Long idUsuario = null;
	private Long idEvento= null;
	private Long idTipoEstado = null;
	
	
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Long getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}
	public Long getIdTipoEstado() {
		return idTipoEstado;
	}
	public void setIdTipoEstado(Long idTipoEstado) {
		this.idTipoEstado = idTipoEstado;
	}
}
