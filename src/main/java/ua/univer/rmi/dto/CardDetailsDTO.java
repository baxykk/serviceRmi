package ua.univer.rmi.dto;

public class CardDetailsDTO implements DetailsDTO {	
	private static final long serialVersionUID = 1L;
	private String cardNumber;
	private String name;
	private String surname;
	private int validTillMonth;
	private int validTillYear;
	
	public CardDetailsDTO() {
		this("", "", "", 0, 0);
	}
	
	public CardDetailsDTO(String cardNumber, String name, String surname, int validTillMonth, int validTillYear) {
		super();
		this.cardNumber = cardNumber;
		this.name = name;
		this.surname = surname;
		this.validTillMonth = validTillMonth;
		this.validTillYear = validTillYear;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	@Override
	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getValidTillMonth() {
		return validTillMonth;
	}

	public void setValidTillMonth(int validTillMonth) {
		this.validTillMonth = validTillMonth;
	}

	public int getValidTillYear() {
		return validTillYear;
	}

	public void setValidTillYear(int validTillYear) {
		this.validTillYear = validTillYear;
	}
	
}
