package ua.univer.rmi.stubs.implementations;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ua.univer.rmi.dto.Role;
import ua.univer.rmi.model.DAOFactory;
import ua.univer.rmi.model.dao.AdminDAO;
import ua.univer.rmi.model.dao.ClientDAO;
import ua.univer.rmi.stubs.RoleDTOInterface;

public class RoleDTOImplementation extends UnicastRemoteObject implements RoleDTOInterface {

	private static final long serialVersionUID = 1L;
	private static final String USER = "user";
	private static final String ADMIN = "administrator";
	
	public RoleDTOImplementation() throws RemoteException {
		super();
	}

	@Override
	public Role getUserRole(String login, String password) throws RemoteException {		
		ClientDAO user = DAOFactory.getClientDAO();
		String roleClient = user.getUserRole(login, password);
		if (roleClient.equalsIgnoreCase(USER)) return Role.USER;
		
		AdminDAO admin = DAOFactory.getAdminDAO();
		String roleAdmin = admin.getUserRole(login, password);
		if (roleAdmin.equalsIgnoreCase(ADMIN)) return Role.ADMIN;
		
		return Role.NONE;
	}

}

