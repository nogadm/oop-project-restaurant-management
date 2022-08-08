package application.exceptions;

public class ConvertToExpressException extends Exception {

	public ConvertToExpressException() {
		super("This regular delivery contain one order, please replace it to express delivery");
	}
	
}
