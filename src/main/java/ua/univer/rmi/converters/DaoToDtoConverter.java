package ua.univer.rmi.converters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.univer.rmi.dto.AccountDTO;
import ua.univer.rmi.dto.AdminDTO;
import ua.univer.rmi.dto.BankDetailsDTO;
import ua.univer.rmi.dto.CardDTO;
import ua.univer.rmi.dto.ClientDTO;
import ua.univer.rmi.model.DAOFactory;
import ua.univer.rmi.model.dao.PoolAccountDAO;
import ua.univer.rmi.model.entity.Account;
import ua.univer.rmi.model.entity.Administrator;
import ua.univer.rmi.model.entity.Card;
import ua.univer.rmi.model.entity.Client;
import ua.univer.rmi.model.entity.PoolAccount;

public class DaoToDtoConverter {
	
	private DaoToDtoConverter() {}
	
	public static ClientDTO convert(Client client) {
		ClientDTO clientDto = new ClientDTO();
		
		clientDto.setName(client.getName());
		clientDto.setSurname(client.getSurname());
		clientDto.setUsername(client.getUsername());		
		return clientDto;
	}
	
	public static AdminDTO convert(Administrator admin) {
		AdminDTO adminDto = new AdminDTO();
		
		adminDto.setName(admin.getName());
		adminDto.setSurname(admin.getSurname());
		adminDto.setUsername(admin.getUsername());
		
		PoolAccountDAO pad = DAOFactory.getPoolAccountDAO();
		adminDto.setPool(pad.getBalance());
		List<AccountDTO> accounts = convertAccounts(admin.getAccounts());
		adminDto.setAccounts(accounts);
		
		return adminDto;
	}
	
	
	
	public static AccountDTO convert(Account account) {
		AccountDTO accountDto = new AccountDTO();
		
		accountDto.setAccountNumber(account.getAccountNumber());
		accountDto.setBalance(account.getBalance());
		accountDto.setBlockedStatus(account.isBlocked());
		return accountDto;
	}
	
	public static List<AccountDTO> convertAccounts(List<Account> accounts) {
		List<AccountDTO> accList = new ArrayList<>();
		AccountDTO accountDTO = null;
		
		if (!accounts.isEmpty())
			for (Account account : accounts) {
				accountDTO = convert(account);
				accList.add(accountDTO);
			}
		else 
			accList = Collections.emptyList();
		return accList;
	}
	
	public static CardDTO convert(Card card) {
		CardDTO newCard = new CardDTO();
		newCard.setCardNumber(card.getCardNumber());
		newCard.setValidTillMonth(card.getValidTillMonth());
		newCard.setValidTillYear(card.getValidTillYear());
		newCard.setBlockedStatus(card.isBlocked());
		return newCard;
	}
	
	public static List<CardDTO> convertCards(List<Card> listOfCards) {
		List<CardDTO> cards = new ArrayList<>();
		CardDTO card = null;
		
		if (!listOfCards.isEmpty())			
			for (Card entry : listOfCards) {
				card = convert(entry);
				cards.add(card);
			}
		else 
			cards = Collections.emptyList();
		return cards;
	}
	
	public static BankDetailsDTO convert(PoolAccount pool) {
		BankDetailsDTO details = new BankDetailsDTO();
		details.setAccountNumber(String.valueOf(pool.getAccountNumber()));
		details.setBankNumber(String.valueOf(pool.getBankNumber()));
		details.setCounterpartyName("EasyPay");
		return details;
	}
}
