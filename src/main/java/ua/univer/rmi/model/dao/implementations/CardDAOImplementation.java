package ua.univer.rmi.model.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.univer.rmi.exceptions.FailedSqlTransactionException;
import ua.univer.rmi.model.dao.CardDAO;
import ua.univer.rmi.model.entity.Card;
import ua.univer.rmi.model.entity.Client;
import ua.univer.rmi.utils.ConnectionPool;
import ua.univer.rmi.utils.ProjectLogger;

public class CardDAOImplementation implements CardDAO {

	@Override
	public void addNewCard(Card card) {
		String cardToInsert = "INSERT INTO cards(card_number, user_id, month, year) VALUES(?, ?, ?, ?);";

		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(cardToInsert);) {
			ps.setLong(1, card.getCardNumber());
			ps.setInt(2, card.getUserId());
			ps.setInt(3, card.getValidTillMonth());
			ps.setInt(4, card.getValidTillYear());
			ps.execute();

		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e);
			throw new FailedSqlTransactionException(e.toString());
		}
	}
	
	@Override
	public List<Card> getClientCards(Client client) {
		List<Card> cards = new ArrayList<>();
		String cardsToSelect = "SELECT card_number, month, year, status, user_id FROM  cards WHERE user_id = " + client.getId() + ";";

		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(cardsToSelect)) {
			ps.execute();
			ResultSet rs = ps.getResultSet();
			cards = getCardsData(rs);
			rs.close();
		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e);
			throw new FailedSqlTransactionException(e.toString());
		}
		return cards;
	}		

	

	@Override
	public List<Card> getBlockedCards() {
		List<Card> cards = new ArrayList<>();
		String cardsToSelect = "SELECT card_number, month, year, status, user_id FROM  cards WHERE status = true;";

		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(cardsToSelect)) {
			ps.execute();
			ResultSet rs = ps.getResultSet();
			cards = getCardsData(rs);
			rs.close();
		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e);
			throw new FailedSqlTransactionException(e.toString());
		}
		return cards;
	}

	@Override
	public boolean updateCardValidity(Card card) {
		String cardToUpdate = "UPDATE cards SET month = ?, year = ? WHERE card_number = ?";
		boolean success = false;
		
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(cardToUpdate)) {
			ps.setInt(1, card.getValidTillMonth());
			ps.setInt(2, card.getValidTillYear());
			ps.setLong(3, card.getCardNumber());
			ps.execute();
			success = true;
		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e);
			throw new FailedSqlTransactionException(e.toString());
		}
		
		return success;
	}

	@Override
	public boolean deleteCard(Card card) {
		String accountToDelete = "DELETE FROM cards WHERE card_number = ?";
		boolean success = false;
		
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(accountToDelete)) {
			ps.setLong(1, card.getCardNumber());
			ps.execute();
			success = true;
		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e);
			throw new FailedSqlTransactionException(e.toString());
		}
		
		return success;
	}
	
	@Override
	public boolean blockCard(long cardNumber, boolean bool) {
		String cardToBlock = "UPDATE cards SET status = ? WHERE card_number = ?";
		boolean success = false;
		
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(cardToBlock)) {
			ps.setBoolean(1, bool);
			ps.setLong(2, cardNumber);
			ps.execute();
			success = true;
		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e);
			throw new FailedSqlTransactionException(e.toString());
		}
		
		return success;
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
			cards = Collections.emptyList();
		
		return cards;
	}

}
