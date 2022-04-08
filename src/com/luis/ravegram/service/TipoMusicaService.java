package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.TipoMusica;

public interface TipoMusicaService {

	public List<TipoMusica> findAll() 
			throws DataException, ServiceException;
}
