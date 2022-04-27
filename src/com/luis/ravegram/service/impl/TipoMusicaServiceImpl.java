package com.luis.ravegram.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.luis.ravegram.dao.TipoMusicaDAO;
import com.luis.ravegram.dao.impl.TipoMusicaDAOImpl;
import com.luis.ravegram.dao.util.ConnectionManager;
import com.luis.ravegram.dao.util.JDBCUtils;
import com.luis.ravegram.exception.DataException;
import com.luis.ravegram.model.TipoMusica;
import com.luis.ravegram.service.TipoMusicaService;

public class TipoMusicaServiceImpl implements TipoMusicaService {

	private static Logger logger = LogManager.getLogger(TipoMusicaServiceImpl.class);

	private TipoMusicaDAO tipoMusicaDAO = null;
	
	public TipoMusicaServiceImpl() {
		tipoMusicaDAO = new TipoMusicaDAOImpl();
	
	}

	@Override
	public List<TipoMusica> findAll() 
			throws DataException {
		Connection c = null;
		boolean commitOrRollback = false;
		List<TipoMusica> tiposMusica = null;
		try  {
			c = ConnectionManager.getConnection();								

			c.setAutoCommit(false);

			tiposMusica = tipoMusicaDAO.findAll(c);		


			commitOrRollback = true;

		} catch (SQLException sqle) { 
			logger.error("findAll : "+sqle.getMessage() ,sqle);
			throw new DataException("findAll: ");						
		} finally {
			JDBCUtils.closeConnection(c, commitOrRollback);
		}
		return tiposMusica;
	}

}
