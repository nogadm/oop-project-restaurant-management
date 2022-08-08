package application.comparators;

import java.io.Serializable;
import java.util.Comparator;
import application.model.Dish;


public class ProfitComparator implements Comparator<Dish>,  Serializable {
	

	@Override
	public int compare(Dish o1, Dish o2) {
		if((o2.getPrice()/o2.getTimeToMake())>(o1.getPrice()/o1.getTimeToMake()))
			return 1;
		else if((o2.getPrice()/o2.getTimeToMake())<(o1.getPrice()/o1.getTimeToMake()))
			return -1;
		return -1*o1.getId().compareTo(o2.getId());
	}

}
