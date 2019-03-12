package ua.univer.rmi.exceptions;

public class FailedSqlTransactionException extends RuntimeException {

	public FailedSqlTransactionException() {
		super();
	}

	public FailedSqlTransactionException(String message) {
		super(message);
	}	
}
