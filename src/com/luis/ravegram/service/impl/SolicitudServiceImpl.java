package com.luis.ravegram.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.EventoDAOImpl;
import com.luis.ravegram.dao.UsuarioEventoSolicitaDAOImpl;
import com.luis.ravegram.dao.impl.EventoDAO;
import com.luis.ravegram.dao.impl.UsuarioEventoSolicitaDAO;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.EventoDTO;
import com.luis.ravegram.model.SolicitudDTO;
import com.luis.ravegram.model.SolicitudEstado;
import com.luis.ravegram.service.SolicitudService;


public class SolicitudServiceImpl implements SolicitudService {

	private static Logger logger = LogManager.getLogger(SolicitudServiceImpl.class);

	private UsuarioEventoSolicitaDAO solicitudDAO  = null;
	private EventoDAO eventoDAO = null;

	public SolicitudServiceImpl() {
		solicitudDAO = new UsuarioEventoSolicitaDAOImpl();
		eventoDAO = new EventoDAOImpl();
	}

	@Override
	public void usuarioSolicita(Long idUsuario, Long idEvento) 
			throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		SolicitudDTO solicitud = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			// Si no existe, la crea
			// Si la solicitud ya existe, le cambia el estado a aceptada

			solicitud = solicitudDAO.findByIdUsuarioIdEvento(c, idUsuario, idEvento);


			if (solicitud == null) {
				solicitud = new SolicitudDTO();

				solicitud.setIdUsuario(idUsuario);
				solicitud.setIdEvento(idEvento);
				solicitud.setIdTipoEstado(SolicitudEstado.SOLICITADO);
				solicitud.setFecha(new Date());
				solicitudDAO.create(c, solicitud);

			} else if (solicitud.getIdTipoEstado() == SolicitudEstado.INVITADO){

				solicitud.setIdTipoEstado(SolicitudEstado.ACEPTADO);
				solicitudDAO.update(c,solicitud);

			} else {
				throw new ServiceException("Invalid request state: "+solicitud.getIdTipoEstado());
			}

			commitOrRollback = true;



		} catch (SQLException e) {
			logger.error("usuarioSolicita: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("usuarioSolicita: "+idUsuario+": "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}
	
	
	@Override
	public void rechazado(Long idUsuario, Long idEvento) 
			throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		SolicitudDTO solicitud = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			// Si no existe, la crea
			// Si la solicitud ya existe, le cambia el estado a aceptada

			solicitud = solicitudDAO.findByIdUsuarioIdEvento(c, idUsuario, idEvento);


			if (solicitud == null) {
				solicitud = new SolicitudDTO();
				solicitud.setIdUsuario(idUsuario);
				solicitud.setIdEvento(idEvento);
				solicitud.setIdTipoEstado(SolicitudEstado.RECHAZADO);
				solicitud.setFecha(new Date());
				solicitudDAO.create(c, solicitud);

			} else if (solicitud.getIdTipoEstado() == SolicitudEstado.INVITADO){

				solicitud.setIdTipoEstado(SolicitudEstado.RECHAZADO);
				solicitudDAO.update(c,solicitud);

			} else {
				throw new ServiceException("Invalid request state: "+solicitud.getIdTipoEstado());
			}

			commitOrRollback = true;



		} catch (SQLException e) {
			logger.error("usuarioDeniega: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("usuarioDeniega: "+idUsuario+": "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}



	@Override
	public void eventoInvita(Long idUsuario,Long idEvento) 
			throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		SolicitudDTO solicitud = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);


			solicitud = solicitudDAO.findByIdUsuarioIdEvento(c, idUsuario, idEvento);


			if (solicitud == null) {

				solicitud = new SolicitudDTO();

				solicitud.setIdUsuario(idUsuario);
				solicitud.setIdEvento(idEvento);
				solicitud.setIdTipoEstado(SolicitudEstado.SOLICITADO);
				solicitud.setFecha(new Date());
				solicitudDAO.create(c, solicitud);
				
			}else if (solicitud.getIdTipoEstado() == 1){
				solicitud.setIdTipoEstado(3L);
				solicitudDAO.update(c,solicitud);
			}

			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("eventoInvita: "+idUsuario+" : "+e.getMessage() ,e);
			throw new DataException("eventoInvita: "+idUsuario+" : "+e.getMessage() ,e);	
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}

	@Override
	public void aceptarSolicitud(Long idUsuario,Long idEvento) 
			throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

		
			

			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("aceptarSolicitud: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("aceptarSolicitud: "+idUsuario+": "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}

	@Override
	public List<SolicitudDTO> historialSolicitudes(Long idUsuario)
			throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		List <SolicitudDTO> solicitudes = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			solicitudes = solicitudDAO.findByUsuario(c, idUsuario);

			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("historialSolicitudes: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("historialSolicitudes: "+idUsuario+": "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return solicitudes;
	}

	@Override
	public SolicitudDTO findByIdUsuarioIdEvento(Long idUsuario,Long idEvento)
			throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		SolicitudDTO  solicitud = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			solicitud = solicitudDAO.findByIdUsuarioIdEvento(c, idUsuario,idEvento);

			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("findByIdUsuarioIdEvento: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("findByIdUsuarioIdEvento: "+idUsuario+": "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return solicitud;
	}

	@Override
	public List<SolicitudDTO> solicitudesAMisEventosPendientes(Long idUsuario,Double latitudUsuario,Double longitudUsuario)
			throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		List <SolicitudDTO> solicitudes = null;
		List <EventoDTO> eventos = null;
		List <Long> idsEventos = new ArrayList<Long>();
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);
			//TODO PROTEGER SI ES NULL
			//SACO UN LISTADO DE TODOS LOS EVENTOS TIENE ESE USUARIO CREADOS
			eventos = eventoDAO.findByCreador(c, idUsuario,latitudUsuario,longitudUsuario);
			for (EventoDTO eventoDTO : eventos) {
				idsEventos.add(eventoDTO.getId()); 
			}
			solicitudes = solicitudDAO.findBySolicitudesAMisEventosPendientes(c, idsEventos);

			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("solicitudesAMisEventosPendientesDeAprobar: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("solicitudesAMisEventosPendientesDeAprobar: "+idUsuario+": "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return solicitudes;
	}
	@Override
	public List<SolicitudDTO> invitacionesAEventosPendientes(Long idUsuario)
			throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<SolicitudDTO>  solicitud = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			solicitud = solicitudDAO.findByInvitacionesAEventosPendientes(c, idUsuario);

			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("invitacionesAEventosPendientesDeAprobar: "+idUsuario+": "+e.getMessage() ,e);
			throw new DataException("invitacionesAEventosPendientesDeAprobar: "+idUsuario+": "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return solicitud;
	}

	@Override
	public void update(SolicitudDTO solitud) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			solicitudDAO.update(c,solitud);

			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("update: "+solitud.getIdEvento()+": "+e.getMessage() ,e);
			throw new DataException(e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}


	@Override
	public void updateEstadoSolicitudesEvento(Long idUsuario,Long idEvento, Long idEstado) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			solicitudDAO.updateEstado(c,idUsuario,idEvento, idEstado);

			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("updateEstadoSolicitudesEvento: "+idEvento+": "+e.getMessage() ,e);
			throw new DataException("updateEstadoSolicitudesEvento: "+idEvento+": "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}





}
