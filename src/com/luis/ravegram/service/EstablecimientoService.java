package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.Establecimiento;
import com.luis.ravegram.model.EstablecimientoCriteria;

public interface EstablecimientoService {
	
	
	public Establecimiento findById(Long idEstablecimiento) 
			throws DataException, ServiceException;
	
	
	public List<Establecimiento> findByLocalidad(Long idLocalidad) 
			throws DataException, ServiceException;
	
	public List<Establecimiento> findByCriteria(EstablecimientoCriteria ec,Double latitud,Double longitud) 
			throws DataException, ServiceException;

	
	public void create(Establecimiento establecimiento) 
			throws DataException, ServiceException;
	
	public void update(Establecimiento establecimiento) 
			throws DataException,ServiceException;

}
