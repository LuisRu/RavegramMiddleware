package com.luis.ravegram.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.impl.EstablecimientoDAO;
import com.luis.ravegram.dao.util.DAOUtils;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.UserNotFoundException;
import com.luis.ravegram.model.Establecimiento;
import com.luis.ravegram.model.EstablecimientoCriteria;


public class EstablecimientoDAOImpl implements EstablecimientoDAO {
	
	private static Logger logger = LogManager.getLogger(EstablecimientoDAOImpl.class);
	
	public EstablecimientoDAOImpl() {	
	}

	public Establecimiento findById(Connection c,Long id)  throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Establecimiento establecimiento = null;

		try {
			// Compose SQL			
			String sql = "select e.id,e.NOMBRE,e.CALLE,e.ZIP,e.AFORO,e.ID_TIPO_ESTABLECIMIENTO,te.NOMBRE,e.ID_LOCALIDAD,l.NOMBRE,e.LATITUD,E.LONGITUD "
						+ "from establecimiento e inner join tipo_establecimiento te "
						+ "on e.ID_TIPO_ESTABLECIMIENTO=te.ID "
						+ "inner join localidad l  "
						+ "on e.ID_LOCALIDAD = l.ID "
						+ "where e.id = ? ";

			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			JDBCUtils.setParameter(preparedStatement, 1, id);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {				
				establecimiento = loadNext(rs);
			}			



		} catch (SQLException e) {	
			logger.error("FindById: "+id+": "+e.getMessage() ,e);
			throw new DataException("FindById: "+id+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return establecimiento;
	}
	

	
	public List<Establecimiento> findByLocalidad(Connection c, Long idLocalidad) throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<Establecimiento> establecimientos = null;

		try {
			// Compose SQL			
			String sql = "select e.id,e.NOMBRE,e.CALLE,e.ZIP,e.AFORO,e.ID_TIPO_ESTABLECIMIENTO,te.NOMBRE,e.ID_LOCALIDAD,l.NOMBRE,e.LATITUD,E.LONGITUD "
					+ "from establecimiento e inner join tipo_establecimiento te "
					+ "on e.ID_TIPO_ESTABLECIMIENTO=te.ID "
					+ "inner join localidad l  "
					+ "on e.ID_LOCALIDAD = l.ID "
					+ "where l.id = ? "
					+ "ORDER BY e.NOMBRE ASC ";
			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


			JDBCUtils.setParameter(preparedStatement, 1, idLocalidad);
			rs = preparedStatement.executeQuery();


			establecimientos = new ArrayList<Establecimiento>();
			Establecimiento establecimiento = null;
			while (rs.next()) {				
				establecimiento = loadNext(rs);
				establecimientos.add(establecimiento);
			}			


		} catch (SQLException e) {			
			logger.error("FindByLocalidad: "+idLocalidad+": "+e.getMessage() ,e);
			throw new DataException("FindByLocalidad: "+idLocalidad+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return  establecimientos;
	}
	
	
	@Override
	public List<Establecimiento> findByCriteria(Connection c, EstablecimientoCriteria ec) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<Establecimiento> establecimientos = null;

		try {
			// Compose SQL			
			StringBuilder queryString = new StringBuilder("select e.id,e.NOMBRE,e.CALLE,e.ZIP,e.AFORO,e.ID_TIPO_ESTABLECIMIENTO,te.NOMBRE,e.ID_LOCALIDAD,l.NOMBRE,e.LATITUD,E.LONGITUD "
					+ "from establecimiento e inner join tipo_establecimiento te "
					+ "on e.ID_TIPO_ESTABLECIMIENTO=te.ID "
					+ "inner join localidad l  "
					+ "on e.ID_LOCALIDAD = l.ID ");
			
			boolean first = true;
			
			if(ec.getTipoEstablecimiento()!=null) {
				DAOUtils.addClause(queryString, first," e.ID_TIPO_ESTABLECIMIENTO = ? ");
				first = false;
			}
			if(ec.getIdLocalidad() != null) {
				DAOUtils.addClause(queryString, first," l.id = ? ");
				first = false;
			}
			
			queryString.append("ORDER BY e.NOMBRE ASC ");
			
			
			// Create prepared statement
			preparedStatement = c.prepareStatement(queryString.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			
			int i = 1;
			if(ec.getTipoEstablecimiento()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getTipoEstablecimiento());
			}
			if(ec.getIdLocalidad()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getIdLocalidad());
			}
			
			rs = preparedStatement.executeQuery();


			establecimientos = new ArrayList<Establecimiento>();
			Establecimiento establecimiento = null;
			while (rs.next()) {				
				establecimiento = loadNext(rs);
				establecimientos.add(establecimiento);
			}			


		} catch (SQLException e) {			
			logger.error("FindByCriteria: "+ec+": "+e.getMessage() ,e);
			throw new DataException("FindByCriteria: "+ec+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return  establecimientos;
	}
	
	public Long create(Connection c,Establecimiento establecimiento) throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			//SQL

			String sql = " INSERT INTO ESTABLECIMIENTO(NOMBRE,CALLE,ZIP,AFORO,ID_TIPO_ESTABLECIMIENTO,ID_LOCALIDAD) "
					+ " VALUES (?,?,?,?,?,?) ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getNombre());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getCalle());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getZip());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getAforo());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getIdTipoEstablecimiento());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getIdLocalidad());


			int insertedRows = preparedStatement.executeUpdate();
			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();
				if(rs.next()) {
					establecimiento.setId(rs.getLong(1));
				}
			} else {
				throw new DataException("Establecimiento: "+establecimiento.getNombre());
			}

		} catch (SQLException e) {			
			logger.error("Establecimiento: ",establecimiento.getId(), e.getMessage(),e);
			throw new DataException("Establecimiento: "+establecimiento.getId()+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return establecimiento.getId();
	}
	
	
	public int update(Connection c, Establecimiento establecimiento) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int updatedRows = 0;
		try {
			//SQL

			String sql ="UPDATE establecimiento "
					+ "SET NOMBRE = ?, "
					+ "	   CALLE= ?, "
					+ "    ZIP=?, "
					+ "    AFORO=?, "
					+ "    ID_TIPO_ESTABLECIMIENTO=?, "
					+ "    ID_LOCALIDAD=? "
					+ "WHERE ID = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getNombre());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getCalle());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getZip());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getAforo());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getIdTipoEstablecimiento());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getIdLocalidad());

			updatedRows = preparedStatement.executeUpdate();
			if (updatedRows!=1) {
				throw new UserNotFoundException("Establecimiento: "+establecimiento.getId());
			}

		} catch (SQLException e) {	
			logger.error("Establecimiento: ",establecimiento.getId(), e.getMessage(),e);
			throw new DataException("Establecimiento: "+establecimiento.getId()+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}

	
	private static Establecimiento loadNext(ResultSet rs) 
			throws SQLException { 
		Establecimiento establecimiento =  new Establecimiento();

		int i = 1;
		establecimiento.setId(rs.getLong(i++));
		establecimiento.setNombre(rs.getString(i++));
		establecimiento.setCalle(rs.getString(i++));
		establecimiento.setZip(rs.getString(i++));
		establecimiento.setAforo(rs.getInt(i++));
		establecimiento.setIdTipoEstablecimiento(rs.getLong(i++));
		establecimiento.setNombreTipoEstablecimiento(rs.getString(i++));
		establecimiento.setIdLocalidad(rs.getLong(i++));
		establecimiento.setNombreLocalidad(rs.getString(i++));
		establecimiento.setLatitud(rs.getDouble(i++));
		establecimiento.setLongitud(rs.getDouble(i++));
		return establecimiento;
	}

	

}



