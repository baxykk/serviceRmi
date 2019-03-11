package ua.univer.rmi.model.dao;

import ua.univer.rmi.model.entity.PoolAccount;

public interface PoolAccountDAO {
	PoolAccount getPoolAcc();
	int getBalance();
	boolean updateBalance(double amount);
	boolean updatePoolAcc();

}
