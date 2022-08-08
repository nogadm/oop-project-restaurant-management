package application.exceptions;

public class IncorrectPasswordException extends Exception {

	public IncorrectPasswordException() {
		super("Password is incorrect, Please try again.");
	}
	
}
