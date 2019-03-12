package ua.univer.rmi.model.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.univer.rmi.exceptions.FailedSqlTransactionException;
import ua.univer.rmi.model.dao.AccountDAO;
import ua.univer.rmi.model.entity.Account;
import ua.univer.rmi.model.entity.Client;
import ua.univer.rmi.utils.ConnectionPool;
import ua.univer.rmi.utils.ProjectLogger;

public class AccountDAOImplementation implements AccountDAO {

	@Override
	public Account addNewAccount(Account account) {
		String accountToAdd = "INSERT INTO accounts (account_number, balance, status, user_id) VALUES (?, ?, ?, ?)";

		account.setBlockedStatus(false);
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(accountToAdd)) {
			ps.setInt(1, account.getAccountNumber());
			ps.setDouble(2, account.getBalance());
			ps.setBoolean(3, account.isBlocked());
			ps.setInt(4, account.getUserId());
			ps.execute();
			
		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e.toString());
			throw new FailedSqlTransactionException(e.toString());
		}
		
		return account;
	}

	@Override
	public List<Account> getAccount(Client client) {
		List<Account> account = new ArrayList<>();
		String accountToGet = "SELECT account_number, balance, status, user_id FROM accounts WHERE user_id = ?; ";
		ResultSet rs = null;

		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(accountToGet)) {
			ps.setLong(1,client.getId());
			ps.execute();
			rs = ps.getResultSet();
			account = getAccountData(rs);
		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e.toString());
			throw new FailedSqlTransactionException(e.toString());
		} finally {
			if (rs != null)
				try { rs.close();} catch (SQLException e) {ProjectLogger.getInstance().warn(e.toString());	}
		}
		
		return account;
	}

	@Override
	public List<Account> getAllAccounts() {
		List<Account> accounts = new ArrayList<>();
		ResultSet rs = null;
		String allAccounts = "SELECT account_number, balance, status, user_id FROM accounts";
		
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(allAccounts)) {
			ps.execute();
			rs = ps.getResultSet();
			accounts = getAccountData(rs);
		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e.toString());
			throw new FailedSqlTransactionException(e.toString());
		} finally {
			if (rs != null)
				try { rs.close();} catch (SQLException e) {
					ProjectLogger.getInstance().warn(e.toString());
				}
		}

		return accounts;
	}	
	
	@Override
	public Account blockAccount(Account account, boolean bool) {
		String accountToBlock = "UPDATE accounts SET status = ? WHERE account_number = ?";
		
		account.setBlockedStatus(bool);
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(accountToBlock)) {
			ps.setBoolean(1, account.isBlocked());
			ps.setInt(2, account.getAccountNumber());
			ps.execute();
		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e.toString());
			throw new FailedSqlTransactionException(e.toString());
		}
		
		return account;
	}

	@Override
	public synchronized boolean updateBalance(int accountNumber, double balance) {
		String accountBalanceToUpdate = "UPDATE accounts SET balance = ? WHERE account_number = ?";
		boolean success = false;
		
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(accountBalanceToUpdate)) {
			ps.setDouble(1, balance);
			ps.setInt(2, accountNumber);
			ps.execute();
			success = true;
		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e.toString());
			throw new FailedSqlTransactionException(e.toString());
		}
		
		return success;
	}
	
	@Override
	public synchronized double getBalance(int accountNumber) {
		String accountBalanceToGet = "SELECT balance FROM accounts WHERE account_number = ?";
		double balance;
		ResultSet rs = null;
		
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(accountBalanceToGet)) {
			ps.setInt(1, accountNumber);
			ps.execute();
			rs = ps.getResultSet();
			rs.next();
			balance = rs.getDouble(1);
			rs.close();
		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e.toString());
			throw new FailedSqlTransactionException(e.toString());
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					ProjectLogger.getInstance().warn(e.toString());
				}
		}
		return balance;
	}

	public static List<Account> getAccountData(ResultSet rs) throws SQLException {
		List<Account> accounts = new ArrayList<>();
		
		if (rs.isBeforeFirst()) {
			
			while (rs.next()) {
				Account account = new Account();
				account.setAccountNumber(rs.getInt(1));
				account.setBalance(rs.getDouble(2));
				account.setBlockedStatus(rs.getBoolean(3));
				account.setUserId(rs.getInt(4));
				accounts.add(account);
			}
		} else {
			accounts = Collections.emptyList();
		}
		
		return accounts;
	}
}
