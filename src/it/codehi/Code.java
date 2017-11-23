package it.codehi;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.uniplore.*;

import java.io.*;

import java.io.IOException;

import com.ibm.icu.util.ULocale;

/*
 * @author ShengtianMin
 * @version 1.0
 * @param
 * @return
 * @throws
 */
public class Code {
	//public int intarray[];
	public static final String mysqlurl="jdbc:mysql://192.168.100.194/tpch?useUnicode=true&characterEncoding=gbk&zeroDateTimeBehavior=convertToNull";
	public static final String mysqlname="com.mysql.jdbc.Driver";
	public static final String mysqluser="root";
	public static final String mysqlpwd="mysql";
	//public static final int wei=6;
	//public static final int cc=1500000;
	//public static final String colname="O_ORDERSTATUS";
	//public static final String keyname="O_ORDERKEY";
	//public static final String tablename="orders"; 
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
 
		//new HelloButton();
//		try
//		{
//			buildReport( );
//		}
//		catch ( IOException e )
//		{
//		}
//		AddClassPrint();
//		SDKDataSourceForCloud.getDataSource("","","");
//		sumFileRows();
//		readFileTime();
	}
	
	
	public static void readFileTime() throws Exception{
		ReadFileSpeed rfs=new ReadFileSpeed();
		rfs.start("c:/RedisData/2file.dat");
		rfs.start("d:/RedisData/2file.dat");
	}
	
	// 统计指定文件目录下的文件行数
	public static void sumFileRows() throws Exception{
		SumFileRows sfr=new SumFileRows();
		sfr.Start();
	}
	
	public void sortArray(){
		System.out.println("start");
		int ds[]={9,3,1,5,4};
		for(int i=0;i<ds.length;i++){
			System.out.println(ds[i]);
		}
		for(int i=0;i<ds.length;i++){
			for(int j=i;j<ds.length;j++){
				if(ds[i]<ds[j]){
					int t=ds[j];
					ds[j]=ds[i];
					ds[i]= t;
				}
			}
		}
		for(int i=0;i<ds.length;i++){
			System.out.println(ds[i]);
		}
	}
	
	// 测试向上和向下转型
	public static void testCycle(){
		Cycle cc = new Cycle();
		cc.start();
	}
	
	// 测试组合实现状态模式
	public static void testShipShow(){
		ShipShow ss = new ShipShow();
		ss.start();
	}
	
	// 测试Super及继承特性
	public static void testExtends(){
		superApple sa = new superApple("SuperApple");
	}
	
	// 测试单例模式及静态方法创建对象
	public static void testPattern(){
		SimplePattern ss=SimplePattern.makeSimplePattern();
		ss.start();
		SimplePattern sp=SimplePattern.access();
		sp.start();
	}
	
	// 测试Stack
	public static void testStack(){
		Apple a=new Apple();
		a.testStack();
	}
	
	// 为java代码添加输出语句
	public static void AddClassPrint() throws Exception{
		ReplaceText rt=new ReplaceText();
		rt.Start();
	}
	
	//打印容器
	public static void PrintContainerStart(){
		PrintContainers pc=new PrintContainers();
		pc.start();
	}
	
//	public static void CodeConn(){
//		Statement stmt = null;
//		ResultSet rset = null;
//		ResultSet mysqlrset=null;
//		String mysqlsql=null;
//		PreparedStatement pst=null;
//		int intarray[];
//	    String arr[];
//	    String arrcode[];
//	    String arrkey[];
//	    arr = new String [cc];
//	    arrcode=new String[cc];
//	    arrkey=new String[cc];
//	  	intarray=new int[cc];
//	  	int count=0;
//		for(int i=0;i<cc;i++)
//		{
//			intarray[i]=i;
//			String tmp=Integer.toHexString(i);
//			tmp=Conv(tmp,wei);
//			arr[i]=tmp;
//		//	System.out.println(arr[i]);
//		}
//		try{
//			//mysql连接
//			Class.forName(mysqlname);
//			Connection mysqlconn=DriverManager.getConnection(mysqlurl,mysqluser,mysqlpwd);
//			mysqlsql="select DISTINCT "+colname+","+keyname+" from "+tablename+" ORDER BY "+colname+" ASC";
//    		pst=mysqlconn.prepareStatement(mysqlsql);
//    		mysqlrset=pst.executeQuery();
//    		while(mysqlrset.next()){
//    			arrcode[count]=mysqlrset.getString(1);
//    			arrkey[count]=mysqlrset.getString(2);
//    			count++;
//    		}
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
//		WriteToFile(count,arr,arrcode,arrkey);
//		System.out.println("OK");
//	}
//	
	// 测试static方法
	public static void TestStatic(){
		Incrementable sf=new Incrementable();
		sf.increment();
		System.out.println(StaticTest.i);
	}
	// 写文件
//	public static void WriteToFile(int count,String arr[],String arrcode[],String arrkey[]){
//		File file=new File("D:/"+tablename+".txt");
//		FileWriter fw=null;
//		BufferedWriter bw=null;
//		try{
//			fw=new FileWriter(file);
//			bw=new BufferedWriter(fw);
//			for(int i=0;i<count;i++)
//			{
//				bw.write(arr[i]+","+arrcode[i]+","+arrkey[i]);
//				bw.newLine();
//				bw.flush();
//			}
//		}
//		catch(Exception e){
//			e.printStackTrace();
//		}
//		finally{
//			try{
//				bw.close();
//				fw.close();
//			}
//			catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//	}
	
	// 位数补全，当s的位数低于指定位数i时，在s的高位补上0
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
	
	// 测试简单的输出语句
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
