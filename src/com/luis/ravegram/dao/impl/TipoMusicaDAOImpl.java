package com.luis.ravegram.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.TipoMusicaDAO;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.TipoMusica;

public class TipoMusicaDAOImpl implements TipoMusicaDAO{
	
	private static Logger logger = LogManager.getLogger(TipoMusicaDAOImpl.class);

	public TipoMusicaDAOImpl() {

	}

	public List <TipoMusica> findAll(Connection c) throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<TipoMusica> tiposmusica = null;

		try { 
			// Compose SQL			
			String sql = "SELECT id,nombre "
					+ "FROM tipo_musica";

			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			

			// Performing operation
			rs = preparedStatement.executeQuery();
			
			if (logger.isInfoEnabled()) {
        		logger.info("findAll query = "+preparedStatement.toString());
			}
			
			int i;
			tiposmusica = new ArrayList<TipoMusica>();
			TipoMusica tipomusica = null;
			while (rs.next()) {				
				tipomusica = new TipoMusica();

				i = 1;
				tipomusica.setId(rs.getLong(i++));
				tipomusica.setNombre(rs.getString(i++));
				tiposmusica.add(tipomusica);
			}			



		} catch (SQLException e) {			
			logger.error("FindByAll:"+e.getMessage() ,e);
			throw new DataException("FindByAll:"+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}	

		return tiposmusica;
	}

}


