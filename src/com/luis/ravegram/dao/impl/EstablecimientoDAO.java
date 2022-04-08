package com.luis.ravegram.dao.impl;

import java.sql.Connection;
import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.Establecimiento;
import com.luis.ravegram.model.EstablecimientoCriteria;

public interface EstablecimientoDAO {
	
	public Establecimiento findById(Connection c,Long id) throws DataException;
	
	public List<Establecimiento> findByLocalidad(Connection c, Long idLocalidad) throws DataException;
	
	public List<Establecimiento> findByCriteria(Connection c,EstablecimientoCriteria ec) throws DataException;
	
	public Long create(Connection c,Establecimiento establecimiento) throws DataException;
	
	public int update(Connection c, Establecimiento establecimiento) throws DataException;
	
	
}