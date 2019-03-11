package ua.univer.rmi.dto;

import java.io.Serializable;

public class CardDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long cardNumber;
	private boolean isBlocked;
	private int validTillMonth;
	private int validTillYear;

	public Long getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(Long cardNumber) {
		this.cardNumber = cardNumber;
	}	
	public boolean isBlocked() {
		return isBlocked;
	}
	public void setBlockedStatus(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}
	public int getValidTillMonth() {
		return validTillMonth;
	}
	public void setValidTillMonth(int month) {
		this.validTillMonth = month;
	}
	public int getValidTillYear() {
		return validTillYear;
	}
	public void setValidTillYear(int year) {
		this.validTillYear = year;
	}
	
}
