package org.uniplore;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Apple {
	private static long counter;
	private final long id=counter++;
	public long id(){
		return id;
	}
	
	// 构建简单的对象列表
	public static List<Apple> arrayList(int c){
		List<Apple> list =new ArrayList<Apple>();
		for(int i=0;i<c;i++){
			list.add(new Apple());
		}
		return list;
	}
	
	// LinkedList插入时比ArrayList更高效
	public static LinkedList<Apple> linkedList(int c){
		LinkedList<Apple> llist= new LinkedList<Apple>();
		for(int i=0;i<c;i++){
			if(i%2==0)
				llist.add(llist.getFirst()); 
			else
				llist.add(llist.getLast());
		}
		// 如果列表为空则返回null
		llist.peek();   
		return llist;
	}
	
	//待添加Stack
	public static Stack<Apple> stack(){
		Stack<Apple> st = new Stack<Apple>();
		st.push(new Apple());
		return st;
	}
	
	public void testStack(){
		String tests=new String("+U+n+c---+e+r+t--");
		Stack<String> cs = new Stack<String>();
		char c;
		char temp;
		for(int i=0;i<tests.length();i++){
			c=tests.charAt(i);
			switch(c){
			case '+':temp=tests.charAt(i+1);
			i++;
			cs.push(temp+"s");
			break;
			case '-':System.out.println(cs.pop());
			break;
			
			}
			
		}
	}

}
