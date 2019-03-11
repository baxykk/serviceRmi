package ua.univer.rmi.exceptions;

public class ConnectionPoolNotClosedException extends RuntimeException {

	public ConnectionPoolNotClosedException() {
		super();
	}

	public ConnectionPoolNotClosedException(String message) {
		super(message);
	}	
}
