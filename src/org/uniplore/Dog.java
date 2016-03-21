package org.uniplore;

public class Dog {
	public String name;
	public String says;
    public void setname(String name){
    	this.name=name;
    }
    public void setsays(String n){
    	this.says=n;    	
    }
    public void dogshow(){
    	System.out.println("Name is"+name+",says"+says);
    }
}
