package org.uniplore;
/*
 * 用于验证Java是否执行了默认初始化
 * 用于验证无论创建了多少个对象，该类中的某个特定的static域只有一个实例
 */
public class TypeTest {
	static int count=0;  //用来计算生成了多少个对象
	int il;  //参数定义，用于验证Java是否执行了默认初始化
	char cchar;
	//初始化函数
	public TypeTest(){
		count++;
	}
	// 返回特定变量函数
	public int getil(){
		return il;
	}
	public char getcchar(){
	    return cchar;
	}
	public int getcount(){
		return count;
	}

}
