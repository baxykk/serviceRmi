package ua.univer.rmi.model.entity;

public class Role {
	private int id;
	private String userRole;
	
	public Role(){
		this(0,"");
	}
	
	public Role(int id, String role) {
		this.id = id;
		this.userRole = role;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String role) {
		this.userRole = role;
	}
	
	
}
