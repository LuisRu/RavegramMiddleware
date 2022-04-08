package com.luis.ravegram.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.impl.UsuarioSigueDAO;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;

public class UsuarioSigueDAOImpl implements UsuarioSigueDAO {
	
	private static Logger logger = LogManager.getLogger(UsuarioSigueDAOImpl.class);

	public UsuarioSigueDAOImpl() {

	}

	public int create(long idSeguidor,long idSeguido) throws DataException{
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int updatedRows = 0;
		try {

			con = ConnectionManager.getConnection();
			//SQL

			String sql =" INSERT INTO USUARIO_SIGUE(ID_USUARIO_SEGUIDOR,ID_USUARIO_SEGUIDO) "
					+ " VALUES (?,?) ";

			//create prepared statement
			preparedStatement = con.prepareStatement(sql);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, idSeguidor);
			JDBCUtils.setParameter(preparedStatement, i++, idSeguido);


			updatedRows = preparedStatement.executeUpdate();
			if (updatedRows!=1) {
				throw new DataException("create: "+idSeguidor);
			}

		} catch (SQLException e) {			
			logger.error("create: "+idSeguido+": "+e.getMessage() ,e);
			throw new DataException("create: "+idSeguidor+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
			JDBCUtils.close(con);
		}
		return updatedRows;
	}


	public int delete(long idSeguidor,long idSeguido) throws DataException {
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int updatedRows = 0;
		try {

			con = ConnectionManager.getConnection();
			//SQL

			String sql ="DELETE FROM USUARIO_SIGUE "
					+ " WHERE ID_USUARIO_SEGUIDOR = ? "
					+ " AND ID_USUARIO_SEGUIDO = ? ";


			//create prepared statement
			preparedStatement = con.prepareStatement(sql);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, idSeguidor);
			JDBCUtils.setParameter(preparedStatement, i++, idSeguido);

			updatedRows = preparedStatement.executeUpdate();
			if (updatedRows!=1) {
				throw new DataException("delete: "+idSeguidor);
			}

		} catch (SQLException e) {			
			logger.error("delete: "+idSeguido+": "+e.getMessage() ,e);
			throw new DataException("delete: "+idSeguidor+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
			JDBCUtils.close(con);
		}
		return updatedRows;
	}


}

