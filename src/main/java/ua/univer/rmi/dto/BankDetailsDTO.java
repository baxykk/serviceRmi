package ua.univer.rmi.dto;

public class BankDetailsDTO implements DetailsDTO {
	private static final long serialVersionUID = 1L;
	private String accountNumber;
	private String bankNumber;
	private String counterpartyName;
	
	public BankDetailsDTO() {
		this("", "", "");
	}
	
	public BankDetailsDTO(String accountNumber, String bankNumber,String counterpartyName) {
		super();
		this.accountNumber = accountNumber;
		this.bankNumber = bankNumber;
		this.counterpartyName = counterpartyName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public String getCounterpartyName() {
		return counterpartyName;
	}

	public void setCounterpartyName(String counterpartyName) {
		this.counterpartyName = counterpartyName;
	}

	@Override
	public void setName(String name) {
		//
	}

	@Override
	public void setSurname(String surname) {
		// 	
	}
}
