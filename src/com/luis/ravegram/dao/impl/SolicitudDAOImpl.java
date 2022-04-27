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

import com.luis.ravegram.dao.SolicitudDAO;
import com.luis.ravegram.dao.util.DAOUtils;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.dao.util.SQLUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.RequestNotFoundException;
import com.luis.ravegram.model.SolicitudDTO;
import com.luis.ravegram.model.criteria.SolicitudCriteria;


public class SolicitudDAOImpl implements SolicitudDAO {

	private static Logger logger = LogManager.getLogger(SolicitudDAOImpl.class);

	private final String QUERY_BASE_FIND = " select ues.ID_USUARIO,u.USER_NAME,ues.ID_EVENTO,e.NOMBRE,ues.FECHA,ues.ID_TIPO_ESTADO,tes.ESTADO "
			+ " from usuario_evento_solicita ues inner join tipo_estado_solicitud tes "
			+ " on ues.ID_TIPO_ESTADO = tes.id "
			+ " inner join evento e "
			+ " on ues.ID_EVENTO = e.ID "
			+ " inner join usuario u "
			+ " on ues.ID_USUARIO = u.ID ";



	public SolicitudDAOImpl() {
	}


	@Override
	public SolicitudDTO findByUsuarioEvento(Connection c,Long idUsuario, Long idEvento)  
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		SolicitudDTO solicitud = null;
		try {
			// Compose SQL			
			StringBuilder sql = new StringBuilder(QUERY_BASE_FIND)
					.append(" WHERE ues.ID_USUARIO = ?  AND ues.ID_EVENTO = ?  ");


			// Create prepared statement
			preparedStatement = c.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			JDBCUtils.setParameter(preparedStatement, i++, idUsuario);
			JDBCUtils.setParameter(preparedStatement, i++, idEvento);


			rs = preparedStatement.executeQuery();

			if (logger.isInfoEnabled()) {
				logger.info("findByUsuarioEventro query = "+preparedStatement.toString());
			}

			if (rs.next()) {				
				solicitud = loadNext(rs);
			}			

		} catch (SQLException e) {			
			logger.error("findByUsuarioEventro: "+idUsuario+" :  "+e.getMessage() ,e);
			throw new DataException("findByUsuarioEventro: "+idUsuario+" : "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return solicitud;
	}





	@Override
	public List<SolicitudDTO> findByCriteria(Connection c,SolicitudCriteria uepc )  
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<SolicitudDTO> solicitudes = null;
		SolicitudDTO solicitud = null;
		try {
			// Compose SQL			
			StringBuilder sql = new StringBuilder(QUERY_BASE_FIND);



			boolean first = true;

			if(uepc.getIdUsuario()!=null){
				DAOUtils.addClause(sql, first," ues.ID_USUARIO = ? ");
				first = false;
			}
			if(uepc.getIdEvento()!=null){
				DAOUtils.addClause(sql, first," ues.ID_EVENTO = ?  ");
				first = false;
			}
			if(uepc.getIdTipoEstado()!=null){
				DAOUtils.addClause(sql, first," ues.ID_TIPO_ESTADO = ? ");
				first = false;
			}

			sql.append("ORDER BY u.USER_NAME DESC");

			// Create prepared statement
			preparedStatement = c.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			if(uepc.getIdUsuario()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, uepc.getIdUsuario());
			}
			if(uepc.getIdEvento()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, uepc.getIdEvento());
			}
			if(uepc.getIdTipoEstado()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, uepc.getIdTipoEstado());
			}


			rs = preparedStatement.executeQuery();

			if (logger.isInfoEnabled()) {
				logger.info("findByCriteria query = "+preparedStatement.toString());
			}

			solicitudes = new ArrayList<SolicitudDTO>();
			while (rs.next()) {				
				solicitud = loadNext(rs);
				solicitudes.add(solicitud);
			}			

		} catch (SQLException e) {			
			logger.error("findByIdCriteria: "+uepc+" :  "+e.getMessage() ,e);
			throw new DataException("findByIdCriteria: "+uepc+" : "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return solicitudes;
	}


	@Override
	public List<SolicitudDTO> findSolicitudesPendientes(Connection c, Long idUsuario) 
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<SolicitudDTO> solicitudes = null;
		SolicitudDTO solicitud = null;
		try {
			// Compose SQL			
			StringBuilder sql = new StringBuilder(QUERY_BASE_FIND)
					.append(" where ues.id_tipo_estado = 2 "
							+ " AND ues.ID_EVENTO IN (SELECT ID "
							+ " FROM evento "
							+ " where ID_USUARIO = ?)"); 

			// Create prepared statement
			preparedStatement = c.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++,idUsuario);

			rs = preparedStatement.executeQuery();

			if (logger.isInfoEnabled()) {
				logger.info("findSolicitudesPendientes query = "+preparedStatement.toString());
			}


			solicitudes = new ArrayList<SolicitudDTO>();
			while (rs.next()) {				
				solicitud = loadNext(rs);
				solicitudes.add(solicitud);
			}			


		} catch (SQLException e) {
			logger.error("findSolicitudesPendientes: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("findSolicitudesPendientes: "+idUsuario+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return  solicitudes;
	}


	@Override
	public List<SolicitudDTO> findInvitacionesPendientes(Connection c, Long idUsuario) 
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<SolicitudDTO> solicitudes = null;
		SolicitudDTO solicitud = null;
		try {
			// Compose SQL			
			StringBuilder sql = new StringBuilder(QUERY_BASE_FIND)
					.append(" where ues.id_tipo_estado = 1 "
							+ " AND ues.ID_EVENTO NOT IN (SELECT ID  "
							+ " FROM evento "
							+ " where ID_USUARIO = ?)"); 

			// Create prepared statement
			preparedStatement = c.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++,idUsuario);

			rs = preparedStatement.executeQuery();

			if (logger.isInfoEnabled()) {
				logger.info("findInvitacionesPendientes query = "+preparedStatement.toString());
			}


			solicitudes = new ArrayList<SolicitudDTO>();
			while (rs.next()) {				
				solicitud = loadNext(rs);
				solicitudes.add(solicitud);
			}			


		} catch (SQLException e) {
			logger.error("findInvitacionesPendientes: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("findInvitacionesPendientes: "+idUsuario+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return  solicitudes;
	}


	public Long create(Connection c,SolicitudDTO usuario)
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//SQL

			String sql = " INSERT INTO USUARIO_EVENTO_SOLICITA(ID_USUARIO,ID_EVENTO,FECHA,ID_TIPO_ESTADO) "
					+ " VALUES (?,?,?,?) ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getIdUsuario());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getIdEvento());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getFecha()); 
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getIdTipoEstado());				


			int insertedRows = preparedStatement.executeUpdate();

			if (logger.isInfoEnabled()) {
				logger.info("create query = "+preparedStatement.toString());
			}

			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();

			} else {
				throw new DataException("create: "+usuario.getIdUsuario());
			}


		} catch (SQLException e) {			
			logger.error("create: "+usuario.getIdUsuario()+": "+e.getMessage() ,e);
			throw new DataException("create: "+usuario.getIdUsuario()+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return usuario.getIdUsuario();
	}

	
	@Override
	public void createMultiple(Connection c,List<SolicitudDTO> solicitudes)
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql =  new StringBuilder(
					"INSERT INTO USUARIO_EVENTO_SOLICITA(ID_USUARIO,ID_EVENTO,FECHA,ID_TIPO_ESTADO) VALUE ")
					.append(SQLUtils.toInterrogacionSolicitudes(solicitudes));
			
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple query = "+sql.toString());
			}
			
			
			preparedStatement = c.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

			int i  = 1;
			for(SolicitudDTO solicitud:solicitudes) {
				JDBCUtils.setParameter(preparedStatement, i++, solicitud.getIdUsuario());
				JDBCUtils.setParameter(preparedStatement, i++, solicitud.getIdEvento());
				JDBCUtils.setParameter(preparedStatement, i++, solicitud.getFecha());
				JDBCUtils.setParameter(preparedStatement, i++, solicitud.getIdTipoEstado());
			}
			
				


			preparedStatement.executeUpdate();
			
			
			if (logger.isInfoEnabled()) {
				logger.info("createMultiple query = "+preparedStatement.toString());
			}
			


		} catch (SQLException e) {			
			logger.error("createMultiple: "+e.getMessage() ,e);
			throw new DataException("createMultiple: "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
	}




	public void deleteByEventoEstado(Connection c,Long idEvento ,Long idEstadoSolicitud)
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//SQL

			String sql = " DELETE FROM USUARIO_EVENTO_SOLICITA  "
					+ " WHERE ID_EVENTO = ? AND ID_TIPO_ESTADO = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, idEvento);
			JDBCUtils.setParameter(preparedStatement, i++, idEstadoSolicitud);			


			preparedStatement.executeUpdate();

			if (logger.isInfoEnabled()) {
				logger.info("deleteByEventoEstado query = "+preparedStatement.toString());
			}


		} catch (SQLException e) {			
			logger.error("deleteByEventoEstadoSolicitud: "+idEvento+": "+e.getMessage() ,e);
			throw new DataException("deleteByEventoEstadoSolicitud: "+idEvento+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
	}
	
	
	public void deleteByUsuario(Connection c,Long idUsuario)
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//SQL

			StringBuilder sql = new StringBuilder(" DELETE FROM USUARIO_EVENTO_SOLICITA "
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

	public void deleteByEventosIds(Connection c,List <Long> idsEventos)
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//SQL

			StringBuilder sql = new StringBuilder(" DELETE FROM USUARIO_EVENTO_SOLICITA "
												+ " WHERE ID_EVENTO IN ( ")
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
				logger.info("deleteByEvento query = "+preparedStatement.toString());
			}


		} catch (SQLException e) {			
			logger.error("deleteByEvento:"+e.getMessage() ,e);
			throw new DataException("deleteByEvento: "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
	}
	
	
	public void deleteByEventoUsuarioIds(Connection c,Long idEvento ,List <Long> idsUsuarios)
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//SQL

			StringBuilder sql = new StringBuilder(" DELETE FROM USUARIO_EVENTO_SOLICITA "
												+ " WHERE ID_EVENTO = ? "
												+ " AND id_usuario IN (")
								.append(SQLUtils.toInterrogacion(idsUsuarios))
								.append(")");

			//create prepared statement
			preparedStatement = c.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, idEvento);
			for(Long id: idsUsuarios) {
				JDBCUtils.setParameter(preparedStatement, i++, id);
			}
						


			preparedStatement.executeUpdate();

			if (logger.isInfoEnabled()) {
				logger.info("deleteByEventoUsuarioIds query = "+preparedStatement.toString());
			}


		} catch (SQLException e) {			
			logger.error("deleteByEventoUsuarioIds: "+idEvento+": "+e.getMessage() ,e);
			throw new DataException("deleteByEventoUsuarioIds: "+idEvento+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
	}

	
	
	







	public Integer update(Connection c,SolicitudDTO solicitud) 
			throws DataException,RequestNotFoundException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Integer updatedRows = 0;
		try {
			//SQL

			String sql =" UPDATE USUARIO_EVENTO_SOLICITA "
					+ " SET  ID_USUARIO = ?, "
					+ "      ID_EVENTO = ?, "
					+ "      FECHA = ?, "
					+ "      ID_TIPO_ESTADO = ? "
					+ " WHERE ID_USUARIO = ?  AND ID_EVENTO = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, solicitud.getIdUsuario());
			JDBCUtils.setParameter(preparedStatement, i++, solicitud.getIdEvento());
			JDBCUtils.setParameter(preparedStatement, i++, solicitud.getFecha());
			JDBCUtils.setParameter(preparedStatement, i++, solicitud.getIdTipoEstado());
			JDBCUtils.setParameter(preparedStatement, i++, solicitud.getIdUsuario());
			JDBCUtils.setParameter(preparedStatement, i++, solicitud.getIdEvento());

			updatedRows = preparedStatement.executeUpdate();

			if (logger.isInfoEnabled()) {
				logger.info("update query = "+preparedStatement.toString());
			}

			if (updatedRows==0) {
				throw new RequestNotFoundException("update: "+solicitud.getIdUsuario());
			}

		} catch (SQLException e) {			
			logger.error("update: "+solicitud.getIdUsuario()+": "+e.getMessage() ,e);
			throw new DataException("update: "+solicitud.getIdUsuario()+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}


	@Override
	public Integer updateEstado(Connection c, Long idUsuario,Long idEvento, Long idEstado) 
			throws DataException,RequestNotFoundException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Integer updatedRows = 0;
		try {
			//SQL

			String sql =" UPDATE usuario_evento_solicita "
					+ " SET ID_TIPO_ESTADO = ? "
					+ " WHERE ID_EVENTO = ? AND ID_USUARIO = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, idEstado);
			JDBCUtils.setParameter(preparedStatement, i++, idEvento);
			JDBCUtils.setParameter(preparedStatement, i++, idUsuario);

			updatedRows = preparedStatement.executeUpdate();


			if (logger.isInfoEnabled()) {
				logger.info("updateEstado query = "+preparedStatement.toString());
			}


			if (updatedRows==0) {
				throw new RequestNotFoundException("updateEstado: "+idEvento);
			}

		} catch (SQLException e) {			
			logger.error("updateEstado: "+idEvento+": "+e.getMessage() ,e);
			throw new DataException("updateEstado: "+idEvento+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}



	private static SolicitudDTO loadNext(ResultSet rs) 
			throws SQLException { 
		SolicitudDTO solicitud =  new SolicitudDTO();

		int i = 1;
		solicitud.setIdUsuario(rs.getLong(i++));
		solicitud.setNombreUsuario(rs.getString(i++));
		solicitud.setIdEvento(rs.getLong(i++));
		solicitud.setNombreEvento(rs.getString(i++));
		solicitud.setFecha(rs.getDate(i++));
		solicitud.setIdTipoEstado(rs.getLong(i++));
		solicitud.setNombreEstado(rs.getString(i++));
		return solicitud;
	}



}
