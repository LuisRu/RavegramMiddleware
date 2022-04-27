package com.luis.ravegram.dao;

import java.sql.Connection;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.EstablecimientoNotFoundException;
import com.luis.ravegram.model.EstablecimientoDTO;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.criteria.EstablecimientoCriteria;

public interface EstablecimientoDAO {
	
	
	public EstablecimientoDTO findById(Connection c, Long idUsuario) 
			throws DataException ;
	
	public Results<EstablecimientoDTO> findByCriteria(Connection c, EstablecimientoCriteria ec,int startIndex, int pageSize) 
			throws DataException ;
	
	public Long create(Connection c,EstablecimientoDTO establecimiento) 
			throws DataException;
	
	public int update(Connection c, EstablecimientoDTO establecimiento) 
			throws DataException,EstablecimientoNotFoundException ;
	
	
}