package org.uniplore;
/*
 * ������֤Java�Ƿ�ִ����Ĭ�ϳ�ʼ��
 * ������֤���۴����˶��ٸ����󣬸����е�ĳ���ض���static��ֻ��һ��ʵ��
 */
public class TypeTest {
	static int count=0;  //�������������˶��ٸ�����
	int il;  //�������壬������֤Java�Ƿ�ִ����Ĭ�ϳ�ʼ��
	char cchar;
	//��ʼ������
	public TypeTest(){
		count++;
	}
	// �����ض���������
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
