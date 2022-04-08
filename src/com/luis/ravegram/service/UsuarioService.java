package com.luis.ravegram.service;

import java.util.List;
import java.util.Set;

import com.luis.ravegram.exception.*;
import com.luis.ravegram.model.*;

public interface UsuarioService {




	public UsuarioDTO findById(Long id) 
			throws DataException, ServiceException;
	
	public List<UsuarioDTO> findByIds(List<Long> idsUsuarios) 
			throws DataException, ServiceException;


	public List<UsuarioDTO> findAsistentes(Long idEvento) 
			throws DataException, ServiceException;

	public List<UsuarioDTO> findSeguidores(Long idUsuario) 
			throws DataException, ServiceException;

	public List<UsuarioDTO> findSeguidos(Long idUsuario) 
			throws DataException, ServiceException;
	
	public Set<Long> findSeguidosIds(Long idUsuario) throws DataException, ServiceException;
	
	public List<UsuarioDTO> findSeguidosMutuamente(Long idUsuario) 
			throws DataException, ServiceException;
	
	public UsuarioDTO findByEmail(String email) 
			throws DataException, ServiceException;
	
	public Results<UsuarioDTO> findByCriteria(UsuarioCriteria uc,int startIndex, int pageSize) 
			throws DataException, ServiceException ;




	/**
	 * 
	 * @param email
	 * @param password
	 * @return
	 * @throws InvalidUserOrPasswordException
	 * @throws ServiceException
	 */
	public UsuarioDTO login(String email, String password) 
			throws InvalidUserOrPasswordException,UserDeleteException, DataException, ServiceException ;
	
	
	/**
	 * Registra un nuevo usuario.
	 * @param u Usuario a registrar, con todos sus datos obligatorios rellenos.
	 * @return Identificador en el sistema del nuevo usuario.
	 * @throws UserAlreadyExistsException Cuando el usuario ya existe en el sistema.
	 * @throws MailException Cuando se produce un error al enviar el email de confirmacion de registro.
	 * @throws ServiceException En otro caso, por ejemplo, cuando alguno(s) de los datos
	 * obligatorios no está cumplimentado o su valor es incorrecto.
	 */
	public Long signUp(UsuarioDTO u) 
			throws UserAlreadyExistsException, MailException, ServiceException;


	public void update(UsuarioDTO u) 
			throws DataException,ServiceException;
	
	public void updateEstado(Long id,Integer idTipoEstadoUsuario) 
			throws DataException,ServiceException;

	public void updateUbicacion(Double latitud, Double longitud,Long id) 
			throws DataException,ServiceException;


}
