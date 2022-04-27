package com.luis.ravegram.dao;

import java.sql.Connection;
import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.RequestNotFoundException;
import com.luis.ravegram.model.SolicitudDTO;
import com.luis.ravegram.model.criteria.SolicitudCriteria;

public interface SolicitudDAO {
	
	public SolicitudDTO findByUsuarioEvento(Connection c,Long idUsuario, Long idEvento)  
			throws DataException ;
	
	public List<SolicitudDTO> findByCriteria(Connection c,SolicitudCriteria uepc )  
			throws DataException;
	
	public List<SolicitudDTO> findSolicitudesPendientes(Connection c, Long idUsuario) 
			throws DataException ;
	
	public List<SolicitudDTO> findInvitacionesPendientes(Connection c, Long idUsuario) 
			throws DataException ;
	
	public Long create(Connection c,SolicitudDTO usuario)
			throws DataException;
	
	public void createMultiple(Connection c,List<SolicitudDTO> solicitudes)
			throws DataException;
	
	public void deleteByEventosIds(Connection c,List <Long> idsEventos)
			throws DataException;
	

	public void deleteByUsuario(Connection c,Long idUsuario)
			throws DataException;
	
	public void deleteByEventoEstado(Connection c,Long idEvento ,Long idEstadoSolicitud)
			throws DataException;
	
	public void deleteByEventoUsuarioIds(Connection c,Long idEvento ,List <Long> idsUsuarios)
			throws DataException;
	
	public Integer update(Connection c,SolicitudDTO solicitud) 
			throws DataException,RequestNotFoundException ;
	
	public Integer updateEstado(Connection c, Long idUsuario,Long idEvento, Long idEstado) 
			throws DataException,RequestNotFoundException;
	
}
