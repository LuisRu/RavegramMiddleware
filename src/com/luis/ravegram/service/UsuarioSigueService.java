package com.luis.ravegram.service;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.ServiceException;

public interface UsuarioSigueService {


	public void unFollow(long idSeguidor,long idSeguido) throws DataException,ServiceException;


	public void follow(long idSeguidor,long idSeguido) throws DataException,ServiceException;
}
