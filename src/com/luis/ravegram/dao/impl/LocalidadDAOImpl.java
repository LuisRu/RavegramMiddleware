package com.luis.ravegram.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.LocalidadDAO;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.Localidad;

public class LocalidadDAOImpl implements LocalidadDAO{
	
	private static Logger logger = LogManager.getLogger(LocalidadDAOImpl.class);

	public LocalidadDAOImpl() {
	}

	public List<Localidad> findByProvincia(Connection c,Long idProvincia)
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<Localidad> localidades = null;
		Localidad localidad = null;
		try { 
			// Compose SQL			
			String sql = "select l.id,l.nombre,p.nombre "
					+ " from localidad l inner join provincia p "
					+ " on l.ID_PROVINCIA= p.id "
					+ " where l.id = ? "
					+ " ORDER BY l.NOMBRE ASC ";
			
			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			
			JDBCUtils.setParameter(preparedStatement, 1, idProvincia);
	
			
			rs = preparedStatement.executeQuery();
			
			if (logger.isInfoEnabled()) {
        		logger.info("findByProvincia query = "+preparedStatement.toString());
			}

			int i;
			localidades = new ArrayList<Localidad>();
			while (rs.next()) {				
				localidad = new Localidad();

				i = 1;
				localidad.setId(rs.getLong(i++));
				localidad.setNombre(rs.getString(i++));
				localidad.setNombreProvincia(rs.getString(i++));
				localidades.add(localidad);
			}			



		} catch (SQLException e) {			
			logger.error("findByProvincia: "+idProvincia+": "+e.getMessage() ,e);
			throw new DataException("findByProvincia: "+idProvincia+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return localidades;
	}

}

