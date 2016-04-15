package org.uniplore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AppleList {
	//测试ArrayList
	public void start(){
		ArrayList<Apple> apples=new ArrayList<Apple>();
		for(int i=0;i<3;i++)
			apples.add(new Apple());
		for(int i=0;i<apples.size();i++)
				System.out.println(apples.get(i).id());
		for(Apple c:apples){
				System.out.println(c.id());
			}
		
		Collection<Integer> c= new ArrayList<Integer>();
		for(int i=0;i<10;i++){
			c.add(i);			
		}
		for(Integer i: c){
			System.out.println(i+",");
		}
		
	}
	//测试集合
	public void test(){
		Collection<Integer> collection = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5));
		Integer[] moreInts={6,7,8,9,10};
		collection.addAll(Arrays.asList(moreInts));
		Collections.addAll(collection, 11,12,13,14,15);
		Collections.addAll(collection, moreInts);
		List<Integer> list=Arrays.asList(16,17,18,19,10);
		list.set(1, 99);
		list.add(21);
	}
}
