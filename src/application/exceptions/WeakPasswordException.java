package application.exceptions;

public class WeakPasswordException extends Exception {

	public WeakPasswordException() {
		super("Password is too weak, please choose "
				+ "\na password with 8 or more characters.");
	}
	
}
