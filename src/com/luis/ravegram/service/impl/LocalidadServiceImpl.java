package com.luis.ravegram.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.LocalidadDAO;
import com.luis.ravegram.dao.impl.LocalidadDAOImpl;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.Localidad;
import com.luis.ravegram.service.LocalidadService;

public class LocalidadServiceImpl implements LocalidadService{

	private static Logger logger = LogManager.getLogger(LocalidadServiceImpl.class);

	private LocalidadDAO localidadDAO  = null;

	public LocalidadServiceImpl() {
		localidadDAO = new LocalidadDAOImpl();
	}

	@Override
	public List<Localidad> findByProvincia(Long idProvincia) throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<Localidad> localidades = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			localidades = localidadDAO.findByProvincia(c, idProvincia);

			commitOrRollback = true;

		} catch (SQLException sqle) {
			logger.error("findByProvincia: "+idProvincia+": "+sqle.getMessage() ,sqle);
			throw new DataException("findByProvincia: "+idProvincia+": "+sqle.getMessage() ,sqle);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return localidades;
	}
}
