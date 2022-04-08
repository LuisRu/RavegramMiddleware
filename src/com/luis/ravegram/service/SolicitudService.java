package com.luis.ravegram.service;

import java.util.List;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.SolicitudDTO;

public interface SolicitudService {

	
	public void usuarioSolicita(Long idUsuario, Long idEvento) 
			throws DataException, ServiceException ;
	
	public void rechazado(Long idUsuario, Long idEvento) 
			throws DataException, ServiceException ;
	
	public void eventoInvita(Long idUsuario,Long idEvento) 
			throws DataException, ServiceException ;
	
	public void aceptarSolicitud(Long idUsuario,Long idEvento) 
			throws DataException, ServiceException;
	
	public List<SolicitudDTO> historialSolicitudes(Long idUsuario) 
			throws DataException, ServiceException; 
	
	public SolicitudDTO findByIdUsuarioIdEvento(Long idUsuario,Long idEvento)
			throws DataException, ServiceException; 
	
	public List<SolicitudDTO> solicitudesAMisEventosPendientes(Long idUsuario,Double latitudUsuario,Double longitudUsuario)
			throws DataException, ServiceException ;
	
	public List<SolicitudDTO> invitacionesAEventosPendientes(Long idUsuario)
			throws DataException, ServiceException ;
	
	
	public void update(SolicitudDTO solitud) 
			throws DataException,ServiceException;
	
	public void updateEstadoSolicitudesEvento(Long idUsuario,Long idEvento, Long idEstado) 
			throws DataException, ServiceException ;
}
