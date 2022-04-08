package com.luis.ravegram.service.impl;

import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.UsuarioSigueDAOImpl;
import com.luis.ravegram.dao.impl.UsuarioSigueDAO;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.service.UsuarioSigueService;

public class UsuarioSigueServiceImpl implements UsuarioSigueService {
	
	private static Logger logger = LogManager.getLogger(UsuarioSigueServiceImpl.class);
	
	private UsuarioSigueDAO usuarioSigueDAO = null;

	public UsuarioSigueServiceImpl() {
		usuarioSigueDAO = new UsuarioSigueDAOImpl();
		
	}

	@Override
	public void unFollow(long idSeguidor, long idSeguido) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuarioSigueDAO.delete(idSeguidor, idSeguido);		


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("Unfollow: "+idSeguido+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("Unfollow: "+idSeguido+": "+ex.getMessage() ,ex);
			throw new ServiceException("Unfollow: "+idSeguido+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}

	@Override
	public void follow(long idSeguidor, long idSeguido) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuarioSigueDAO.create(idSeguidor, idSeguido);		


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("follow: "+idSeguido+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("follow: "+idSeguido+": "+ex.getMessage() ,ex);
			throw new ServiceException("follow: "+idSeguido+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}
	

	

}
