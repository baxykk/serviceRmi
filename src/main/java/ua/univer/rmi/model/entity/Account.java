package ua.univer.rmi.model.entity;

public class Account {
	private int id;
	private int accountNumber;
	private double balance;
	private boolean isBlocked;
	private int userId;
	
	public Account() {
		this(0, 0, 0, false, 0);
	}
	
	public Account(int id, int accountNumber, double balance, boolean isBlocked, int userId) {
		this.id = id;
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.isBlocked = isBlocked;
		this.userId = userId;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlockedStatus(boolean block) {
		this.isBlocked = block;
	}

	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int id) {
		this.userId = id;
	}
}
