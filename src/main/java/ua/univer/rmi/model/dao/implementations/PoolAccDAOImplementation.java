package ua.univer.rmi.model.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ua.univer.rmi.model.dao.PoolAccountDAO;
import ua.univer.rmi.model.entity.PoolAccount;
import ua.univer.rmi.utils.ConnectionPool;

public class PoolAccDAOImplementation implements PoolAccountDAO  {

	@Override
	public PoolAccount getPoolAcc() {
		PoolAccount coreAccount = new PoolAccount();
		String poolAcc = "SELECT * FROM  pool_account;";

		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(poolAcc)) {
			ps.execute();
			ResultSet rs = ps.getResultSet();
			rs.next();
			coreAccount.setAccountNumber(rs.getInt(2));
			coreAccount.setBankNumber(rs.getString(3));
			coreAccount.setBalance(rs.getInt(4));
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return coreAccount;
	}

	@Override
	public int getBalance() {
		int currentBalance = 0;
		String balance = "SELECT balance FROM  pool_account;";

		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(balance)) {
			ps.execute();
			ResultSet rs = ps.getResultSet();
			rs.next();
			currentBalance = rs.getInt(1);
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return currentBalance;
	}

	@Override
	public boolean updateBalance(double amount) {
		String balanceUpdate = "UPDATE pool_account SET balance = ?";
		boolean success = false;
		
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(balanceUpdate)) {
			ps.setDouble(1, amount);
			ps.execute();
			success = true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return success;
	}

	@Override
	public boolean updatePoolAcc() {
		// TODO Auto-generated method stub
		return false;
	}

}
