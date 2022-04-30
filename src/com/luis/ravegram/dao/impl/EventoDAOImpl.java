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

import com.luis.ravegram.dao.EventoDAO;
import com.luis.ravegram.dao.util.DAOUtils;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.dao.util.SQLUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.EventoNotFoundException;
import com.luis.ravegram.model.EventoDTO;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.criteria.EventoCriteria;
import com.luis.ravegram.service.util.CalculadoraDistanciaUtil;

public class EventoDAOImpl implements EventoDAO{
	
	private static Logger logger = LogManager.getLogger(EventoDAOImpl.class);
	
	private static Map<String, String> SORTING_CRITERIA_MAP = null;
	
	
	static {
		SORTING_CRITERIA_MAP = new HashMap<String, String>();

		SORTING_CRITERIA_MAP.put("NOMBRE-ASC", " ORDER BY e.NOMBRE ASC ");
		SORTING_CRITERIA_MAP.put("NOMBRE-DESC", " ORDER BY e.NOMBRE DESC ");
		SORTING_CRITERIA_MAP.put("FECHA-ASC", " ORDER BY e.FECHA_HORA ASC ");
		SORTING_CRITERIA_MAP.put("FECHA-DESC", " ORDER BY e.FECHA_HORA DESC ");
	}

	
	private final String QUERY_BASE_FIND = 
			  " select e.ID,e.NOMBRE,e.DESCRIPCION,e.FECHA_HORA,e.NUM_ASISTENTES,e.EDAD_DESDE,e.EDAD_HASTA,e.PUBLIC_PRIVADO,e.LATITUD,"
			+ " e.LONGITUD,e.CALLE,e.ZIP,u.ID,u.USER_NAME,tt.ID,tt.NOMBRE,te.ID,te.NOMBRE,l.ID,l.NOMBRE,tee.ID,tee.estado,tm.ID,tm.nombre "
			+ "  from evento e inner join usuario u on e.id_usuario = u.id "
			+ " left outer join tipo_tematica tt on e.id_tipo_tematica = tt.id "
			+ " left outer join tipo_establecimiento te on e.ID_TIPO_ESTABLECIMIENTO = te.ID "
			+ " inner join localidad l on e.id_localidad = l.ID left outer join establecimiento es "
			+ " on e.ID_ESTABLECIMIENTO = es.ID left outer join tipo_estado_evento tee "
			+ " on e.ID_TIPO_ESTADO_EVENTO = tee.id left outer join tipo_musica tm "
			+ " on e.ID_TIPO_MUSICA = tm.ID ";
	
	public EventoDAOImpl() {
		
	}


	@Override
	public EventoDTO findById(Connection c, Long idUsuario,Double latitudBuscador,Double longitudBuscador) 
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		EventoDTO evento = null;
		try {
			
			// Compose SQL			
			StringBuilder queryString = new StringBuilder(QUERY_BASE_FIND)
														.append("WHERE e.id = ? ");
					
			
			
			// create prepared statement
			preparedStatement = c.prepareStatement(queryString.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			
			
			JDBCUtils.setParameter(preparedStatement, 1, idUsuario);
			
			rs = preparedStatement.executeQuery();
			
			
			
			if (logger.isInfoEnabled()) {
        		logger.info("findById query = "+preparedStatement.toString());
			}

			
			if (rs.next()) {				
				evento = loadNext(rs,latitudBuscador,longitudBuscador);
			}			


		} catch (SQLException e) {			
			logger.error("findById: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("FindByfindByIdCriteria: "+idUsuario+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return  evento;
	}
	
	
	@Override
	public List<Long> findByIdCreador(Connection c, Long idUsuario) 
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<Long> eventosIds = null;
		try {
			
			// Compose SQL			
			String sql = " SELECT id "
							  + " FROM evento "
							  + " where ID_USUARIO = ? ";
								
					
			
			
			// create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			
			
			JDBCUtils.setParameter(preparedStatement, 1, idUsuario);
			
			rs = preparedStatement.executeQuery();
			
			
			
			if (logger.isInfoEnabled()) {
        		logger.info("findByIdCreador query = "+preparedStatement.toString());
			}

			
			eventosIds = new ArrayList<Long>();
			while (rs.next()) {
				eventosIds.add(rs.getLong(1));
			}	
				


		} catch (SQLException e) {			
			logger.error("findByIdCreador: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("findByIdCreador: "+idUsuario+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return  eventosIds;
	}
	
	
	public Results<EventoDTO> findBySeguidosDisponibles(Connection c,Long idUsuario,Double latitudUsuario,Double longitudUsuario,long startIndex, int pageSize)
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Results<EventoDTO> results = null;
		List<EventoDTO> eventos = null;
		EventoDTO evento = null;
		try {

			// Compose SQL			
			StringBuilder sql = new StringBuilder(QUERY_BASE_FIND)
					.append(" where u.id IN "
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
						+ " ORDER BY e.NOMBRE DESC");
		

			// Create prepared statement
			preparedStatement = c.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;
			JDBCUtils.setParameter(preparedStatement, i++, idUsuario);
			JDBCUtils.setParameter(preparedStatement, i++, idUsuario);
			JDBCUtils.setParameter(preparedStatement, i++, idUsuario);


			
			
			rs = preparedStatement.executeQuery();
			
			
			if (logger.isInfoEnabled()) {
        		logger.info("findBySeguidosDisponiblesEvento query = "+preparedStatement.toString());
			}

			
			results = new Results<EventoDTO>();
			eventos = new ArrayList<EventoDTO>();
			evento = new EventoDTO();
			//TODO PAGINACION
			while (rs.next()) {				
				evento = loadNext(rs,latitudUsuario,longitudUsuario);
				eventos.add(evento);

			}			
			
			results.setData(eventos);

		} catch (SQLException e) {			
			logger.error("findBySeguidosDisponibles: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("findBySeguidosDisponibles: "+idUsuario+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return results;
	}
	

	
	
	public Results<EventoDTO> findByCriteria(Connection c,EventoCriteria ec, int startIndex, int pageSize)
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Results<EventoDTO> results = null;
		List<EventoDTO> eventos = null;
		try {

			// Compose SQL			
			StringBuilder queryString = new StringBuilder(QUERY_BASE_FIND);
			
			boolean first = true;

			if(ec.getId()!=null) {
				DAOUtils.addClause(queryString, first, " e.id = ? ");
				first=false;
			}	
			
			if(ec.getIdCreador()!=null) {
				DAOUtils.addClause(queryString, first, " u.id = ? ");
				first=false;
			}	
			
			if(ec.getIdEstablecimiento()!=null) {
				DAOUtils.addClause(queryString, first, " e.ID_ESTABLECIMIENTO = ? ");
				first=false;
			}	
			
			if(ec.getFecha()!=null) {
				DAOUtils.addClause(queryString, first, " e.FECHA_HORA = ? ");
				first=false;
			}
			
			if(ec.getPublicPrivado()!=null) {
				DAOUtils.addClause(queryString, first, " e.PUBLIC_PRIVADO = ? ");
				first=false;
			}	
			
			if(ec.getTipoEstablecimiento()!=null) {
				DAOUtils.addClause(queryString, first, " te.ID = ?  ");
				first=false;
			}
			if(ec.getTipoTematica() != null) {
				DAOUtils.addClause(queryString, first, " tt.id = ?  ");
				first=false;
			}
			if(ec.getTipoMusica() != null) {
				DAOUtils.addClause(queryString, first, " tm.id = ?  ");
				first=false;
			}
			if(ec.getTipoEstadoEvento() != null) {
				DAOUtils.addClause(queryString, first, " tee.id = ?  ");
				first=false;
			}
			if(ec.getDescartarInteractuados() != null) {
				if(ec.getDescartarInteractuados()==true) {
					DAOUtils.addClause(queryString, first, " e.ID NOT IN "
														+ " (select id_evento "					//Los ids de los eventos a los que ya solicite/estoy aceptado / estoy rechazado\r\n"
														+ "	from usuario_evento_solicita "
														+ "	where id_usuario = ? AND ID_TIPO_ESTADO IN (2,3,4)) "
														+ "AND u.id <> ? "); // para que no salgan mis propios eventos
					first=false;
				}
			}
			
			if(ec.getDescartarNoAceptado() != null) {
				if(ec.getDescartarNoAceptado()==true) {
					DAOUtils.addClause(queryString, first, " e.id IN "
														+ "(select id_evento "					//sacar los ids de los eventos a los que estoy aceptado
														+ " from usuario_evento_solicita "
														+ "	where id_usuario = ? AND ID_TIPO_ESTADO = 3)");
					first=false;
				}
			}
			
			if(ec.getOrderBy()!=null) {
				queryString.append(SORTING_CRITERIA_MAP.get(ec.getOrderBy()));
			}else {
				queryString.append("ORDER BY e.FECHA_HORA DESC");
			}
			
			


		
			
			// Create prepared statement
			preparedStatement = c.prepareStatement(queryString.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			
			int i  = 1;
			
			if (ec.getId()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getId());
			}
			
			if(ec.getIdCreador()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getIdCreador());
			}	
			
			if(ec.getIdEstablecimiento()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getIdEstablecimiento());
			}		
			if (ec.getFecha()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getFecha());
			}
			if (ec.getPublicPrivado()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getPublicPrivado());
			}
			if (ec.getTipoEstablecimiento()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getTipoEstablecimiento());	
			}
			if(ec.getTipoTematica()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getTipoTematica());	
			}
			if(ec.getTipoMusica()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getTipoMusica());	
			}
			if(ec.getTipoEstadoEvento()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getTipoEstadoEvento());	
			}
			if(ec.getDescartarInteractuados()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getIdBuscador());
				JDBCUtils.setParameter(preparedStatement, i++, ec.getIdBuscador());
			}
			if(ec.getDescartarNoAceptado()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, ec.getIdBuscador());
			}
			
			rs = preparedStatement.executeQuery();
			
			
			if (logger.isInfoEnabled()) {
        		logger.info("FindByCriteriaEvento query = "+preparedStatement.toString());
			}

			
			
			results = loadByDistance(rs, ec.getLatitudBuscador(), ec.getLongitudBuscador(), ec.getDistancia(),startIndex,pageSize);

			
			
		} catch (SQLException e) {			
			logger.error("findByCriteria: "+ec+": "+e.getMessage() ,e);
			throw new DataException("findByCriteria: "+ec+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return results;
	}







	public Long create(Connection c,EventoDTO evento) 
			throws DataException {
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
			JDBCUtils.setParameter(preparedStatement, i++, evento.getZip(),true);
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdUsuario());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdTipoTematica(),true);
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdTipoEstablecimiento());
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdLocalidad(),true);
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdEstablecimiento(),true);
			JDBCUtils.setParameter(preparedStatement, i++, evento.getIdTipoEstadoEvento());
			JDBCUtils.setParameter(preparedStatement, i++,evento.getIdTipoMusica(),true);


			int insertedRows = preparedStatement.executeUpdate();
			
			if (logger.isInfoEnabled()) {
        		logger.info("createEvento query = "+preparedStatement.toString());
			}
			
			
			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();
				if(rs.next()) {
					evento.setId(rs.getLong(1));
					}
			} else {
				throw new DataException("create: "+evento.getNombre());
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

	
	public void delete(Connection c,List<Long> idsEventos) 
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			//SQL

			StringBuilder sql = new StringBuilder(" DELETE FROM EVENTO WHERE ID IN (")
												.append(SQLUtils.toInterrogacion(idsEventos))
												.append(")") ;

			//create prepared statement
			preparedStatement = c.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

			int i  = 1;
			for(Long id: idsEventos) {
				JDBCUtils.setParameter(preparedStatement, i++,id);
			}

			preparedStatement.executeUpdate();
			
			if (logger.isInfoEnabled()) {
        		logger.info("delete query = "+preparedStatement.toString());
			}
			


		} catch (SQLException e) {			
			logger.error("delete: "+e.getMessage() ,e);
			throw new DataException("delete"+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
	}

	
	
	
	
	
	
	
	public  int update(Connection c,EventoDTO evento) 
			throws DataException,EventoNotFoundException{
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
			
			if (logger.isInfoEnabled()) {
        		logger.info("updateEvento query = "+preparedStatement.toString());
			}
			
			if (updatedRows!=1) {
				throw new EventoNotFoundException("update: "+evento.getId());
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


	public  int updateEstado(Connection c,Long idEvento,Long estado)
			throws DataException,EventoNotFoundException{
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
			
			if (logger.isInfoEnabled()) {
        		logger.info("updateEstadoEvento query = "+preparedStatement.toString());
			}
			
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
	private static Results<EventoDTO> loadByDistance(ResultSet rs, Double latitudUsuario, Double longitudUsuario, Integer distanciaKmMax,int startIndex, int pageSize)
			throws SQLException {

		if (latitudUsuario==null||longitudUsuario==null||distanciaKmMax==null||distanciaKmMax==0) {
			distanciaKmMax = Integer.MAX_VALUE;
		}

		Results<EventoDTO> results = new Results<EventoDTO>();
		List<EventoDTO> eventos = new ArrayList<EventoDTO>();
		EventoDTO evento = null;
		
		
		int resultsLoaded = 0;
		if ((startIndex >=1) && rs.absolute(startIndex)) {
			do {
				evento = loadNext(rs, latitudUsuario, longitudUsuario);
				if (evento.getDistanciaKm() <= distanciaKmMax) {
					eventos.add(evento);
					resultsLoaded++;
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("Evento descartado por distancia: "+evento);
					}
				}
			} while (resultsLoaded<pageSize && rs.next());
		}
		
		results.setData(eventos);
		results.setTotal(DAOUtils.getTotalRows(rs));

		
		

//		while (rs.next()) {
//			evento = loadNext(rs, latitudUsuario, longitudUsuario);
//			if (evento.getDistanciaKm() <= distanciaKmMax) {
//				eventos.add(evento);
//			} else {
//				if (logger.isDebugEnabled()) {
//					logger.debug("Evento descartado por distancia: "+evento);
//				}
//			}
//		} 
		return results;
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