package com.luis.ravegram.service;

import com.luis.ravegram.exception.DataException;

public interface UsuarioSigueService {


	public void unFollow(long idSeguidor,long idSeguido)
			throws DataException;


	public void follow(long idSeguidor,long idSeguido) 
			throws DataException;
	
	public void deleteAll(Long idUsuario) 
			throws DataException;
}
