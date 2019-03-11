package ua.univer.rmi.stubs;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ua.univer.rmi.dto.UserDTO;

public interface  NewUserDTOInterface extends Remote, RmiInterface {

	void registerNewUser(UserDTO userData) throws RemoteException;
}
