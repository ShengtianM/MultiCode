package org.uniplore;

public class Dog {
	public String name;
	public String says;
	//���Դ������ĺ���
    public void setname(String name){
    	this.name=name;
    }
    public void setsays(String n){
    	this.says=n;    	
    }
    //���Բ�������
    public void dogshow(){
    	System.out.println("Name is"+name+",says"+says);
    }
    //���Ժ�������
    public void bark(){
    	System.out.println("barking");
    }
    //���Ժ�������
    public void bark(String ss){
    	System.out.println("howling");
    }
}
