package com.luis.ravegram.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.EstablecimientoDAO;
import com.luis.ravegram.dao.impl.EstablecimientoDAOImpl;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.EstablecimientoNotFoundException;
import com.luis.ravegram.model.EstablecimientoDTO;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.criteria.EstablecimientoCriteria;
import com.luis.ravegram.service.EstablecimientoService;

public class EstablecimientoServiceImpl implements EstablecimientoService {
	
	private static Logger logger = LogManager.getLogger(EstablecimientoServiceImpl.class);

	private EstablecimientoDAO establecimientoDAO = null;

	public EstablecimientoServiceImpl() {
		establecimientoDAO = new EstablecimientoDAOImpl();
	}
	

	@Override
	public EstablecimientoDTO findById(Long idUsuario)
			throws DataException{
		Connection c = null;
		boolean commitOrRollback = false;
		EstablecimientoDTO establecimiento = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);
			
			establecimiento = establecimientoDAO.findById(c, idUsuario);		


			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("findById: "+idUsuario+": "+sqle.getMessage() ,sqle);
			throw new DataException("findById: "+idUsuario);						
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return establecimiento;
	}
	
	
	@Override
	public Results<EstablecimientoDTO> findByCriteria(EstablecimientoCriteria ec,int startIndex, int pageSize)
			throws DataException{
		Connection c = null;
		boolean commitOrRollback = false;
		Results<EstablecimientoDTO> results = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);
			
			results = establecimientoDAO.findByCriteria(c, ec,startIndex,pageSize);		


			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("findByCriteria: "+ec+": "+sqle.getMessage() ,sqle);
			throw new DataException("findByCriteria: "+ec);						
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return results;
	}



	@Override
	public void create(EstablecimientoDTO establecimiento) 
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			establecimientoDAO.create(c, establecimiento);		


			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("create: "+establecimiento.getNombre()+": "+sqle.getMessage() ,sqle);
			throw new DataException("create: "+establecimiento.getNombre());						
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}

	@Override
	public void update(EstablecimientoDTO establecimiento)
			throws DataException,EstablecimientoNotFoundException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			establecimientoDAO.update(c, establecimiento);		


			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("update: "+establecimiento.getNombre()+": "+sqle.getMessage() ,sqle);
			throw new DataException("update: "+establecimiento.getNombre());						
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}




	

}
