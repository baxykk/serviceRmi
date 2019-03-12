package ua.univer.rmi.utils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

import ua.univer.rmi.configuration.Configuration;
import ua.univer.rmi.exceptions.ConnectionPoolNotClosedException;
import ua.univer.rmi.exceptions.NoSqlConnectionException;

public class ConnectionPool {

	private static final Configuration properties = Configuration.getInstance();	
	private static final ProjectLogger logger = ProjectLogger.getInstance();
	
	private static final String DRIVER_CLASS_NAME = properties.getProperty("DriverClass");
	private static final String DB_URL = getDBUrl();
	private static final String USER = properties.getProperty("Login");
	private static final String PASSWORD = properties.getProperty("Passw");
	private static final int POOL_SIZE = 5;

	private static final BasicDataSource ds = new BasicDataSource();
	
	static {
		ds.setDriverClassName(DRIVER_CLASS_NAME);
		ds.setUrl(DB_URL);
		ds.setUsername(USER);
		ds.setPassword(PASSWORD);
		ds.setInitialSize(POOL_SIZE);
	}

	private ConnectionPool(){}
	
	private static String getDBUrl() {
		return "jdbc:"  + properties.getProperty("Database")
				+ "://" + properties.getProperty("FullAddress")
				+ "/"   + properties.getProperty("DBName")
				+ "?"   + properties.getProperty("OptionalParameters");
	}
	
	public static Connection getConnection() {
        Connection c = null;
		try {
			c = ds.getConnection();
		} catch (SQLException e) {
			logger.error("Error receiving connection from pool" + e);
			throw new NoSqlConnectionException("\nError receiving connection from pool\n");
		}
		return c;
    }
	
	public static void close() {
		try {
			ds.close();
		} catch (SQLException e) {
			logger.error("Connection pool was not closed properly" + e);
			throw new ConnectionPoolNotClosedException("\nError closing the pool\n");
		}
	}
}
