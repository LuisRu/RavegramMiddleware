package com.luis.ravegram.dao.impl;

import com.luis.ravegram.exception.DataException;

public interface UsuarioSigueDAO {
	
	public int delete(long idSeguidor,long idSeguido) throws DataException;
		
	
	public int create(long idSeguidor,long idSeguido) throws DataException;
}
