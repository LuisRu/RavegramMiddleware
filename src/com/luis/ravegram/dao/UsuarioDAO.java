package com.luis.ravegram.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.UserNotFoundException;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.model.criteria.UsuarioCriteria;

public interface UsuarioDAO {

	public UsuarioDTO findById(Connection c, Long idUsuario) 
			throws DataException ;
	
	public List<UsuarioDTO> findByIds(Connection c, List<Long> ids) 
			throws DataException;
	
	public List<UsuarioDTO> findSeguidoresNoAceptadoEvento(Connection c, Long idUsuario,Long idEvento) 
			throws DataException;
	
	public Set<Long> findSeguidosIds(Connection c, Long idUsuario) 
			throws DataException ;
	
	public UsuarioDTO findByEmail (Connection c, String email)
			throws DataException;
	
	public List<UsuarioDTO> findSeguidores(Connection c, Long idUsuario) 
			throws DataException;
	
	public List<UsuarioDTO> findSeguidos(Connection c, Long idUsuario) 
			throws DataException;
	
	public Results<UsuarioDTO> findByCriteria(Connection c, UsuarioCriteria uc, int startIndex, int pageSize) 
			throws DataException;
	
	public Long create(Connection c, UsuarioDTO usuario)
			throws DataException;
	
	public void deleteById(Connection c, Long usuarioId)
			throws DataException;
	
	public int update(Connection c, UsuarioDTO usuario) 
			throws DataException,UserNotFoundException;
	
	public  int updateEstado(Connection c,Long id,Integer idTipoEstado)
			throws DataException,UserNotFoundException;
	
	public  int updateUbicacion(Connection c, Double latitud, Double longitud,Long id)
			throws DataException,UserNotFoundException;
}

