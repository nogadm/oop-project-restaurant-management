package application.exceptions;

public class IllegalCustomerException extends Exception {

	public IllegalCustomerException() {
		super("The restaurant is in conflict with this customer so this customer does not will order a new order!");
	}

	
}
