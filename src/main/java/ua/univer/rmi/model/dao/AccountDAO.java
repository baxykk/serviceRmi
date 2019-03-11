package ua.univer.rmi.model.dao;

import java.util.List;

import ua.univer.rmi.model.entity.Account;
import ua.univer.rmi.model.entity.Card;
import ua.univer.rmi.model.entity.Client;

public interface AccountDAO {
	Account addNewAccount(Account account);
	List<Account> getAccount(Client client);
	List<Account> getAccount(int userId);
	List<Account> getAllAccounts();	
	Account deleteAccount(Account account);
	double getBalance(int accountNumber);
	boolean updateBalance(int accountNumber, double balance);
	Account blockAccount(Account account, boolean bool);

}
