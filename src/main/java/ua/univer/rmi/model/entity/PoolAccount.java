package ua.univer.rmi.model.entity;

public class PoolAccount {
	private int poolId;
	private int accountNumber;
	private int bankNumber;
	private double balance;
	
	public PoolAccount() {
		this(0, 0, 0, 0);
	}
	
	public PoolAccount(int poolId, int accountNumber, int bankNumber, double balance) {
		this.poolId = poolId;
		this.accountNumber = accountNumber;
		this.bankNumber = bankNumber;
		this.balance = balance;
	}

	public int getPoolId() {
		return poolId;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(int bankNumber) {
		this.bankNumber = bankNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
