package ua.univer.rmi.model.entity;

public abstract class User {
	private int id;
	private String name;
	private String surname;
	private int roleId;
	private String username;
	private String password;
	private String email;
	
	public User() {
		this(0, "", "", 0, "", "" ,"");
	}
	
	public User(int id, String name, String surname, int roleId, String username, String passw, String email) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.roleId = roleId;
		this.username = username;
		this.password = passw;
		this.email = email;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getRoleID() {
		return roleId;
	}

	public void setRoleID(int roleId) {
		this.roleId = roleId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
