package ua.univer.rmi.model.dao;

import java.util.List;

import ua.univer.rmi.dto.CardDTO;
import ua.univer.rmi.model.entity.Client;

public interface ClientDAO {

	void addClient(Client client);
	String getUserRole(String username, String passw);
	List<Client> getClient(String username);
	List<Client> getClient(CardDTO cardNumber);	
	
}
