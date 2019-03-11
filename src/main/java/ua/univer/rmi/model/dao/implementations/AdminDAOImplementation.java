package ua.univer.rmi.model.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.univer.rmi.model.DAOFactory;
import ua.univer.rmi.model.dao.AccountDAO;
import ua.univer.rmi.model.dao.AdminDAO;
import ua.univer.rmi.model.entity.Account;
import ua.univer.rmi.model.entity.Administrator;
import ua.univer.rmi.model.entity.Card;
import ua.univer.rmi.model.entity.Client;
import ua.univer.rmi.utils.ConnectionPool;

public class AdminDAOImplementation implements AdminDAO {

	@Override
	public synchronized List<Administrator> getUser(String external_id) {
		List<Administrator> admin = new ArrayList<>();
		List<Account> accounts = new ArrayList<>();
		ResultSet rs = null;

		String selectedUserData = "SELECT * FROM users WHERE external_key = ?;";

		AccountDAO accDao = DAOFactory.getAccountDAO();
		accounts = accDao.getAllAccounts();
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(selectedUserData)) {
			ps.setString(1, external_id);
			ps.execute();
			rs = ps.getResultSet();
			admin = getClientDataFromUsers(rs);
			admin.get(0).setAccounts(accounts);
		} catch (SQLException e) {			
				throw new RuntimeException(e);		
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				//
			}
		}
		return admin;
	}

	private List<Administrator> getClientDataFromUsers(ResultSet rs) throws SQLException {
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
		} else
			admList = Collections.EMPTY_LIST;
		return admList;
	}

	@Override
	public List<Client> getAllClients() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addClient(Client client) {

		String clientToInsert = "INSERT INTO users (external_key, name, surname, role_id)" + " VALUES(?, ?, ?, ?);";

		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(clientToInsert)) {
			ps.setString(1, client.getUsername());
			ps.setString(2, client.getName());
			ps.setString(3, client.getSurname());
			ps.setInt(4, client.getRoleID());
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateClient(Client client) {
		// TODO Auto-generated method stub

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

}
