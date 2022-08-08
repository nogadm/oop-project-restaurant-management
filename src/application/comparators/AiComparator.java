package application.comparators;

import java.io.Serializable;
import java.util.Comparator;

import application.model.Delivery;
import application.model.ExpressDelivery;
import application.model.RegularDelivery;


public class AiComparator implements Comparator<Delivery>, Serializable {

	
	@Override
	public int compare(Delivery o1, Delivery o2) {
		if(o2 instanceof RegularDelivery && o1 instanceof ExpressDelivery)
			return -1;
		if(o2 instanceof ExpressDelivery && o1 instanceof RegularDelivery)
			return 1;
		return o2.getId()>o1.getId() ? -1: 1;
	}
	
}
