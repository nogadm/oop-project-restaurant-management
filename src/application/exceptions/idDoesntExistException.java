package application.exceptions;

public class idDoesntExistException extends Exception {
	
	public idDoesntExistException() {
		super("System does not contain entered ID. Please enter valid input.");
	}

}
