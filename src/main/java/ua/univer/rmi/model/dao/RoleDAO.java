package ua.univer.rmi.model.dao;

import java.util.List;

import ua.univer.rmi.model.entity.Role;

public interface RoleDAO {
	
	void addRole(Role role);
	Role getRoleByName(String role);
	List<Role> getAllRoles();
	void deleteAllRoles();

}
