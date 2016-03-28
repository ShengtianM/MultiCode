package it.codehi;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.uniplore.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.*;
import com.mysql.jdbc.*;
/*
 * @author ShengtianMin
 * @version 1.0
 * @param
 * @return
 * @throws
 */
public class Code {
	//public int intarray[];
    /*  编码变量定义
	public static final String mysqlurl="jdbc:mysql://192.168.100.194/tpch?useUnicode=true&characterEncoding=gbk&zeroDateTimeBehavior=convertToNull";
	public static final String mysqlname="com.mysql.jdbc.Driver";
	public static final String mysqluser="root";
	public static final String mysqlpwd="mysql";
	public static final int wei=6;
	public static final int cc=1500000;
	public static final String colname="O_ORDERSTATUS";
	public static final String keyname="O_ORDERKEY";
	public static final String tablename="orders"; 
	*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//TypeTest tt=new TypeTest();
		//TypeTest tt2=new TypeTest();
		//System.out.println(tt.getil()+"'"+tt.getcchar()+"'"+tt.getcount());
		//ATypeName a=new ATypeName();
		//System.out.println(a.storage("Test"));
		//DataOnly doy=new DataOnly();
		//doy.i=47;
		//doy.d=1.1;
		//doy.b=false;
		//doy.showBaozhuang();
		/*编码主函数
		Statement stmt = null;
		ResultSet rset = null;
		ResultSet mysqlrset=null;
		String mysqlsql=null;
		PreparedStatement pst=null;
		int intarray[];
	    String arr[];
	    String arrcode[];
	    String arrkey[];
	    arr = new String [cc];
	    arrcode=new String[cc];
	    arrkey=new String[cc];
	  	intarray=new int[cc];
	  	int count=0;
		for(int i=0;i<cc;i++)
		{
			intarray[i]=i;
			String tmp=Integer.toHexString(i);
			tmp=Conv(tmp,wei);
			arr[i]=tmp;
		//	System.out.println(arr[i]);
		}
		try{
			//mysql连接
			Class.forName(mysqlname);
			Connection mysqlconn=DriverManager.getConnection(mysqlurl,mysqluser,mysqlpwd);
			mysqlsql="select DISTINCT "+colname+","+keyname+" from "+tablename+" ORDER BY "+colname+" ASC";
    		pst=mysqlconn.prepareStatement(mysqlsql);
    		mysqlrset=pst.executeQuery();
    		while(mysqlrset.next()){
    			arrcode[count]=mysqlrset.getString(1);
    			arrkey[count]=mysqlrset.getString(2);
    			count++;
    		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		WriteToFile(count,arr,arrcode,arrkey);
		System.out.println("OK");
		*/
        //TestStatic();
       // TestPrint();
       // TestPractice2();
		TestPractice3();
	}
	
	//测试static关键字
	public static void TestStatic(){
		Incrementable sf=new Incrementable();
		sf.increment();
		System.out.println(StaticTest.i);
	}
	//写入文件
	public static void WriteToFile(int count,String arr[],String arrcode[],String arrkey[]){
		/*
		File file=new File("D:/"+tablename+".txt");
		FileWriter fw=null;
		BufferedWriter bw=null;
		try{
			fw=new FileWriter(file);
			bw=new BufferedWriter(fw);
			for(int i=0;i<count;i++)
			{
				bw.write(arr[i]+","+arrcode[i]+","+arrkey[i]);
				bw.newLine();
				bw.flush();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				bw.close();
				fw.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}*/
	}
	//转化为十六进制
	public static String Conv(String s,int i){
		int sub=0;
		int len=s.length();
		if (len<i)
		{
			sub=i-len;
		}
		for(int j=0;j<sub;j++)
		{
			s='0'+s;
		}
		return s;
	} 
	// 测试输出
	public static void TestPrint(){
		PrintTest pt=new PrintTest();
		pt.printsimple();
	}
	public static void TestPractice2(){
		PracticeTwo pt2=new PracticeTwo();
		pt2.show();
		pt2.dogshow();
	}
	public static void TestPractice3(){
		PracticeThree pt2=new PracticeThree();
		pt2.show();
		pt2.testthis();
	}

}
