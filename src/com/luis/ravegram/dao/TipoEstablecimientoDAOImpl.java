package com.luis.ravegram.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.impl.TipoEstablecimientoDAO;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.TipoEstablecimiento;

public class TipoEstablecimientoDAOImpl implements TipoEstablecimientoDAO {
	
	private static Logger logger = LogManager.getLogger(TipoEstablecimientoDAOImpl.class);

	public TipoEstablecimientoDAOImpl() {

	}

	public List <TipoEstablecimiento> findAll(Connection c) throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<TipoEstablecimiento> tiposestablecimiento = null;
		try {
			// Compose SQL			
			String sql = "SELECT id,nombre "
					+ "FROM tipo_establecimiento "
					+ " ORDER BY NOMBRE ASC";

			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i;

			// Set parameter values
			i = 1;


			// Performing operation
			rs = preparedStatement.executeQuery();

			tiposestablecimiento = new ArrayList<TipoEstablecimiento>();
			TipoEstablecimiento tipoestablecimiento = null;
			while (rs.next()) {				
				tipoestablecimiento = new TipoEstablecimiento();

				i = 1;
				tipoestablecimiento.setId(rs.getLong(i++));
				tipoestablecimiento.setNombre(rs.getString(i++));
				tiposestablecimiento.add(tipoestablecimiento);
			}			



		} catch (SQLException e) {			
			logger.error("FindByAll:"+e.getMessage() ,e);
			throw new DataException("FindByAll:"+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return tiposestablecimiento;
	}

}


