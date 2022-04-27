package com.luis.ravegram.dao;

import java.sql.Connection;

import com.luis.ravegram.exception.DataException;

public interface UsuarioSigueDAO {
	
	public void create(Connection c,long idSeguidor,long idSeguido) 
			throws DataException;
	
	public void delete(Connection c,long idSeguidor,long idSeguido)
			throws DataException ;
	
	public void deleteAll(Connection c,Long idUsuario)
			throws DataException ;
}
