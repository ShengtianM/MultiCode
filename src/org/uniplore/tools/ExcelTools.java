package org.uniplore.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.poi.poifs.filesystem.DocumentFactoryHelper;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;

public class ExcelTools implements ExcelToolsInterface{

	public ExcelTools() {
	}
	
	public static void main(String[] args) {
		ExcelTools ext = new ExcelTools();
		ext.beginComplexWork(60);
		
    }
	
	public void beginComplexWork(int num){
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(60);
		String filePath = null;
		for(int i = 0;i<num;i++) {
			filePath="d://2013年70大中城市城市工资与房价"+i%3+".xlsx";
			ExcelReadTask sqltask = new ExcelReadTask(i,filePath);
			fixedThreadPool.execute(sqltask);
		}
	}

	@Override
	public List<List<Object>> getAllExcelData(File file) {
		List<List<Object>> result = new ArrayList<>();
		String fileName = file.getName();
        if ("".equals(fileName) || fileName == null) {
            return null;
        }
        //根据后缀判断excel的版本
        String ext = fileName.substring(fileName.lastIndexOf("."));        
        try {
        	BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            if (".csv".equals(ext)) {
                //result = getAllCsvData(inputStream, codeType);
            } else {
                String version = 
                		POIFSFileSystem.hasPOIFSHeader(inputStream)?"03":
                			DocumentFactoryHelper.hasOOXMLHeader(inputStream)?"07":null;
                if ("03".equals(version)) {
                    result = getAll03ExcelData(inputStream);
                } else if ("07".equals(version)) {
                    result = getAll07ExcelData(inputStream);
                } else {
                    throw new Exception("Excel类型不匹配");
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return result;
	}
	
	public static List<List<Object>> getAll03ExcelData(InputStream inputStream) throws Exception {
        List<List<Object>> result = new ArrayList<>();
        // 读取第一个sheet
        int sheetIndex = 0;
        // 表头位置，默认为0
        int headerIndex = 0;
        try {
            ExcelReader reader = ExcelUtil.getReader(inputStream, sheetIndex);
            result = reader.read();
            result.remove(headerIndex);
        } catch (Exception e) {            
            throw e;
        } finally {
        }
        return result;
    }
	public static List<List<Object>> getAll07ExcelData(InputStream inputStream) {
        List<List<Object>> result = new ArrayList<>();
        Excel07ReaderUtil eru = new Excel07ReaderUtil();
        Excel07SaxReader reader = new Excel07SaxReader(eru.getDataRowHandler());
        // 默认只读取第0个sheet
        int sheetIndex = 0;
        reader.read(inputStream, sheetIndex);
        result = eru.getDataList();
        // 表头位置，默认为0
        int headerIndex = 0;
        // 得到去掉标题的数据
        result.remove(headerIndex);
        return result;
    }

}
