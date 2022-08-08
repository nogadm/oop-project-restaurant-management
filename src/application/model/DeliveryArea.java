package application.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


import application.util.Neighberhood;

public class DeliveryArea implements java.io.Serializable{
	private static int idCounter = Restaurant.getInstance().getAreas().keySet().size()+1;
	private int id;
	private String areaName;
	private HashSet<DeliveryPerson> delPersons;
	private HashSet<Delivery> delivers;
	private HashSet<Neighberhood> neighberhoods;
	private final int deliverTime;
	
	public DeliveryArea(String areaName, HashSet<Neighberhood> neighberhoods, int deliverTime) {
		super();
		this.id = idCounter++;
		this.areaName = areaName;
		this.neighberhoods = neighberhoods;
		this.deliverTime = deliverTime;
		delPersons = new HashSet<>();
		delivers = new HashSet<>();
	}
	
	public DeliveryArea(int id) {
		this.id = id;
		this.deliverTime = 0;
	}

	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		DeliveryArea.idCounter = idCounter;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Set<DeliveryPerson> getDelPersons() {
		return Collections.unmodifiableSet(delPersons);
	}

	public Set<Delivery> getDelivers() {
		return Collections.unmodifiableSet(delivers);
	}

	public Set<Neighberhood> getNeighberhoods() {
		return Collections.unmodifiableSet(neighberhoods);
	}

	public int getDeliverTime() {
		return deliverTime;
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
		DeliveryArea other = (DeliveryArea) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return areaName;
	}
	
	//methods
	public boolean addDelPerson(DeliveryPerson dp) {
		return delPersons.add(dp);
	}
	
	public boolean removeDelPerson(DeliveryPerson dp) {
		return delPersons.remove(dp);
	}
	
	public boolean addDelivery(Delivery d) {
		return delivers.add(d);
	}
	
	public boolean removeDelivery(Delivery d) {
		return delivers.remove(d);
	}
	
	public boolean addNeighberhood(Neighberhood neighberhood) {
		return neighberhoods.add(neighberhood);
	}
	
	public boolean removeNeighberhood(Neighberhood neighberhood) {
		return neighberhoods.remove(neighberhood);
	}
}
