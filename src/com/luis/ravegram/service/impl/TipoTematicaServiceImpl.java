package com.luis.ravegram.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.TipoTematicaDAO;
import com.luis.ravegram.dao.impl.TipoTematicaDAOImpl;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.TipoTematica;
import com.luis.ravegram.service.TipoTematicaService;

public class TipoTematicaServiceImpl implements TipoTematicaService {

	private static Logger logger = LogManager.getLogger(TipoTematicaServiceImpl.class);

	private TipoTematicaDAO tipoTematicaDAO = null;
	
	public TipoTematicaServiceImpl() {
		tipoTematicaDAO = new TipoTematicaDAOImpl();
	}

	@Override
	public List<TipoTematica> findAll() throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<TipoTematica> tiposTematica = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			tiposTematica = tipoTematicaDAO.findAll(c);		


			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("findAll : "+sqle.getMessage() ,sqle);
			throw new DataException("findAll: ");						
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return tiposTematica;
	}
}
