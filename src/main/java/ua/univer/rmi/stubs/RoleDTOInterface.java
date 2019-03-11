package ua.univer.rmi.stubs;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ua.univer.rmi.dto.Role;

public interface RoleDTOInterface extends Remote, RmiInterface {
	
	Role getUserRole(String login, String password) throws RemoteException;

}
