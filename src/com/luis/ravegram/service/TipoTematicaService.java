package com.luis.ravegram.service;

import java.util.List;


import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.TipoTematica;


public interface TipoTematicaService {
	
	public List<TipoTematica> findAll() 
			throws DataException;

}
