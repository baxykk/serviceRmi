package ua.univer.rmi.stubs.implementations;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.List;

import ua.univer.rmi.converters.DaoToDtoConverter;
import ua.univer.rmi.converters.DtoToDaoConverter;
import ua.univer.rmi.dto.AccountDTO;
import ua.univer.rmi.dto.AdminDTO;
import ua.univer.rmi.dto.CardDTO;
import ua.univer.rmi.model.DAOFactory;
import ua.univer.rmi.model.dao.AccountDAO;
import ua.univer.rmi.model.dao.AdminDAO;
import ua.univer.rmi.model.dao.CardDAO;
import ua.univer.rmi.model.entity.Account;
import ua.univer.rmi.model.entity.Administrator;
import ua.univer.rmi.model.entity.Card;
import ua.univer.rmi.stubs.AdminDTOInterface;

public class AdminDTOImplementation extends UnicastRemoteObject implements AdminDTOInterface {

	private static final long serialVersionUID = 1L;

	public AdminDTOImplementation() throws RemoteException {
		super();
	}

	@Override
	public AdminDTO getUserData(String token) throws RemoteException {

		AdminDAO dao = DAOFactory.getAdminDAO();
		List<Administrator> adminData = dao.getUser(token);		
		Administrator admin = adminData.get(0);		
		AdminDTO adminDto = DaoToDtoConverter.convert(admin);
		CardDAO cdao = DAOFactory.getCardDAO();
		List<Card> blockedCards = cdao.getBlockedCards();
		List<CardDTO> cards = DaoToDtoConverter.convertCards(blockedCards);
		adminDto.setBlockedCards(cards);
		return adminDto;
	}

	@Override
	public void changeAccountStatus(AccountDTO account) throws RemoteException {
		Account acc = DtoToDaoConverter.convert(account);
		AccountDAO accDao = DAOFactory.getAccountDAO();
		accDao.blockAccount(acc, account.isBlocked());		
	}

	@Override
	public boolean unblockCard(CardDTO card) throws RemoteException {
		CardDAO cardDao = DAOFactory.getCardDAO();
		cardDao.blockCard(card.getCardNumber(), card.isBlocked());
		return true;
	}
	
}
