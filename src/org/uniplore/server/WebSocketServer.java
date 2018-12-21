package org.uniplore.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class WebSocketServer {

	public WebSocketServer() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args){
		try {
			runServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void runServer() throws IOException {  
        ServerSocket ss=new ServerSocket(10240);//服务端监听3574这个端口  
        Socket clientSocket=null;//服务端接受客户端的连接          
          
  
        while (true) { 
        	clientSocket=ss.accept();
        	System.out.println(clientSocket.getLocalSocketAddress().toString());
        	InputStream in=clientSocket.getInputStream();//得到客户端的输入流，为了得到客户端传来的数据  
            OutputStream out =clientSocket.getOutputStream();//得到客户端的输出流，为了向客户端输出数据  
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));  
            PrintWriter bufferedWriter=new PrintWriter(out,true);
            String line=null;  
            bufferedWriter.println(new Date()+",Hello,I'm Server!");//向客户端输出数据  
            line=bufferedReader.readLine();//读取客户端传来的数据  
            //if(line==null)  
            //    break;  
            System.out.println("客户端说："+line);//打印客户端传来的数据  
  
        }  
    }  

}
