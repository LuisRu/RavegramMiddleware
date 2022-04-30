package com.luis.ravegram.service.impl;



import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.PuntuacionDAO;
import com.luis.ravegram.dao.SolicitudDAO;
import com.luis.ravegram.dao.UsuarioDAO;
import com.luis.ravegram.dao.UsuarioSigueDAO;
import com.luis.ravegram.dao.impl.PuntuacionDAOImpl;
import com.luis.ravegram.dao.impl.SolicitudDAOImpl;
import com.luis.ravegram.dao.impl.UsuarioDAOImpl;
import com.luis.ravegram.dao.impl.UsuarioSigueDAOImpl;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.InvalidUserOrPasswordException;
import com.luis.ravegram.exception.MailException;
import com.luis.ravegram.exception.UserAlreadyExistsException;
import com.luis.ravegram.exception.UserDeleteException;
import com.luis.ravegram.exception.UserNotFoundException;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.model.criteria.UsuarioCriteria;
import com.luis.ravegram.service.EventoService;
import com.luis.ravegram.service.MailService;
import com.luis.ravegram.service.UsuarioService;
import com.luis.ravegram.service.util.PasswordEncryptionUtil;

public class UsuarioServiceImpl implements UsuarioService {

	private static Logger logger = LogManager.getLogger(UsuarioServiceImpl.class);

	private MailService mailService = null;
	private UsuarioDAO usuarioDAO = null;
	private EventoService eventoService = null;
	private UsuarioSigueDAO usuarioSigueDAO = null;
	private SolicitudDAO solicitudDAO = null;
	private PuntuacionDAO puntuacionDAO = null;

	public UsuarioServiceImpl() {
		mailService = new MailServiceImpl();
		usuarioDAO = new UsuarioDAOImpl();
		eventoService = new EventoServiceImpl();
		usuarioSigueDAO = new UsuarioSigueDAOImpl();
		solicitudDAO = new SolicitudDAOImpl();
		puntuacionDAO = new PuntuacionDAOImpl();
	}



	@Override
	public UsuarioDTO findById(Long idUsuario)
			throws DataException{
		Connection c = null;
		boolean commitOrRollback = false;
		UsuarioDTO usuario = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuario = usuarioDAO.findById(c, idUsuario);		


			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("findById: "+idUsuario+": "+sqle.getMessage() ,sqle);
			throw new DataException("findById: "+idUsuario);						
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return usuario;
	}


	@Override
	public List<UsuarioDTO> findByIds(List<Long> idsUsuarios) throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		List <UsuarioDTO> usuarios = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuarios = usuarioDAO.findByIds(c,idsUsuarios);


			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("FindByIds: "+sqle.getMessage() ,sqle);
			throw new DataException("FindByIds: ");			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return usuarios;
	}

	@Override
	public List<UsuarioDTO> findSeguidoresNoAceptadoEvento(Long idUsuario,Long idEvento)
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		List <UsuarioDTO> usuarios = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuarios = usuarioDAO.findSeguidoresNoAceptadoEvento(c, idUsuario, idEvento);


			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("findSeguidoresNoAceptadoEvento: "+idUsuario+": "+sqle.getMessage() ,sqle);
			throw new DataException("findSeguidoresNoAceptadoEvento: "+idUsuario);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return usuarios;
	}

	@Override
	public Set<Long> findSeguidosIds(Long idUsuario) throws DataException{
		Connection c = null;
		boolean commitOrRollback = false;
		Set <Long> usuariosIds = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuariosIds = usuarioDAO.findSeguidosIds(c, idUsuario);		


			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("findSeguidosIds: "+idUsuario+": "+sqle.getMessage() ,sqle);
			throw new DataException("findSeguidosIds: "+idUsuario);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return usuariosIds;
	}




	@Override
	public UsuarioDTO findByEmail(String email) throws DataException{
		Connection c = null;
		boolean commitOrRollback = false;
		UsuarioDTO usuario = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuario = usuarioDAO.findByEmail(c, email);	


			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("findByEmail: "+email+": "+sqle.getMessage() ,sqle);
			throw new DataException("findByEmail: "+email);				
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return usuario;
	}


	@Override
	public List<UsuarioDTO> findSeguidores(Long idUsuario) 
			throws DataException{
		Connection c = null;
		boolean commitOrRollback = false;
		List <UsuarioDTO> usuarios = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuarios = usuarioDAO.findSeguidores(c, idUsuario);


			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("findSeguidores: "+idUsuario+": "+sqle.getMessage() ,sqle);
			throw new DataException("findSeguidores: "+idUsuario);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return usuarios;
	}


	@Override
	public List<UsuarioDTO> findSeguidos(Long idUsuario) 
			throws DataException{
		Connection c = null;
		boolean commitOrRollback = false;
		List <UsuarioDTO> usuarios = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuarios = usuarioDAO.findSeguidos(c, idUsuario);


			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("findSeguidores: "+idUsuario+": "+sqle.getMessage() ,sqle);
			throw new DataException("findSeguidores: "+idUsuario);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return usuarios;
	}



	@Override
	public Results<UsuarioDTO> findByCriteria(UsuarioCriteria uc,int startIndex, int pageSize) 
			throws DataException{
		Connection c = null;
		boolean commitOrRollback = false;
		Results<UsuarioDTO> results = new Results<UsuarioDTO>();
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			results = usuarioDAO.findByCriteria(c, uc, startIndex, pageSize);


			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("findByCriteria: "+uc.getBusqueda()+": "+sqle.getMessage() ,sqle);
			throw new DataException("findByCriteria: "+uc.getBusqueda());			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return results;
	}





	@Override
	public UsuarioDTO login(String email, String password) 
			throws InvalidUserOrPasswordException,UserDeleteException, DataException{
		Connection c = null;
		UsuarioDTO usuario = null;
		boolean passwordOK = false; 
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();					

			c.setAutoCommit(false);

			usuario = usuarioDAO.findByEmail(c, email); 

			if(usuario!=null) {
				
					passwordOK= PasswordEncryptionUtil.checkPassword(password, (usuario==null ? null : usuario.getContrasena()));

					if ((!passwordOK)) {
						throw new InvalidUserOrPasswordException(email);
					}


					commitOrRollback = true;

			}else {
				throw new InvalidUserOrPasswordException(email);
			}
			
		} catch (SQLException sqle) {
			logger.error("login: "+email+": "+sqle.getMessage() ,sqle);
			throw new DataException("login: "+email+": "+sqle.getMessage() ,sqle);

		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return usuario;		
	}





	//create
	@Override
	public Long signUp(UsuarioDTO u) throws 
	UserAlreadyExistsException, MailException, DataException {

		Connection c = null;
		boolean commitOrRollback = false;
		Long userId = null;
		u.setTipoEstadoCuenta(1); //TODO dentro o fuera?
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);
			if (usuarioDAO.findByEmail(c, u.getEmail())!=null) {
				throw new UserAlreadyExistsException(u.getEmail());				
			}


			u.setContrasena(PasswordEncryptionUtil.encryptPassword(u.getContrasena()));
			userId = usuarioDAO.create(c, u);		

			StringBuilder welcomeMsgSb = new StringBuilder("Hola,").append(u.getUserName())
					.append(" buscar tu primer evento!");

			String welcomeMsg = welcomeMsgSb.toString();


			mailService.sendEmail("ravegram98@gmail.com", 
					("Bienvenido a Ravegram!"), 
					welcomeMsg, 
					u.getEmail());

			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("singUp: "+u.getUserName()+": "+e.getMessage() ,e);	
			throw new DataException("singUp: "+u.getUserName()+": "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return userId;
	}

	@Override
	public void deleteAll(Long idUsuario) 
			throws DataException{
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			//borramos las solicitudes creadas por el usuario
			solicitudDAO.deleteByUsuario(c,idUsuario);
			
			//borramos las valoraciones creadas por el usuario
			puntuacionDAO.deleteByUsuario(c,idUsuario);
			
			//borramos sus seguidos y seguidores
			usuarioSigueDAO.deleteAll(c, idUsuario);

			//borramos sus eventos con sus valoraciones y solicitudes
			eventoService.deleteAll(idUsuario);

			//borramos el usuario
			usuarioDAO.deleteById(c, idUsuario);	


			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("deleteAll: "+idUsuario+": "+sqle.getMessage() ,sqle);	
			throw new DataException("deleteAll: "+idUsuario+": "+sqle.getMessage() ,sqle);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}



	@Override
	public void update(UsuarioDTO usuario,UsuarioDTO usuarioSesion) 
			throws DataException,UserNotFoundException{
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			// no la cambio
			if(usuario.getContrasena()==null) {
				System.out.println(usuarioSesion.getContrasena());
				usuario.setContrasena(usuarioSesion.getContrasena());
			}else {
				usuario.setContrasena(PasswordEncryptionUtil.encryptPassword(usuario.getContrasena()));
			}


			usuarioDAO.update(c, usuario);	


			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("update: "+usuario.getUserName()+": "+sqle.getMessage() ,sqle);	
			throw new DataException("update: "+usuario.getUserName()+": "+sqle.getMessage() ,sqle);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}

	@Override
	public void updateUbicacion(Double latitud, Double longitud, Long id)
			throws DataException, UserNotFoundException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuarioDAO.updateUbicacion(c, latitud,longitud,id);	


			commitOrRollback = true;


		} catch (SQLException sqle) { 
			logger.error("updateUbicacion: "+id+": "+sqle.getMessage() ,sqle);	
			throw new DataException("updateUbicacion: "+id+": "+sqle.getMessage() ,sqle);		
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}





	@Override
	public void updateEstado(Long id, Integer idTipoEstadoUsuario)
			throws DataException, UserNotFoundException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuarioDAO.updateEstado(c, id,idTipoEstadoUsuario);	


			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("updateEstado: "+id+": "+sqle.getMessage() ,sqle);	
			throw new DataException("updateEstado: "+id+": "+sqle.getMessage() ,sqle);	
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}



}
