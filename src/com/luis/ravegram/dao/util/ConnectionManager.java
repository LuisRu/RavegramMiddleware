package com.luis.ravegram.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionManager {
	
	private static Logger logger = LogManager.getLogger(ConnectionManager.class);

    public static Connection getConnection()  throws SQLException {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ravegram", "root","1998");
            return con;

        }  catch (SQLException e) {        	
        	logger.fatal(e);
        	throw e;
        } catch (ClassNotFoundException cnf) {
        	logger.fatal(cnf);
        	throw new UnknownError(cnf.getMessage());
        }
    }

}