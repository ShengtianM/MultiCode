package org.uniplore.tools;

import java.io.BufferedReader;
import java.io.FileReader;

public class ReadFileContentToString {
	
	public ReadFileContentToString() {
	}
	
	public static String start(String path){
		return readByBuffer(path);
	}
	
	
	public static String readByBuffer(String path){
		StringBuffer sb = new StringBuffer();
		try {
		BufferedReader in = new BufferedReader(new FileReader(path));
		String s=null;
		//s=in.readLine();
		while((s=in.readLine())!=null){
			sb.append(s).append("\r\n");
		}
		 in.close();
		 return sb.toString();
		}catch(Exception e) {
			return "-1";
		}
		
	}

}
