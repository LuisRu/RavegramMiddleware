package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.TipoEstadoEvento;

public interface TipoEstadoEventoService {
	
	public List<TipoEstadoEvento> findAll() 
			throws DataException;

}
