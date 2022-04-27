package com.luis.ravegram.dao;

import java.sql.Connection;
import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.TipoMusica;

public interface TipoMusicaDAO {
	
	public List <TipoMusica> findAll(Connection c) 
			throws DataException;
}
