package application.comparators;

import java.io.Serializable;
import java.util.Comparator;


import application.model.Order;


public class OrderInDeliveryComparator implements Comparator<Order>, Serializable {

	
	@Override
	public int compare(Order o1, Order o2) {
		return o1.getDelivery().getDeliveredDate().compareTo(o2.getDelivery().getDeliveredDate());
	}
	
}
