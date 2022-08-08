package application.exceptions;

public class PasswordsDoNotMatchException extends Exception {

	public PasswordsDoNotMatchException() {
		super("Passwords don't match, please verify new password again.");
	}
	
}
