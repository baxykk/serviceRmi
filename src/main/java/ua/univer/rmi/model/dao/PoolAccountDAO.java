package ua.univer.rmi.model.dao;

import ua.univer.rmi.model.entity.PoolAccount;

public interface PoolAccountDAO {
	
	PoolAccount getPoolAccount();
	double getBalance();
	void updateBalance(double amount);

}
