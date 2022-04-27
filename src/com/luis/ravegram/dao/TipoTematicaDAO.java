package com.luis.ravegram.dao;

import java.sql.Connection;
import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.TipoTematica;

public interface TipoTematicaDAO {
	
	public List <TipoTematica> findAll(Connection c) throws DataException;

}
