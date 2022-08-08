package application.model;

import java.time.LocalDate;

import application.util.Gender;
import application.util.Expertise;

public class Cook extends Person implements java.io.Serializable{
	private static int idCounter = Restaurant.getInstance().getCooks().keySet().size()+1;
	private Expertise expert;
	private boolean isChef;
	
	public Cook(String firstName, String lastName, LocalDate birthDay, Gender gender, Expertise expert,
			boolean isChef) {
		super(idCounter++, firstName, lastName, birthDay, gender);
		this.expert = expert;
		this.isChef = isChef;
	}
	
	public Cook(int id) {
		super(id);
	}

	public Expertise getExpert() {
		return expert;
	}

	public void setExpert(Expertise expert) {
		this.expert = expert;
	}

	public boolean isChef() {
		return isChef;
	}

	public void setChef(boolean isChef) {
		this.isChef = isChef;
	}
	

	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		Cook.idCounter = idCounter;
	}

	@Override
	public String toString() {
		return super.getFirstName() + " " + super.getLastName();
	}
}
