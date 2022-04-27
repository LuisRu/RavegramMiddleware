package com.luis.ravegram.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.UsuarioSigueDAO;
import com.luis.ravegram.dao.impl.UsuarioSigueDAOImpl;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.service.UsuarioSigueService;

public class UsuarioSigueServiceImpl implements UsuarioSigueService {
	
	private static Logger logger = LogManager.getLogger(UsuarioSigueServiceImpl.class);
	
	private UsuarioSigueDAO usuarioSigueDAO = null;

	public UsuarioSigueServiceImpl() {
		usuarioSigueDAO = new UsuarioSigueDAOImpl();
		
	}

	@Override
	public void unFollow(long idSeguidor, long idSeguido) 
			throws DataException{
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuarioSigueDAO.delete(c,idSeguidor, idSeguido);		


			commitOrRollback = true;

		} catch (SQLException sqle) {
			logger.error("unFollow: "+idSeguido+": "+sqle.getMessage() ,sqle);
			throw new DataException("unFollow: "+idSeguido+": "+sqle.getMessage() ,sqle);							
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}

	@Override
	public void follow(long idSeguidor, long idSeguido) 
			throws DataException{
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuarioSigueDAO.create(c,idSeguidor, idSeguido);		


			commitOrRollback = true;

		} catch (SQLException sqle) {
			logger.error("follow: "+idSeguido+": "+sqle.getMessage() ,sqle);
			throw new DataException("follow: "+idSeguido+": "+sqle.getMessage() ,sqle);		
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}
	
	
	@Override
	public void deleteAll(Long idUsuario) 
			throws DataException{
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuarioSigueDAO.deleteAll(c,idUsuario);		


			commitOrRollback = true;

		} catch (SQLException sqle) {
			logger.error("deleteAll: "+idUsuario+": "+sqle.getMessage() ,sqle);
			throw new DataException("deleteAll: "+idUsuario+": "+sqle.getMessage() ,sqle);		
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}
	

	

}
