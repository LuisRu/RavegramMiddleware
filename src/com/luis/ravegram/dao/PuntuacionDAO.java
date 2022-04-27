package com.luis.ravegram.dao;

import java.sql.Connection;
import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.RaitingNotFoundException;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioEventoPuntuaDTO;
import com.luis.ravegram.model.criteria.PuntuacionCriteria;

public interface PuntuacionDAO {
	
	public Results<UsuarioEventoPuntuaDTO> findByCriteria(Connection c,PuntuacionCriteria uep, int startIndex, int pageSize) 
			throws DataException;
	
	public Results<UsuarioEventoPuntuaDTO> findByEventosUsuario(Connection c,Long idUsuario,int startIndex, int pageSize) 
			throws DataException;
	
	public Long create(Connection c,UsuarioEventoPuntuaDTO usuario) throws DataException;
	
	public void deleteByUsuario(Connection c,Long idUsuario)
			throws DataException;
	
	public void deleteByEventos(Connection c,List<Long> idsEventos)
			throws DataException;
	
	public int update(Connection c,String comentario,Integer valoracion,Long idUsuario,Long idEvento) throws DataException,RaitingNotFoundException;
}
