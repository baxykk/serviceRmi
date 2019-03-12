package ua.univer.rmi.model.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.univer.rmi.exceptions.FailedSqlTransactionException;
import ua.univer.rmi.model.DAOFactory;
import ua.univer.rmi.model.dao.AccountDAO;
import ua.univer.rmi.model.dao.AdminDAO;
import ua.univer.rmi.model.entity.Account;
import ua.univer.rmi.model.entity.Administrator;
import ua.univer.rmi.utils.ConnectionPool;
import ua.univer.rmi.utils.ProjectLogger;

public class AdminDAOImplementation implements AdminDAO {

	@Override
	public List<Administrator> getUser(String username) {
		List<Administrator> admin = new ArrayList<>();
		List<Account> accounts = new ArrayList<>();
		ResultSet rs = null;

		String selectedUserData = "SELECT username, name, surname, role_id FROM users WHERE username = ?;";

		AccountDAO accDao = DAOFactory.getAccountDAO();
		accounts = accDao.getAllAccounts();
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(selectedUserData)) {
			ps.setString(1, username);
			ps.execute();
			rs = ps.getResultSet();
			admin = getAdminDataFromUsers(rs);
			admin.get(0).setAccounts(accounts);
		} catch (SQLException e) {			
			ProjectLogger.getInstance().error(e.toString());
			throw new FailedSqlTransactionException(e.toString());
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				ProjectLogger.getInstance().warn(e.toString());
			}
		}
		return admin;
	}

	@Override
	public String getUserRole(String username, String passw) {
		String role = "";
		String userAuthentication = "SELECT role FROM user_roles WHERE id = (SELECT role_id FROM users WHERE username = ? AND passw = ?);";
		ResultSet rs = null;
		
		try (Connection c = ConnectionPool.getConnection(); PreparedStatement ps = c.prepareStatement(userAuthentication)) {
			ps.setString(1, username);
			ps.setString(2, passw);
			ps.execute();
			rs = ps.getResultSet();
			if (rs.isBeforeFirst()) {
				rs.next();
				role = rs.getString(1);
			}
		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e.toString());
			throw new FailedSqlTransactionException(e.toString());
		} finally {
			if (rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {	
					ProjectLogger.getInstance().warn(e.toString());
				}				
			}
	
		return role;
	}

	private List<Administrator> getAdminDataFromUsers(ResultSet rs) throws SQLException {
		List<Administrator> admList = new ArrayList<>();		
		if (rs.isBeforeFirst()) {
			
			while (rs.next()) {		
				Administrator admin = new Administrator();
				admin.setUsername(rs.getString(1));
				admin.setName(rs.getString(2));
				admin.setSurname(rs.getString(3));
				admin.setRoleID(rs.getInt(4));			
				admList.add(admin);
			}
		} 
		else
			admList = Collections.emptyList();
		return admList;
	}
}
