package com.luis.ravegram.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.EstablecimientoDAO;
import com.luis.ravegram.dao.util.DAOUtils;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.EstablecimientoNotFoundException;
import com.luis.ravegram.model.EstablecimientoDTO;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.criteria.EstablecimientoCriteria;


public class EstablecimientoDAOImpl implements EstablecimientoDAO {
	
	private static Logger logger = LogManager.getLogger(EstablecimientoDAOImpl.class);
	
	private static Map<String, String> SORTING_CRITERIA_MAP = null;

	static {
		SORTING_CRITERIA_MAP = new HashMap<String, String>();

		SORTING_CRITERIA_MAP.put("AFORO-ASC", " ORDER BY e.AFORO ASC");
		SORTING_CRITERIA_MAP.put("AFORO-DESC", " ORDER BY e.AFORO DESC");
	}
	
	private final String QUERY_BASE_FIND = " select e.id,e.NOMBRE,e.CALLE,e.ZIP,e.AFORO,e.ID_TIPO_ESTABLECIMIENTO,te.NOMBRE,e.ID_LOCALIDAD,l.NOMBRE,e.LATITUD,E.LONGITUD "
			+ " from establecimiento e inner join tipo_establecimiento te "
			+ " on e.ID_TIPO_ESTABLECIMIENTO=te.ID "
			+ " inner join localidad l  "
			+ " on e.ID_LOCALIDAD = l.ID  ";

	
	public EstablecimientoDAOImpl() {	
	}
	
	@Override
	public EstablecimientoDTO findById(Connection c, Long idUsuario) 
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		EstablecimientoDTO establecimiento = null;
		try {
			
			// Compose SQL			
			StringBuilder queryString = new StringBuilder(QUERY_BASE_FIND)
														.append("WHERE e.ID =  ? ");
					
			
			
			// create prepared statement
			preparedStatement = c.prepareStatement(queryString.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			
			
			JDBCUtils.setParameter(preparedStatement, 1, idUsuario);
			
			rs = preparedStatement.executeQuery();
			
			
			
			if (logger.isInfoEnabled()) {
        		logger.info("findById query = "+preparedStatement.toString());
			}

			
			if (rs.next()) {				
				establecimiento = loadNext(rs);
			}			


		} catch (SQLException e) {			
			logger.error("findById: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("FindByfindByIdCriteria: "+idUsuario+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return  establecimiento;
	}
	


	@Override
	public Results<EstablecimientoDTO> findByCriteria(Connection c, EstablecimientoCriteria ec,int startIndex, int pageSize) 
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Results<EstablecimientoDTO> results = null;
		List<EstablecimientoDTO> establecimientos = null;
		EstablecimientoDTO establecimiento = null;
		try {
			
			// Compose SQL			
			StringBuilder queryString = new StringBuilder(QUERY_BASE_FIND);
			
			boolean first = true;
			
			if(ec.getId()!=null) {
				DAOUtils.addClause(queryString, first," e.ID = ? ");
				first = false;
			}
			
			if(ec.getIdTipoEstablecimiento()!=null) {
				DAOUtils.addClause(queryString, first," e.ID_TIPO_ESTABLECIMIENTO = ? ");
				first = false;
			}
			if(ec.getIdLocalidad() != null) {
				DAOUtils.addClause(queryString, first," l.id = ? ");
				first = false;
			}
			
			
			//ORDER BY
			if (ec.getOrderBy()!=null) {
				queryString.append(SORTING_CRITERIA_MAP.get(ec.getOrderBy()));			
			}else {
				queryString.append("ORDER BY e.NOMBRE ");
			}
			
			
			
			
			
			// create prepared statement
			preparedStatement = c.prepareStatement(queryString.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			
			//seteado de datos
			int i = 1;
			if(ec.getId()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getId());
			}
			if(ec.getIdTipoEstablecimiento()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getIdTipoEstablecimiento());
			}
			if(ec.getIdLocalidad()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getIdLocalidad());
			}
			
			rs = preparedStatement.executeQuery();
			
			
			
			if (logger.isInfoEnabled()) {
        		logger.info("FindByCriteriaEstablecimiento query = "+preparedStatement.toString());
			}


			//paginacion
			int resultsLoaded = 0;
			results = new Results<EstablecimientoDTO>();
			establecimientos = new ArrayList<EstablecimientoDTO>();
			
			if ((startIndex >=1) && rs.absolute(startIndex)) {
				do { 
					establecimiento = loadNext(rs);
					if(establecimiento.getId()!=null) {
						establecimientos.add(establecimiento);
						resultsLoaded++;
					}
				} while (resultsLoaded<pageSize && rs.next());
			}

			results.setData(establecimientos);
			results.setTotal(DAOUtils.getTotalRows(rs));
			
		

		} catch (SQLException e) {			
			logger.error("FindByCriteria: "+ec+": "+e.getMessage() ,e);
			throw new DataException("FindByCriteria: "+ec+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return  results;
	}
	
	public Long create(Connection c,EstablecimientoDTO establecimiento) 
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			//SQL

			String sql = " INSERT INTO ESTABLECIMIENTO(NOMBRE,CALLE,ZIP,AFORO,ID_TIPO_ESTABLECIMIENTO,ID_LOCALIDAD,LATITUD,LONGITUD) "
					+ " VALUES (?,?,?,?,?,?,?,?)";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getNombre());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getCalle());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getZip());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getAforo());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getIdTipoEstablecimiento());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getIdLocalidad());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getLatitud());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getLongitud());


			int insertedRows = preparedStatement.executeUpdate();
			
			if (logger.isInfoEnabled()) {
        		logger.info("CreateEstablecimiento query = "+preparedStatement.toString());
			}

			
			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();
				if(rs.next()) {
					establecimiento.setId(rs.getLong(1));
				}
			} else {
				throw new DataException("create: "+establecimiento.getNombre());
			}

		} catch (SQLException e) {			
			logger.error("create: ",establecimiento.getId(), e.getMessage(),e);
			throw new DataException("create: "+establecimiento.getId()+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return establecimiento.getId();
	}
	
	
	public int update(Connection c, EstablecimientoDTO establecimiento) 
			throws DataException,EstablecimientoNotFoundException {
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
					+ "    ID_LOCALIDAD=?, "
					+ "    LATITUD=?, "
					+ "    LONGITUD=? "
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
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getLatitud());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getLongitud());
			JDBCUtils.setParameter(preparedStatement, i++, establecimiento.getId());
			

			updatedRows = preparedStatement.executeUpdate();
			
			if (logger.isInfoEnabled()) {
        		logger.info("UpdateEstablecimiento query = "+preparedStatement.toString());
			}
			
			
			
			if (updatedRows!=1) {
				throw new EstablecimientoNotFoundException("update: "+establecimiento.getId());
			}

			
		} catch (SQLException e) {	
			logger.error("update: ",establecimiento.getId(), e.getMessage(),e);
			throw new DataException("update: "+establecimiento.getId()+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}

	
	private static EstablecimientoDTO loadNext(ResultSet rs) 
			throws SQLException { 
		EstablecimientoDTO establecimiento =  new EstablecimientoDTO();

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



