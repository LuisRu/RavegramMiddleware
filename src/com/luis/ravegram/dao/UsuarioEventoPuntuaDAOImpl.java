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

import com.luis.ravegram.dao.impl.UsuarioEventoPuntuaDAO;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.UsuarioEventoPuntuaDTO;

public class UsuarioEventoPuntuaDAOImpl implements UsuarioEventoPuntuaDAO{
	
	private static Logger logger = LogManager.getLogger(UsuarioEventoPuntuaDAOImpl.class);
	
	public UsuarioEventoPuntuaDAOImpl() {
		
	}
	
	public List<UsuarioEventoPuntuaDTO> findByEvento(Connection c,Long idEvento) throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<UsuarioEventoPuntuaDTO> puntuaciones = null;
		
		try {
			// Compose SQL			
			String sql = "select uep.ID_USUARIO,u.USER_NAME,uep.ID_EVENTO,e.NOMBRE,uep.COMENTARIO,uep.VALORACION,uep.FECHA "
						+ " from usuario_evento_puntua uep inner join usuario u "
						+ " on uep.ID_USUARIO = u.ID "
						+ " inner join evento e "
						+ " on uep.ID_EVENTO = e.ID "
						+ "where uep.ID_EVENTO = ? "
						+ "ORDER BY uep.VALORACION ASC ";
			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


			JDBCUtils.setParameter(preparedStatement, 1, idEvento);
			rs = preparedStatement.executeQuery();


			puntuaciones = new ArrayList<UsuarioEventoPuntuaDTO>();
			UsuarioEventoPuntuaDTO puntuacion = null;
			while (rs.next()) {		
				puntuacion = loadNext(rs);
				puntuaciones.add(puntuacion);
			}			


		} catch (SQLException e) {			
			logger.error("FindByEvento: "+idEvento+": "+e.getMessage() ,e);
			throw new DataException("FindByEvento: "+idEvento+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return puntuaciones;
	}
	
	
	public List<UsuarioEventoPuntuaDTO> findByCreador(Connection c,Long idUsuario) throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<UsuarioEventoPuntuaDTO> puntuaciones = null;
		
		try {
			// Compose SQL			
			String sql = "select uep.ID_USUARIO,u.USER_NAME,uep.ID_EVENTO,e.NOMBRE,uep.COMENTARIO,uep.VALORACION,uep.FECHA "
						+ " from usuario_evento_puntua uep inner join usuario u "
						+ " on uep.ID_USUARIO = u.ID "
						+ " inner join evento e "
						+ " on uep.ID_EVENTO = e.ID "
						+ "where uep.ID_USUARIO = ? "
						+ "ORDER BY uep.VALORACION ASC ";
			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


			JDBCUtils.setParameter(preparedStatement, 1, idUsuario);
			rs = preparedStatement.executeQuery();


			puntuaciones = new ArrayList<UsuarioEventoPuntuaDTO>();
			UsuarioEventoPuntuaDTO puntuacion = null;
			while (rs.next()) {		
				puntuacion = loadNext(rs);
				puntuaciones.add(puntuacion);
			}			


		} catch (SQLException e) {			
			logger.error("FindByCreador: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("FindByCreador: "+idUsuario+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return puntuaciones;
	}
	
	public Long create(Connection c,UsuarioEventoPuntuaDTO usuario) throws DataException {
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




	public int update(Connection c,String comentario,Integer valoracion,Long idUsuario,Long idEvento) throws DataException {
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
			if (updatedRows!=1) {
				throw new DataException("Valoracion: "+comentario);
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
