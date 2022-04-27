package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.RequestInvalidStateException;
import com.luis.ravegram.exception.RequestNotFoundException;
import com.luis.ravegram.model.SolicitudDTO;
import com.luis.ravegram.model.criteria.SolicitudCriteria;

public interface SolicitudService {

	public SolicitudDTO findByUsuarioEvento(Long idUsuario, Long idEvento)  
			throws DataException;
	
	public void usuarioSolicita(Long idUsuario, Long idEvento) 
			throws DataException,RequestNotFoundException,RequestInvalidStateException ;
	
	public void eventoInvita(Long idUsuario,Long idEvento) 
			throws DataException,RequestNotFoundException,RequestInvalidStateException;
	
	public void eventoNoInteresa(Long idUsuario,Long idEvento) 
			throws DataException,RequestNotFoundException,RequestInvalidStateException;
	
	public void anadirUsuarios(Long idEvento,List<Long> idsUsuarios)
			throws DataException ;
	
	public List<SolicitudDTO> findByCriteria(SolicitudCriteria uepc)
			throws DataException ;
	
	public List<SolicitudDTO> findSolicitudesPendientes(Long idUsuario) 
			throws DataException ;
	
	public List<SolicitudDTO> findInvitacionesPendientes(Long idUsuario) 
			throws DataException;
	
	public void create(SolicitudDTO usuario) 
			throws DataException;
	
	public void createMultiple(List<SolicitudDTO> solicitudes) 
			throws DataException ;
	
	public void deleteByEventosIds(List <Long> idsEventos)
			throws DataException ;
	
	public void deleteByUsuario(Long idUsuario)
			throws DataException;
	
	public void deleteByEventoEstado(Long idEvento ,Long idEstadoSolicitud)
			throws DataException;
	
	public void update(SolicitudDTO solitud) 
			throws DataException, RequestNotFoundException ;
	
	public void updateEstado(Long idUsuario,Long idEvento, Long idEstado)
			throws DataException, RequestNotFoundException ;
}