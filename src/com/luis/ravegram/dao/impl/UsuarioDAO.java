package com.luis.ravegram.dao.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioCriteria;
import com.luis.ravegram.model.UsuarioDTO;

public interface UsuarioDAO {

	public UsuarioDTO findById(Connection c, Long id) throws DataException;

	public List<UsuarioDTO> findByIds(Connection c, List<Long> ids) throws DataException;

	public List<UsuarioDTO> findAsistentes(Connection c, Long idEvento) throws DataException;
	
	public List<UsuarioDTO> findSeguidores(Connection c, Long idUsuario) throws DataException;
	
	public Set<Long> findSeguidosIds(Connection c, Long idUsuarioSeguidor) throws DataException;
	
	public List<UsuarioDTO> findSeguidos(Connection c, Long idUsuario) throws DataException;

	public UsuarioDTO findByEmail (Connection c, String email) throws DataException;

	public Results<UsuarioDTO> findByCriteria(Connection c, UsuarioCriteria uc, int startIndex, int pageSize) throws DataException ;
	
	public Long create(Connection c, UsuarioDTO usuario) throws DataException;
	
	public int update(Connection c, UsuarioDTO usuario) throws DataException;
	
	public  int updateEstado(Connection c,Long id,Integer idTipoEstado) throws DataException;
	
	public  int updateUbicacion(Connection c, Double latitud, Double longitud,Long id) throws DataException;

}
