package org.uniplore;

import java.util.*;
import java.util.Collection;

public class PrintContainers {
	//测试集合
	static Collection fill(Collection<String> collection){
		collection.add("rat");
		collection.add("cat");
		collection.add("dog");
		collection.add("dog");
		return collection;		
	}
	//测试Map
	static Map fill(Map<String,String> map){
		map.put("rat","Fuzzy");
		map.put("cat","Rags");
		map.put("dog","Bosco");
		map.put("dog","Spot");
		return map;
	}
	
	public static void start(){
		System.out.println(fill(new ArrayList<String>()));
		System.out.println(fill(new LinkedList<String>()));
		System.out.println(fill(new HashSet<String>()));
		System.out.println(fill(new TreeSet<String>()));
		System.out.println(fill(new LinkedHashSet<String>()));
		System.out.println(fill(new HashMap<String,String>()));
		System.out.println(fill(new TreeMap<String,String>()));
		System.out.println(fill(new LinkedHashMap<String,String>()));
	}

}
