package org.uniplore.http;


import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
 
public class DownloadPicFromURL {
	
	private final static int MIN_X=5630;
	private final static int MAX_X=5959;
	private final static int MIN_Y=1487;
	private final static int MAX_Y=1653;
	private final static int Z=15;
	private final static String baiduUrl = "http://online4.map.bdimg.com/tile/?qt=vtile&x=86&y=23&z=9&styles=pl&scaler=1&udt=20190815";
	private final static String amapUrl = "https://wprd01.is.autonavi.com/appmaptile?x=26085&y=13886&z=15&lang=zh_cn&size=1&scl=2&style=8&ltype=11";
   
	
	public static void main(String[] args) {
    	
		downloadAmapTile();
        
    }
	
	private static void downloadAmapTile(){
        String url = "";
        String path="G:/amaptile/tiles";
        String targetPath = "G:/amaptile/target/tiles";
        String dirPath = null;
        File baseDir = new File(path);
        File[] listz = baseDir.listFiles();
        for(File zFile:listz){
        	if(zFile.isDirectory()){
        		String z = zFile.getName();
        		File[] listx = zFile.listFiles();
        		for(File xFile:listx){
        			if(xFile.isDirectory()){
        				String x = xFile.getName();
        				File[] listy = xFile.listFiles();
        				for(File yFile:listy){
        					String filePath = yFile.getName();
                			String y = filePath.split("_")[1];                			
                			y = y.substring(0, y.length()-4);
                			String realPath = targetPath+File.separator+z+File.separator+x+File.separator+y+".png";
                			String realUrl = "https://wprd01.is.autonavi.com/appmaptile?x="+x+
                					"&y="+y+"&z="+z+"&lang=zh_cn&size=1&scl=2&style=8&ltype=11";
                			File t = new File(targetPath+File.separator+z+File.separator+x);
                    		if(!t.exists()){
                    			t.mkdirs();
                    		}
                			downloadPicture(realUrl,realPath);
                			System.out.println("Download pic ="+realPath+" success");
        				}
        			}
        			
        			
        			
        		}
        	}
        }
	}
	
	private static void downloadBaiduTile(){
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
        System.out.println("Download pic z="+Z+" success");
	}
    
    
    //链接url下载图片
    private static void downloadPicture(String urlList,String path) {
        URL url = null;
        try {
            url = new URL(urlList);

            HttpURLConnection httpUrl = null;
            //DataInputStream dataInputStream = new DataInputStream(url.openStream());

            httpUrl = (HttpURLConnection) url.openConnection();  
            httpUrl.connect();  
            BufferedInputStream dataInputStream = new BufferedInputStream(httpUrl.getInputStream()); 
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
