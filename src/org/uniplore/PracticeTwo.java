package org.uniplore;

public class PracticeTwo {
	public void show(){
	TestAssignment ta1= new TestAssignment();
	ta1.f1=1;
	TestAssignment ta2= new TestAssignment();
	ta2.f1=2;
	ta1=ta2;
	System.out.println("直接对象赋值给对象ta1.f1="+ta1.f1+"ta2.f1="+ta2.f1);	
	}
	public void dogshow(){
		Dog dog1=new Dog();
		dog1.setname("spot");
		dog1.setsays("Ruff!");
		Dog dog2=new Dog();
		dog2.setname("scryffy");
		dog2.setsays("Wurf!");
		dog1.dogshow();
		dog2.dogshow();
		Dog dog3=new Dog();
		dog3=dog1;
		System.out.println((dog3.name=="spot")+""+(dog3.equals(dog1)));
	}
}
