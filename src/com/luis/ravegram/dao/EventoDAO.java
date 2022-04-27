package com.luis.ravegram.dao;

import java.sql.Connection;
import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.EventoNotFoundException;
import com.luis.ravegram.model.EventoDTO;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.criteria.EventoCriteria;

public interface EventoDAO {
	
	public EventoDTO findById(Connection c, Long idUsuario,Double latitudBuscador,Double longitudBuscador) 
			throws DataException;
	
	public List<Long> findByIdCreador(Connection c, Long idUsuario) 
			throws DataException;

	public Results<EventoDTO> findBySeguidosDisponibles(Connection c,Long idUsuario,Double latitudUsuario,Double longitudUsuario,long startIndex, int pageSize)
			throws DataException ; 
	
	public Results<EventoDTO> findByCriteria(Connection c,EventoCriteria ec, int startIndex, int pageSize)
			throws DataException;
	
	public Long create(Connection c,EventoDTO evento) 
			throws DataException;
	
	public void delete(Connection c,List<Long> idsEventos) 
			throws DataException;
	
	public  int update(Connection c,EventoDTO evento) 
			throws DataException,EventoNotFoundException;
	
	public  int updateEstado(Connection c,Long idEvento,Long estado) 
			throws DataException,EventoNotFoundException;

}
