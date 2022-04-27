package com.luis.ravegram.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.TipoEstablecimientoDAO;
import com.luis.ravegram.dao.impl.TipoEstablecimientoDAOImpl;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.TipoEstablecimiento;
import com.luis.ravegram.service.TipoEstablecimientoService;

public class TipoEstablecimientoServiceImpl implements TipoEstablecimientoService{
	
	private static Logger logger = LogManager.getLogger(TipoEstablecimientoServiceImpl.class);

	private TipoEstablecimientoDAO tipoEstablecimientoDAO = null;
	
	public TipoEstablecimientoServiceImpl() {
		tipoEstablecimientoDAO = new TipoEstablecimientoDAOImpl();
	}

	@Override
	public List<TipoEstablecimiento> findAll() throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<TipoEstablecimiento> tiposEstablecimientos = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			tiposEstablecimientos = tipoEstablecimientoDAO.findAll(c);		


			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("findAll: : "+sqle.getMessage() ,sqle);
			throw new DataException("findAll: ");						
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return tiposEstablecimientos;
	}

}
