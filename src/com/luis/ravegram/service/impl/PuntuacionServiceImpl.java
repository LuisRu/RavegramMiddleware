package com.luis.ravegram.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.UsuarioEventoPuntuaDAOImpl;
import com.luis.ravegram.dao.impl.UsuarioEventoPuntuaDAO;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.UsuarioEventoPuntuaDTO;
import com.luis.ravegram.service.PuntuacionService;

public class PuntuacionServiceImpl implements PuntuacionService {

	private static Logger logger = LogManager.getLogger(PuntuacionServiceImpl.class);
	
	private UsuarioEventoPuntuaDAO usuarioPuntuaDAO = null;
	
	public PuntuacionServiceImpl() {
		usuarioPuntuaDAO = new UsuarioEventoPuntuaDAOImpl();
	}

	@Override
	public List<UsuarioEventoPuntuaDTO> findByEvento(Long idEvento) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<UsuarioEventoPuntuaDTO> valoraciones = null;
		try  {
			c = ConnectionManager.getConnection();								
			
			c.setAutoCommit(false);
			
			valoraciones = usuarioPuntuaDAO.findByEvento(c, idEvento);
			
			commitOrRollback = true;
			
		} catch (SQLException e) {
			logger.error("FindByEvento: "+idEvento+": "+e.getMessage() ,e);
			throw new DataException("FindByEvento: "+idEvento+": "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
		return valoraciones;
	}

	@Override
	public List<UsuarioEventoPuntuaDTO> historialPuntuaciones(Long idUsuario) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<UsuarioEventoPuntuaDTO> valoraciones = null;
		try  {
			c = ConnectionManager.getConnection();								
			
			c.setAutoCommit(false);
			
			valoraciones = usuarioPuntuaDAO.findByCreador(c, idUsuario);
			
			commitOrRollback = true;
			
		} catch (SQLException e) {
			logger.error("HistorialPuntiaciones: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("HistorialPuntiaciones: "+idUsuario+": "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
		return valoraciones;
	}
	
	@Override
	public Long create(UsuarioEventoPuntuaDTO u) 
			throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		Long puntuacionId = null;
		Date date = new Date(System.currentTimeMillis());
		u.setFechaComentario(date);
		try  {
			c = ConnectionManager.getConnection();								
			
			c.setAutoCommit(false);
			
			puntuacionId = usuarioPuntuaDAO.create(c, u);	
			
			commitOrRollback = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
		return puntuacionId;
	}

	@Override
	public Integer update(String comentario,Integer valoracion,Long idUsuario,Long idEvento) 
			throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		Integer updateRows = null;
		try  {
			c = ConnectionManager.getConnection();								
			
			c.setAutoCommit(false);
			
			updateRows = usuarioPuntuaDAO.update(c,comentario,valoracion,idUsuario,idEvento);	
			
			commitOrRollback = true;
			
		} catch (SQLException e) {
			logger.error("Update: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("Update: "+idUsuario+": "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
		return updateRows;
	}

	

}
