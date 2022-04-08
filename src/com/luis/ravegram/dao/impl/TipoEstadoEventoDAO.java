package com.luis.ravegram.dao.impl;

import java.sql.Connection;
import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.TipoEstadoEvento;

public interface TipoEstadoEventoDAO {
	
	public List <TipoEstadoEvento> findAll(Connection c) throws DataException;;

}
