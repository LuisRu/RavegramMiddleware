package com.luis.ravegram.service;

import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.UsuarioEventoPuntuaDTO;

public interface PuntuacionService {

	
	public List<UsuarioEventoPuntuaDTO> findByEvento(Long idEvento) 
			throws DataException, ServiceException;
	
	public List<UsuarioEventoPuntuaDTO> historialPuntuaciones(Long idUsuario) 
			throws DataException, ServiceException;
	
	public Long create(UsuarioEventoPuntuaDTO u) 
			throws DataException, ServiceException;
	
	public Integer update(String comentario,Integer valoracion,Long idUsuario,Long idEvento) 
			throws DataException,ServiceException;
}
