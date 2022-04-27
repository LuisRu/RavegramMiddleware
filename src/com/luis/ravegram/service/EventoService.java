package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.EventoNotFoundException;
import com.luis.ravegram.exception.MailException;
import com.luis.ravegram.model.EventoDTO;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.criteria.EventoCriteria;


public interface EventoService {
	
	public EventoDTO findById(Long idUsuario,Double latitudBuscador,Double longitudBuscador) 
			throws DataException;
	
	public List<Long> findByIdCreador(Long idUsuario) 
			throws DataException;

	public Results<EventoDTO> findBySeguidosDisponibles(Long idUsuario,Double latitudUsuario,Double longitudUsuario,long startIndex, int pageSize)
			throws DataException;
	
	public Results<EventoDTO> findByCriteria(EventoCriteria ec,int startIndex, int pageSize) 
			throws DataException;
	
	public void create(EventoDTO evento, List<Long> idsAsistentes)
			throws DataException ;
	
	public void deleteAll(Long idUsuario)
			throws DataException ;
	
	public void delete(Long idEvento)
			throws DataException;
	
	public void update(EventoDTO evento, List<Long> idsAsistentes) 
			throws DataException, EventoNotFoundException ;
	
	public Integer updateEstado(Long idEvento, Long estado) 
			throws DataException,EventoNotFoundException;
			
			
	public void compartir(Long idUsuarioComparte,Long idEvento,Double latitudUsuario, Double longitudUsuario,List<Long> idAmigos) 
			throws MailException, DataException ;
	
	
}
