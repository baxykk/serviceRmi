package ua.univer.rmi.dto;

import java.io.Serializable;

public class AccountDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int accountNumber;
	private double balance;
	private boolean isBlocked;
	
	public AccountDTO() {
		super();
	}
	
	public int getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}
	public boolean isBlocked() {
		return isBlocked;
	}
	public void setBlockedStatus(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
}
