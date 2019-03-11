package ua.univer.rmi.model.dao;

import java.util.List;

import ua.univer.rmi.dto.CardDTO;
import ua.univer.rmi.model.entity.Client;

public interface ClientDAO {

	List<Client> getClient(String external_key);
	List<Client> getClient(int id);
	List<Client> getClient(CardDTO cardNumber);
	List<Client> getAllClients();
	
	String getUserRole(String external_key, String passw);
	
	void addClient(Client client);
	boolean updateClientCards(Client client);
	void deleteClient(String external_key);
	void deleteAllClients();
	
}
