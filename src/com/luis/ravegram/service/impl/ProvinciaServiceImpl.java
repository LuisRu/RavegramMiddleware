package com.luis.ravegram.service.impl;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.ProvinciaDAO;
import com.luis.ravegram.dao.impl.ProvinciaDAOImpl;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.Provincia;
import com.luis.ravegram.service.ProvinciaService;

public class ProvinciaServiceImpl implements ProvinciaService{
	
	private static Logger logger = LogManager.getLogger(ProvinciaServiceImpl.class);
	
	private ProvinciaDAO provinciaDAO = null;


	public ProvinciaServiceImpl() {
		provinciaDAO = new ProvinciaDAOImpl();
	}

	@Override
	public List<Provincia> findAll() throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<Provincia> provincias = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			provincias = provinciaDAO.findAll(c);	

			commitOrRollback = true;

		} catch (SQLException sqle) {
			logger.error("findAll : "+sqle.getMessage() ,sqle);
			throw new DataException("findAll : "+sqle.getMessage() ,sqle);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return provincias;
	}
	}

