package application.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import application.util.DishType;

public class Dish implements java.io.Serializable{
	
	private static int idCounter = Restaurant.getInstance().getDishes().keySet().size()+1;
	private int id;
	private String dishName;
	private DishType type;
	private ArrayList<Component> componenets;
	private double price;
	private int timeToMake;
	
	// constructors 
	
	public Dish(String dishName, DishType type, ArrayList<Component> componenets, int timeToMake) {
		super();
		this.id = idCounter++;
		this.dishName = dishName;
		this.type = type;
		this.componenets = componenets;
		this.timeToMake = timeToMake;
		price = calcDishPrice();
	}
	
	public Dish(int id) {
		this.id = id;
	}
	
	// getters setters
	
	public static int getIdCounter() {
		return idCounter;
	}
	public static void setIdCounter(int idCounter) {
		Dish.idCounter = idCounter;
	}
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDishName() {
		return dishName;
	}
	public void setDishName(String dishName) {
		this.dishName = dishName;
	}
	public DishType getType() {
		return type;
	}
	public void setType(DishType type) {
		this.type = type;
	}
	public List<Component> getComponenets() {
		return Collections.unmodifiableList(this.componenets);
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		if(price > 0.0)
			this.price = price;
		else
			price = 0.0;
	}
	
	
	public int getTimeToMake() {
		return timeToMake;
	}
	public void setTimeToMake(int timeToMake) {
		this.timeToMake = timeToMake;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dish other = (Dish) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return dishName;
	}
	
	
	// methods
	
	public double calcDishPrice() {
		double price = 0.0;
		for(Component c : getComponenets()) {
			price += c.getPrice();
		}
		price = price*3;
		//MyFileLogWriter.println(this+" Price is "+price);
		return price;
	}
	
	public boolean addComponent(Component c) {
		return this.componenets.add(c);
	}
	
	public boolean removeComponent(Component c) {
		return this.componenets.remove(c);
	}
}
