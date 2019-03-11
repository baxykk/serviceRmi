package ua.univer.rmi.model.entity;

import java.util.List;

public class Administrator extends User {
	
	private List<Account> accounts;

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
}
