package org.uniplore.http;


import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
 
public class DownloadPicFromURL {
	
	private final static int MIN_X=714;
	private final static int MAX_X=739;
	private final static int MIN_Y=166;
	private final static int MAX_Y=192;
	private final static int Z=12;
    public static void main(String[] args) {
    	Random rand=new Random(47);
        String url = "http://online4.map.bdimg.com/tile/?qt=vtile&x=86&y=23&z=9&styles=pl&scaler=1&udt=20190815";
        String path="d:/test/pic.jpg";
        String dirPath = null;
        for(int i=MIN_X;i<=MAX_X;i++){
        	for(int j=MIN_Y;j<=MAX_Y;j++){
        		url="http://online"+Math.abs(rand.nextInt()%5)+".map.bdimg.com/tile/?qt=vtile&x="
        				+i+"&y="+j+"&z="+Z+"&styles=pl&scaler=1&udt=20190815";
        		
        		path="d:/test/"+Z+"/"+i+"/"+j+".jpg";
        		dirPath = "d:/test/"+Z+"/"+i+"/";
        		File t = new File(dirPath);
        		if(!t.exists()){
        			t.mkdir();
        		}
        		downloadPicture(url,path);
        	}
        }
        
        
    }
    
    
    //链接url下载图片
    private static void downloadPicture(String urlList,String path) {
        URL url = null;
        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
 
            File t = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(t);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
 
            byte[] buffer = new byte[1024];
            int length;
 
            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
