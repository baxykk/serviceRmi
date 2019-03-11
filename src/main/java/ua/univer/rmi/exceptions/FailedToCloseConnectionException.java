package ua.univer.rmi.exceptions;

public class FailedToCloseConnectionException extends RuntimeException {

	public FailedToCloseConnectionException() {
		super();
	}

	public FailedToCloseConnectionException(String message) {
		super(message);
	}	
}
