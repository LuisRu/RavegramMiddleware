package com.luis.ravegram.dao;

import java.sql.Connection;
import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.TipoEstablecimiento;

public interface TipoEstablecimientoDAO {

	public List <TipoEstablecimiento> findAll(Connection c) 
			throws DataException;;
}
