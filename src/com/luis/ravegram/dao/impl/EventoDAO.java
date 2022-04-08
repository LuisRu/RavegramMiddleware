package com.luis.ravegram.dao.impl;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.EventoCriteria;
import com.luis.ravegram.model.EventoDTO;

public interface EventoDAO {

	public EventoDTO findById(Connection c,Long id,Double latitudUsuario, Double longitudUsuario) throws DataException;
	
	public List<EventoDTO> findBySeguidos(Connection c,Long idUsuario,Double latitudUsuario, Double longitudUsuario) throws DataException;
	
	public List<EventoDTO> findByPendientes(Connection c,Long idUsuario,Date fechaActual,Double latitudUsuario, Double longitudUsuario) throws DataException;
	
	
	public List<EventoDTO> findByCreador(Connection c,Long idUsuario,Double latitudUsuario, Double longitudUsuario) throws DataException;
	
	public List<EventoDTO> findByEstablecimiento(Connection c,Long idEstablecimiento,Double latitudUsuario, Double longitudUsuario) throws DataException;
	
	public List<EventoDTO> findByCriteria(Connection c,EventoCriteria ec,Long idUsuarioBuscador,Double latitudUsuario, Double longitudUsuario, long startIndex, int pageSize) throws DataException ; 
	
	public Long create(Connection c,EventoDTO evento) throws DataException;
	
	public  int update(Connection c,EventoDTO evento) throws DataException;
	
	public  int updateEstado(Connection c,Long idEvento,Long estado) throws DataException;

}
