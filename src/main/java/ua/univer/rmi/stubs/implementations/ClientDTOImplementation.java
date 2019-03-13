package ua.univer.rmi.stubs.implementations;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.List;

import ua.univer.rmi.api.MockPaymentsApi;
import ua.univer.rmi.api.TransferExecutor;
import ua.univer.rmi.converters.DaoToDtoConverter;
import ua.univer.rmi.converters.DtoToDaoConverter;
import ua.univer.rmi.dto.AccountDTO;
import ua.univer.rmi.dto.BankDetailsDTO;
import ua.univer.rmi.dto.CardDTO;
import ua.univer.rmi.dto.CardDetailsDTO;
import ua.univer.rmi.dto.ClientDTO;
import ua.univer.rmi.dto.DetailsDTO;
import ua.univer.rmi.exceptions.InsufficientFundsException;
import ua.univer.rmi.exceptions.MalformedReferenceException;
import ua.univer.rmi.model.DAOFactory;
import ua.univer.rmi.model.dao.AccountDAO;
import ua.univer.rmi.model.dao.CardDAO;
import ua.univer.rmi.model.dao.ClientDAO;
import ua.univer.rmi.model.dao.PoolAccountDAO;
import ua.univer.rmi.model.entity.Account;
import ua.univer.rmi.model.entity.Card;
import ua.univer.rmi.model.entity.Client;
import ua.univer.rmi.model.entity.PoolAccount;
import ua.univer.rmi.stubs.ClientDTOInterface;

public class ClientDTOImplementation extends UnicastRemoteObject implements ClientDTOInterface {

	private static final long serialVersionUID = 1L;

	public ClientDTOImplementation() throws RemoteException {//
	}
	
	@Override
	public ClientDTO getUserData(String username) throws RemoteException {
		ClientDTO client = new ClientDTO();
		AccountDTO accountDto;
		List<CardDTO> cardsDto;
		
		ClientDAO clientDao = DAOFactory.getClientDAO();
		List<Client> clientList = clientDao.getClient(username);		
				
		if(!clientList.isEmpty()) {	
			
			//convert client
			client = DaoToDtoConverter.convert(clientList.get(0));
			
			//get and convert account
			AccountDAO accountDao = DAOFactory.getAccountDAO();
			List<Account> account = accountDao.getAccount(clientList.get(0));
			
			accountDto = DaoToDtoConverter.convert(account.get(0));						
			client.setAccount(accountDto);
			
			//get and convert cards
			CardDAO cardDao = DAOFactory.getCardDAO();
			List<Card> cards = cardDao.getClientCards(clientList.get(0));
			
			if (!cards.isEmpty()) 
				cardsDto = DaoToDtoConverter.convertCards(cards);
			else cardsDto = Collections.emptyList();						
			client.setCards(cardsDto);			
		}	
		return client;
	}	

	@Override
	public boolean addNewCard(CardDTO card, String username) throws RemoteException {
		int clientId = DAOFactory.getClientDAO()
								.getClient(username)
								.get(0)
								.getId();
		
		Card newCard = DtoToDaoConverter.convert(card, clientId);
		DAOFactory.getCardDAO().addNewCard(newCard);
		return true;
	}
		
	@Override
	public boolean changeCardStatus(CardDTO card) throws RemoteException {
		CardDAO cardDao = DAOFactory.getCardDAO(); 
		cardDao.blockCard(card.getCardNumber(), card.isBlocked());	
		return true; 
	}
	
	@Override
	public synchronized boolean transferBetweenCards(DetailsDTO fromCard, DetailsDTO toCard, double amount, String reference)
			throws RemoteException {
		
		boolean success = false;
		PoolAccountDAO pad = DAOFactory.getPoolAccountDAO();
		PoolAccount pool = pad.getPoolAccount();		
		BankDetailsDTO poolDetails = DaoToDtoConverter.convert(pool);
		
		TransferExecutor paymentApi = MockPaymentsApi.getApi();
		
		String accountNumAsString = reference.substring(5,15);
		String ref1 = "TBC for " + accountNumAsString + ": from " + ((CardDetailsDTO)fromCard).getCardNumber();
		String ref2 = "TBC for " + accountNumAsString + ": to " + ((CardDetailsDTO)toCard).getCardNumber();
		
		synchronized (paymentApi) {
			if (paymentApi.executeMoneyTransfer((CardDetailsDTO)fromCard, poolDetails, amount, ref1))
						success = paymentApi.executeMoneyTransfer(poolDetails, (CardDetailsDTO) toCard, -amount, ref2);
		}
		
		if (success) {
			double currentPoolBalance = pool.getBalance();
			double updatedPoolBalance = currentPoolBalance + amount;
			pad.updateBalance(updatedPoolBalance);
			updatedPoolBalance = updatedPoolBalance - amount;
			pad.updateBalance(updatedPoolBalance);
		}
		return success;
	}

	@Override
	public synchronized boolean transferMoney(DetailsDTO payeeDetails, double amount, String reference) throws RemoteException {
		boolean success = false;
		PoolAccountDAO pad = DAOFactory.getPoolAccountDAO();
		PoolAccount pool = pad.getPoolAccount();
		
		String accountNumAsString = reference.substring(5,15);
		int accountNum = Integer.parseInt(accountNumAsString);		
		AccountDAO ad = DAOFactory.getAccountDAO();		
		
		
		BankDetailsDTO poolDetails = DaoToDtoConverter.convert(pool);
		TransferExecutor paymentApi = MockPaymentsApi.getApi();
		
		String tempRef;
		if (reference.length() < 15) throw new MalformedReferenceException(reference);
		else if (reference.length() == 15) tempRef = "";
		else tempRef =  reference.substring(15);
		
		String ref = "TR for " + accountNumAsString + ": "  + tempRef;

		double currentBalance = ad.getBalance(accountNum);
		if (currentBalance >= amount) {
			if (payeeDetails instanceof CardDetailsDTO)
				success = paymentApi.executeMoneyTransfer(poolDetails, (CardDetailsDTO) payeeDetails, -amount, ref);
			else
				success = paymentApi.executeMoneyTransfer(poolDetails, (BankDetailsDTO) payeeDetails, -amount, ref);
			
			double currentPoolBalance = pool.getBalance();
			double updatedPoolBalance = currentPoolBalance - amount;
			pad.updateBalance(updatedPoolBalance);
			
			double updatedBalance = currentBalance - amount;
			ad.updateBalance(accountNum, updatedBalance);
		}
		else throw new InsufficientFundsException("Transfer amount:" + amount + " lower than balance " + currentBalance);
		return success;
	}

	@Override
	public synchronized boolean addMoneyToAccount(DetailsDTO payerCard, double amount, String reference) throws RemoteException {
		boolean success = false;
		PoolAccountDAO pad = DAOFactory.getPoolAccountDAO();
		PoolAccount pool = pad.getPoolAccount();		
		BankDetailsDTO poolDetails = DaoToDtoConverter.convert(pool);
		
		String accountNumAsString = reference.substring(5,15);
		int accountNum = Integer.parseInt(accountNumAsString);		
		AccountDAO ad = DAOFactory.getAccountDAO();
		
		TransferExecutor paymentApi = MockPaymentsApi.getApi();
		String ref = "TU for " + accountNumAsString + ": "  + reference.substring(15);
		
		success = paymentApi.executeMoneyTransfer((CardDetailsDTO)payerCard, poolDetails, amount, ref);
		
		double currentPoolBalance = pool.getBalance();
		double updatedPoolBalance = currentPoolBalance + amount;
		pad.updateBalance(updatedPoolBalance);
		
		double currentBalance = ad.getBalance(accountNum);
		double updatedBalance = currentBalance + amount;
		ad.updateBalance(accountNum, updatedBalance);
		
		return success;
	}

	@Override
	public void forgetCard(CardDTO cardDto) throws RemoteException {
		CardDAO cd = DAOFactory.getCardDAO();
		Card card = DtoToDaoConverter.convert(cardDto);
		cd.deleteCard(card);
	}	
}
