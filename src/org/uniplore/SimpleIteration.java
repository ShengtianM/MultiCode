package org.uniplore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SimpleIteration {

	public SimpleIteration() {
		
	}
	
	public void start(){
		List<String> apples=new ArrayList<String>();
		Iterator<String> it=apples.iterator();
		while(it.hasNext()){
			String a=it.next();
			System.out.println(a);
		}
		it=apples.iterator();
		for(int i=0; i<6; i++){
			it.next();
			it.remove();  //移除由next产生的最后一个元素
		}
		System.out.println(apples);
	}
	
	//listIterator可以双向移动的迭代器，只能用于各种List类的访问
	public void listIteration(){
		List<String> apples=new ArrayList<String>();
		ListIterator<String> lit=apples.listIterator();
		lit.hasNext();       // 是否存在下一个元素
		lit.next();
		lit.hasPrevious();   // 是否存在前一个元素
		lit.previous();
		lit.set(new String("11"));// 替换访问的最后一个元素		
	}

}
