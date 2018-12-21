package org.uniplore.tools;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadFileSpeed {
	
	//private static String filePath="e:/RedisData/1file";
    private static List<String> tl=new ArrayList<String>();
    private static List<String> tc=new ArrayList<String>();
    private static String dir="d:/RedisData/";
   // private int multi=100;
	public ReadFileSpeed() {
		// TODO Auto-generated constructor stub
	}
	
	public void start(String path)throws Exception{
		readByBuffer(path);
		//readByFileIn(path);
		//readByMemory(path);
		//writeByBuffer(path);
		writeByByte(path);
	}
	
	public void readByFileIn(String path) throws Exception{
		tl.clear();
		long startTime=System.currentTimeMillis();
		InputStream ips=new FileInputStream(new File(path));
		BufferedReader br=new BufferedReader(new InputStreamReader(ips));
		String s=null;
		while((s=br.readLine())!=null){
			tl.add(s);
		}
		br.close();
		ips.close();
		long endTime=System.currentTimeMillis();
		System.out.println("Read:"+path+":timecost:"+(endTime-startTime));
	}
	
	public void readByBuffer(String path)throws Exception{
		tl.clear();
		long startTime=System.currentTimeMillis();
		//for(int i=0;i<multi;i++){
		BufferedReader in = new BufferedReader(new FileReader(path));
		String s=null;
		while((s=in.readLine())!=null){
			tl.add(s);
		}
		in.close();
		//}
		long endTime=System.currentTimeMillis();
		System.out.println("Read:"+path+":timecost:"+(endTime-startTime));
	}
	
	
	public void readByMemory(String path)throws Exception{
		long startTime=System.currentTimeMillis();
		List<String> fl=Files.readAllLines(new File(path).toPath(), Charset.defaultCharset());
		//FileUtils.readLines(new File(path));
	    tc.addAll(fl);
		long endTime=System.currentTimeMillis();
		System.out.println("Read By Memory:"+path+":timecost:"+(endTime-startTime));
	}
	
	public void readByScanner(String path)throws Exception{
		long startTime=System.currentTimeMillis();
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
		    inputStream = new FileInputStream(path);
		    sc = new Scanner(inputStream, "UTF-8");
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();
		        tl.add(line);
		        // System.out.println(line);
		    }
		    // note that Scanner suppresses exceptions
		    if (sc.ioException() != null) {
		        throw sc.ioException();
		    }
		} finally {
		    if (inputStream != null) {
		        inputStream.close();
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
		long endTime=System.currentTimeMillis();
		System.out.println("Read By Scanner:"+path+":timecost:"+(endTime-startTime));
	}
	
	
	public void writeByBuffer(String path)throws Exception{
		long startTime=System.currentTimeMillis();
		File file=new File(path+"1");
		//file.createNewFile();
		//File file=File.createTempFile(path+"w", ".temp", new File(dir));
		//OutputStream os = new BufferedOutputStream( new FileOutputStream( file ), 32768 );
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
        int i=1;
		//for(int m=0;m<10;m++){
		for(String ss:tl){
			bw.write(ss);
			bw.newLine();
			//os.write(ss.getBytes());
			if(i%4096==0){
			bw.flush();
			}
			i++;				        	
		}
		//}
		//os.close();
		bw.close();
		fw.close();
		long endTime=System.currentTimeMillis();
		System.out.println("Write:"+path+":timecost:"+(endTime-startTime));
	}
	
	public void writeByByte(String path)throws Exception{
		long startTime=System.currentTimeMillis();
	
		//file.createNewFile();
		File file=File.createTempFile(path+"w", ".temp", new File(dir));
		OutputStream os = new BufferedOutputStream( new FileOutputStream( file ), 32768 );
		//FileWriter fw = new FileWriter(file);
		//BufferedWriter bw = new BufferedWriter(fw);
        int i=1;
		//for(int m=0;m<10;m++){
		for(String ss:tl){
			//bw.write(ss);
			//bw.newLine();
			os.write(ss.getBytes());
			//if(i%4096==0){
			//bw.flush();
			//}
			//i++;				        	
		}
		//}
		os.close();
		//bw.close();
		//fw.close();
		long endTime=System.currentTimeMillis();
		System.out.println("Write:"+path+":timecost:"+(endTime-startTime));
	}

}
