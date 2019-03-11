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
import ua.univer.rmi.exceptions.UsernameAlreadyExistsException;
import ua.univer.rmi.model.dao.ClientDAO;
import ua.univer.rmi.model.entity.Card;
import ua.univer.rmi.model.entity.Client;
import ua.univer.rmi.utils.ConnectionPool;

public class ClientDAOImplementation implements ClientDAO {

	@Override
	public synchronized List<Client> getClient(String external_id) {
		List<Client> client = new ArrayList<>();
		List<Card> cards = new ArrayList<>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		String selectedUserData = "SELECT * FROM users WHERE external_key = ?;";
		String selectedCards = "SELECT * FROM  cards WHERE user_id = ?;";

		try {
			c = ConnectionPool.getConnection();
			c.setAutoCommit(false);

			ps = c.prepareStatement(selectedUserData);
			ps.setString(1, external_id);
			ps.execute();
			rs = ps.getResultSet();
			assert rs.isBeforeFirst()==true;
			client = getClientDataFromUsers(rs);
			assert client.size()>0;
			ps.close();
			ps = c.prepareStatement(selectedCards);
			ps.setInt(1, client.get(0).getId());
			ps.execute();
			rs = ps.getResultSet();
			cards = getCardsData(rs);
			client.get(0).setCards(cards);
			c.commit();
		} catch (SQLException e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (c != null)
					c.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
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
		} else
			clients = Collections.EMPTY_LIST;
		return clients;
	}

	private List<Card> getCardsData(ResultSet rs) throws SQLException {
		List<Card> cards = new ArrayList<>();
		if (rs.isBeforeFirst()) {
			while (rs.next()) {
				Card card = new Card();
				card.setId(rs.getInt(1));
				card.setCardNumber(rs.getLong(2));
				card.setUserId(rs.getInt(4));
				cards.add(card);
			}
		} else
			cards = Collections.EMPTY_LIST;
		return cards;
	}

	@Override
	public List<Client> getAllClients() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addClient(Client client) {

		String clientToInsert = "INSERT INTO users (external_key, name, surname, role_id, passw, email)" + " VALUES(?, ?, ?, ?, ?, ?);";

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
			throw new RuntimeException(e);
		} 
		
	}

	@Override
	public boolean updateClientCards(Client client) {
		
		return false;
	}

	@Override
	public void deleteClient(String external_key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllClients() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getUserRole(String username, String passw) {
		String role = "";
		String userAuthentification = "SELECT role FROM role WHERE id_role = (SELECT role_id FROM users WHERE external_key = ? AND passw = ?) ;";
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
			throw new RuntimeException(e);
		} finally {
			if (rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {	// clean up 
				}
				
			}
	
		return role;
	}

	@Override
	public List<Client> getClient(CardDTO cardNumber) {		
		List<Client> client = new ArrayList<>();
		ResultSet rs = null;

		String cardDetails = "SELECT * FROM users WHERE id_user = (SELECT user_id FROM  cards WHERE external_key = ?);";

		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(cardDetails)) {
			ps.setLong(1, cardNumber.getCardNumber());
			ps.execute();
			rs = ps.getResultSet();
			client = getClientDataFromUsers(rs);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (rs!=null)
				try { rs.close(); } catch (SQLException e) {}
		}
		
		return client;
	}

	@Override
	public List<Client> getClient(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
