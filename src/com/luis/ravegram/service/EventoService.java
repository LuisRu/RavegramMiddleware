package com.luis.ravegram.service;

import java.sql.Connection;
import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.MailException;
import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.EventoCriteria;
import com.luis.ravegram.model.EventoDTO;


public interface EventoService {

	public EventoDTO findById(Long id,Double latitudUsuario, Double longitudUsuario)
			throws DataException, ServiceException;
	
	public List<EventoDTO> findByCreador(Long idUsuario,Double latitudUsuario, Double longitudUsuario)
		throws DataException, ServiceException;
	
	public List<EventoDTO> findyBySeguidos(Long idUsuario,Double latitudUsuario,Double longitudUsuario)
			throws DataException, ServiceException;
			
	
	public List<EventoDTO> findByPendientes(Long idUsuario,Double latitudUsuario,Double longitudUsuario)
			throws DataException, ServiceException;
	
	public List<EventoDTO> findByEstablecimiento(Long idEstablecimiento,Double latitudUsuario, Double longitudUsuario) 
			throws DataException, ServiceException;
	
	public List<EventoDTO> findByCriteria(EventoCriteria ec,Long idUsuario,Double latitudUsuario, Double longitudUsuario,long startIndex, int pageSize)  throws DataException, ServiceException ;
	
	public Long create(EventoDTO evento) 
			throws DataException, ServiceException;
	
	public Integer update(EventoDTO evento) 
			throws DataException,ServiceException;
	
	public Integer updateEstado(Long idEvento,Long estado) 
			throws DataException,ServiceException;
	
	public void eliminadoEventoConEmail(Long idEvento,Double latitudUsuario,Double longitudUsuario) 
			throws DataException,ServiceException;
	
	public void compartir(Long idUsuarioComparte,Long idEvento,Double latitudUsuario, Double longitudUsuario,List<Long> idAmigos) 
			throws MailException, ServiceException;
	
	
		
	
}
