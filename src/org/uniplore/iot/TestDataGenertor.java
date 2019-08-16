package org.uniplore.iot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 用于生成测试IOT协议所用的数据
 * @author MinShengtian
 *
 * 2018年12月3日
 */
public class TestDataGenertor {
	private String proerty = null;
	private String pvalue = null;
	public TestDataGenertor(String key,String value){
		this.proerty = key;
		this.pvalue = value;
	}
	
	public static String genJsonData() throws JsonProcessingException{
		Map<String,Object> map = new HashMap<>();
		Random random = new Random();
		int opFlag = random.nextInt(10);
		if(opFlag%2==0){
			map.put("temperature",random.nextInt(100));
		}else{
			String str = "-"+random.nextInt(100);
			map.put("temperature",Integer.parseInt(str));
		}


        ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(map);
	}
	
	public static String genRpcCmdJsonData() throws JsonProcessingException{
		Map<String,Object> map = new HashMap<>();
		map.put("method", "getTime");
		map.put("params","{}");

        ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(map);
	}
	
	
	public static String genJsonDataWithTimeStamp() throws JsonProcessingException{
		Map<String,Object> map = new HashMap<>();
		Map<String,Object> vMap = new HashMap<>();
		Random random = new Random();
		int opFlag = random.nextInt(10);
		if(opFlag%2==0){
			vMap.put("temperature",random.nextInt(100));
		}else{
			String str = "-"+random.nextInt(100);
			vMap.put("temperature",Integer.parseInt(str));
		}
		map.put("ts", new Date().getTime());
		map.put("values", vMap);

        ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(map);
	}

}
