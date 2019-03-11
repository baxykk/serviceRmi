package ua.univer.rmi.converters;

import java.util.ArrayList;
import java.util.List;

import ua.univer.rmi.dto.AccountDTO;
import ua.univer.rmi.dto.CardDTO;
import ua.univer.rmi.dto.ClientDTO;
import ua.univer.rmi.model.DAOFactory;
import ua.univer.rmi.model.dao.ClientDAO;
import ua.univer.rmi.model.entity.Account;
import ua.univer.rmi.model.entity.Card;
import ua.univer.rmi.model.entity.Client;

public class DtoToDaoConverter {	
	
	private DtoToDaoConverter() {}
	
	public static Client convert(ClientDTO clientDto) { 

		ClientDAO cd = DAOFactory.getClientDAO();
		Client cl = cd.getClient(clientDto.getUsername()).get(0);
		Client client = new Client();
		List<Card> cards = new ArrayList<>();
		
		client.setUsername(clientDto.getUsername());
		client.setName(clientDto.getName());
		client.setSurname(clientDto.getSurname());
		client.setEmail(cl.getEmail());
		client.setPassword(cl.getPassword());
		client.setRoleID(cl.getRoleID());
		client.setId(cl.getId());
		List<CardDTO> listOfCards = clientDto.getCards();
		for (CardDTO card:listOfCards) {
			Card tempCard = convert(card, cl.getId());
			cards.add(tempCard);
		}
		return client;
	}
	
	public static Card convert(CardDTO clientCard, int clientId){
		Card card = new Card();
		card.setCardNumber(clientCard.getCardNumber());
		card.setValidTillMonth(clientCard.getValidTillMonth());
		card.setValidTillYear(clientCard.getValidTillYear());
		card.setBlockedStatus(clientCard.isBlocked());
		card.setUserId(clientId);
		return card;
	}
	
	public static Account convert(AccountDTO accountDto) {
		Account account = new Account();
		account.setAccountNumber(accountDto.getAccountNumber());
		account.setBalance(accountDto.getBalance());
		account.setBlockedStatus(accountDto.isBlocked());
		return account;
	}

	public static Card convert(CardDTO cardDto) {
		Card card = new Card();
		card.setCardNumber(cardDto.getCardNumber());
		card.setBlockedStatus(cardDto.isBlocked());
		card.setValidTillMonth(cardDto.getValidTillMonth());
		card.setValidTillYear(cardDto.getValidTillYear());
		return card;
	}
}
