package ua.univer.rmi.model.entity;

public class Card {
	private int id;
	private long cardNumber;
	private int userId;
	private int validTillMonth;
	private int validTillYear;
	private boolean isBlocked;

	public Card() {
		this(0,111122223333L,0,0,0,false);
	}

	public Card(int id, long cardNumber, int userId, int validTillMonth, int validTillYear, boolean status) {
		this.id = id;
		this.cardNumber = cardNumber;
		this.userId = userId;
		this.validTillMonth = validTillMonth;
		this.validTillYear = validTillYear;
		this.isBlocked = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
	
	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlockedStatus(boolean block) {
		this.isBlocked = block;
	}
}
