package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.TipoEstadoSolicitud;


public interface TipoEstadoSolicitudService {
	
	public List<TipoEstadoSolicitud> findAll() 
			throws DataException, ServiceException;
	
	

}
