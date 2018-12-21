package org.uniplore.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

public class GetSystemInfo {

	public GetSystemInfo() {
		// TODO Auto-generated constructor stub
	}
	
	//获取本机IP地址
	public static String getIpAddress() throws UnknownHostException{
		InetAddress address= InetAddress.getLocalHost();
		return address.getHostAddress();
	}
	
	public static String getMACAddress(){  
		  
        String address = "";  
  
        String os = System.getProperty("os.name");  
        String osUser=System.getProperty("user.name");  
        if (os != null && os.startsWith("Windows")) {  
  
            try {  
  
                String command = "cmd.exe /c ipconfig /all";  
                  
                Process p = Runtime.getRuntime().exec(command);  
  
                BufferedReader br =new BufferedReader(new InputStreamReader(p.getInputStream()));  
  
                String line;  
  
                while ((line = br.readLine()) != null) {  
  
                    if (line.indexOf("Physical Address") > 0) {  
  
                        int index = line.indexOf(":");  
  
                        index += 2;  
  
                        address = line.substring(index);  
  
                        break;  
  
                    }  
  
                }  
  
                br.close();  
  
                return address.trim();  
  
            }  
  
            catch (IOException e) {  
            }  
  
        }  
        return address;  
  
    } 
	
	public static String getProperty(){
		Properties props=System.getProperties();
		// 操作系统的架构
		String osArch=props.getProperty("os.arch");
		// 操作系统版本
		String osVersion=props.getProperty("os.version");
		return osVersion;
	}

}
