package com.luis.ravegram.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.PuntuacionDAO;
import com.luis.ravegram.dao.impl.PuntuacionDAOImpl;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.RaitingNotFoundException;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioEventoPuntuaDTO;
import com.luis.ravegram.model.criteria.PuntuacionCriteria;
import com.luis.ravegram.service.PuntuacionService;

public class PuntuacionServiceImpl implements PuntuacionService {

	private static Logger logger = LogManager.getLogger(PuntuacionServiceImpl.class);
	
	private PuntuacionDAO usuarioPuntuaDAO = null;
	
	public PuntuacionServiceImpl() {
		usuarioPuntuaDAO = new PuntuacionDAOImpl();
	}

	
	
	
	/**
	 * Nos devuelve las valoraciones de los eventos de 
	 * un usuario
	 */
	@Override
	public Results<UsuarioEventoPuntuaDTO> findByCriteria(PuntuacionCriteria uep, int startIndex, int pageSize) 
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		Results<UsuarioEventoPuntuaDTO> results = null;
		try  {
			c = ConnectionManager.getConnection();								
			
			c.setAutoCommit(false);
			
			results = usuarioPuntuaDAO.findByCriteria(c, uep,startIndex,pageSize);
			
			commitOrRollback = true;
			
		} catch (SQLException e) {
			logger.error("findByCriteria: "+uep+": "+e.getMessage() ,e);
			throw new DataException("findByCriteria: "+uep+": "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
		return results;
	}
	

	/**
	 * Nos devuelve las valoraciones de los eventos de 
	 * un usuario
	 */
	@Override
	public Results<UsuarioEventoPuntuaDTO> findByEventosUsuario(Long idUsuario,int startIndex, int pageSize) 
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		Results<UsuarioEventoPuntuaDTO> results = null;
		try  {
			c = ConnectionManager.getConnection();								
			
			c.setAutoCommit(false);
			
			results = usuarioPuntuaDAO.findByEventosUsuario(c, idUsuario,startIndex,pageSize);
			
			commitOrRollback = true;
			
		} catch (SQLException e) {
			logger.error("findByEventosUsuario: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("findByEventosUsuario: "+idUsuario+": "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
		return results;
	}
	
	
	@Override
	public Long create(UsuarioEventoPuntuaDTO u) 
			throws DataException  {
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
	public void deleteByUsuario(Long idUsuario) 
			throws DataException  {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								
			
			c.setAutoCommit(false);
			
			usuarioPuntuaDAO.deleteByUsuario(c, idUsuario);	
			
			commitOrRollback = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
	}
	
	
	@Override
	public void deleteByEventos(List<Long> idsEventos) 
			throws DataException  {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								
			
			c.setAutoCommit(false);
			
			usuarioPuntuaDAO.deleteByEventos(c, idsEventos);	
			
			commitOrRollback = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
	}

	@Override
	public Integer update(String comentario,Integer valoracion,Long idUsuario,Long idEvento) 
			throws DataException,RaitingNotFoundException {
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
