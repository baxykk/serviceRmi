package ua.univer.rmi.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Client extends User {
	private List<Card> cards = new ArrayList<>();

	public Client(){
		super();
		cards = Collections.emptyList();
	}

	public List<Card> getCards() {
		return cards;
	}


	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	@Override
	public String toString() {
		
		return super.toString() + " Client [cards=" + cards + "]";
	}
	
	
}
