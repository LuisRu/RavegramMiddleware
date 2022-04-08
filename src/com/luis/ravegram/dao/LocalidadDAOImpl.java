package com.luis.ravegram.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.impl.LocalidadDAO;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.Localidad;

public class LocalidadDAOImpl implements LocalidadDAO{
	
	private static Logger logger = LogManager.getLogger(LocalidadDAOImpl.class);

	public LocalidadDAOImpl() {
	}

	public List <Localidad> findByProvincia(Connection c,Long id) throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<Localidad> localidades = null;

		try { 
			// Compose SQL			
			String sql = "select l.id,l.nombre,p.nombre "
					+ " from localidad l inner join provincia p "
					+ " on l.ID_PROVINCIA= p.id "
					+ " where l.id = ? "
					+ " ORDER BY l.NOMBRE ASC ";
			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			
			JDBCUtils.setParameter(preparedStatement, 1, id);
			int i;

			// Set parameter values
			i = 1;


			// Performing operation
			rs = preparedStatement.executeQuery();

			localidades = new ArrayList<Localidad>();
			Localidad localidad = null;
			while (rs.next()) {				
				localidad = new Localidad();

				i = 1;
				localidad.setId(rs.getLong(i++));
				localidad.setNombre(rs.getString(i++));
				localidad.setNombreProvincia(rs.getString(i++));
				localidades.add(localidad);
			}			



		} catch (SQLException e) {			
			logger.error("FindById: "+id+": "+e.getMessage() ,e);
			throw new DataException("FindById: "+id+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return localidades;
	}

}

