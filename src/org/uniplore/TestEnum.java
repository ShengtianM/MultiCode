package org.uniplore;

public class TestEnum {
	// 枚举对象
	public enum Spiciness {
		NOT,MILD,MEDIUM,HOT,FLAMING}

	public void start(){
		Spiciness howHot = Spiciness.MEDIUM;
		System.out.println(howHot);
	}
	
	public TestEnum() {
		// TODO Auto-generated constructor stub
		
	}

}
