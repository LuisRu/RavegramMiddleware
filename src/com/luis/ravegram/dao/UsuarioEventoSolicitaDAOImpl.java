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

import com.luis.ravegram.dao.impl.UsuarioEventoSolicitaDAO;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.dao.util.SQLUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.SolicitudDTO;


public class UsuarioEventoSolicitaDAOImpl implements UsuarioEventoSolicitaDAO {
	
	private static Logger logger = LogManager.getLogger(UsuarioEventoSolicitaDAOImpl.class);


	public UsuarioEventoSolicitaDAOImpl() {
	}

	@Override
	public SolicitudDTO findByIdUsuarioIdEvento(Connection c, Long idUsuario,Long idEvento)  throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		SolicitudDTO solicitud = null;

		try {
			// Compose SQL			
			String sql = "select ues.ID_USUARIO,u.USER_NAME,ues.ID_EVENTO,e.NOMBRE,ues.FECHA,ues.ID_TIPO_ESTADO,tes.ESTADO "
					+ " from usuario_evento_solicita ues inner join tipo_estado_solicitud tes "
					+ "	on ues.ID_TIPO_ESTADO = tes.id "
					+ "	inner join evento e "
					+ "	on ues.ID_EVENTO = e.ID "
					+ "	inner join usuario u "
					+ "	on ues.ID_USUARIO = u.ID "
					+ " where ues.ID_USUARIO = ? AND ues.ID_EVENTO = ? ";

			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			JDBCUtils.setParameter(preparedStatement, i++, idUsuario);
			JDBCUtils.setParameter(preparedStatement, i++, idEvento);
			rs = preparedStatement.executeQuery();


			if (rs.next()) {				
				solicitud = loadNext(rs);
			}			

		} catch (SQLException e) {			
			logger.error("findByIdUsuarioIdEvento: "+idEvento+": "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("findByIdUsuarioIdEvento: "+idEvento+": "+idUsuario+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return solicitud;
	}

	public List<SolicitudDTO> findByUsuario(Connection c, Long idUsuario)  throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<SolicitudDTO> solicitudes = null;
		try {
			// Compose SQL			
			String sql = "select ues.ID_USUARIO,u.USER_NAME,ues.ID_EVENTO,e.NOMBRE,ues.FECHA,ues.ID_TIPO_ESTADO,tes.ESTADO "
					+ " from usuario_evento_solicita ues inner join tipo_estado_solicitud tes "
					+ "	on ues.ID_TIPO_ESTADO = tes.id "
					+ "	inner join evento e "
					+ "	on ues.ID_EVENTO = e.ID "
					+ "	inner join usuario u "
					+ "	on ues.ID_USUARIO = u.ID "
					+ " where ues.ID_USUARIO = ? ";

			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			JDBCUtils.setParameter(preparedStatement, i++, idUsuario);
			rs = preparedStatement.executeQuery();

			solicitudes = new ArrayList<SolicitudDTO>();
			SolicitudDTO solicitud = null;
			while (rs.next()) {				
				solicitud = loadNext(rs);
				solicitudes.add(solicitud);
			}			

		} catch (SQLException e) {			
			logger.error("findByUsuario: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("findByUsuario: "+idUsuario+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return solicitudes;
	}




	public List<SolicitudDTO> findBySolicitudesAMisEventosPendientes(Connection c, List<Long> idsEventos)  throws DataException {
		Statement statement = null;
		ResultSet rs = null;
		List<SolicitudDTO> solicitudes = null;
		try {
			// Compose SQL			
			StringBuilder sqlsb = new StringBuilder("select ues.ID_USUARIO,u.USER_NAME,ues.ID_EVENTO,e.NOMBRE,ues.FECHA,ues.ID_TIPO_ESTADO,tes.ESTADO "
					+ " from usuario_evento_solicita ues inner join tipo_estado_solicitud tes "
					+ "	on ues.ID_TIPO_ESTADO = tes.id "
					+ "	inner join evento e "
					+ "	on ues.ID_EVENTO = e.ID "
					+ "	inner join usuario u "
					+ "	on ues.ID_USUARIO = u.ID "
					+ " where ues.id_tipo_estado = 2"
					+ " AND ues.ID_EVENTO IN (")
					.append(SQLUtils.toIN(idsEventos))
					.append(")");

			statement = c.createStatement();
			rs = statement.executeQuery(sqlsb.toString());

			solicitudes = new ArrayList<SolicitudDTO>();
			SolicitudDTO solicitud = null;
			while (rs.next()) {				
				solicitud = loadNext(rs);
				solicitudes.add(solicitud);
			}			

		} catch (SQLException e) {			
			logger.error("findBySolicitudesAMisEventosPendientes: "+SQLUtils.toIN(idsEventos)+": "+e.getMessage() ,e);
			throw new DataException("findBySolicitudesAMisEventosPendientes: "+SQLUtils.toIN(idsEventos)+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(statement);
		}
		return solicitudes;
	}
	
	
	public List<SolicitudDTO> findByInvitacionesAEventosPendientes(Connection c, Long idUsuario)  throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<SolicitudDTO> solicitudes = null;
		try {
			// Compose SQL			
			String sql = "select ues.ID_USUARIO,u.USER_NAME,ues.ID_EVENTO,e.NOMBRE,ues.FECHA,ues.ID_TIPO_ESTADO,tes.ESTADO "
					+ " from usuario_evento_solicita ues inner join tipo_estado_solicitud tes "
					+ "	on ues.ID_TIPO_ESTADO = tes.id "
					+ "	inner join evento e "
					+ "	on ues.ID_EVENTO = e.ID "
					+ "	inner join usuario u "
					+ "	on ues.ID_USUARIO = u.ID "
					+ " where ues.ID_USUARIO = ? AND ues.ID_TIPO_ESTADO =  1 ";

			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			JDBCUtils.setParameter(preparedStatement, i++, idUsuario);
			rs = preparedStatement.executeQuery();

			solicitudes = new ArrayList<SolicitudDTO>();
			SolicitudDTO solicitud = null;
			while (rs.next()) {				
				solicitud = loadNext(rs);
				solicitudes.add(solicitud);
			}			

		} catch (SQLException e) {			
			logger.error("findByInvitacionesAEventosPendientes: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("findByInvitacionesAEventosPendientes: "+idUsuario+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return solicitudes;
	}

	



	public Long create(Connection c,SolicitudDTO usuario) throws DataException{
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
			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();

			} else {
				throw new DataException("Solicitud: "+usuario.getIdUsuario());
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





	public Integer update(Connection c,SolicitudDTO solicitud) throws DataException {
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
			if (updatedRows!=1) {
				throw new DataException("Solicitud: "+solicitud.getIdUsuario());
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
	public Integer updateEstado(Connection c, Long idUsuario,Long idEvento, Long idEstado) throws DataException {
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
			if (updatedRows!=1) {
				throw new DataException("Solicitud: "+idEvento);
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
