package application.model;

public class Component implements Comparable<Component>, java.io.Serializable{
	private static int idCounter = Restaurant.getInstance().getComponenets().keySet().size()+1;
	private Integer id;
	private String componentName;
	private boolean hasLactose;
	private boolean hasGluten;
	private Double price;
	
	public Component(String componentName, boolean hasLactose, boolean hasGluten, double price) {
		super();
		this.id = idCounter++;
		this.componentName = componentName;
		this.hasLactose = hasLactose;
		this.hasGluten = hasGluten;
		setPrice(price);
	}
	
	public Component(int id) {
		this.id = id;
	}

	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		Component.idCounter = idCounter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public boolean isHasLactose() {
		return hasLactose;
	}

	public void setHasLactose(boolean hasLactose) {
		this.hasLactose = hasLactose;
	}

	public boolean isHasGluten() {
		return hasGluten;
	}

	public void setHasGluten(boolean hasGluten) {
		this.hasGluten = hasGluten;
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

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Component other = (Component) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return componentName;
	}


	@Override
	public int compareTo(Component o) {
		if(this.price.compareTo(o.getPrice())!=0)
			return this.price.compareTo(o.getPrice());
		return this.id.compareTo(o.getId());
	}
}
