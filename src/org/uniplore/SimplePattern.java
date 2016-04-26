package org.uniplore;

public class SimplePattern {
	
	private static SimplePattern sp = new SimplePattern();

	private SimplePattern() {
		// TODO Auto-generated constructor stub
	}
	// 单例模式
	public static SimplePattern access(){
		return sp;
	}
	
	// 通过静态方法创建对象
	public static SimplePattern makeSimplePattern(){
		return new SimplePattern();
	}
	
	public void start(){
		System.out.println(this.equals(sp));
	}
	

}
