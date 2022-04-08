package com.luis.ravegram.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.EstablecimientoDAOImpl;
import com.luis.ravegram.dao.impl.EstablecimientoDAO;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.exception.ServiceException;
import com.luis.ravegram.model.Establecimiento;
import com.luis.ravegram.model.EstablecimientoCriteria;
import com.luis.ravegram.service.EstablecimientoService;
import com.luis.ravegram.service.util.CalculadoraDistanciaUtil;

public class EstablecimientoServiceImpl implements EstablecimientoService {
	
	private static Logger logger = LogManager.getLogger(EstablecimientoServiceImpl.class);

	private EstablecimientoDAO establecimientoDAO = null;

	public EstablecimientoServiceImpl() {
		establecimientoDAO = new EstablecimientoDAOImpl();
	}
	
	
	@Override
	public Establecimiento findById(Long idEstablecimiento) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		Establecimiento establecimiento = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			establecimiento = establecimientoDAO.findById(c, idEstablecimiento);


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("FindByIdService: "+idEstablecimiento+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("FindByIdService: "+idEstablecimiento+": "+ex.getMessage() ,ex);
			throw new ServiceException("FindById: "+idEstablecimiento+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return establecimiento;
	}

	@Override
	public List<Establecimiento> findByLocalidad(Long idLocalidad) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		List <Establecimiento> establecimientos = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			establecimientos = establecimientoDAO.findByLocalidad(c, idLocalidad);


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("FindByLocalidService: "+idLocalidad+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("FindByLocalidService: "+idLocalidad+": "+ex.getMessage() ,ex);
			throw new ServiceException("FindByLocalidService: "+idLocalidad+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return establecimientos;
	}
	
	@Override
	public List<Establecimiento> findByCriteria(EstablecimientoCriteria ec, Double latitudUsuario, Double longitudUsuario)
			throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		List <Establecimiento> establecimientos = null;
		List <Establecimiento> establecimientosOKDistancia = null;
		Double distancia = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);
			
			establecimientos = establecimientoDAO.findByCriteria(c, ec);		

			if (ec.getDistancia() != null) {
				establecimientosOKDistancia = new ArrayList<Establecimiento>();
				for (Establecimiento e : establecimientos) {
					distancia = CalculadoraDistanciaUtil.calcularDistanciaPuntosSuperficieTierra(latitudUsuario, longitudUsuario, e.getLatitud(), e.getLongitud());
					if (distancia <= ec.getDistancia()) {
						e.setDistanciaKm(distancia);
						establecimientosOKDistancia.add(e);
					}
				}
			}else {
				establecimientosOKDistancia = establecimientos ;		
			}	


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("FindByCriteriaService: "+ec+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("FindByCriteriaService: "+ec+": "+ex.getMessage() ,ex);
			throw new ServiceException("FindByCriteriaService: "+ec+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

		return establecimientosOKDistancia;
	}



	@Override
	public void create(Establecimiento establecimiento) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			establecimientoDAO.create(c, establecimiento);		


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("CreateService: "+establecimiento+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("CreateService: "+establecimiento+": "+ex.getMessage() ,ex);
			throw new ServiceException("CreateService: "+establecimiento+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}

	@Override
	public void update(Establecimiento establecimiento) throws DataException, ServiceException {
		Connection c = null;
		boolean commitOrRollback = false;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			establecimientoDAO.update(c, establecimiento);		


			commitOrRollback = true;

		} catch (DataException de) { 
			logger.error("UpdateService: "+establecimiento+": "+de.getMessage() ,de);
			throw de;			
		} catch (Exception ex) {
			logger.error("UpdateService: "+establecimiento+": "+ex.getMessage() ,ex);
			throw new ServiceException("UpdateService: "+establecimiento+": "+ex.getMessage() ,ex);			
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}

	}




	

}
