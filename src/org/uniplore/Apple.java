package org.uniplore;

import java.util.ArrayList;
import java.util.List;

public class Apple {
	private static long counter;
	private final long id=counter++;
	public long id(){
		return id;
	}
	
	public List<Apple> arrayList(int c){
		List<Apple> list =new ArrayList<Apple>();
		for(int i=0;i<c;i++){
			list.add(new Apple());
		}
		return list;
	}

}
