package com.luis.ravegram.service;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.EstablecimientoNotFoundException;
import com.luis.ravegram.model.EstablecimientoDTO;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.criteria.EstablecimientoCriteria;

public interface EstablecimientoService {
	
	public EstablecimientoDTO findById(Long idUsuario)
			throws DataException;
	
	public Results<EstablecimientoDTO> findByCriteria(EstablecimientoCriteria ec,int startIndex, int pageSize)
			throws DataException;
	
	public void create(EstablecimientoDTO establecimiento) 
			throws DataException;
	
	public void update(EstablecimientoDTO establecimiento)
			throws DataException,EstablecimientoNotFoundException;

}
