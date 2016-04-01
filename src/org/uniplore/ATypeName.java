package org.uniplore;
/*
 * 用于测试类型相关的定义
 */
public class ATypeName {
	public ATypeName(){
		
	}
	//测试返回类型为int
	public int storage(String s){
		return s.length()*2;
	}
	//测试返回类型为boolean
	public boolean flag(){
		return true;
	}
	//测试返回类型为double
	public double naturalLogBase(){
		return 2.718;
	}
	//测试空返回类型
	public void nothing(){
		return;
	}
	//测试空返回类型
	public void nothing2(){
		
	}

}
