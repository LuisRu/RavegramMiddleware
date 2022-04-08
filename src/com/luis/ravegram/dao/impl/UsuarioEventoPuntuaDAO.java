package com.luis.ravegram.dao.impl;

import java.sql.Connection;
import java.util.List;

import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.UsuarioEventoPuntuaDTO;

public interface UsuarioEventoPuntuaDAO {
	
	public List<UsuarioEventoPuntuaDTO> findByEvento(Connection c,Long idEvento) throws DataException;
	
	public List<UsuarioEventoPuntuaDTO> findByCreador(Connection c,Long idUsuario) throws DataException;
	
	public Long create(Connection c,UsuarioEventoPuntuaDTO usuario) throws DataException;
	
	public int update(Connection c,String comentario,Integer valoracion,Long idUsuario,Long idEvento) throws DataException;
}
