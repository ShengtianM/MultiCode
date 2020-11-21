package org.uniplore.http;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
 
public class DownloadPicFromURL {
	
	private final static String baiduUrl = "http://online4.map.bdimg.com/tile/?qt=vtile&x=86&y=23&z=9&styles=pl&scaler=1&udt=20190815";
    private final static String amapUrl1 = "http://wprd0{1-4}.is.autonavi.com/appmaptile?x={x}&y={y}&z={z}&lang=zh_cn&size=1&scl=1&style=7"; //矢量图（含路网、含注记）
    private final static String amapUrl2 = "http://wprd0{1-4}.is.autonavi.com/appmaptile?x={x}&y={y}&z={z}&lang=zh_cn&size=1&scl=2&style=7"; //含路网，不含注记
    private final static String amapUrl3 = "http://wprd0{1-4}.is.autonavi.com/appmaptile?x={x}&y={y}&z={z}&lang=zh_cn&size=1&scl=1&style=6"; //影像底图（不含路网，不含注记））
    private final static String amapUrl4 = "http://wprd0{1-4}.is.autonavi.com/appmaptile?x={x}&y={y}&z={z}&lang=zh_cn&size=1&scl=2&style=6"; //影像底图（不含路网、不含注记）
    private final static String amapUrl5 = "http://wprd0{1-4}.is.autonavi.com/appmaptile?x={x}&y={y}&z={z}&lang=zh_cn&size=1&scl=1&style=8"; //影像路图（含路网，含注记）
    private final static String amapUrl6 = "http://wprd0{1-4}.is.autonavi.com/appmaptile?x={x}&y={y}&z={z}&lang=zh_cn&size=1&scl=2&style=8"; //影像路网（含路网，不含注记）
    private final static String geoqUrl = "https://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineStreetPurplishBlue/MapServer/tile/{z}/{y}/{x}";
      
	
	public static void main(String[] args) {
		downloadMapTile(geoqUrl,"G:/geomaptile/test/tiles/{z}/{x}/{x}_{y}.png",
				"G:/amaptile/guizhou11_12/tiles",5);
    }
	/**
	 * 并发下载指定来源的瓦片数据并根据存储规则存储
	 * @param sourceUrl 瓦片数据来源
	 * @param savePathUrl 存储规则
	 * @param sourceBasePath 根据google的z/x/x_y获取z,x,y数据 
	 * @param threadNum 并发线程数
	 */
	private static void downloadMapTile(String sourceUrl,
			String savePathUrl,String sourceBasePath,int threadNum){
        File baseDir = new File(sourceBasePath);
        File[] listz = baseDir.listFiles();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(threadNum);
        for(File zFile:listz){
        	if(zFile.isDirectory()){
        		String z = zFile.getName();
        		File[] listx = zFile.listFiles();
        		for(File xFile:listx){
        			if(xFile.isDirectory()){
        				String x = xFile.getName();
        				List<File> listy = Arrays.asList(xFile.listFiles());
        				List<String> ys = listy.stream().map(i->i.getName()).collect(Collectors.toList());

        				for(int i =0;i<5;i++){
        					DownloadTileTask dtt = new DownloadTileTask(z, x, ys, sourceUrl, savePathUrl, i,threadNum);
        					fixedThreadPool.execute(dtt);
        				}
        			}        			
        		}
        	}
        }
	}
    
}
