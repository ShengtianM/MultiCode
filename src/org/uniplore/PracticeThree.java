package org.uniplore;

public class PracticeThree {
	public void show(){
		TestInitialize ti=new TestInitialize();
		System.out.println("Java�Զ���ʼ��String"+ti.ps);
		System.out.println("Java�Զ�����String"+ti.psn);
		TestInitialize ti1=new TestInitialize();
		TestInitialize ti2=new TestInitialize("ti2");
	}
	public void testthis(){
		show();
		this.show();
	}

}
