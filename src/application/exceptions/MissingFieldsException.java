package application.exceptions;


public class MissingFieldsException extends Exception {

	public MissingFieldsException() {
		super("Please fill all fields.");
		
	}
	
}
