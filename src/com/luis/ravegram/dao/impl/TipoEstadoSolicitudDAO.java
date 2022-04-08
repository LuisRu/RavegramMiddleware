package com.luis.ravegram.dao.impl;

import java.sql.Connection;
import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.TipoEstadoSolicitud;

public interface TipoEstadoSolicitudDAO {
	
	public List <TipoEstadoSolicitud> findAll(Connection c) throws DataException;;

}
