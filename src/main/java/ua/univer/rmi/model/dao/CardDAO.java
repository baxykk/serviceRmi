package ua.univer.rmi.model.dao;

import java.util.List;

import ua.univer.rmi.dto.CardDTO;
import ua.univer.rmi.model.entity.Card;
import ua.univer.rmi.model.entity.Client;

public interface CardDAO {
	void addNewCard(Card card);
	List<Card> getClientCards(Client client);
	List<Card> getBlockedCards();
	boolean updateCardValidity(Card card);
	boolean deleteCard(Card card);
	
	boolean blockCard(long cardNumber, boolean bool);

}
