package com.luis.ravegram.dao.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionManager {


	private static final String CFGM_PFX = "db.";
	private static final String CLASE = CFGM_PFX + "clase";
	private static final String URL = CFGM_PFX + "url";
	private static final String USER = CFGM_PFX + "user";
	private static final String PASSWORD = CFGM_PFX + "password";

	public static final String WEB_RAVEGRAM_PROPERTIES = "ravegram-config";

	private static Logger logger = LogManager.getLogger(ConnectionManager.class);

	private static String DRIVER_CLASS = null;
	private static String DB_URL = null;
	private static String DB_USER = null;
	private static String DB_PASSWORD = null;

	private static ComboPooledDataSource dataSource = null;

	static {
		DRIVER_CLASS = ConfigurationManager.getInstance().getParameter(WEB_RAVEGRAM_PROPERTIES, CLASE);
		DB_URL = ConfigurationManager.getInstance().getParameter(WEB_RAVEGRAM_PROPERTIES, URL);
		DB_USER =ConfigurationManager.getInstance().getParameter(WEB_RAVEGRAM_PROPERTIES, USER);
		DB_PASSWORD = ConfigurationManager.getInstance().getParameter(WEB_RAVEGRAM_PROPERTIES, PASSWORD);
		try {

			Class.forName(DRIVER_CLASS);

			dataSource = new ComboPooledDataSource(); // crates the connection pool
			dataSource.setDriverClass(DRIVER_CLASS);
			dataSource.setJdbcUrl(DB_URL);
			dataSource.setUser(DB_USER);
			dataSource.setPassword(DB_PASSWORD);

		} catch (Throwable t) {
			logger.fatal("Unable to load db driver class: "+DRIVER_CLASS);
			System.exit(0);
		}

	}

	public static Connection getConnection() throws SQLException {
		try {
			// Sin pool ...
			// Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			Connection con = dataSource.getConnection(); 
			return con;
		}  catch (SQLException sqle) {
			logger.fatal(sqle);
			throw sqle;
		}
	}
}


//	
//
//public static Connection getConnection()  throws SQLException {
//	try {
//
//		Class.forName(cfgM.getParameter(WEB_RAVEGRAM_PROPERTIES, CLASE));
//		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ravegram", "Ravegram","Cedeira1998");
//		return con;
//
//	}  catch (SQLException e) {        	
//		logger.fatal(e);
//		throw e;
//	} catch (ClassNotFoundException cnf) {
//		logger.fatal(cnf);
//		throw new UnknownError(cnf.getMessage());
//	}
//}
//
//}