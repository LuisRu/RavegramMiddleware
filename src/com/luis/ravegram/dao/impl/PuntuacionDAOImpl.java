package com.luis.ravegram.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.PuntuacionDAO;
import com.luis.ravegram.dao.util.DAOUtils;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.dao.util.SQLUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.RaitingNotFoundException;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioEventoPuntuaDTO;
import com.luis.ravegram.model.criteria.PuntuacionCriteria;

public class PuntuacionDAOImpl implements PuntuacionDAO{
	
	private static Logger logger = LogManager.getLogger(PuntuacionDAOImpl.class);
	
	private final String QUERY_BASE_FIND = " select uep.ID_USUARIO,u.USER_NAME,uep.ID_EVENTO,e.NOMBRE,uep.COMENTARIO,uep.VALORACION,uep.FECHA "
										 + " from usuario_evento_puntua uep inner join usuario u "
										 + " on uep.ID_USUARIO = u.ID  "
										 + " inner join evento e "
										 + " on uep.ID_EVENTO = e.ID ";
	
	public PuntuacionDAOImpl() {
		
	}

	
	public Results<UsuarioEventoPuntuaDTO> findByCriteria(Connection c,PuntuacionCriteria uep, int startIndex, int pageSize) 
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Results<UsuarioEventoPuntuaDTO> results = new Results<UsuarioEventoPuntuaDTO>();
		List<UsuarioEventoPuntuaDTO> puntuaciones = null;
		UsuarioEventoPuntuaDTO puntuacion = null;
		try {
			// Compose SQL			
			StringBuilder sql = new StringBuilder(QUERY_BASE_FIND);
			
			
			boolean first = true;

			if(uep.getIdUsuario()!=null){
				DAOUtils.addClause(sql, first," uep.ID_USUARIO = ? ");
				first = false;
			}
			if(uep.getIdEvento()!=null){
				DAOUtils.addClause(sql, first," uep.ID_EVENTO = ? ");
				first = false;
			}
			
			sql.append("ORDER BY uep.valoracion ASC");
			
			
			
			
			if (logger.isInfoEnabled()) {
        		logger.info("findByCriteria query = "+sql.toString());
			}

			
			// Create prepared statement
			preparedStatement = c.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

						
			int i = 1;
			if(uep.getIdUsuario()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, uep.getIdUsuario());
			}
			if(uep.getIdEvento()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, uep.getIdEvento());
			}
			
			rs = preparedStatement.executeQuery();
			
			if (logger.isInfoEnabled()) {
        		logger.info("findByCriteria query = "+preparedStatement.toString());
			}

			
			puntuaciones = new ArrayList<UsuarioEventoPuntuaDTO>();
			int resultsLoaded = 0;
			if ((startIndex >=1) && rs.absolute(startIndex)) {
				do {
					puntuacion = loadNext(rs);
					puntuaciones.add(puntuacion);
					resultsLoaded++;
				} while (resultsLoaded<pageSize && rs.next());
			}

			results.setData(puntuaciones);
			results.setTotal(DAOUtils.getTotalRows(rs));



		} catch (SQLException e) {			
			logger.error("findByCriteria: "+uep+": "+e.getMessage() ,e);
			throw new DataException("findByCriteria: "+uep+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return results;
	}
	
	
	public Results<UsuarioEventoPuntuaDTO> findByEventosUsuario(Connection c,Long idUsuario,int startIndex, int pageSize) 
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Results<UsuarioEventoPuntuaDTO> results = new Results<UsuarioEventoPuntuaDTO>();
		List<UsuarioEventoPuntuaDTO> puntuaciones = null;
		UsuarioEventoPuntuaDTO puntuacion = null;
		try {
			// Compose SQL			
			String sql = "select uep.ID_USUARIO,u.USER_NAME,uep.ID_EVENTO,e.NOMBRE,uep.COMENTARIO,uep.VALORACION,uep.FECHA "
					+ " from usuario_evento_puntua uep inner join usuario u "
					+ " on uep.ID_USUARIO = u.ID  "
					+ " inner join evento e "
					+ " on uep.ID_EVENTO = e.ID  "
					+ " where uep.ID_EVENTO IN (SELECT id "
					+ " 						from evento e "
					+ " 						where e.ID_USUARIO = ?) "
					+ " ORDER BY uep.VALORACION ASC ";
			
			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


			JDBCUtils.setParameter(preparedStatement, 1, idUsuario);
			
			rs = preparedStatement.executeQuery();
			
			if (logger.isInfoEnabled()) {
        		logger.info("findByEventosUsuario query = "+preparedStatement.toString());
			}


			puntuaciones = new ArrayList<UsuarioEventoPuntuaDTO>();
			int resultsLoaded = 0;
			if ((startIndex >=1) && rs.absolute(startIndex)) {
				do {
					puntuacion = loadNext(rs);
					puntuaciones.add(puntuacion);
					resultsLoaded++;
				} while (resultsLoaded<pageSize && rs.next());
			}

			results.setData(puntuaciones);
			results.setTotal(DAOUtils.getTotalRows(rs));



		} catch (SQLException e) {			
			logger.error("findByEventosUsuario: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("findByEventosUsuario: "+idUsuario+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return results;
	}

	
	
	
	public Long create(Connection c,UsuarioEventoPuntuaDTO usuario) 
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			//SQL

			String sql = " INSERT INTO USUARIO_EVENTO_PUNTUA(ID_USUARIO,ID_EVENTO,COMENTARIO,VALORACION,FECHA) "
					+ " VALUES (?,?,?,?,?)";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getIdUsuario());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getIdEvento());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getComentario());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getValoracion());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getFechaComentario());
			
			


			int insertedRows = preparedStatement.executeUpdate();
			
			if (logger.isInfoEnabled()) {
        		logger.info("create query = "+preparedStatement.toString());
			}
			
			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();

			} else {
				throw new DataException("Puntuacion: "+usuario.getIdUsuario());
			}


		} catch (SQLException e) {			
			logger.error("Puntuacion: ",usuario.getIdUsuario(), e.getMessage(),e);
			throw new DataException("Puntuacion: "+usuario.getIdUsuario()+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return usuario.getIdUsuario();
	}


	public void deleteByUsuario(Connection c,Long idUsuario)
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//SQL

			StringBuilder sql = new StringBuilder(" DELETE FROM USUARIO_EVENTO_PUNTUA "
												+ " WHERE ID_USUARIO = ? ");

			//create prepared statement
			preparedStatement = c.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, idUsuario);
			
						


			preparedStatement.executeUpdate();

			if (logger.isInfoEnabled()) {
				logger.info("deleteByUsuario query = "+preparedStatement.toString());
			}


		} catch (SQLException e) {			
			logger.error("deleteByUsuario:"+e.getMessage() ,e);
			throw new DataException("deleteByUsuario: "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
	}
	
	
	public void deleteByEventos(Connection c,List<Long> idsEventos)
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//SQL

			StringBuilder sql = new StringBuilder(" DELETE FROM USUARIO_EVENTO_PUNTUA "
												+ " WHERE ID_EVENTO IN (")
												.append(SQLUtils.toInterrogacion(idsEventos))
												.append(")");

			//create prepared statement
			preparedStatement = c.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

			int i  = 1;
			for(Long id: idsEventos) {
				JDBCUtils.setParameter(preparedStatement, i++, id);
			}
			
			
						


			preparedStatement.executeUpdate();

			if (logger.isInfoEnabled()) {
				logger.info("deleteByEventos query = "+preparedStatement.toString());
			}


		} catch (SQLException e) {			
			logger.error("deleteByEventos:"+e.getMessage() ,e);
			throw new DataException("deleteByEventos: "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
	}
	


	public int update(Connection c,String comentario,Integer valoracion,Long idUsuario,Long idEvento) 
			throws DataException,RaitingNotFoundException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int updatedRows = 0;
		try {
			//SQL

			String sql ="UPDATE USUARIO_EVENTO_PUNTUA "
					+ " SET  COMENTARIO = ?, "
					+ " 	VALORACION = ? "
					+ " WHERE ID_USUARIO = ? AND ID_EVENTO = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, comentario);
			JDBCUtils.setParameter(preparedStatement, i++, valoracion);
			JDBCUtils.setParameter(preparedStatement, i++, idUsuario);
			JDBCUtils.setParameter(preparedStatement, i++, idEvento);

			updatedRows = preparedStatement.executeUpdate();
			
			
			if (logger.isInfoEnabled()) {
        		logger.info("update query = "+preparedStatement.toString());
			}
			
			
			if (updatedRows!=1) {
				throw new RaitingNotFoundException("Valoracion: "+comentario);
			}

		} catch (SQLException e) {			
			logger.error("Valoracion: ",comentario, e.getMessage(),e);
			throw new DataException("Valoracion: "+comentario+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}

	
	private static UsuarioEventoPuntuaDTO loadNext(ResultSet rs) 
			throws SQLException { 
		UsuarioEventoPuntuaDTO valoracion =  new UsuarioEventoPuntuaDTO();
		int i = 1;
		valoracion.setIdUsuario(rs.getLong(i++));
		valoracion.setNombreUsuario(rs.getString(i++));
		valoracion.setIdEvento(rs.getLong(i++));
		valoracion.setNombreEvento(rs.getString(i++));
		valoracion.setComentario(rs.getString(i++));
		valoracion.setValoracion(rs.getInt(i++));
		valoracion.setFechaComentario(rs.getDate(i++));
		return valoracion;
	}
	
	

}
