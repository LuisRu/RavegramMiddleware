package com.luis.ravegram.service.impl;



import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.UsuarioDAOImpl;
import com.luis.ravegram.dao.impl.UsuarioDAO;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.InvalidUserOrPasswordException;
import com.luis.ravegram.exception.MailException;
import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.exception.UserAlreadyExistsException;
import com.luis.ravegram.exception.UserDeleteException;
import com.luis.ravegram.model.CuentaEstado;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioCriteria;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.service.MailService;
import com.luis.ravegram.service.UsuarioService;
import com.luis.ravegram.service.util.PasswordEncryptionUtil;

public class UsuarioServiceImpl implements UsuarioService {

	private static Logger logger = LogManager.getLogger(UsuarioServiceImpl.class);

	private MailService mailService = null;
	private UsuarioDAO usuarioDAO = null;

	public UsuarioServiceImpl() {
		mailService = new MailServiceImpl();
		usuarioDAO = new UsuarioDAOImpl();
	}


	@Override
	public UsuarioDTO findById(Long id) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		UsuarioDTO usuario = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuario = usuarioDAO.findById(c, id);		


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("FindById: "+id+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("FindById: "+id+": "+ex.getMessage() ,ex);
			throw new ServiceException("FindById: "+id+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return usuario;
	}


	@Override
	public List<UsuarioDTO> findByIds(List<Long> idsUsuarios) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		List <UsuarioDTO> usuarios = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuarios = usuarioDAO.findByIds(c,idsUsuarios);


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("FindByIds: "+idsUsuarios+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("FindByIds: "+idsUsuarios+": "+ex.getMessage() ,ex);
			throw new ServiceException("FindByIds: "+idsUsuarios+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return usuarios;
	}

	@Override
	public List<UsuarioDTO> findAsistentes(Long idEvento) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		List <UsuarioDTO> usuarios = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuarios = usuarioDAO.findAsistentes(c, idEvento);		


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("FindAsistentes: "+idEvento+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("FindAsistentes: "+idEvento+": "+ex.getMessage() ,ex);
			throw new ServiceException("FindAsistentes: "+idEvento+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return usuarios;
	}

	@Override
	public List<UsuarioDTO> findSeguidores(Long idUsuario) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		List <UsuarioDTO> usuarios = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuarios = usuarioDAO.findSeguidores(c, idUsuario);		


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("FindSeguidores: "+idUsuario+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("FindSeguidores: "+idUsuario+": "+ex.getMessage() ,ex);
			throw new ServiceException("FindSeguidores: "+idUsuario+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return usuarios;
	}



	@Override
	public List<UsuarioDTO> findSeguidos(Long idUsuario) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		List <UsuarioDTO> usuarios = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuarios = usuarioDAO.findSeguidos(c, idUsuario);		


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("findSeguidos: "+idUsuario+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("findSeguidos: "+idUsuario+": "+ex.getMessage() ,ex);
			throw new ServiceException("findSeguidos: "+idUsuario+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return usuarios;
	}



	@Override
	public Set<Long> findSeguidosIds(Long idUsuario) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		Set <Long> usuariosIds = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuariosIds = usuarioDAO.findSeguidosIds(c, idUsuario);		


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("findSeguidosIds: "+idUsuario+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("findSeguidosIds: "+idUsuario+": "+ex.getMessage() ,ex);
			throw new ServiceException("findSeguidosIds: "+idUsuario+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return usuariosIds;
	}


	@Override
	public List<UsuarioDTO> findSeguidosMutuamente(Long idUsuario) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		List <UsuarioDTO> usuariosSeguidos = null;
		List <UsuarioDTO> usuariosSeguidores = null;
		List <UsuarioDTO> usuariosSeguidosMutuamente = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuariosSeguidos = usuarioDAO.findSeguidos(c, idUsuario);
			usuariosSeguidores = usuarioDAO.findSeguidores(c, idUsuario);

			for (UsuarioDTO usuarioDTO : usuariosSeguidos) {
				usuarioDTO.getId();
				for (UsuarioDTO usuarioDTO2 : usuariosSeguidores) {
					if(usuarioDTO.getId()==usuarioDTO2.getId()) {
						usuariosSeguidosMutuamente = new ArrayList<UsuarioDTO>();
						usuariosSeguidosMutuamente.add(usuarioDTO);
					}
				}
			}


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("findSeguidosMutuamente: "+idUsuario+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("findSeguidosMutuamente: "+idUsuario+": "+ex.getMessage() ,ex);
			throw new ServiceException("findSeguidosMutuamente: "+idUsuario+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return usuariosSeguidosMutuamente;
	}

	@Override
	public UsuarioDTO findByEmail(String email) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		UsuarioDTO usuario = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuario = usuarioDAO.findByEmail(c, email);	


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("findByEmail: "+email+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("findByEmail: "+email+": "+ex.getMessage() ,ex);
			throw new ServiceException("findByEmail: "+email+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return usuario;
	}



	@Override
	public Results<UsuarioDTO> findByCriteria(UsuarioCriteria uc,int startIndex, int pageSize) 
			throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		Results<UsuarioDTO> results = new Results<UsuarioDTO>();
		//List <UsuarioDTO> usuariosOKDistancia = null;
		//Double distancia = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			results = usuarioDAO.findByCriteria(c, uc, startIndex, pageSize);


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("findByCriteria: "+uc.getEdadDesde()+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("findByCriteria: "+uc.getEdadDesde()+": "+ex.getMessage() ,ex);
			throw new ServiceException("findByCriteria: "+uc.getEdadDesde()+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return results;
	}





	@Override
	public UsuarioDTO login(String email, String password) 
			throws InvalidUserOrPasswordException,UserDeleteException, DataException, ServiceException {
		Connection c = null;
		UsuarioDTO usuario = null;
		boolean passwordOK = false; 
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();					

			c.setAutoCommit(false);

			usuario = usuarioDAO.findByEmail(c, email); 

			if(usuario.getTipoEstadoCuenta()==CuentaEstado.ELIMINADA) {
				
				throw new UserDeleteException(email);
				
			} else {

				passwordOK= PasswordEncryptionUtil.checkPassword(password, (usuario==null ? null : usuario.getContrasena()));

				if ((!passwordOK)) {
					throw new InvalidUserOrPasswordException(email);
				}


				commitOrRollback = true;

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
	UserAlreadyExistsException, MailException, ServiceException {

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

			StringBuilder welcomeMsgSb = new StringBuilder("Hola ")
					.append(u.getUserName())
					.append(", Bienvenido a...")
					.append(" ...");

			String welcomeMsg = welcomeMsgSb.toString();


			mailService.sendEmail("ravegram98@gmail.com", 
					("Bienvenido a Ravegram..."), 
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
	public void update(UsuarioDTO usuario) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			//TODO Diferencia con tener otro campo
			if(usuario.getContrasena()!=null) {
				usuario.setContrasena(PasswordEncryptionUtil.encryptPassword(usuario.getContrasena()));
			}

			usuarioDAO.update(c, usuario);	


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("update: "+usuario.getUserName()+": "+de.getMessage() ,de);	
			throw de;			
		} catch (Exception ex) {
			logger.error("update: "+usuario.getUserName()+": "+ex.getMessage() ,ex);
			throw new ServiceException("update: "+usuario.getUserName()+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}

	@Override
	public void updateUbicacion(Double latitud, Double longitud, Long id) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuarioDAO.updateUbicacion(c, latitud,longitud,id);	


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("updateUbicacion: "+id+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("updateUbicacion: "+id+": "+ex.getMessage() ,ex);
			throw new ServiceException("updateUbicacion: "+id+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}





	@Override
	public void updateEstado(Long id, Integer idTipoEstadoUsuario) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			usuarioDAO.updateEstado(c, id,idTipoEstadoUsuario);	


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("updateEstado: "+id+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("updateEstado: "+id+": "+ex.getMessage() ,ex);
			throw new ServiceException("updateEstado: "+id+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}



}
