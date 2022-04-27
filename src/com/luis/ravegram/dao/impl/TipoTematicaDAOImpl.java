package com.luis.ravegram.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.TipoTematicaDAO;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.TipoTematica;

public class TipoTematicaDAOImpl  implements TipoTematicaDAO{
	
	private static Logger logger = LogManager.getLogger(TipoTematicaDAOImpl.class);

	public TipoTematicaDAOImpl() {

	}

	public List <TipoTematica> findAll(Connection c) throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<TipoTematica> tipostematica = null;

		try {
			// Compose SQL			
			String sql = "SELECT id,nombre "
					+ "FROM tipo_tematica "
					+ " ORDER BY NOMBRE ASC";

			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		

			// Performing operation
			rs = preparedStatement.executeQuery();
			
			if (logger.isInfoEnabled()) {
        		logger.info("findAll query = "+preparedStatement.toString());
			}

			int i;
			tipostematica = new ArrayList<TipoTematica>();
			TipoTematica tipotematica = null;
			while (rs.next()) {				
				tipotematica = new TipoTematica();

				i = 1;
				tipotematica.setId(rs.getLong(i++));
				tipotematica.setNombre(rs.getString(i++));
				tipostematica.add(tipotematica);
			}			



		} catch (SQLException e) {			
			logger.error("FindByAll:"+e.getMessage() ,e);
			throw new DataException("FindByAll:"+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}	


		return tipostematica;
	}

}


