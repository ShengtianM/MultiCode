package org.uniplore.http;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.ibm.icu.text.MessageFormat;

/**
 * 瓦片下载任务类
 * @author tian
 *
 */
public class DownloadTileTask implements Runnable {
	private String z;
	private String x;
	private List<String> listy;
	private String sourceUrl;
	private String savePathUrl;
	private int threadNum;
	private int total;

	public DownloadTileTask(String z,String x,List<String> listy,String sourceUrl,String savePathUrl,int num,int total) {
		this.z = z;
		this.x = x;
		this.listy = listy;
		this.sourceUrl = sourceUrl;
		this.savePathUrl = savePathUrl;
		this.threadNum = num;
		this.total = total;
	}

	@Override
	public void run() {
		int size = listy.size();
		String url = sourceUrl.replace("{z}", "{0}").replace("{x}", "{1}").replace("{y}", "{2}");
        String targetPath = savePathUrl.replace("{z}", "{0}").replace("{x}", "{1}").replace("{y}", "{2}");
		for(int i=0;i<size;i++){
			/**
			 * 根据线程数量对下载任务进行划分
			 */
			if(i%total == threadNum){
				String filePath = listy.get(i);
				String y = filePath.split("_")[1];                			
				y = y.substring(0, y.length()-4);				
		        
		        Object[] paramList = new Object[]{z,x,y};
		        String realUrl = MessageFormat.format(url, paramList);
		        String realPath = MessageFormat.format(targetPath, paramList);
		        
		        File t = new File(realPath);
		        if(!t.getParentFile().exists()){
		        	t.getParentFile().mkdirs();
		        }
		        downloadPicture(realUrl,realPath);
		        System.out.println("Thread "+this.threadNum+" download pic ="+realPath+" success");
			}			
		}

	}
	
	/**
     * 根据 urlStr 下载指定瓦片并保存到 savePath
     * @param urlStr
     * @param savePath
     */
    private void downloadPicture(String urlStr,String savePath) {
        URL url = null;
        try {
            url = new URL(urlStr);
            HttpURLConnection httpUrl = null;
            httpUrl = (HttpURLConnection) url.openConnection();  
            httpUrl.connect();
            /**
             * 获取瓦片数据流
             */
            BufferedInputStream dataInputStream = new BufferedInputStream(httpUrl.getInputStream()); 
            
            /**
             * 保存瓦片数据
             */
            File t = new File(savePath);
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
