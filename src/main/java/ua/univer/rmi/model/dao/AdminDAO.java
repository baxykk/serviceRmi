package ua.univer.rmi.model.dao;

import java.util.List;

import ua.univer.rmi.model.entity.Administrator;
import ua.univer.rmi.model.entity.Client;

public interface AdminDAO {

	List<Administrator> getUser(String external_key);
	String getUserRole(String external_key, String passw);
	
	List<Client> getAllClients();
	void addClient(Client client);
	void updateClient(Client client);
	void deleteClient(String external_key);
	void deleteAllClients();
}
