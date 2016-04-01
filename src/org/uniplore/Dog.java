package org.uniplore;

public class Dog {
	public String name;
	public String says;
	//测试带参数的函数
    public void setname(String name){
    	this.name=name;
    }
    public void setsays(String n){
    	this.says=n;    	
    }
    //测试参数传递
    public void dogshow(){
    	System.out.println("Name is"+name+",says"+says);
    }
    //测试函数重载
    public void bark(){
    	System.out.println("barking");
    }
    //测试函数重载
    public void bark(String ss){
    	System.out.println("howling");
    }
}
