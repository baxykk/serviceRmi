package ua.univer.rmi.utils;

import java.rmi.RemoteException;

import ua.univer.rmi.stubs.AdminDTOInterface;
import ua.univer.rmi.stubs.ClientDTOInterface;
import ua.univer.rmi.stubs.NewUserDTOInterface;
import ua.univer.rmi.stubs.RoleDTOInterface;
import ua.univer.rmi.stubs.implementations.AdminDTOImplementation;
import ua.univer.rmi.stubs.implementations.ClientDTOImplementation;
import ua.univer.rmi.stubs.implementations.NewUserDTOImplementation;
import ua.univer.rmi.stubs.implementations.RoleDTOImplementation;

public class DTOFactory {

	private DTOFactory() {}
	
	public static AdminDTOInterface getAdminDTO() throws RemoteException {
		return new AdminDTOImplementation();
	}
	
	public static ClientDTOInterface getClientDTO() throws RemoteException {
		return new ClientDTOImplementation();
	}
	
	public static RoleDTOInterface getRoleDTO() throws RemoteException {
		return new RoleDTOImplementation();
	}
	
	public static NewUserDTOInterface getNewUserDTO() throws RemoteException {
		return new NewUserDTOImplementation();
	}
}
