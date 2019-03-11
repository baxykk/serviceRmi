package ua.univer.rmi.model.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.univer.rmi.model.dao.AccountDAO;
import ua.univer.rmi.model.entity.Account;
import ua.univer.rmi.model.entity.Card;
import ua.univer.rmi.model.entity.Client;
import ua.univer.rmi.utils.ConnectionPool;

public class AccountDAOImplementation implements AccountDAO {

	@Override
	public Account addNewAccount(Account account) {
		String accountToAdd = "INSERT INTO accounts (external_key, balance, status_blocked, user_id) VALUES (?, ?, ?, ?)";

		account.setBlockedStatus(false);
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(accountToAdd)) {
			ps.setInt(1, account.getAccountNumber());
			ps.setDouble(2, account.getBalance());
			ps.setBoolean(3, account.isBlocked());
			ps.setInt(4, account.getUserId());
			ps.execute();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return account;
	}

	@Override
	public List<Account> getAccount(Client client) {
		List<Account> account = new ArrayList<>();
		String accountToGet = "SELECT * FROM accounts WHERE user_id = ?; ";
		ResultSet rs = null;

		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(accountToGet)) {
			ps.setLong(1,client.getId());
			ps.execute();
			rs = ps.getResultSet();
			account = getAccountData(rs);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (rs != null)
				try { rs.close();} catch (SQLException e) {	}
		}
		
		return account;
	}

	private List<Account> getAccountData(ResultSet rs) throws SQLException {
		List<Account> accounts = new ArrayList<>();
		
		if (rs.isBeforeFirst()) {
			
			while (rs.next()) {
				Account account = new Account();
				account.setID(rs.getInt(1));
				account.setAccountNumber(rs.getInt(2));
				account.setBalance(rs.getDouble(3));
				account.setBlockedStatus(rs.getBoolean(4));
				accounts.add(account);
			}
		} else {
			accounts = Collections.emptyList();
		}
		
		return accounts;
	}

	@Override
	public List<Account> getAllAccounts() {
		List<Account> accounts = new ArrayList<>();
		ResultSet rs = null;
		String allAccounts = "SELECT * FROM accounts";
		
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(allAccounts)) {
			ps.execute();
			rs = ps.getResultSet();
			accounts = getAccountData(rs);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (rs != null)
				try { rs.close();} catch (SQLException e) {	}
		}

		return accounts;
	}	

	@Override
	public Account deleteAccount(Account account) {
		String accountToDelete = "DELETE FROM accounts WHERE external_key = ?";
		
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(accountToDelete)) {
			ps.setInt(1, account.getAccountNumber());
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return account;
	}
	
	@Override
	public Account blockAccount(Account account, boolean bool) {
		String accountToBlock = "UPDATE accounts SET status_blocked = ? WHERE external_key = ?";
		
		account.setBlockedStatus(bool);
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(accountToBlock)) {
			ps.setBoolean(1, account.isBlocked());
			ps.setInt(2, account.getAccountNumber());
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return account;
	}

	@Override
	public boolean updateBalance(int accountNumber, double balance) {
		String accountBalanceToUpdate = "UPDATE accounts SET balance = ? WHERE external_key = ?";
		boolean success = false;
		
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(accountBalanceToUpdate)) {
			ps.setDouble(1, balance);
			ps.setInt(2, accountNumber);
			ps.execute();
			success = true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return success;
	}
	
	@Override
	public double getBalance(int accountNumber) {
		String accountBalanceToGet = "SELECT balance FROM accounts WHERE external_key = ?";
		double balance;
		
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(accountBalanceToGet)) {
			ps.setInt(1, accountNumber);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			rs.next();
			balance = rs.getDouble(1);
			rs.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		return balance;
	}

	@Override
	public List<Account> getAccount(int userId) {
		// TODO Auto-generated method stub
		return null;
	}
}
