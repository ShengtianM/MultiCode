package it.codehi;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.uniplore.*;
import org.uniplore.tools.Des3Util;
import org.uniplore.tools.ReadFileSpeed;
import org.uniplore.tools.ReplaceText;

import com.uniplore.image.ProcessPng;

/*
 * @author ShengtianMin
 * @version 1.0
 * @param
 * @return
 * @throws
 */
public class Code {
	public static final String mysqlurl="jdbc:mysql://192.168.100.194/tpch?useUnicode=true&characterEncoding=gbk&zeroDateTimeBehavior=convertToNull";
	public static final String mysqlname="com.mysql.jdbc.Driver";
	public static final String mysqluser="root";
	public static final String mysqlpwd="mysql";
	
	public static void main(String[] args) throws Exception {
		String filePath = "‪52091_27702.png";
		String outPath = "52091_27702new.png";
		ProcessPng.repalceColor(filePath, outPath);
	}
	
	
	public static void readFileTime() throws Exception{
		ReadFileSpeed rfs=new ReadFileSpeed();
		rfs.start("c:/RedisData/2file.dat");
		rfs.start("d:/RedisData/2file.dat");
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
	

	// 写文件
	public static void WriteToFile(String fileName,int count,String arr[],String arrcode[],String arrkey[]){
		File file=new File("D:/"+fileName+".txt");
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
		}
	}
	
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
	
}
