package ua.univer.rmi.converters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.univer.rmi.model.entity.Account;
import ua.univer.rmi.model.entity.Administrator;
import ua.univer.rmi.model.entity.Card;
import ua.univer.rmi.model.entity.Client;

public class ResultSetToEntityConverter {

	private ResultSetToEntityConverter() {
	}

	public static List<Client> convertClient(ResultSet rs) throws SQLException {
		List<Client> clients = new ArrayList<>();		
		if (rs.isBeforeFirst()) {
			Client client = new Client();
			while (rs.next()) {					
				client.setId(rs.getInt(1));
				client.setUsername(rs.getString(2));
				client.setName(rs.getString(3));
				client.setSurname(rs.getString(4));
				client.setRoleID(rs.getInt(5));			
				clients.add(client);
			}
		} 
		else
			clients = Collections.emptyList();
		
		return clients;
	}
	
	public static List<Administrator> getClientDataFromUsers(ResultSet rs) throws SQLException {
		List<Administrator> admList = new ArrayList<>();		
		if (rs.isBeforeFirst()) {
			
			while (rs.next()) {		
				Administrator admin = new Administrator();
				admin.setId(rs.getInt(1));
				admin.setUsername(rs.getString(2));
				admin.setName(rs.getString(3));
				admin.setSurname(rs.getString(4));
				admin.setRoleID(rs.getInt(5));			
				admList.add(admin);
			}
		} 
		else
			admList = Collections.emptyList();
		return admList;
	}
	
	public static List<Card> convertCards(ResultSet rs) throws SQLException {
		List<Card> cards = new ArrayList<>();
		if (rs.isBeforeFirst()) {
			while (rs.next()) {
				Card card = new Card();
				card.setId(rs.getInt(1));
				card.setCardNumber(rs.getLong(2));
				card.setUserId(rs.getInt(4));
				cards.add(card);
			}
		}
		else
			cards = Collections.emptyList();
		
		return cards;
	}
	
	public static List<Account> convertAccounts(ResultSet rs) throws SQLException {
		List<Account> accounts = new ArrayList<>();
		
		if (rs.isBeforeFirst()) {
			
			while (rs.next()) {
				Account account = new Account();
				account.setID(rs.getInt(1));
				account.setAccountNumber(rs.getInt(2));
				account.setBalance(rs.getDouble(3));
				account.setBlockedStatus(rs.getBoolean(4));
				accounts.add(account);
			}
		} else {
			accounts = Collections.emptyList();
		}
		
		return accounts;
	}
	
}
