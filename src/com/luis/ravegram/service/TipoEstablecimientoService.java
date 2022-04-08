package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.TipoEstablecimiento;

public interface TipoEstablecimientoService {

	public List<TipoEstablecimiento> findAll() 
			throws DataException, ServiceException;
	
}
