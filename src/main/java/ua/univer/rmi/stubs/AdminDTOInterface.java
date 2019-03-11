package ua.univer.rmi.stubs;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ua.univer.rmi.dto.AccountDTO;
import ua.univer.rmi.dto.AdminDTO;
import ua.univer.rmi.dto.CardDTO;

public interface AdminDTOInterface extends Remote, RmiInterface {
	
	AdminDTO getUserData(String token) throws RemoteException;
	void changeAccountStatus(AccountDTO account) throws RemoteException;
	boolean unblockCard(CardDTO card) throws RemoteException;
	
}
