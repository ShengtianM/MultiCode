package org.uniplore;

public class Cycle {

	public Cycle() {
		// TODO Auto-generated constructor stub
	}
	
	public void banlance(){
		System.out.println("Cycle");
	}
	public void start(){
		// Tricycle未实现banlance方法
	    // Unicycle实现了banlance方法
		// 向上转型
		Cycle[] ac={new Tricycle(),new Unicycle()};
		ac[0].banlance();
		ac[1].banlance();
		// 向下转型
		ac[0]=(Cycle) ac[0];
		ac[0].banlance();
		ac[1]=(Cycle) ac[1];
		ac[1].banlance();
	}

}
