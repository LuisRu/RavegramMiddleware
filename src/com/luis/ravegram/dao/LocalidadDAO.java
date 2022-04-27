package com.luis.ravegram.dao;

import java.sql.Connection;
import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.Localidad;

public interface LocalidadDAO {

	public List <Localidad> findByProvincia(Connection c,Long id) throws DataException;;
}
