package ua.univer.rmi.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;

public class ProjectLogger{
	private static final ProjectLogger INSTANCE = new ProjectLogger();
	private static final Logger logger = LogManager.getLogger();
	
	private ProjectLogger() {
	}

	public static ProjectLogger getInstance() {
		return INSTANCE;
	}
	
	public void error(String s) {
		logger.error(s);
	}
	
	public void error(Throwable t) {
		logger.error(t);
	}
	
	public void warn(Throwable t) {
		logger.warn(t);
	}
	
	public void warn(String s) {
		logger.warn(s);
	}
	
	public void info(String s) {
		logger.info(s);
	}	

	public void log(Level level, String message) {
		logger.log(level, message);
	}
}
