package ua.univer.rmi.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import ua.univer.rmi.App;
import ua.univer.rmi.exceptions.ConfigurationNotFoundException;
import ua.univer.rmi.utils.ProjectLogger;

public class Configuration extends Properties {

	private static final long serialVersionUID = 1L;
	private static final Configuration INSTANCE = new Configuration();

	private Configuration() {
	}

	public static void loadConfig() {
			InputStream input = App.class.getResourceAsStream("/config.txt");
			try {
				INSTANCE.load(input);
			} catch (IOException e) {
				ProjectLogger.getInstance().error(e);
				throw new ConfigurationNotFoundException();
			}
	}

	public static Configuration getInstance() {
		return INSTANCE;
	}
}
