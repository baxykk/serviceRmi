package ua.univer.rmi.stubs;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ua.univer.rmi.dto.CardDTO;
import ua.univer.rmi.dto.ClientDTO;
import ua.univer.rmi.dto.DetailsDTO;

public interface ClientDTOInterface extends Remote, RmiInterface {
	
	ClientDTO getUserData(String username) throws RemoteException;
	boolean addNewCard(CardDTO card, String username)throws RemoteException;
	boolean changeCardStatus(CardDTO card) throws RemoteException;
	void forgetCard(CardDTO card) throws RemoteException;
	
	boolean transferBetweenCards(DetailsDTO fromCard, DetailsDTO toCard, double amount, String reference) throws RemoteException;
	boolean transferMoney(DetailsDTO payeeDetails, double amount, String reference) throws RemoteException;
	boolean addMoneyToAccount(DetailsDTO payerCard, double amount, String reference) throws RemoteException;

}
