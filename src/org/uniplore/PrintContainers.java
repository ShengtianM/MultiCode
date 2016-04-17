package org.uniplore;

import java.util.*;
import java.util.Collection;

public class PrintContainers {
	//预设字符链表
	public static Collection<String> slist=new ArrayList<String>();
	public PrintContainers(){
		String[] s={"aaa","bbbb","cccc","ddd"};
		slist.addAll(Arrays.asList(s));
	}
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
	
	//测试生成器类
	
	static String next(){
		String s=null;
		for(String ss:slist){
			s=ss;
		}
		return s;
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
