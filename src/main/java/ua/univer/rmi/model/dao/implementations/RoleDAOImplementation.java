package ua.univer.rmi.model.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ua.univer.rmi.exceptions.FailedSqlTransactionException;
import ua.univer.rmi.model.dao.RoleDAO;
import ua.univer.rmi.model.entity.Role;
import ua.univer.rmi.utils.ConnectionPool;
import ua.univer.rmi.utils.ProjectLogger;

public class RoleDAOImplementation implements RoleDAO {

	@Override
	public Role getRoleByName(String role) {
		String userRole = "SELECT * FROM user_roles WHERE role = ?;";
		Role roleToGet = new Role();
		ResultSet rs = null;
		
		try (Connection c = ConnectionPool.getConnection();
				PreparedStatement ps = c.prepareStatement(userRole))
		{
			ps.setString(1, role);
			ps.execute();
			rs = ps.getResultSet();
			rs.next();
			roleToGet.setId(rs.getInt(1));
			roleToGet.setUserRole(rs.getString(2));
			rs.close();
		} catch (SQLException e) {
			ProjectLogger.getInstance().error(e);
			throw new FailedSqlTransactionException(e.toString());
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					ProjectLogger.getInstance().warn(e);
				}
		}
		
		return roleToGet;
	}
	
}
