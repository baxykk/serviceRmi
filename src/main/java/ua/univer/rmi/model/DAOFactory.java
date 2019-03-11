package ua.univer.rmi.model;

import ua.univer.rmi.model.dao.AccountDAO;
import ua.univer.rmi.model.dao.AdminDAO;
import ua.univer.rmi.model.dao.CardDAO;
import ua.univer.rmi.model.dao.ClientDAO;
import ua.univer.rmi.model.dao.PoolAccountDAO;
import ua.univer.rmi.model.dao.RoleDAO;
import ua.univer.rmi.model.dao.implementations.AccountDAOImplementation;
import ua.univer.rmi.model.dao.implementations.AdminDAOImplementation;
import ua.univer.rmi.model.dao.implementations.CardDAOImplementation;
import ua.univer.rmi.model.dao.implementations.ClientDAOImplementation;
import ua.univer.rmi.model.dao.implementations.PoolAccDAOImplementation;
import ua.univer.rmi.model.dao.implementations.RoleDAOImplementation;

public class DAOFactory {

	private DAOFactory() {}
	
	public static CardDAO getCardDAO() {
		return new CardDAOImplementation();
	}
	
	public static ClientDAO getClientDAO() {
		return new ClientDAOImplementation();
	}
	
	public static AdminDAO getAdminDAO() {
		return new AdminDAOImplementation();
	}
	
	public static RoleDAO getRoleDAO() {
		return new RoleDAOImplementation();
	}
	
	public static AccountDAO getAccountDAO() {
		return new AccountDAOImplementation();
	}
	
	public static PoolAccountDAO getPoolAccountDAO() {
		return new PoolAccDAOImplementation();
	}
}
