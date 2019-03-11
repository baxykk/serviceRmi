package ua.univer.rmi.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdminDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String username;
	private String name;
	private String surname;
	private double pool;	

	private List<AccountDTO> accounts = new ArrayList<>();
	private List<CardDTO> blockedCards = new ArrayList<>();
	
	public AdminDTO() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public double getPool() {
		return pool;
	}

	public void setPool(double pool) {
		this.pool = pool;
	}
	
	public List<AccountDTO> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountDTO> accounts) {
		this.accounts = accounts;
	}

	public List<CardDTO> getBlockedCards() {
		return blockedCards;
	}

	public void setBlockedCards(List<CardDTO> blockedCards) {
		this.blockedCards = blockedCards;
	}	
}
