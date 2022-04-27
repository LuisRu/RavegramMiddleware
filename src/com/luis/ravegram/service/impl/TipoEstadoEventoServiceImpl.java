package com.luis.ravegram.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.TipoEstadoEventoDAO;
import com.luis.ravegram.dao.impl.TipoEstadoEventoDAOImpl;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.TipoEstadoEvento;
import com.luis.ravegram.service.TipoEstadoEventoService;

public class TipoEstadoEventoServiceImpl implements TipoEstadoEventoService{
	
	private static Logger logger = LogManager.getLogger(TipoEstadoEventoServiceImpl.class);
	
	private TipoEstadoEventoDAO tipoEstadoEventoDAO = null;

	public TipoEstadoEventoServiceImpl() {
		tipoEstadoEventoDAO = new TipoEstadoEventoDAOImpl();
		
	}

	@Override
	public List<TipoEstadoEvento> findAll() 
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<TipoEstadoEvento> estadosEventos = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			estadosEventos = tipoEstadoEventoDAO.findAll(c);		
			
			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("findAll : "+sqle.getMessage() ,sqle);
			throw new DataException("findAll: ");						
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		
		return estadosEventos;
	}
	
	
	
	

}
