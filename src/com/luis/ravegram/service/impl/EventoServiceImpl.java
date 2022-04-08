package com.luis.ravegram.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.EventoDAOImpl;
import com.luis.ravegram.dao.UsuarioDAOImpl;
import com.luis.ravegram.dao.UsuarioEventoSolicitaDAOImpl;
import com.luis.ravegram.dao.impl.EventoDAO;
import com.luis.ravegram.dao.impl.UsuarioDAO;
import com.luis.ravegram.dao.impl.UsuarioEventoSolicitaDAO;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.MailException;
import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.EventoCriteria;
import com.luis.ravegram.model.EventoDTO;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.service.EventoService;
import com.luis.ravegram.service.MailService;

public class EventoServiceImpl implements EventoService {

	private static Logger logger = LogManager.getLogger(EventoServiceImpl.class);

	private MailService mailService = null;
	private EventoDAO eventoDAO = null;
	private UsuarioDAO usuarioDAO = null;
	private UsuarioEventoSolicitaDAO solicitudDAO = null;


	public EventoServiceImpl() {
		mailService = new MailServiceImpl();
		eventoDAO = new EventoDAOImpl();
		usuarioDAO = new UsuarioDAOImpl();
		solicitudDAO = new UsuarioEventoSolicitaDAOImpl();
		
	}

	@Override
	public EventoDTO findById(Long id,Double latitudUsuario, Double longitudUsuario) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		EventoDTO evento = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			evento = eventoDAO.findById(c, id,latitudUsuario,longitudUsuario);		


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("FindByIdService: "+id+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("FindByIdService: "+id+": "+ex.getMessage() ,ex);
			throw new ServiceException("FindByIdService: "+id+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return evento;
	}


	@Override
	public List<EventoDTO> findByCreador(Long idUsuario,Double latitudUsuario, Double longitudUsuario) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		List <EventoDTO> eventos = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			eventos = eventoDAO.findByCreador(c, idUsuario,latitudUsuario,longitudUsuario);		


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("FindByCreador: "+idUsuario+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("FindByCreador: "+idUsuario+": "+ex.getMessage() ,ex);
			throw new ServiceException("FindByCreador: "+idUsuario+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return eventos;
	}


	@Override
	public List<EventoDTO> findyBySeguidos(Long idUsuario,Double latitudUsuario,Double longitudUsuario) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		List <EventoDTO> eventos = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			eventos = eventoDAO.findBySeguidos(c, idUsuario,latitudUsuario,longitudUsuario);	

			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("FindBySeguidos: "+idUsuario+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("FindBySeguidos: "+idUsuario+": "+ex.getMessage() ,ex);
			throw new ServiceException("FindBySeguidos: "+idUsuario+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return eventos;
	}

	@Override
	public List<EventoDTO> findByPendientes(Long idUsuario,Double latitudUsuario,Double longitudUsuario)
			throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		List <EventoDTO> eventos = null;
		Date fechaActual = new Date();
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			eventos = eventoDAO.findByPendientes(c, idUsuario,fechaActual,latitudUsuario,longitudUsuario);		


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("FindByPendientes: "+idUsuario+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("FindByPendientes: "+idUsuario+": "+ex.getMessage() ,ex);
			throw new ServiceException("FindByPendientes: "+idUsuario+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return eventos;
	}





	@Override
	public List<EventoDTO> findByEstablecimiento(Long idEstablecimiento,Double latitudUsuario, Double longitudUsuario) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		List <EventoDTO> eventos = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			eventos = eventoDAO.findByEstablecimiento(c, idEstablecimiento,latitudUsuario,longitudUsuario);		


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("FindByEstablecimiento: "+idEstablecimiento+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("FindByEstablecimiento: "+idEstablecimiento+": "+ex.getMessage() ,ex);
			throw new ServiceException("FindByEstablecimiento: "+idEstablecimiento+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return eventos;
	}


	@Override
	public List<EventoDTO> findByCriteria(EventoCriteria ec,Long idUsuario,Double latitudUsuario, Double longitudUsuario,long startIndex, int pageSize) 
			throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<EventoDTO> eventos = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			eventos = eventoDAO.findByCriteria(c, ec,idUsuario, latitudUsuario, longitudUsuario,10,10);		
			
			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("FindByCriteria: "+ec+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("FindByCriteria: "+ec+": "+ex.getMessage() ,ex);
			throw new ServiceException("FindByCriteria: "+ec+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return eventos;
	}

	@Override
	public Long create(EventoDTO e) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		Long eventoId = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			eventoId = eventoDAO.create(c, e);		


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("Create: "+e.getNombre()+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("Create: "+e.getNombre()+": "+ex.getMessage() ,ex);
			throw new ServiceException("Create: "+e.getNombre()+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return eventoId;

	}	
	@Override
	public Integer update(EventoDTO evento) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		Integer eventoId = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			eventoId = eventoDAO.update(c, evento);		


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("Update: "+evento.getNombre()+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("Update: "+evento.getNombre()+": "+ex.getMessage() ,ex);
			throw new ServiceException("Update: "+evento.getNombre()+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return eventoId;

	}

	@Override
	public Integer updateEstado(Long idEvento, Long estado) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		Integer updateRows = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			updateRows = eventoDAO.updateEstado(c,idEvento,estado);		


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("UpdateEstado: "+idEvento+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("UpdateEstado: "+idEvento+": "+ex.getMessage() ,ex);
			throw new ServiceException("UpdateEstado: "+idEvento+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return updateRows;
	}

	@Override
	public void compartir(Long idUsuarioComparte,Long idEvento,Double latitudUsuario, Double longitudUsuario,List<Long> idAmigos) throws MailException, ServiceException {
		Connection c = null;	
		boolean commitOrRollback = false;

		try {
			c = ConnectionManager.getConnection();

			c.setAutoCommit(false);

			UsuarioDTO uComparte = 
					usuarioDAO.findById(c, idUsuarioComparte);

			EventoDTO evento =
					eventoDAO.findById(c, idEvento,latitudUsuario,longitudUsuario);

			/** Porqueria 
			List<UsuarioDTO> amigos = new ArrayList<UsuarioDTO>();
			for (Long idAmigo: idAmigos) {
				amigos.add(usuarioDAO.findById(c, idAmigo));
			}
			 */
			List<UsuarioDTO> amigos = usuarioDAO.findByIds(c, idAmigos);

			for (UsuarioDTO amigo: amigos) {
				mailService.sendEmail("ravegram98@gmail.com", 
						"Nos apuntamos a "+evento.getNombre(), 
						"Hola "+amigo.getUserName()+", "
								+ " Tu amigo "+uComparte.getUserName()
								+ " te sugiere que vayas a "+evento.getNombre()+" el "+evento.getFechaHora()+"."
								+".... enlace ", 
								amigo.getEmail());
			}
			commitOrRollback = true;
		} catch (SQLException sqle) {
			logger.error("Compartir: "+idEvento+": "+sqle.getMessage() ,sqle);
			throw new DataException("Compartir: "+idEvento+": "+sqle.getMessage() ,sqle);
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);			
		}			
	}

	

	@Override
	public void eliminadoEventoConEmail(Long idEvento,Double latitudUsuario,Double longitudUsuario) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<UsuarioDTO> asistentes = null;
		EventoDTO evento = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);
			
			evento = eventoDAO.findById(c, idEvento,latitudUsuario,longitudUsuario);
			
			asistentes = usuarioDAO.findAsistentes(c, idEvento);
			
			eventoDAO.updateEstado(c,idEvento,3L);	
			
			
			
			for (UsuarioDTO usuario: asistentes) {
				mailService.sendEmail("ravegram98@gmail.com", 
						"Evento "+evento.getNombre()+" cancelado",
						"Hola "+usuario.getUserName()+" sentimos informarle de que el evento "+evento.getNombre()+" que se iba a celebrar el "+evento.getFechaHora()+" fue cancelado", 
						usuario.getEmail());
			}
			
			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("EliminarEventoConEmail: "+idEvento+": "+de.getMessage() ,de);	
			throw de;			
		} catch (Exception ex) {
			logger.error("EliminarEventoConEmail: "+idEvento+": "+ex.getMessage() ,ex);
			throw new ServiceException("EliminarEventoConEmail: "+idEvento+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}
	}

