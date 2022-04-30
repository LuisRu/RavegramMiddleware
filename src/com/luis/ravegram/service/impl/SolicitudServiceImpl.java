package com.luis.ravegram.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.SolicitudDAO;
import com.luis.ravegram.dao.impl.SolicitudDAOImpl;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.RequestInvalidStateException;
import com.luis.ravegram.exception.RequestNotFoundException;
import com.luis.ravegram.model.SolicitudDTO;
import com.luis.ravegram.model.criteria.SolicitudCriteria;
import com.luis.ravegram.model.state.SolicitudEstado;
import com.luis.ravegram.service.SolicitudService;


public class SolicitudServiceImpl implements SolicitudService {

	private static Logger logger = LogManager.getLogger(SolicitudServiceImpl.class);

	private SolicitudDAO solicitudDAO  = null;

	public SolicitudServiceImpl() {
		solicitudDAO = new SolicitudDAOImpl();
	}


	public SolicitudDTO findByUsuarioEvento(Long idUsuario, Long idEvento)  
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		SolicitudDTO solicitud = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);


			solicitud = solicitudDAO.findByUsuarioEvento(c, idUsuario, idEvento);


			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("findByUsuarioEventro: "+e.getMessage() ,e);
			throw new DataException("findByUsuarioEventro: "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return solicitud;
	}


	@Override
	public void usuarioSolicita(Long idUsuario, Long idEvento) 
			throws DataException,RequestNotFoundException,RequestInvalidStateException {
		Connection c = null;
		boolean commitOrRollback = false;
		SolicitudDTO solicitud = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			// Si no existe, la crea
			// Si la solicitud ya existe, le cambia el estado a aceptada


			//findById
			solicitud = solicitudDAO.findByUsuarioEvento(c, idUsuario, idEvento); 



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
				throw new RequestInvalidStateException("Invalid request state: "+solicitud.getIdTipoEstado());
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
	public void eventoInvita(Long idUsuario,Long idEvento) 
			throws DataException,RequestNotFoundException,RequestInvalidStateException{
		Connection c = null;
		boolean commitOrRollback = false;
		SolicitudDTO solicitud = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);


			//findById
			solicitud = solicitudDAO.findByUsuarioEvento(c, idUsuario, idEvento);

			if (solicitud == null) {

				solicitud = new SolicitudDTO();

				solicitud.setIdUsuario(idUsuario);
				solicitud.setIdEvento(idEvento);
				solicitud.setIdTipoEstado(SolicitudEstado.INVITADO);
				solicitud.setFecha(new Date());
				solicitudDAO.create(c, solicitud);

			}else if (solicitud.getIdTipoEstado() == SolicitudEstado.SOLICITADO){
				solicitud.setIdTipoEstado(SolicitudEstado.ACEPTADO);
				solicitudDAO.update(c,solicitud);
			}else if (solicitud.getIdTipoEstado() == SolicitudEstado.ACEPTADO || solicitud.getIdTipoEstado() == SolicitudEstado.INVITADO ){
				//TODO exception avisando de que ya esta invitado o aceptado?
			}else {

				throw new RequestInvalidStateException("Invalid request state: "+solicitud.getIdTipoEstado());

			}
			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("eventoInvita: "+idUsuario+" : "+e.getMessage() ,e);
			throw new DataException("eventoInvita: "+idUsuario+" : "+e.getMessage() ,e);	
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}

	/**
	 * Se activa cuando pulsamos la X en la web, rechaza el evento y no lo volvera a mostrar
	 */
	@Override
	public void eventoNoInteresa(Long idUsuario,Long idEvento) 
			throws DataException,RequestNotFoundException,RequestInvalidStateException{
		Connection c = null;
		boolean commitOrRollback = false;
		SolicitudDTO solicitud = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);


			//findById
			solicitud = solicitudDAO.findByUsuarioEvento(c, idUsuario, idEvento);


			if (solicitud == null) {

				solicitud = new SolicitudDTO();

				solicitud.setIdUsuario(idUsuario);
				solicitud.setIdEvento(idEvento);
				solicitud.setIdTipoEstado(SolicitudEstado.RECHAZADO);
				solicitud.setFecha(new Date());
				solicitudDAO.create(c, solicitud);

			}else if (solicitud.getIdTipoEstado() == SolicitudEstado.INVITADO){
				solicitud.setIdTipoEstado(SolicitudEstado.RECHAZADO);
				solicitudDAO.update(c,solicitud);
			}else {

				throw new RequestInvalidStateException("Invalid request state: "+solicitud.getIdTipoEstado());

			}
			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("eventoNoInteresa: "+idUsuario+" : "+e.getMessage() ,e);
			throw new DataException("eventoNoInteresa: "+idUsuario+" : "+e.getMessage() ,e);	
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}




	@Override
	public void anadirUsuarios(Long idEvento,List<Long> idsUsuarios)
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<SolicitudDTO> solicitudes = new ArrayList<SolicitudDTO>();
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			//siempre debo borra los aceptados porque si viene null no habra invitados por tanto delete todo los aceptados
			//si viene algun dato sera el listados de todos los asistentes por lo que borro los aceptados y ademas borro las peticiones de los ids de la lista
			//por si hay alguna en estado solicitado no de error de varible duplicada
			
			solicitudDAO.deleteByEventoEstado(c, idEvento, SolicitudEstado.ACEPTADO);
			
			
			//si no viene vacia borro las solicitudes de los ids enviados por si acaso y creo nuevas solicitudes en aceptado
			if(idsUsuarios != null ) {
				solicitudDAO.deleteByEventoUsuarioIds(c, idEvento, idsUsuarios);
				for (Long idUsuario: idsUsuarios) {
					//no entiendo no se deberia pisar?
					SolicitudDTO solicitud = new SolicitudDTO();
					solicitud.setIdUsuario(idUsuario);
					solicitud.setIdEvento(idEvento);
					solicitud.setIdTipoEstado(SolicitudEstado.ACEPTADO);
					solicitud.setFecha(new Date());
					solicitudes.add(solicitud);

				}
				for(SolicitudDTO solicitud: solicitudes) {
					solicitudDAO.create(c, solicitud);
				}
					
					
				//solicitudDAO.createMultiple(c, solicitudes);

			}

			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("añadirUsuarios: "+e.getMessage() ,e);
			throw new DataException("añadirUsuarios: "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}



	@Override
	public List<SolicitudDTO> findByCriteria(SolicitudCriteria uepc)
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<SolicitudDTO> solicitudes = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);


			solicitudes = solicitudDAO.findByCriteria(c, uepc);


			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("findByCriteria: "+e.getMessage() ,e);
			throw new DataException("findByCriteria: "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return solicitudes;
	}



	@Override
	public List<SolicitudDTO> findSolicitudesPendientes(Long idUsuario) 
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<SolicitudDTO> solicitudes = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);


			solicitudes = solicitudDAO.findSolicitudesPendientes(c, idUsuario);


			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("findSolicitudesPendientes: "+e.getMessage() ,e);
			throw new DataException("findSolicitudesPendientes: "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return solicitudes;
	}


	@Override
	public List<SolicitudDTO> findInvitacionesPendientes(Long idUsuario) 
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<SolicitudDTO> solicitudes = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);


			solicitudes = solicitudDAO.findInvitacionesPendientes(c, idUsuario);


			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("findInvitacionesPendientes: "+e.getMessage() ,e);
			throw new DataException("findInvitacionesPendientes: "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return solicitudes;
	}


	@Override
	public void create(SolicitudDTO usuario) 
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);


			solicitudDAO.create(c, usuario);


			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("create: "+e.getMessage() ,e);
			throw new DataException("create: "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}



	@Override
	public void createMultiple(List<SolicitudDTO> solicitudes) 
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);


			solicitudDAO.createMultiple(c, solicitudes);


			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("createMultiple: "+e.getMessage() ,e);
			throw new DataException("createMultiple: "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}


	@Override
	public void deleteByEventosIds(List <Long> idsEventos)
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			solicitudDAO.deleteByEventosIds(c,idsEventos);

			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("deleteByEventos : "+e.getMessage() ,e);
			throw new DataException("deleteByEventos: "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}

	
	
	@Override
	public void deleteByUsuario(Long idUsuario)
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			solicitudDAO.deleteByUsuario(c,idUsuario);

			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("deleteByEventos : "+e.getMessage() ,e);
			throw new DataException("deleteByEventos: "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}

	
	
	@Override
	public void deleteByEventoEstado(Long idEvento ,Long idEstadoSolicitud)
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			solicitudDAO.deleteByEventoEstado(c,idEvento, idEstadoSolicitud);

			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("deleteByEventoEstadoSolicitud: "+idEvento+": "+e.getMessage() ,e);
			throw new DataException("deleteByEventoEstadoSolicitud: "+idEvento+": "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}


	@Override
	public void update(SolicitudDTO solitud) 
			throws DataException, RequestNotFoundException {
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
	public void updateEstado(Long idUsuario,Long idEvento, Long idEstado)
			throws DataException, RequestNotFoundException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			solicitudDAO.updateEstado(c,idUsuario,idEvento, idEstado);

			commitOrRollback = true;

		} catch (SQLException e) {
			logger.error("updateEstado: "+idEvento+": "+e.getMessage() ,e);
			throw new DataException("updateEstado: "+idEvento+": "+e.getMessage() ,e);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
	}









}
