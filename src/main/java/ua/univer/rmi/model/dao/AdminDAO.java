package ua.univer.rmi.model.dao;

import java.util.List;

import ua.univer.rmi.model.entity.Administrator;

public interface AdminDAO {

	List<Administrator> getUser(String username);
	String getUserRole(String username, String passw);

}
