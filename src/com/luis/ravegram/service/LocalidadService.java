package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.Localidad;

public interface LocalidadService {

	
	public List<Localidad> findByProvincia(Long idProvincia) 
			throws DataException;

}
