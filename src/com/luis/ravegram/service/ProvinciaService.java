package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.Provincia;

public interface ProvinciaService {
	
	public List<Provincia> findAll() 
			throws DataException;

}
