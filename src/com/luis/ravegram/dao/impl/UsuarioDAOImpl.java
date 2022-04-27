package com.luis.ravegram.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.UsuarioDAO;
import com.luis.ravegram.dao.util.DAOUtils;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.dao.util.SQLUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.UserNotFoundException;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.model.criteria.UsuarioCriteria;




public class UsuarioDAOImpl implements UsuarioDAO {


	private static Logger logger = LogManager.getLogger(UsuarioDAOImpl.class);

	private static Map<String, String> SORTING_CRITERIA_MAP = null;


	static {
		SORTING_CRITERIA_MAP = new HashMap<String, String>();

		SORTING_CRITERIA_MAP.put("EDAD-ASC", " ORDER BY u.fecha_nacimiento ASC ");
		SORTING_CRITERIA_MAP.put("EDAD-DESC", " ORDER BY u.fecha_nacimiento DESC ");
	}


	private final String QUERY_BASE_FIND = "SELECT u.id,u.user_name,u.email,u.contrasena,u.fecha_nacimiento,u.sexo,u.latitud,u.longitud,u.telefono,u.biografia, u.id_tipo_estado_cuenta,AVG(uep.VALORACION) "  
			+ " from usuario u left outer join usuario_sigue us " 
			+ " on u.id = us.id_usuario_seguidor "  
			+ " left outer join evento e "  
			+ " on u.id = e.ID_USUARIO "
			+ " left outer join usuario_evento_puntua uep " 
			+ " on  e.id = uep.ID_EVENTO "
			+ " left outer join usuario_evento_solicita ues " 
			+ " on u.id = ues.ID_USUARIO ";
			
			


	public UsuarioDAOImpl() {

	}



	@Override
	public UsuarioDTO findById(Connection c, Long idUsuario) 
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		UsuarioDTO usuario = null;
		try {
			
			// Compose SQL			
			StringBuilder queryString = new StringBuilder(QUERY_BASE_FIND)
														.append("WHERE u.id = ? ");
					
			
			
			// create prepared statement
			preparedStatement = c.prepareStatement(queryString.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			
			
			JDBCUtils.setParameter(preparedStatement, 1, idUsuario);
			
			rs = preparedStatement.executeQuery();
			
			
			
			if (logger.isInfoEnabled()) {
        		logger.info("findById query = "+preparedStatement.toString());
			}

			
			if (rs.next()) {				
				usuario = loadNext(rs);
			}			


		} catch (SQLException e) {			
			logger.error("findById: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("FindByfindByIdCriteria: "+idUsuario+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return  usuario;
	}


	@Override
	public List<UsuarioDTO> findByIds(Connection c, List<Long> ids) 
			throws DataException{
		Statement statement = null;
		ResultSet rs = null;
		List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();	

		try {




			// Compose SQL	
			StringBuilder sqlsb = new StringBuilder(QUERY_BASE_FIND)
					.append(" where u.id IN (")
					.append(SQLUtils.toIN(ids))
					.append(") group by u.id ORDER BY u.USER_NAME DESC ");




			statement = c.createStatement();
			rs = statement.executeQuery(sqlsb.toString());

			if (logger.isInfoEnabled()) {
				logger.info("findByIds query = "+sqlsb.toString());
			}


			while (rs.next()) {				
				usuarios.add(loadNext(rs));
			}			

		} catch (SQLException e) {		
			logger.error("FindByIds: "+SQLUtils.toIN(ids)+": "+e.getMessage() ,e);
			throw new DataException("FindByIds: "+SQLUtils.toIN(ids)+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(statement);
		}
		return usuarios;
	}



	@Override
	public List<UsuarioDTO> findSeguidoresNoAceptadoEvento(Connection c, Long idUsuario,Long idEvento) 
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<UsuarioDTO> usuarios = null;

		try {
			// Compose SQL			
			StringBuilder sql = new StringBuilder(QUERY_BASE_FIND)
					.append("where us.ID_USUARIO_SEGUIDO = ? AND u.ID NOT IN (select ID_USUARIO "
							+ "												 from usuario_evento_solicita "
							+ "												 where ID_EVENTO = ?  AND ID_TIPO_ESTADO = 3)"
							+ " group by u.id "
							+ " ORDER BY u.USER_NAME DESC ");


			// Create prepared statement
			preparedStatement = c.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++,idUsuario);
			JDBCUtils.setParameter(preparedStatement, i++, idEvento);

			rs = preparedStatement.executeQuery();

			if (logger.isInfoEnabled()) {
				logger.info("findSeguidoresNoAceptadoEvento query = "+sql.toString());
			}


			usuarios = new ArrayList<UsuarioDTO>();
			UsuarioDTO usuario = null;
			while (rs.next()) {				
				usuario = loadNext(rs);
				usuarios.add(usuario);
			}			


		} catch (SQLException e) {
			logger.error("findSeguidoresNoAceptadoEvento: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("findSeguidoresNoAceptadoEvento: "+idUsuario+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return  usuarios;
	}




	@Override
	public Set<Long> findSeguidosIds(Connection c, Long idUsuario) 
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Set<Long> usuariosIds = null;
		try {
			// Compose SQL			
			String sql = " SELECT u.id "
					+ " from usuario u inner join usuario_sigue us "
					+ " on u.id = us.id_usuario_seguido "
					+ " where us.ID_USUARIO_SEGUIDOR = ? ";
			// Create prepared statement
			preparedStatement = c.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);


			JDBCUtils.setParameter(preparedStatement, 1, idUsuario);

			rs = preparedStatement.executeQuery();
			
		
			if (logger.isInfoEnabled()) {
				logger.info("findSeguidosIds query = "+sql.toString());
			}


			usuariosIds = new HashSet<Long>();
			while (rs.next()) {
				usuariosIds.add(rs.getLong(1));
			}			


		} catch (SQLException e) {		
			logger.error("findSeguidosIds: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("findSeguidosIds: "+idUsuario+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return  usuariosIds;
	}




	@Override
	public UsuarioDTO findByEmail (Connection c, String email)
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		UsuarioDTO usuario = null;

		try {

			// Compose SQL			
			StringBuilder sql = new StringBuilder(QUERY_BASE_FIND)
					.append(" where u.email = ? "
							+ " group by u.id ");					


			// Create prepared statement
			preparedStatement = c.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			JDBCUtils.setParameter(preparedStatement, 1, email);

			rs = preparedStatement.executeQuery();
			
			if (logger.isInfoEnabled()) {
        		logger.info("findByEmail query = "+preparedStatement.toString());
			}



			if (rs.next()) {				
				usuario = loadNext(rs);
			}			



		} catch (SQLException e) {		
			logger.error("FindByEmail: "+email+": "+e.getMessage() ,e);
			throw new DataException("FindByEmail: "+email+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return usuario;
	}

	
	

	 
	 

		@Override
		public List<UsuarioDTO> findSeguidores(Connection c, Long idUsuario) 
				throws DataException {
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			List<UsuarioDTO> usuarios = null;

			try {
				// Compose SQL			
				StringBuilder sql = new StringBuilder( " select u.id,u.user_name,u.email,u.contrasena,u.fecha_nacimiento,u.sexo,u.latitud,u.longitud,u.telefono,u.biografia, u.id_tipo_estado_cuenta,AVG(uep.VALORACION) " 
						 							 + " from usuario_sigue us " 
						 							 + " inner join usuario u on us.ID_USUARIO_SEGUIDOR = u.id "
						 							 + " left outer join usuario_evento_puntua uep "
						 							 + " on  u.ID = uep.ID_USUARIO "
						 							 + " where ID_USUARIO_SEGUIDO = ? "
						 							 + " group by u.id ");

				// Create prepared statement
				preparedStatement = c.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				int i  = 1;
				JDBCUtils.setParameter(preparedStatement, i++,idUsuario);

				rs = preparedStatement.executeQuery();

				if (logger.isInfoEnabled()) {
					logger.info("findSeguidores query = "+sql.toString());
				}


				usuarios = new ArrayList<UsuarioDTO>();
				UsuarioDTO usuario = null;
				while (rs.next()) {				
					usuario = loadNext(rs);
					usuarios.add(usuario);
				}			


			} catch (SQLException e) {
				logger.error("findSeguidores: "+idUsuario+": "+e.getMessage() ,e);
				throw new DataException("findSeguidores: "+idUsuario+": "+e.getMessage() ,e);
			} finally {
				JDBCUtils.close(rs);
				JDBCUtils.close(preparedStatement);
			}

			return  usuarios;
		}

		
		@Override
		public List<UsuarioDTO> findSeguidos(Connection c, Long idUsuario) 
				throws DataException {
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			List<UsuarioDTO> usuarios = null;
			UsuarioDTO usuario = null;
			try {
				// Compose SQL			
				StringBuilder sql = new StringBuilder( " select u.id,u.user_name,u.email,u.contrasena,u.fecha_nacimiento,u.sexo,u.latitud,u.longitud,u.telefono,u.biografia, u.id_tipo_estado_cuenta,AVG(uep.VALORACION) "
													 + " from usuario_sigue us "
													 + " inner join usuario u on us.ID_USUARIO_SEGUIDO = u.id "
													 + " left outer join usuario_evento_puntua uep "
													 + " on  u.ID = uep.ID_USUARIO "
													 + " where ID_USUARIO_SEGUIDOR = ?"
													 + " group by u.id ");

				// Create prepared statement
				preparedStatement = c.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				int i  = 1;
				JDBCUtils.setParameter(preparedStatement, i++,idUsuario);

				rs = preparedStatement.executeQuery();

				if (logger.isInfoEnabled()) {
					logger.info("findSeguidores query = "+preparedStatement.toString());
				}


				usuarios = new ArrayList<UsuarioDTO>();
				while (rs.next()) {				
					usuario = loadNext(rs);
					usuarios.add(usuario);
				}			


			} catch (SQLException e) {
				logger.error("findSeguidores: "+idUsuario+": "+e.getMessage() ,e);
				throw new DataException("findSeguidores: "+idUsuario+": "+e.getMessage() ,e);
			} finally {
				JDBCUtils.close(rs);
				JDBCUtils.close(preparedStatement);
			}

			return  usuarios;
		}




	@Override
	public Results<UsuarioDTO> findByCriteria(Connection c, UsuarioCriteria uc, int startIndex, int pageSize) 
			throws DataException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Results<UsuarioDTO> results = new Results<UsuarioDTO>();
		List<UsuarioDTO> usuarios = null;
		UsuarioDTO usuario = null;
		try {
			// Compose SQL			
			StringBuilder sql = new StringBuilder(QUERY_BASE_FIND);



			boolean first = true;

			if(uc.getId()!=null){
				DAOUtils.addClause(sql, first," u.id = ? ");
				first = false;
			}
			if(uc.getIdEvento()!=null){
				DAOUtils.addClause(sql, first," ues.id_evento = ? ");
				first = false;
			}
			if(uc.getTipoEstadoSolicitud()!=null){
				DAOUtils.addClause(sql, first," ues.id_tipo_estado = ? ");
				first = false;
			}
			if(uc.getIdBuscador()!=null) {
				DAOUtils.addClause(sql, first," u.id <> ? ");
				first = false;
			}
			if(uc.getEdadDesde()!=null) {
				DAOUtils.addClause(sql, first," u.fecha_nacimiento >= ? ");
				first = false;
			}
			if(uc.getEdadHasta()!=null) {
				DAOUtils.addClause(sql, first," u.fecha_nacimiento <= ? ");
				first = false;
			}

			if(uc.getBusqueda()!=null) {
				DAOUtils.addClause(sql, first," UPPER(u.user_name) LIKE upper(?)");
				first = false;
			}

			//TODO
			sql.append(" group by u.id ");
			
			if(uc.getOrderBy()!=null) {
				sql.append(SORTING_CRITERIA_MAP.get(uc.getOrderBy()));
			}else {
				sql.append("ORDER BY u.user_name ASC");
			}



			if (logger.isInfoEnabled()) {
        		logger.info("findByCriteria query = "+sql.toString());
			}




			// Create prepared statement
			preparedStatement = c.prepareStatement(sql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			int i = 1;


			if(uc.getId()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, uc.getId());
			}
			if(uc.getIdEvento()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, uc.getIdEvento());
			}
			if(uc.getTipoEstadoSolicitud()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, uc.getTipoEstadoSolicitud());
			}
			if(uc.getIdBuscador()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, uc.getIdBuscador());
			}
			if(uc.getEdadDesde()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, uc.getEdadDesde());
			}
			if(uc.getEdadHasta()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, uc.getEdadHasta());
			}
			if(uc.getBusqueda()!=null) {
				JDBCUtils.setParameter(preparedStatement, i++, DAOUtils.addPorcentaje(uc.getBusqueda()));
			}


			rs = preparedStatement.executeQuery();
			

			if (logger.isInfoEnabled()) {
        		logger.info("findByCriteria query = "+preparedStatement.toString());
			}


			usuarios = new ArrayList<UsuarioDTO>();
			int resultsLoaded = 0;
			if ((startIndex >=1) && rs.absolute(startIndex)) {
				do {
					usuario = loadNext(rs);
					usuarios.add(usuario);
					resultsLoaded++;
				} while (resultsLoaded<pageSize && rs.next());
			}

			results.setData(usuarios);
			results.setTotal(DAOUtils.getTotalRows(rs));


		} catch (SQLException e) {		
			logger.error("FindByCriteria: "+uc+": "+e.getMessage() ,e);
			throw new DataException("FindByCriteria: "+uc+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}

		return results;
	}


	public Long create(Connection c, UsuarioDTO usuario)
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			//SQL

			String sql = " INSERT INTO USUARIO(USER_NAME,EMAIL,CONTRASENA,FECHA_NACIMIENTO,SEXO,LATITUD,LONGITUD,TELEFONO,BIOGRAFIA,ID_TIPO_ESTADO_CUENTA) "
					+ " VALUES (?,?,?,?,?,?,?,?,?,?) ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++,usuario.getUserName());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getEmail());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getContrasena()); 
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getFechaNacimiento());				
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getSexo(),true);
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getLatitud());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getLongitud());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getTelefono());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getBiografia(),true);
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getTipoEstadoCuenta());


			int insertedRows = preparedStatement.executeUpdate();
			
			if (logger.isInfoEnabled()) {
        		logger.info("create query = "+preparedStatement.toString());
			}
			
			if (insertedRows==1) {
				rs = preparedStatement.getGeneratedKeys();
				if(rs.next()) {
					usuario.setId(rs.getLong(1));
				}
			} else {
				throw new DataException("create: "+usuario.getUserName());
			}


		} catch (SQLException e) {			
			logger.error("create: ",usuario.getId(), e.getMessage(),e);
			throw new DataException("create: "+usuario.getId()+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return usuario.getId();
	}

	
	public void deleteById(Connection c, Long usuarioId)
			throws DataException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			//SQL

			String sql = " DELETE "
					+ " from usuario "
					+ " WHERE ID = ?";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, usuarioId);


			int insertedRows = preparedStatement.executeUpdate();
			
			if (logger.isInfoEnabled()) {
        		logger.info("deleteById query = "+preparedStatement.toString());
			}
			

		} catch (SQLException e) {			
			logger.error("deleteById: ",usuarioId, e.getMessage(),e);
			throw new DataException("deleteById: "+usuarioId+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
	}





	public int update(Connection c, UsuarioDTO usuario) 
			throws DataException,UserNotFoundException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int updatedRows = 0;
		try {
			//SQL

			String sql =" UPDATE USUARIO "
					+ " SET  USER_NAME = ?,"
					+ "      EMAIL = ?,"
					+ "      CONTRASENA = ?,"
					+ "      FECHA_NACIMIENTO = ?,"
					+ "		 SEXO = ?,"
					+ "      LATITUD = ?, "
					+ " 	 LONGITUD = ?, "
					+ "      TELEFONO = ?, "
					+ "      BIOGRAFIA = ? , "
					+ "      ID_TIPO_ESTADO_CUENTA = ? "
					+ " WHERE ID = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getUserName());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getEmail());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getContrasena());
			System.out.println(usuario.getContrasena());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getFechaNacimiento());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getSexo(),true);
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getLatitud());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getLongitud());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getTelefono());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getBiografia(),true);
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getTipoEstadoCuenta());
			JDBCUtils.setParameter(preparedStatement, i++, usuario.getId());


			updatedRows = preparedStatement.executeUpdate();
			
			if (logger.isInfoEnabled()) {
        		logger.info("update query = "+preparedStatement.toString());
			}
			
			if (updatedRows!=1) {
				throw new UserNotFoundException("User: "+usuario.getId());
			}

		} catch (SQLException e) {
			logger.error("User: ",usuario.getId(), e.getMessage(),e);
			throw new DataException("User: "+usuario.getId()+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}


	public  int updateEstado(Connection c,Long id,Integer idTipoEstado)
			throws DataException,UserNotFoundException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int updatedRows = 0;
		try {
			//SQL

			String sql =" UPDATE USUARIO "
					+ " SET ID_TIPO_ESTADO_CUENTA = ? "
					+ " WHERE ID = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, idTipoEstado);
			JDBCUtils.setParameter(preparedStatement, i++, id);


			updatedRows = preparedStatement.executeUpdate();
			
			if (logger.isInfoEnabled()) {
        		logger.info("updateEstado query = "+preparedStatement.toString());
			}
			
			if (updatedRows!=1) {
				throw new UserNotFoundException("User: "+id);
			}

		} catch (SQLException e) {			
			logger.error("User: "+id+": "+"Estado: "+idTipoEstado+": "+e.getMessage() ,e);
			throw new DataException("User: "+id+": "+"Estado: "+idTipoEstado+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}

	public  int updateUbicacion(Connection c, Double latitud, Double longitud,Long id)
			throws DataException,UserNotFoundException{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int updatedRows = 0;
		try {
			//SQL

			String sql =" UPDATE USUARIO "
					+ " SET LATITUD = ?, "
					+ " LONGITUD = ? "
					+ " WHERE ID = ? ";

			//create prepared statement
			preparedStatement = c.prepareStatement(sql);

			int i  = 1;

			JDBCUtils.setParameter(preparedStatement, i++, latitud);
			JDBCUtils.setParameter(preparedStatement, i++, longitud);
			JDBCUtils.setParameter(preparedStatement, i++, id);


			updatedRows = preparedStatement.executeUpdate();
			
			if (logger.isInfoEnabled()) {
        		logger.info("updateUbicacion query = "+preparedStatement.toString());
			}
			
			if (updatedRows!=1) {
				throw new UserNotFoundException("User: "+id);
			}

		} catch (SQLException e) {			
			logger.error("User: "+id+": "+"Ubicacion: "+latitud+": "+e.getMessage() ,e);
			throw new DataException("User: "+id+": "+"Ubicacion: "+latitud+": "+e.getMessage() ,e);
		} finally {
			JDBCUtils.close(rs);
			JDBCUtils.close(preparedStatement);
		}
		return updatedRows;
	}



	private static UsuarioDTO loadNext(ResultSet rs)
			throws SQLException,DataException { 
		UsuarioDTO usuario =  new UsuarioDTO();

		int i = 1;
		usuario.setId(rs.getLong(i++));
		usuario.setUserName(rs.getString(i++));
		usuario.setEmail(rs.getString(i++));
		usuario.setContrasena(rs.getString(i++));
		usuario.setFechaNacimiento(rs.getDate(i++));
		// El sexo en BD esta almacenado como un char de 1 nullable
		String sexo = rs.getString(i++);		
		usuario.setSexo(sexo==null?null:sexo.charAt(0));
		usuario.setLatitud(rs.getDouble(i++));
		usuario.setLongitud(rs.getDouble(i++));
		usuario.setTelefono(rs.getString(i++));
		usuario.setBiografia(rs.getString(i++));
		usuario.setTipoEstadoCuenta(rs.getInt(i++));
		usuario.setValoracionMedia(rs.getDouble(i++));
		return usuario;
	}


}






