package ua.univer.rmi.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String name;
	private String surname;
	
	private AccountDTO account;
	private List<CardDTO> cards = new ArrayList<>();	
	
	public ClientDTO() {
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

	public List<CardDTO> getCards() {
		return cards;
	}

	public void setCards(List<CardDTO> cards) {
		this.cards = cards;
	}

	public AccountDTO getAccount() {
		return account;
	}

	public void setAccount(AccountDTO account) {
		this.account = account;
	}		
}
