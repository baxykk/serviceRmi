package ua.univer.rmi.model.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ua.univer.rmi.exceptions.FailedSqlTransactionException;
import ua.univer.rmi.model.dao.PoolAccountDAO;
import ua.univer.rmi.model.entity.PoolAccount;
import ua.univer.rmi.utils.ConnectionPool;
import ua.univer.rmi.utils.ProjectLogger;

public class PoolAccDAOImplementation implements PoolAccountDAO {

	@Override
	public PoolAccount getPoolAccount() {
		PoolAccount coreAccount = new PoolAccount();
		ResultSet rs = null;
		String poolAcc = "SELECT * FROM  pool_account;";

		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(poolAcc)) {
			ps.execute();
			rs = ps.getResultSet();
			rs.next();
			coreAccount.setAccountNumber(rs.getInt(2));
			coreAccount.setBankNumber(rs.getInt(3));
			coreAccount.setBalance(rs.getDouble(4));
			rs.close();
		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e);
			throw new FailedSqlTransactionException();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					ProjectLogger.getInstance().warn(e);
				}
		}

		return coreAccount;
	}

	@Override
	public synchronized double getBalance() {
		double currentBalance = 0;
		String balance = "SELECT balance FROM  pool_account;";
		ResultSet rs = null;

		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(balance)) {
			ps.execute();
			rs = ps.getResultSet();
			rs.next();
			currentBalance = rs.getDouble(1);
			rs.close();
		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e);
			throw new FailedSqlTransactionException();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					ProjectLogger.getInstance().warn(e);
				}
		}

		return currentBalance;
	}

	@Override
	public synchronized void updateBalance(double amount) {
		String balanceUpdate = "UPDATE pool_account SET balance = ?";

		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(balanceUpdate)) {
			ps.setDouble(1, amount);
			ps.execute();
		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e);
			throw new FailedSqlTransactionException();
		}

	}

}
