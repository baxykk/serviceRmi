package ua.univer.rmi.model.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.univer.rmi.dto.CardDTO;
import ua.univer.rmi.exceptions.FailedSqlTransactionException;
import ua.univer.rmi.exceptions.UsernameAlreadyExistsException;
import ua.univer.rmi.model.dao.ClientDAO;
import ua.univer.rmi.model.entity.Card;
import ua.univer.rmi.model.entity.Client;
import ua.univer.rmi.utils.ConnectionPool;
import ua.univer.rmi.utils.ProjectLogger;

public class ClientDAOImplementation implements ClientDAO {

	@Override
	public void addClient(Client client) {

		String clientToInsert = "INSERT INTO users (username, name, surname, role_id, passw, email) VALUES(?, ?, ?, ?, ?, ?);";

		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(clientToInsert)) {
			ps.setString(1, client.getUsername());
			ps.setString(2, client.getName());
			ps.setString(3, client.getSurname());
			ps.setInt(4, client.getRoleID());
			ps.setString(5, client.getPassword());
			ps.setString(6, client.getEmail());
			ps.execute();
		} 
		catch (SQLIntegrityConstraintViolationException e) {
			throw new UsernameAlreadyExistsException();
		}
		catch (SQLException e) {
			ProjectLogger.getInstance().error(e);
			throw new FailedSqlTransactionException(e.toString());
		} 
		
	}

	@Override
	public String getUserRole(String username, String passw) {
		String role = "";
		String userAuthentification = "SELECT role FROM user_roles WHERE id = (SELECT role_id FROM users WHERE username = ? AND passw = ?) ;";
		ResultSet rs = null;
		
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(userAuthentification)) {
			ps.setString(1, username);
			ps.setString(2, passw);
			ps.execute();
			rs = ps.getResultSet();
			if (rs.isBeforeFirst()) {
				rs.next();
				role = rs.getString(1);
			}
		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e);
			throw new FailedSqlTransactionException(e.toString());
		} finally {
			if (rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {	
					ProjectLogger.getInstance().warn(e);
				}			
			}
		
		return role;
	}	
	
	@Override
	public List<Client> getClient(String username) {
		List<Client> client = new ArrayList<>();
		List<Card> cards = new ArrayList<>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectedUserData = "SELECT id, username, name, surname, role_id FROM users WHERE username = ?;";
		String selectedCards = "SELECT card_number, month, year, status, user_id FROM  cards WHERE user_id = ?;";

		try {
			c = ConnectionPool.getConnection();
			c.setAutoCommit(false);

			ps = c.prepareStatement(selectedUserData);
			ps.setString(1, username);
			ps.execute();
			rs = ps.getResultSet();
			client = getClientDataFromUsers(rs);
			ps.close();
			ps = c.prepareStatement(selectedCards);
			ps.setInt(1, client.get(0).getId());
			ps.execute();
			rs = ps.getResultSet();
			cards = getCardsData(rs);
			client.get(0).setCards(cards);
			ps.close();
			c.commit();
		} catch (SQLException e) {
			try {
				c.rollback();
				ProjectLogger.getInstance().warn(e);
			} catch (SQLException e1) {
				ProjectLogger.getInstance().error(e1);
				throw new FailedSqlTransactionException(e1.toString());
			}
			ProjectLogger.getInstance().error(e.toString());
		} finally {
			try {
				if (rs != null)	rs.close();
				if (ps != null)	ps.close();
				if (c != null)	c.close();
			} catch (SQLException e) {
				ProjectLogger.getInstance().warn(e);
			}
		}
		return client;
	}	

	@Override
	public List<Client> getClient(CardDTO cardNumber) {		
		List<Client> client = new ArrayList<>();
		ResultSet rs = null;

		String cardDetails = "SELECT id, username, name, surname, role_id FROM users WHERE id = (SELECT user_id FROM  cards WHERE card_number = ?);";

		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(cardDetails)) {
			ps.setLong(1, cardNumber.getCardNumber());
			ps.execute();
			rs = ps.getResultSet();
			client = getClientDataFromUsers(rs);
		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e);
			throw new FailedSqlTransactionException(e.toString());
		} finally {
			if (rs!=null)
				try { rs.close(); } catch (SQLException e) {
					ProjectLogger.getInstance().warn(e);
				}
		}
		
		return client;
	}

	
	private List<Client> getClientDataFromUsers(ResultSet rs) throws SQLException {
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
	
	private List<Card> getCardsData(ResultSet rs) throws SQLException {
		List<Card> cards = new ArrayList<>();
		if (rs.isBeforeFirst()) {
			while (rs.next()) {
				Card card = new Card();
				card.setCardNumber(rs.getLong(1));
				card.setValidTillMonth(rs.getInt(2));
				card.setValidTillYear(rs.getInt(3));
				card.setBlockedStatus(rs.getBoolean(4));
				card.setUserId(rs.getInt(5));
				cards.add(card);
			}
		} 
		else
			cards = Collections.emptyList();;
		return cards;
	}

}
