package com.luis.ravegram.model;

import com.luis.ravegram.dao.util.AbstractValueObject;

public class UsuarioSigueDTO extends AbstractValueObject {

	private Long idSeguidor;
	private Long idSeguido;
	private String nombreSeguidor;
	private String nombreSeguido;
	private Integer numSeguidores;
	
	public UsuarioSigueDTO() {
	}

	public Long getIdSeguidor() {
		return idSeguidor;
	}

	public void setIdSeguidor(Long idSeguidor) {
		this.idSeguidor = idSeguidor;
	}

	public Long getIdSeguido() {
		return idSeguido;
	}

	public void setIdSeguido(Long idSeguido) {
		this.idSeguido = idSeguido;
	}

	public String getNombreSeguidor() {
		return nombreSeguidor;
	}

	public void setNombreSeguidor(String nombreSeguidor) {
		this.nombreSeguidor = nombreSeguidor;
	}

	public String getNombreSeguido() {
		return nombreSeguido;
	}

	public void setNombreSeguido(String nombreSeguido) {
		this.nombreSeguido = nombreSeguido;
	}

	public Integer getNumSeguidores() {
		return numSeguidores;
	}

	public void setNumSeguidores(Integer numSeguidores) {
		this.numSeguidores = numSeguidores;
	}

}
