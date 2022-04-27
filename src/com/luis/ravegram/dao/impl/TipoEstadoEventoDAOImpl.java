package com.luis.ravegram.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.TipoEstadoEventoDAO;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.TipoEstadoEvento;

public class TipoEstadoEventoDAOImpl implements TipoEstadoEventoDAO{
	
	private static Logger logger = LogManager.getLogger(TipoEstadoEventoDAOImpl.class);

	public TipoEstadoEventoDAOImpl() {}

	public List <TipoEstadoEvento> findAll(Connection c)  
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<TipoEstadoEvento> tiposEstadoEvento = null;

		try {
			// Compose SQL			
			String sql = "SELECT id,estado "
					+ "FROM tipo_estado_evento"
					+ " ORDER BY ID ASC ";

			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			

			// Performing operation
			rs = preparedStatement.executeQuery();
			
			
			if (logger.isInfoEnabled()) {
        		logger.info("findAll query = "+preparedStatement.toString());
			}

			int i;
			tiposEstadoEvento = new ArrayList<TipoEstadoEvento>();
			TipoEstadoEvento tipoestadoevento = null;
			while (rs.next()) {				
				tipoestadoevento = new TipoEstadoEvento();

				i = 1;
				tipoestadoevento.setId(rs.getLong(i++));
				tipoestadoevento.setNombre(rs.getString(i++));
				tiposEstadoEvento.add(tipoestadoevento);
			}			


		} catch (SQLException e) {			
			logger.error("FindByAll:"+e.getMessage() ,e);
			throw new DataException("FindByAll:"+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return tiposEstadoEvento;
	}

}

