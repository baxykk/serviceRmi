package ua.univer.rmi.stubs.implementations;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import ua.univer.rmi.dto.UserDTO;
import ua.univer.rmi.exceptions.UsernameAlreadyExistsException;
import ua.univer.rmi.model.DAOFactory;
import ua.univer.rmi.model.dao.AccountDAO;
import ua.univer.rmi.model.dao.ClientDAO;
import ua.univer.rmi.model.dao.RoleDAO;
import ua.univer.rmi.model.entity.Account;
import ua.univer.rmi.model.entity.Client;
import ua.univer.rmi.model.entity.Role;
import ua.univer.rmi.stubs.NewUserDTOInterface;

public class NewUserDTOImplementation extends UnicastRemoteObject implements NewUserDTOInterface {

	private static final long serialVersionUID = 1L;
	private static final String ROLE = "user";

	public NewUserDTOImplementation() throws RemoteException {
		super();
	}

	@Override
	public synchronized void registerNewUser(UserDTO userData) throws RemoteException {
		Client client = new Client();
		client.setName(userData.getName());
		client.setSurname(userData.getSurname());
		client.setUsername(userData.getUsername());
		client.setEmail(userData.getEmail());
		client.setPassword(userData.getPassword());
		

		RoleDAO rd = DAOFactory.getRoleDAO();
		Role userRole = rd.getRoleByName(ROLE);
		if(userRole != null)
				client.setRoleID(userRole.getId());
		
		Account account = new Account();
		account.setAccountNumber(generateAccNumber());
		account.setBalance(0);
		account.setBlockedStatus(false);
		
		
		ClientDAO cd = DAOFactory.getClientDAO();
		try {
			cd.addClient(client);
			List<Client> clients = cd.getClient(userData.getUsername());
			int id = clients.get(0).getId();
			account.setUserId(id);
		
			AccountDAO ad = DAOFactory.getAccountDAO();
			ad.addNewAccount(account);			
		}
		catch (UsernameAlreadyExistsException e){
			throw new UsernameAlreadyExistsException();
		}
	}

	private int generateAccNumber() {
		String s = "";
		String key = String.valueOf((int)(Math.random()*Math.pow(10, 5)));
		int j = 6 - key.length();
		for (int i = 1; i<=j ;i++) s = s.concat(Integer.toString(0));
		return Integer.valueOf(("1200" + key + s));
	}
		
}
