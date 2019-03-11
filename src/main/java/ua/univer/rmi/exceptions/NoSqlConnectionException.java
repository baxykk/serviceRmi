package ua.univer.rmi.exceptions;

public class NoSqlConnectionException extends RuntimeException {

	public NoSqlConnectionException() {
		super();
	}

	public NoSqlConnectionException(String message) {
		super(message);
	}	
}
