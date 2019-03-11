package ua.univer.rmi.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	
	public void info(String s) {
		logger.info(s);
	}	
}
