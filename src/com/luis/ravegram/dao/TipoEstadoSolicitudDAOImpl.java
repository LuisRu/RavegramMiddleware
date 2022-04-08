package com.luis.ravegram.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.impl.TipoEstadoSolicitudDAO;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.TipoEstadoSolicitud;

public class TipoEstadoSolicitudDAOImpl implements TipoEstadoSolicitudDAO{
	
	private static Logger logger = LogManager.getLogger(TipoEstadoSolicitudDAOImpl.class);

	public TipoEstadoSolicitudDAOImpl() {

	}

	public List <TipoEstadoSolicitud> findAll(Connection c) throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<TipoEstadoSolicitud> tiposestadosolicitud = null;
		try {
			// Compose SQL			
			String sql = "SELECT id,estado "
					+ "FROM tipo_estado_solicitud"
					+ " ORDER BY ESTADO ASC";

			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i;

			// Set parameter values
			i = 1;


			// Performing operation
			rs = preparedStatement.executeQuery();

			tiposestadosolicitud = new ArrayList<TipoEstadoSolicitud>();
			TipoEstadoSolicitud tipoestadosolicitud = null;
			while (rs.next()) {				
				tipoestadosolicitud = new TipoEstadoSolicitud();

				i = 1;
				tipoestadosolicitud.setId(rs.getLong(i++));
				tipoestadosolicitud.setNombre(rs.getString(i++));
				tiposestadosolicitud.add(tipoestadosolicitud);
			}			



		} catch (SQLException e) {			
			logger.error("FindByAll:"+e.getMessage() ,e);
			throw new DataException("FindByAll:"+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return tiposestadosolicitud;
	}

}

