package com.luis.ravegram.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.EventoDAO;
import com.luis.ravegram.dao.PuntuacionDAO;
import com.luis.ravegram.dao.SolicitudDAO;
import com.luis.ravegram.dao.UsuarioDAO;
import com.luis.ravegram.dao.impl.EventoDAOImpl;
import com.luis.ravegram.dao.impl.PuntuacionDAOImpl;
import com.luis.ravegram.dao.impl.SolicitudDAOImpl;
import com.luis.ravegram.dao.impl.UsuarioDAOImpl;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.EventoNotFoundException;
import com.luis.ravegram.exception.MailException;
import com.luis.ravegram.model.EventoDTO;
import com.luis.ravegram.model.Results;
import com.luis.ravegram.model.SolicitudDTO;
import com.luis.ravegram.model.UsuarioDTO;
import com.luis.ravegram.model.criteria.EventoCriteria;
import com.luis.ravegram.model.state.SolicitudEstado;
import com.luis.ravegram.service.EventoService;
import com.luis.ravegram.service.MailService;

public class EventoServiceImpl implements EventoService {

	private static Logger logger = LogManager.getLogger(EventoServiceImpl.class);

	private MailService mailService = null;
	private EventoDAO eventoDAO = null;
	private UsuarioDAO usuarioDAO = null;
	private SolicitudDAO solicitudDAO = null;
	private PuntuacionDAO puntuacionDAO = null;

	public EventoServiceImpl() {
		mailService = new MailServiceImpl();
		eventoDAO = new EventoDAOImpl();
		usuarioDAO = new UsuarioDAOImpl();
		solicitudDAO = new SolicitudDAOImpl();
		puntuacionDAO = new PuntuacionDAOImpl();

	}

	@Override
	public EventoDTO findById(Long idUsuario, Double latitudBuscador, Double longitudBuscador) throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		EventoDTO evento = null;
		try {
			c = ConnectionManager.getConnection();

			c.setAutoCommit(false);

			evento = eventoDAO.findById(c, idUsuario, latitudBuscador, longitudBuscador);

			commitOrRollback = true;

		} catch (SQLException sqle) {
			logger.error("findById: " + idUsuario + ": " + sqle.getMessage(), sqle);
			throw new DataException("findById: " + idUsuario);
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return evento;
	}
	
	
	public List<Long> findByIdCreador(Long idUsuario) 
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<Long> eventosIds = null;
		try {
			c = ConnectionManager.getConnection();

			c.setAutoCommit(false);

			eventosIds = eventoDAO.findByIdCreador(c, idUsuario);

			commitOrRollback = true;

		} catch (SQLException sqle) {
			logger.error("findByIdCreador: " + idUsuario + ": " + sqle.getMessage(), sqle);
			throw new DataException("findByIdCreador: " + idUsuario);
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return eventosIds;
	}

	@Override
	public Results<EventoDTO> findBySeguidosDisponibles(Long idUsuario, Double latitudUsuario, Double longitudUsuario,
			long startIndex, int pageSize) throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		Results<EventoDTO> results = null;
		try {
			c = ConnectionManager.getConnection();

			c.setAutoCommit(false);

			results = eventoDAO.findBySeguidosDisponibles(c, idUsuario, latitudUsuario, longitudUsuario, startIndex,
					pageSize);

			commitOrRollback = true;

		} catch (SQLException sqle) {
			logger.error("findBySeguidosDisponibles: " + idUsuario + ": " + sqle.getMessage(), sqle);
			throw new DataException("findBySeguidosDisponibles: " + idUsuario + ": " + sqle.getMessage(), sqle);
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return results;
	}

	@Override
	public Results<EventoDTO> findByCriteria(EventoCriteria ec, int startIndex, int pageSize) throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		Results<EventoDTO> results = null;
		try {
			c = ConnectionManager.getConnection();

			c.setAutoCommit(false);

			results = eventoDAO.findByCriteria(c, ec, startIndex, pageSize);

			commitOrRollback = true;

		} catch (SQLException sqle) {
			logger.error("findByCriteria: " + ec.getIdBuscador() + ": " + sqle.getMessage(), sqle);
			throw new DataException("findByCriteria: " + ec.getIdBuscador() + ": " + sqle.getMessage(), sqle);
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return results;
	}

	@Override
	public void create(EventoDTO evento, List<Long> idsUsuarios)
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<SolicitudDTO> solicitudes = new ArrayList<SolicitudDTO>();
		Long eventoId = null;
		try {
			c = ConnectionManager.getConnection();

			c.setAutoCommit(false);

			eventoId = eventoDAO.create(c, evento);

			if(idsUsuarios != null ) {
				for (Long idUsuario: idsUsuarios) {
					SolicitudDTO solicitud = new SolicitudDTO();
					solicitud.setIdUsuario(idUsuario);
					solicitud.setIdEvento(eventoId);
					solicitud.setIdTipoEstado(SolicitudEstado.ACEPTADO);
					solicitud.setFecha(new Date());
					solicitudes.add(solicitud);

				}
					
				solicitudDAO.createMultiple(c, solicitudes);

			}

			

			commitOrRollback = true;

		} catch (SQLException sqle) {
			logger.error("create: " + evento.getNombre() + ": " + sqle.getMessage(), sqle);
			throw new DataException("create: " + evento.getNombre() + ": " + sqle.getMessage(), sqle);
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}


	}
	
	

	@Override
	public void deleteAll(Long idUsuario)
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<Long> idsEventos = null;
		try {
			c = ConnectionManager.getConnection();

			c.setAutoCommit(false);
			
			//buscamos los eventos el usuario
			idsEventos = findByIdCreador(idUsuario);
			
			if(idsEventos.size()>0) {
				
				solicitudDAO.deleteByEventosIds(c,idsEventos);
				
				puntuacionDAO.deleteByEventos(c,idsEventos);
				
				eventoDAO.delete(c, idsEventos);

			}

			

			commitOrRollback = true;

		} catch (SQLException sqle) {
			logger.error("delete: "+ sqle.getMessage(), sqle);
			throw new DataException("delete: " + sqle.getMessage(), sqle);
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}


	}
	
	
	@Override
	public void delete(Long idEvento)
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<Long> idsEventos = new ArrayList<Long>();
		idsEventos.add(idEvento);
		try {
			c = ConnectionManager.getConnection();

			c.setAutoCommit(false);
			
			
			solicitudDAO.deleteByEventosIds(c,idsEventos);
			
			puntuacionDAO.deleteByEventos(c,idsEventos);
					
			eventoDAO.delete(c, idsEventos);
			

			commitOrRollback = true;

		} catch (SQLException sqle) {
			logger.error("delete: "+ sqle.getMessage(), sqle);
			throw new DataException("delete: " + sqle.getMessage(), sqle);
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}


	}
	

	@Override
	public void update(EventoDTO evento, List<Long> idsUsuarios) 
			throws DataException, EventoNotFoundException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<SolicitudDTO> solicitudes = new ArrayList<SolicitudDTO>();
		try {
			c = ConnectionManager.getConnection();

			c.setAutoCommit(false);

			eventoDAO.update(c, evento);
			
			
			//siempre debo borra los aceptados porque si viene null no habra invitados por tanto delete todo los aceptados
			//si viene algun dato sera el listados de todos los asistentes por lo que borro los aceptados y ademas borro las peticiones de los ids de la lista
			//por si hay alguna en estado solicitado no de error de varible duplicada
			
			solicitudDAO.deleteByEventoEstado(c, evento.getId(), SolicitudEstado.ACEPTADO);
			
			
			//si no viene vacia borro las solicitudes de los ids enviados por si acaso y creo nuevas solicitudes en aceptado
			if(idsUsuarios != null ) {
				solicitudDAO.deleteByEventoUsuarioIds(c, evento.getId(), idsUsuarios);
				for (Long idUsuario: idsUsuarios) {
					//no entiendo no se deberia pisar?
					SolicitudDTO solicitud = new SolicitudDTO();
					solicitud.setIdUsuario(idUsuario);
					solicitud.setIdEvento(evento.getId());
					solicitud.setIdTipoEstado(SolicitudEstado.ACEPTADO);
					solicitud.setFecha(new Date());
					solicitudes.add(solicitud);

				}
				
					
				solicitudDAO.createMultiple(c, solicitudes);

			}
			

			commitOrRollback = true;

		} catch (SQLException sqle) {
			logger.error("update: " + evento.getNombre() + ": " + sqle.getMessage(), sqle);
			throw new DataException("update: " + evento.getNombre() + ": " + sqle.getMessage(), sqle);
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}


	}

	@Override
	public Integer updateEstado(Long idEvento, Long estado) throws DataException, EventoNotFoundException {
		Connection c = null;
		boolean commitOrRollback = false;
		Integer updateRows = null;
		try {
			c = ConnectionManager.getConnection();

			c.setAutoCommit(false);

			updateRows = eventoDAO.updateEstado(c, idEvento, estado);

			commitOrRollback = true;

		} catch (SQLException sqle) {
			logger.error("updateEstado: " + idEvento + ": " + sqle.getMessage(), sqle);
			throw new DataException("updateEstado: " + idEvento + ": " + sqle.getMessage(), sqle);
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return updateRows;
	}

	@Override
	public void compartir(Long idUsuarioComparte, Long idEvento, Double latitudUsuario, Double longitudUsuario,
			List<Long> idAmigos) throws MailException, DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		UsuarioDTO uComparte = null;
		EventoDTO eCompartido = null;
		try {
			c = ConnectionManager.getConnection();

			c.setAutoCommit(false);

			// buscamos el usuario que compoarte
			uComparte = usuarioDAO.findById(c, idUsuarioComparte);

			// buscamos el evento que se comparte
			eCompartido = eventoDAO.findById(c, idEvento, latitudUsuario, longitudUsuario);

			List<UsuarioDTO> amigos = usuarioDAO.findByIds(c, idAmigos);

			for (UsuarioDTO amigo : amigos) {
				mailService.sendEmail("ravegram98@gmail.com", "Nos apuntamos a " + eCompartido.getNombre(),
						"Hola " + amigo.getUserName() + ", " + " Tu amigo " + uComparte.getUserName()
								+ " te sugiere que vayas a " + eCompartido.getNombre() + " el "
								+ eCompartido.getFechaHora() + "." + ".... enlace ",
						amigo.getEmail());
			}

			commitOrRollback = true;
		} catch (SQLException sqle) {
			logger.error("Compartir: " + idEvento + ": " + sqle.getMessage(), sqle);
			throw new DataException("Compartir: " + idEvento + ": " + sqle.getMessage(), sqle);
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}

}
