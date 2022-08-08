package application.comparators;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;

import application.model.Component;

public class PopularComparator implements Comparator<Component>, Serializable {
	
	HashMap<Component, Integer> componentsandAmount;
	
	@Override
	public int compare(Component o1, Component o2) {
		int result = -1 * componentsandAmount.get(o1).compareTo(componentsandAmount.get(o2));
		if(result !=0)
			return result;
		if(o1.getId() > o2.getId())
			return -1;
		return 1;
	}
	
	public void getInfoForComparator(HashMap<Component, Integer> info) {
		componentsandAmount = new HashMap<>();
		componentsandAmount = info;
		
		
	}
	
}
