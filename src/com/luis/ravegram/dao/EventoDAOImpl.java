package com.luis.ravegram.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.impl.EventoDAO;
import com.luis.ravegram.dao.util.DAOUtils;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.EventoNotFoundException;
import com.luis.ravegram.model.EventoCriteria;
import com.luis.ravegram.model.EventoDTO;
import com.luis.ravegram.service.util.CalculadoraDistanciaUtil;

public class EventoDAOImpl implements EventoDAO{
	
	private static Logger logger = LogManager.getLogger(EventoDAOImpl.class);
	
	public EventoDAOImpl() {
		
	}

	public EventoDTO findById(Connection c,Long id, Double latitudUsuario, Double longitudUsuario) throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		EventoDTO evento = null;

		try {

			// Compose SQL			
			String sql = "select e.ID,e.NOMBRE,e.DESCRIPCION,e.FECHA_HORA,e.NUM_ASISTENTES,e.EDAD_DESDE,e.EDAD_HASTA,e.PUBLIC_PRIVADO,e.LATITUD,"
					+ "e.LONGITUD,e.CALLE,e.ZIP,u.ID,u.USER_NAME,tt.ID,tt.NOMBRE,te.ID,te.NOMBRE,l.ID,l.NOMBRE,tee.ID,tee.estado,tm.ID,tm.nombre "
					+ " from evento e inner join usuario u on e.id_usuario = u.id "
					+ "left outer join tipo_tematica tt on e.id_tipo_tematica = tt.id "
					+ "left outer join tipo_establecimiento te on e.ID_TIPO_ESTABLECIMIENTO = te.ID "
					+ "inner join localidad l on e.id_localidad = l.ID left outer join establecimiento es "
					+ "on e.ID_ESTABLECIMIENTO = es.ID left outer join tipo_estado_evento tee "
					+ "on e.ID_TIPO_ESTADO_EVENTO = tee.id left outer join tipo_musica tm  "
					+ "on e.ID_TIPO_MUSICA = tm.ID "
					+ "where e.id = ? ";

			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			JDBCUtils.setParameter(preparedStatement, 1, id);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {				
				evento = loadNext(rs,latitudUsuario,longitudUsuario);

			}			



		} catch (SQLException e) {			
			logger.error("FindById: "+id+": "+e.getMessage() ,e);
			throw new DataException("FindById: "+id+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return evento;
	}
	

	
	public List<EventoDTO> findBySeguidos(Connection c,Long idUsuario, Double latitudUsuario, Double longitudUsuario) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<EventoDTO> eventos = null;

		try {

			// Compose SQL			
			String sql = "select e.ID,e.NOMBRE,e.DESCRIPCION,e.FECHA_HORA,e.NUM_ASISTENTES,e.EDAD_DESDE,e.EDAD_HASTA,e.PUBLIC_PRIVADO,e.LATITUD, "
					+ "	e.LONGITUD,e.CALLE,e.ZIP,u.ID,u.USER_NAME,tt.ID,tt.NOMBRE,te.ID,te.NOMBRE,l.ID,l.NOMBRE,tee.ID,tee.estado,tm.ID,tm.nombre  "
					+ "	from evento e inner join usuario u "
					+ "	 on e.id_usuario = u.id "
					+ "	left outer join tipo_tematica tt "
					+ "	on e.id_tipo_tematica = tt.id "
					+ "	left outer join tipo_establecimiento te "
					+ "	on e.ID_TIPO_ESTABLECIMIENTO = te.ID "
					+ "	left outer join localidad l  "
					+ "	on e.id_localidad = l.ID "
					+ "	left outer join establecimiento es "
					+ "	on e.ID_ESTABLECIMIENTO = es.ID  "
					+ "	left outer join tipo_estado_evento tee "
					+ "	on e.ID_TIPO_ESTADO_EVENTO = tee.id "
					+ "	left outer join tipo_musica tm  "
					+ "	on e.ID_TIPO_MUSICA = tm.ID  "
					+ "	where u.id IN "
					+ " (SELECT u.id "							//SACAR LOS IDS DE LOS USUARIOS QUE SIGO
					+ "	from usuario u inner join usuario_sigue us 	"
					+ "	on u.id = us.id_usuario_seguido "
					+ "	where us.ID_USUARIO_SEGUIDOR = ? )"
					+ " AND tee.id = 1  "
					+ " AND e.ID NOT IN "
					+ " (select id_evento "					//Los ids de los eventos a los que ya solicite/estoy aceptado / estoy rechazado
					+ "	from usuario_evento_solicita "
					+ "	where id_usuario = ? AND ID_TIPO_ESTADO IN (2,3,4)) "
					+ " AND u.id <> ? "
					+ " ORDER BY e.NOMBRE DESC"  ;
		

			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			JDBCUtils.setParameter(preparedStatement, i++, idUsuario);
			JDBCUtils.setParameter(preparedStatement, i++, idUsuario);
			JDBCUtils.setParameter(preparedStatement, i++, idUsuario);


			
			
			rs = preparedStatement.executeQuery();

			eventos = new ArrayList<EventoDTO>();
			EventoDTO evento = null;
			while (rs.next()) {				
				evento = loadNext(rs,latitudUsuario,longitudUsuario);
				eventos.add(evento);

			}			

		} catch (SQLException e) {			
			logger.error("findBySeguidos: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("findBySeguidos: "+idUsuario+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return eventos;
	}
	
	public List<EventoDTO> findByPendientes(Connection c,Long idUsuario,Date fechaActual, Double latitudUsuario, Double longitudUsuario) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<EventoDTO> eventos = null;

		try {

			// Compose SQL			
			String sql = " select e.ID,e.NOMBRE,e.DESCRIPCION,e.FECHA_HORA,e.NUM_ASISTENTES,e.EDAD_DESDE,e.EDAD_HASTA,e.PUBLIC_PRIVADO,e.LATITUD, "
					+ "	e.LONGITUD,e.CALLE,e.ZIP,u.ID,u.USER_NAME,tt.ID,tt.NOMBRE,te.ID,te.NOMBRE,l.ID,l.NOMBRE,tee.ID,tee.estado,tm.ID,tm.nombre  "
					+ "	from evento e inner join usuario u "
					+ "	on e.id_usuario = u.id "
					+ "	left outer join tipo_tematica tt "
					+ "	on e.id_tipo_tematica = tt.id "
					+ "	left outer join tipo_establecimiento te "
					+ "	on e.ID_TIPO_ESTABLECIMIENTO = te.ID "
					+ "	left outer join localidad l  "
					+ "	on e.id_localidad = l.ID "
					+ "	left outer join establecimiento es "
					+ "	on e.ID_ESTABLECIMIENTO = es.ID  "
					+ "	left outer join tipo_estado_evento tee "
					+ "	on e.ID_TIPO_ESTADO_EVENTO = tee.id "
					+ "	left outer join tipo_musica tm  "
					+ "	on e.ID_TIPO_MUSICA = tm.ID "
					+ " where tee.id = 1 AND e.FECHA_HORA >= ?  "
					+ " AND e.id IN "
					+ " (select id_evento "					//sacar los ids de los eventos a los que estoy aceptado
					+ " from usuario_evento_solicita "
					+ " where id_usuario = ? AND ID_TIPO_ESTADO = 3) "
					+ " ORDER BY e.NOMBRE DESC"  ;
			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			JDBCUtils.setParameter(preparedStatement, i++, fechaActual);
			JDBCUtils.setParameter(preparedStatement, i++, idUsuario);
			
			
			rs = preparedStatement.executeQuery();

			eventos = new ArrayList<EventoDTO>();
			EventoDTO evento = null;
			while (rs.next()) {				
				evento = loadNext(rs, longitudUsuario, latitudUsuario);
				eventos.add(evento);

			}			

		} catch (SQLException e) {			
			logger.error("findByPendientes: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("findByPendientes: "+idUsuario+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return eventos;
	}


	public List<EventoDTO> findByCreador(Connection c,Long idUsuario,Double latitudUsuario,Double longitudUsuario) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<EventoDTO> eventos = null;

		try {

			// Compose SQL			
			String sql = "select e.ID,e.NOMBRE,e.DESCRIPCION,e.FECHA_HORA,e.NUM_ASISTENTES,e.EDAD_DESDE,e.EDAD_HASTA,e.PUBLIC_PRIVADO,e.LATITUD,"
					+ "e.LONGITUD,e.CALLE,e.ZIP,u.ID,u.USER_NAME,tt.ID,tt.NOMBRE,te.ID,te.NOMBRE,l.ID,l.NOMBRE,tee.ID,tee.estado,tm.ID,tm.nombre "
					+ "from evento e inner join usuario u "
					+ "on e.id_usuario = u.id "
					+ "left outer join tipo_tematica tt "
					+ "on e.id_tipo_tematica = tt.id "
					+ "left outer join tipo_establecimiento te "
					+ "on e.ID_TIPO_ESTABLECIMIENTO = te.ID "
					+ "left outer join localidad l "
					+ "on e.id_localidad = l.ID "
					+ "left outer join establecimiento es "
					+ "on e.ID_ESTABLECIMIENTO = es.ID  "
					+ "left outer join tipo_estado_evento tee  "
					+ "on e.ID_TIPO_ESTADO_EVENTO = tee.id  "
					+ "left outer join tipo_musica tm  "
					+ "on e.ID_TIPO_MUSICA = tm.ID "
					+ "where u.id = ? "
					+ "ORDER BY e.NOMBRE DESC ";

			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			JDBCUtils.setParameter(preparedStatement, 1, idUsuario);
			rs = preparedStatement.executeQuery();

			eventos = new ArrayList<EventoDTO>();
			EventoDTO evento = null;
			while (rs.next()) {				
				evento = loadNext(rs,latitudUsuario,longitudUsuario);
				eventos.add(evento);

			}			

		} catch (SQLException e) {			
			logger.error("findByCreador: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("findByCreador: "+idUsuario+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return eventos;
	}
	
	public List<EventoDTO> findByEstablecimiento(Connection c,Long idEstablecimiento, Double latitudUsuario, Double longitudUsuario) throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<EventoDTO> eventos = null;

		try {

			// Compose SQL
			//NO FUNCIONA LA QUERY
			String sql = "select e.ID,e.NOMBRE,e.DESCRIPCION,e.FECHA_HORA,e.NUM_ASISTENTES,e.EDAD_DESDE,e.EDAD_HASTA,e.PUBLIC_PRIVADO,e.LATITUD,"
					+ "e.LONGITUD,e.CALLE,e.ZIP,u.ID,u.USER_NAME,tt.ID,tt.NOMBRE,te.ID,te.NOMBRE,l.ID,l.NOMBRE,tee.ID,tee.estado,tm.ID,tm.nombre "
					+ "from evento e inner join usuario u "
					+ "on e.id_usuario = u.id "
					+ "left outer join tipo_tematica tt "
					+ "on e.id_tipo_tematica = tt.id "
					+ "left outer join tipo_establecimiento te "
					+ "on e.ID_TIPO_ESTABLECIMIENTO = te.ID "
					+ "left outer join localidad l "
					+ "on e.id_localidad = l.ID "
					+ "left outer join establecimiento es "
					+ "on e.ID_ESTABLECIMIENTO = es.ID  "
					+ "left outer join tipo_estado_evento tee  "
					+ "on e.ID_TIPO_ESTADO_EVENTO = tee.id  "
					+ "left outer join tipo_musica tm  "
					+ "on e.ID_TIPO_MUSICA = tm.ID "
					+ "	where e.ID_ESTABLECIMIENTO = ? "
					+ "	ORDER BY e.NOMBRE DESC ";

			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			JDBCUtils.setParameter(preparedStatement, 1, idEstablecimiento);
			rs = preparedStatement.executeQuery();

			eventos = new ArrayList<EventoDTO>();
			EventoDTO evento = null;
			while (rs.next()) {				
				evento = loadNext(rs,latitudUsuario,longitudUsuario);
				eventos.add(evento);

			}			

		} catch (SQLException e) {			
			logger.error("findByEstablecimiento: "+idEstablecimiento+": "+e.getMessage() ,e);
			throw new DataException("findByEstablecimiento: "+idEstablecimiento+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return eventos;
	}

	
	
	public List<EventoDTO> findByCriteria(Connection c,EventoCriteria ec,Long idUsuarioBuscador,Double latitudUsuario, Double longitudUsuario, long startIndex, int pageSize) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<EventoDTO> eventos = null;
		

		try {

			// Compose SQL			
			StringBuilder queryString = new StringBuilder("select e.ID,e.NOMBRE,e.DESCRIPCION,e.FECHA_HORA,e.NUM_ASISTENTES,e.EDAD_DESDE,e.EDAD_HASTA,e.PUBLIC_PRIVADO,e.LATITUD,e.LONGITUD,e.CALLE,e.ZIP,u.ID,u.USER_NAME,tt.ID,tt.NOMBRE,te.ID,te.NOMBRE,l.ID,l.NOMBRE,tee.ID,tee.estado,tm.ID,tm.nombre  "
					+ " from evento e inner join usuario u "
					+ "	on e.id_usuario = u.id "
					+ "	left outer join tipo_tematica tt "
					+ "	on e.id_tipo_tematica = tt.id "
					+ "	left outer join tipo_establecimiento te "
					+ "	on e.ID_TIPO_ESTABLECIMIENTO = te.ID "
					+ "	left outer join localidad l  "
					+ "	on e.id_localidad = l.ID "
					+ "	left outer join establecimiento es "
					+ "	on e.ID_ESTABLECIMIENTO = es.ID  "
					+ "	left outer join tipo_estado_evento tee "
					+ "	on e.ID_TIPO_ESTADO_EVENTO = tee.id "
					+ "	left outer join tipo_musica tm  "
					+ "	on e.ID_TIPO_MUSICA = tm.ID  "
					+ "	where tee.id = 1  "
					+ "	 AND e.ID NOT IN "
					+ "	(select id_evento "					//Los ids de los eventos a los que ya solicite/estoy aceptado / estoy rechazado
					+ "	from usuario_evento_solicita "
					+ "	where id_usuario = ? AND ID_TIPO_ESTADO IN (2,3,4)) "
					+ "	AND u.id <> ? " );
			
			boolean first = false;

			if(ec.getPublicPrivado()!=null) {
				DAOUtils.addClause(queryString, first, " e.PUBLIC_PRIVADO = ? ");
			}	
			if(ec.getFecha()!=null) {
				DAOUtils.addClause(queryString, first, " e.FECHA_HORA = ? ");
			}
			if(ec.getTipoEstablecimiento()!=null) {
				DAOUtils.addClause(queryString, first, " te.ID = ?  ");
			}
			if (ec.getEdadMax() != null) {
				DAOUtils.addClause(queryString, first, " e.EDAD_HASTA = ? ");
			}
			if(ec.getEdadMin() != null) {
				DAOUtils.addClause(queryString, first, " e.EDAD_DESDE = ?  ");
			}
			if(ec.getTipoTematica() != null) {
				DAOUtils.addClause(queryString, first, " tt.id = ?  ");
			}
			if(ec.getTipoMusica() != null) {
				DAOUtils.addClause(queryString, first, " tm.id = ?  ");
			}
			if(ec.getTipoEstadoEvento() != null) {
				DAOUtils.addClause(queryString, first, " tee.id = ?  ");
			}
			
			queryString.append(" ORDER BY e.NOMBRE DESC ");

			System.out.println(queryString.toString());
			
			// Create prepared statement
			preparedStatement = c.prepareStatement(queryString.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			
			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, idUsuarioBuscador);
			JDBCUtils.setParameter(preparedStatement, i++, idUsuarioBuscador);
			
			if (ec.getPublicPrivado()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getPublicPrivado(),true);
			}
			if (ec.getFecha()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getFecha(),true);
			}
			if (ec.getTipoEstablecimiento()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getTipoEstablecimiento(),true);	
			}
			if(ec.getEdadMax()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getEdadMax(),true);	
			}
			if(ec.getEdadMin()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getEdadMin(),true);	
			}
			if(ec.getTipoTematica()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getTipoTematica(),true);	
			}
			if(ec.getTipoMusica()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getTipoMusica(),true);	
			}
			if(ec.getTipoEstadoEvento()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getTipoEstadoEvento(),true);	
			}
			
			
			rs = preparedStatement.executeQuery();

			eventos = new ArrayList<EventoDTO>();
			eventos = loadByDistance(rs, latitudUsuario, longitudUsuario, ec.getDistancia());



		} catch (SQLException e) {			
			logger.error("findByCriteria: "+ec+": "+e.getMessage() ,e);
			throw new DataException("findByCriteria: "+ec+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return eventos;
	}







	public Long create(Connection c,EventoDTO evento) throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//SQL

			String sql = " INSERT INTO EVENTO(NOMBRE,DESCRIPCION,FECHA_HORA,NUM_ASISTENTES,EDAD_DESDE,EDAD_HASTA,PUBLIC_PRIVADO,"
					+ "LATITUD,LONGITUD,CALLE,ZIP,ID_USUARIO,ID_TIPO_TEMATICA,ID_TIPO_ESTABLECIMIENTO,ID_LOCALIDAD,ID_ESTABLECIMIENTO,ID_TIPO_ESTADO_EVENTO,ID_TIPO_MUSICA) "
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, evento.getNombre());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getDescripcion());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getFechaHora());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getNumAsistentes(),true);
			JDBCUtils.setParameter(preparedStatement, i++, evento.getEdadeDesde(),true);
			JDBCUtils.setParameter(preparedStatement, i++, evento.getEdadHasta(),true);
			JDBCUtils.setParameter(preparedStatement, i++, evento.getPublicoPrivado());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getLatitud());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getLongitud());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getCalle());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getZip());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdUsuario());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdTipoTematica(),true);
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdTipoEstablecimiento());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdLocalidad());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdEstablecimiento(),true);
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdTipoEstadoEvento());
			JDBCUtils.setParameter(preparedStatement, i++,evento.getIdTipoMusica(),true);


			int insertedRows = preparedStatement.executeUpdate();
			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();
				if(rs.next()) {
					evento.setId(rs.getLong(1));
					}
			} else {
				throw new DataException("Evento: "+evento.getNombre());
			}


		} catch (SQLException e) {			
			logger.error("create: "+evento.getNombre()+": "+e.getMessage() ,e);
			throw new DataException("create: "+evento.getNombre()+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return evento.getId();
	}


	public  int update(Connection c,EventoDTO evento) throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int updatedRows = 0;
		try {
			//SQL

			String sql =" UPDATE EVENTO "
					+ " SET  NOMBRE = ?,"
					+ "      DESCRIPCION = ?,"
					+ "      FECHA_HORA= ?,"
					+ "      NUM_ASISTENTES = ?,"
					+ "		 EDAD_DESDE = ?,"
					+ "      EDAD_HASTA = ?, "
					+ " 	 PUBLIC_PRIVADO = ?, "
					+ "      LATITUD = ?, "
					+ "      LONGITUD = ?, "
					+ "      CALLE = ? ,"
					+ "      ZIP = ? ,"
					+ "      ID_USUARIO = ?, "
					+ "      ID_TIPO_TEMATICA = ?, "
					+ "      ID_TIPO_ESTABLECIMIENTO = ?, "
					+ "      ID_LOCALIDAD = ? ,"
					+ "    	 ID_ESTABLECIMIENTO = ?, "
					+ "      ID_TIPO_ESTADO_EVENTO = ?, "
					+ "      ID_TIPO_MUSICA = ? "
					+ " WHERE ID = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

		
			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, evento.getNombre());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getDescripcion());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getFechaHora());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getNumAsistentes());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getEdadeDesde());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getEdadHasta());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getPublicoPrivado());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getLatitud());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getLongitud());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getCalle());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getZip());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdUsuario());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdTipoTematica(),true);
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdTipoEstablecimiento());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdLocalidad());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdEstablecimiento(),true);
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdTipoEstadoEvento());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdTipoMusica(),true);
			JDBCUtils.setParameter(preparedStatement, i++, evento.getId());


			updatedRows = preparedStatement.executeUpdate();
			if (updatedRows!=1) {
				throw new EventoNotFoundException("Evento: "+evento.getId());
			}

		} catch (SQLException e) {			
			logger.error("update: "+evento.getNombre()+": "+e.getMessage() ,e);
			throw new DataException("update: "+evento.getNombre()+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}


	public  int updateEstado(Connection c,Long idEvento,Long estado) throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int updatedRows = 0;
		try {
			//SQL

			String sql =" UPDATE EVENTO "
					+ " SET  ID_TIPO_ESTADO_EVENTO = ? "
					+ " WHERE ID = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, estado);
			JDBCUtils.setParameter(preparedStatement, i++, idEvento);


			updatedRows = preparedStatement.executeUpdate();
			if (updatedRows!=1) {
				throw new EventoNotFoundException("Evento: "+idEvento);
			}

		} catch (SQLException e) {			
			logger.error("updateEstado: "+idEvento+": "+e.getMessage() ,e);
			throw new DataException("update: "+idEvento+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}

	
	// TODO En bucle para llenar la página de rsultados y ordenados de menor a mayor distancia 
	

	/**
	 * Metodo para la creacion de un listado de eventos que se ajusta a la distancia
	 * seleccionada por el usuario.
	 * 
	 *  
	 * @param rs resulset de la query ejecutada.
	 * @param latitudUsuario latitud del usuario que realiza la consulta.
	 * @param longitudUsuario longitud del usuario que realiza la consulta.
	 * @param distanciaKm la distancia seleccionada por el usuario en el criteria.
	 */
	private static List<EventoDTO> loadByDistance(ResultSet rs, Double latitudUsuario, Double longitudUsuario, Integer distanciaKmMax)
			throws SQLException {

		if (latitudUsuario==null||longitudUsuario==null||distanciaKmMax==null||distanciaKmMax==0) {
			distanciaKmMax = Integer.MAX_VALUE;
		}

		List<EventoDTO> eventos = new ArrayList<EventoDTO>();
		EventoDTO evento = null;

		while (rs.next()) {
			evento = loadNext(rs, latitudUsuario, longitudUsuario);
			if (evento.getDistanciaKm() <= distanciaKmMax) {
				eventos.add(evento);
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("Evento descartado por distancia: "+evento);
				}
			}
		} 
		return eventos;
	}


	
	
	private static EventoDTO loadNext(ResultSet rs, Double latitudUsuario, Double longitudUsuario) 
			throws SQLException { 
		EventoDTO evento = new EventoDTO();

		int i = 1;
		evento.setId(rs.getLong(i++));
		evento.setNombre(rs.getString(i++));
		evento.setDescripcion(rs.getString(i++));
		evento.setFechaHora(rs.getDate(i++));
		evento.setNumAsistentes(rs.getInt(i++));
		evento.setEdadeDesde(rs.getDate(i++));
		evento.setEdadHasta(rs.getDate(i++));
		evento.setPublicoPrivado(rs.getBoolean(i++));
		evento.setLatitud(rs.getDouble(i++));
		evento.setLongitud(rs.getDouble(i++));
		
		double distanciaEvento = CalculadoraDistanciaUtil.calcularDistanciaPuntosSuperficieTierra(latitudUsuario, longitudUsuario, evento.getLatitud(), evento.getLongitud());
		evento.setDistanciaKm(distanciaEvento);
		
		evento.setCalle(rs.getString(i++));
		evento.setZip(rs.getString(i++));
		evento.setIdUsuario(rs.getLong(i++));
		evento.setNombreUsuarioCreador(rs.getString(i++));
		evento.setIdTipoTematica(rs.getLong(i++));
		evento.setTipoTematica(rs.getString(i++));
		evento.setIdTipoEstablecimiento(rs.getLong(i++));
		evento.setTipoEstablecimiento(rs.getString(i++));
		evento.setIdLocalidad(rs.getLong(i++));
		evento.setLocalidad(rs.getString(i++));
		evento.setIdTipoEstadoEvento(rs.getLong(i++));
		evento.setTipoEstadoEvento(rs.getString(i++));
		evento.setIdTipoMusica(rs.getLong(i++));
		evento.setTipoMusica(rs.getString(i++));
				
		return evento;
	}
}