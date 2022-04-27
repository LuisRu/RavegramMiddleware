package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.RaitingNotFoundException;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioEventoPuntuaDTO;
import com.luis.ravegram.model.criteria.PuntuacionCriteria;

public interface PuntuacionService {

	
	public Results<UsuarioEventoPuntuaDTO> findByCriteria(PuntuacionCriteria uep, int startIndex, int pageSize) 
			throws DataException ;

	
	public Results<UsuarioEventoPuntuaDTO> findByEventosUsuario(Long idUsuario,int startIndex, int pageSize) 
			throws DataException;
	
	public Long create(UsuarioEventoPuntuaDTO u) 
			throws DataException;
	
	public void deleteByUsuario(Long idUsuario) 
			throws DataException  ;
	
	public void deleteByEventos(List<Long> idsEventos) 
			throws DataException  ;
	
	public Integer update(String comentario,Integer valoracion,Long idUsuario,Long idEvento) 
			throws DataException,RaitingNotFoundException;
}
