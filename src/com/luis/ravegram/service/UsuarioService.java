package com.luis.ravegram.service;

import java.util.List;
import java.util.Set;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.InvalidUserOrPasswordException;
import com.luis.ravegram.exception.MailException;
import com.luis.ravegram.exception.UserAlreadyExistsException;
import com.luis.ravegram.exception.UserDeleteException;
import com.luis.ravegram.exception.UserNotFoundException;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.model.criteria.UsuarioCriteria;

public interface UsuarioService {

	
	public UsuarioDTO findById(Long idUsuario)
			throws DataException;

	public List<UsuarioDTO> findByIds(List<Long> idsUsuarios) 
			throws DataException;

	
	public List<UsuarioDTO> findSeguidoresNoAceptadoEvento(Long idUsuario,Long idEvento) 
			throws DataException ;
	
	public Set<Long> findSeguidosIds(Long idUsuario) 
			throws DataException;
	
	
	public UsuarioDTO findByEmail(String email) 
			throws DataException;
	
	public List<UsuarioDTO> findSeguidores(Long idUsuario) 
			throws DataException;
	
	public List<UsuarioDTO> findSeguidos(Long idUsuario) 
			throws DataException;
	
	public Results<UsuarioDTO> findByCriteria(UsuarioCriteria uc,int startIndex, int pageSize) 
			throws DataException;


	public UsuarioDTO login(String email, String password) 
			throws InvalidUserOrPasswordException,UserDeleteException, DataException;
	
	
	public Long signUp(UsuarioDTO u) 
			throws UserAlreadyExistsException, MailException, DataException ;

	
	public void deleteAll(Long usuarioId) 
			throws DataException;

	public void update(UsuarioDTO usuario,UsuarioDTO usuarioSesion) 
			throws DataException,UserNotFoundException;
	

	public void updateUbicacion(Double latitud, Double longitud,Long id) 
			throws DataException, UserNotFoundException;

	
	public void updateEstado(Long id,Integer idTipoEstadoUsuario) 
			throws DataException, UserNotFoundException;


}
