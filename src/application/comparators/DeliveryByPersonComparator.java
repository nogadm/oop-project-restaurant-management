package application.comparators;

import java.io.Serializable;
import java.util.Comparator;

import application.model.Delivery;

public class DeliveryByPersonComparator implements Comparator<Delivery>, Serializable {

	
	@Override
	public int compare(Delivery o1, Delivery o2) {
		if(o1.getDeliveredDate().getDayOfMonth() > o2.getDeliveredDate().getDayOfMonth())
			return 1;
		if(o1.getDeliveredDate().getDayOfMonth() < o2.getDeliveredDate().getDayOfMonth())
			return -1;
		return o1.getId()>o2.getId() ? 1 :-1;
	}
	
}
