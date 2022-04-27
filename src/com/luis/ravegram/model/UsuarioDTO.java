package com.luis.ravegram.model;

import java.util.Date;

import com.luis.ravegram.dao.util.AbstractValueObject;

public class UsuarioDTO  extends AbstractValueObject{

	private Long id;
	private	String userName;
	private String email;
	private Date fechaNacimiento;
	private Character sexo;
	private Double latitud;
	private Double longitud;
	private String telefono;
	private String biografia;
	private Integer numSeguidos;
	private String contrasena;
	private Integer tipoEstadoCuenta;
	private Double distanciaKm;
	private Double valoracionMedia;

		
	public UsuarioDTO() {
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}



	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public Character getSexo() {
		return sexo;
	}


	public void setSexo(Character sexo) {
		this.sexo = sexo;
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


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getBiografia() {
		return biografia;
	}


	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}




	public Integer getNumSeguidos() {
		return numSeguidos;
	}


	public void setNumSeguidos(Integer numSeguidos) {
		this.numSeguidos = numSeguidos;
	}


	public String getContrasena() {
		return contrasena;
	}


	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}


	public Integer getTipoEstadoCuenta() {
		return tipoEstadoCuenta;
	}


	public void setTipoEstadoCuenta(Integer tipoEstadoCuenta) {
		this.tipoEstadoCuenta = tipoEstadoCuenta;
	}


	public Double getDistanciaKm() {
		return distanciaKm;
	}


	public void setDistanciaKm(Double distanciaKm) {
		this.distanciaKm = distanciaKm;
	}


	public Double getValoracionMedia() {
		return valoracionMedia;
	}


	public void setValoracionMedia(Double valoracionMedia) {
		this.valoracionMedia = valoracionMedia;
	}




}
