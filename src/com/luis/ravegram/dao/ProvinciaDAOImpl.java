package com.luis.ravegram.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.impl.ProvinciaDAO;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.Provincia;

public class ProvinciaDAOImpl implements ProvinciaDAO {
	
	private static Logger logger = LogManager.getLogger(ProvinciaDAOImpl.class);

	public ProvinciaDAOImpl() {
	}

	public List <Provincia> findAll(Connection c) throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<Provincia> provincias = null;

		try { 
			// Compose SQL			
			String sql = "SELECT id,nombre "
					+ "FROM provincia "
					+ " ORDER BY NOMBRE ASC";

			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i;

			// Set parameter values
			i = 1;


			// Performing operation
			rs = preparedStatement.executeQuery();

			provincias = new ArrayList<Provincia>();
			Provincia provincia = null;
			while (rs.next()) {				
				provincia = new Provincia();

				i = 1;
				provincia.setId(rs.getLong(i++));
				provincia.setNombre(rs.getString(i++));
				provincias.add(provincia);
			}			



		} catch (SQLException e) {			
			logger.error("FindByAll:"+e.getMessage() ,e);
			throw new DataException("FindByAll:"+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return provincias;
	}

}


