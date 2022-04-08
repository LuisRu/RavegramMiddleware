package com.luis.ravegram.dao.impl;

import java.sql.Connection;
import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.SolicitudDTO;

public interface UsuarioEventoSolicitaDAO {
	
	public SolicitudDTO findByIdUsuarioIdEvento(Connection c, Long idUsuario,Long idEvento)
			throws DataException;
	
	public List<SolicitudDTO> findByUsuario(Connection c, Long idUsuario) 
			throws DataException ;
	
	public List<SolicitudDTO> findBySolicitudesAMisEventosPendientes(Connection c, List<Long> idsEventos) 
			throws DataException ;
	
	public List<SolicitudDTO> findByInvitacionesAEventosPendientes(Connection c, Long idUsuario) 
			throws DataException ;
	
	public Long create(Connection c,SolicitudDTO usuario) 
			throws DataException;
	
	public Integer update(Connection c,SolicitudDTO solitud) 
			throws DataException;
	
	public Integer updateEstado(Connection c, Long idUsuario,Long idEvento, Long idEstado) 
			throws DataException ;

}
