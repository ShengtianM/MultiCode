package org.uniplore;

import java.util.Iterator;
import java.util.List;

public class SimpleIteration {

	public SimpleIteration() {
		// TODO Auto-generated constructor stub
		
	}
	
	public void start(){
		List<Apple> apples=Apple.arrayList(12);
		Iterator<Apple> it=apples.iterator();
		while(it.hasNext()){
			Apple a=it.next();
			System.out.println(a.id());
		}
		it=apples.iterator();
		for(int i=0; i<6; i++){
			it.next();
			it.remove();  //移除由next产生的最后一个元素
		}
		System.out.println(apples);
	}

}
