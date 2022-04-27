package com.luis.ravegram.dao;

import java.sql.Connection;
import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.Provincia;

public interface ProvinciaDAO {

	public List <Provincia> findAll(Connection c) throws DataException;;

}
