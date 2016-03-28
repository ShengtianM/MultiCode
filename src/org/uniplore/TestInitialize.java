package org.uniplore;

public class TestInitialize {
	public String ps;
	public String psn="Test create";
	TestInitialize(){
		System.out.println("TestInitialize create Success!");
	}
	TestInitialize(String ss){
		this();
		System.out.println("TestInitialize create Success!"+ss);
	}

}
