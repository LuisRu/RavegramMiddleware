package com.luis.ravegram.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.UsuarioSigueDAO;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;

public class UsuarioSigueDAOImpl implements UsuarioSigueDAO {
	
	private static Logger logger = LogManager.getLogger(UsuarioSigueDAOImpl.class);

	public UsuarioSigueDAOImpl() {

	}

	public void create(Connection c,long idSeguidor,long idSeguido) 
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {


			String sql =" INSERT INTO USUARIO_SIGUE(ID_USUARIO_SEGUIDOR,ID_USUARIO_SEGUIDO) "
					+ " VALUES (?,?) ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, idSeguidor);
			JDBCUtils.setParameter(preparedStatement, i++, idSeguido);


			int insertedRows = preparedStatement.executeUpdate();
			
			if (logger.isInfoEnabled()) {
        		logger.info("create query = "+preparedStatement.toString());
			}
			
			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();

			} else {
				throw new DataException("Puntuacion: "+idSeguidor);
			}


		} catch (SQLException e) {			
			logger.error("create: "+idSeguido+": "+e.getMessage() ,e);
			throw new DataException("create: "+idSeguidor+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
	}


	public void delete(Connection c,long idSeguidor,long idSeguido)
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {


			String sql ="DELETE FROM USUARIO_SIGUE "
					+ " WHERE ID_USUARIO_SEGUIDOR = ? "
					+ " AND ID_USUARIO_SEGUIDO = ? ";


			//create prepared statement
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, idSeguidor);
			JDBCUtils.setParameter(preparedStatement, i++, idSeguido);


			int deletedRows = preparedStatement.executeUpdate();
			
			if (logger.isInfoEnabled()) {
        		logger.info("create query = "+preparedStatement.toString());
			}
			
			if (deletedRows==1) {
				rs = preparedStatement.getGeneratedKeys();

			} else {
				throw new DataException("Puntuacion: "+idSeguidor);
			}


		} catch (SQLException e) {			
			logger.error("delete: "+idSeguido+": "+e.getMessage() ,e);
			throw new DataException("delete: "+idSeguidor+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
	}
		
		
		
		public void deleteAll(Connection c,Long idUsuario)
				throws DataException {
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			try {


				String sql ="DELETE FROM USUARIO_SIGUE "
						+ " WHERE ID_USUARIO_SEGUIDOR = ? "
						+ " OR ID_USUARIO_SEGUIDO = ? ";


				//create prepared statement
				preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				int i  = 1;
				JDBCUtils.setParameter(preparedStatement, i++, idUsuario);
				JDBCUtils.setParameter(preparedStatement, i++, idUsuario);

				preparedStatement.executeUpdate();
				
				if (logger.isInfoEnabled()) {
	        		logger.info("deleteAll query = "+preparedStatement.toString());
				}
				
				

			} catch (SQLException e) {			
				logger.error("deleteAll: "+idUsuario+": "+e.getMessage() ,e);
				throw new DataException("deleteAll: "+idUsuario+": "+e.getMessage() ,e);
			} finally {
				JDBCUtils.close(rs);
				JDBCUtils.close(preparedStatement);
			}
	}
}

