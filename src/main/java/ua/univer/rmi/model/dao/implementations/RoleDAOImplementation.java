package ua.univer.rmi.model.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.univer.rmi.model.dao.RoleDAO;
import ua.univer.rmi.model.entity.Role;
import ua.univer.rmi.utils.ConnectionPool;

public class RoleDAOImplementation implements RoleDAO {

	@Override
	public void addRole(Role role) {
		String cardToInsert = "INSERT INTO role(role) VALUE(?);";
		try (Connection c = ConnectionPool.getConnection();
			 PreparedStatement ps = c.prepareStatement(cardToInsert))
		{
			ps.setString(1, role.getUserRole());
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}		
	}

	@Override
	public List<Role> getAllRoles() {
		String allRoles = "SELECT * FROM role;";
		List<Role> roles = new ArrayList<>();
		ResultSet rs = null;
		
		try (Connection c = ConnectionPool.getConnection();
				PreparedStatement ps = c.prepareStatement(allRoles))
		{
			ps.execute();
			rs = ps.getResultSet();
			if (rs.isBeforeFirst())
				while (rs.next()) {
					int id = rs.getInt(1);
					String description = rs.getString(2);
					Role r = new Role(id, description);
					roles.add(r);
				}
			else roles = Collections.EMPTY_LIST;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {	}
		}		
		return roles;
	}

	@Override
	public void deleteAllRoles() {
		String allRoles = "DELETE role.* FROM role;";
		
		try (Connection c = ConnectionPool.getConnection();
				PreparedStatement ps = c.prepareStatement(allRoles))
		{
			ps.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

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
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
		return roleToGet;
	}
	
}
