package org.uniplore.tools;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 瓦片下载任务类
 * @author tian
 *
 */
public class ExcelReadTask implements Runnable {
	private String filePath;
	private int threadIndex;
	Logger logger = LoggerFactory.getLogger(ExcelReadTask.class);
	
	public ExcelReadTask(int i,String filePath) {
		this.filePath = filePath;
		this.threadIndex = i;
	}

	@Override
	public void run() {
		try {			
			ExcelTools ext = new ExcelTools();
			File file = new File(filePath);
			List<List<Object>> list = ext.getAllExcelData(file);
			while(true){
				System.out.println("Thread :"+threadIndex+",list size :"+list.size());
				//System.out.println("Thread :"+threadIndex);
				Thread.sleep(1000);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	


}
