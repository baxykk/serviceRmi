package ua.univer.rmi;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Properties;

import org.apache.logging.log4j.Level;

import ua.univer.rmi.configuration.Configuration;
import ua.univer.rmi.stubs.AdminDTOInterface;
import ua.univer.rmi.stubs.ClientDTOInterface;
import ua.univer.rmi.stubs.NewUserDTOInterface;
import ua.univer.rmi.stubs.RoleDTOInterface;
import ua.univer.rmi.utils.DTOFactory;
import ua.univer.rmi.utils.ProjectLogger;


public class App {

	private static final int PORT;
	private static final String HOSTNAME;
	
	private static final Configuration config = Configuration.getInstance();	
	private static final ProjectLogger logger = ProjectLogger.getInstance();
	
	static {
		Configuration.loadConfig();
		PORT = Integer.parseInt(config.getProperty("Port"));
		HOSTNAME = config.getProperty("Address");
		logger.info("Config loaded");
	}
	
	public static void main(String[] args) throws RemoteException {
		
		System.setProperty("java.rmi.server.hostname", HOSTNAME);
		
		RoleDTOInterface authentication = DTOFactory.getRoleDTO();
		ClientDTOInterface service = DTOFactory.getClientDTO();
		AdminDTOInterface administration = DTOFactory.getAdminDTO();
		NewUserDTOInterface registration = DTOFactory.getNewUserDTO();
		
		Registry registry = LocateRegistry.createRegistry(PORT);
		
		registry.rebind("authentication", authentication);
		registry.rebind("service", service);
		registry.rebind("administration", administration);
		registry.rebind("registration", registration);
		
		logger.info("Server started at port " + PORT);
	}
	
}
