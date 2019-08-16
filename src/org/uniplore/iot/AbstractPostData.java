package org.uniplore.iot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author MinShengtian
 *
 * 2018年12月4日
 */
public abstract class AbstractPostData {
	public String deviceId = null;
	public String serverIp = null;
	public String serverPort = null;
	
	public AbstractPostData(String device,String ip,String port){
		this.deviceId = device;
		this.serverIp = ip;
		this.serverPort = port;
	}
	
	public abstract void postProperty();
	
	public abstract void postTelemetry();
	
	public abstract void subscribeRpcFromServer();
	
	public abstract void sendRpcToServer();
	
	public abstract void postTelemetry(String payload);
	
	public abstract String getDataByKeys(List<String> clientKeys,List<String> sharedKeys);
	
	public void postTelemetryThread(int interval,String type){
    	PostDataThread pdt = new PostDataThread(interval,type);
    	pdt.run();
    }
	
	public void postTelemetryFileThread(int interval,String fileName){
		PostDataFileThread pdf = new PostDataFileThread(interval,fileName);
		pdf.run();
	}

	class PostDataFileThread implements Runnable{
		private int timeInterval = 1000;
		private String fileName = "telemetry";	
		public PostDataFileThread(int interval,String type){
			this.timeInterval = interval;
			this.fileName = type;
		}
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					System.out.println(Thread.currentThread());
					while(true){
						InputStream ips=new FileInputStream(new File(fileName));
						BufferedReader br=new BufferedReader(new InputStreamReader(ips));
						String s=null;
						s=br.readLine();
						while((s=br.readLine())!=null){
							String[] ts=s.split(",");
							Map<String,Object> map = new HashMap<>();
							map.put("风速", ts[1]);
							map.put("发电机转速", ts[2]);
							map.put("网侧有功功率(kw)", ts[3]);
							map.put("对风角(°)", ts[4]);
							map.put("25秒平均风向角", ts[5]);
							map.put("偏航位置", ts[6]);
							map.put("偏航速度", ts[7]);
							map.put("叶片1角度", ts[8]);
							map.put("叶片2角度", ts[9]);
							map.put("叶片3角度", ts[10]);
							map.put("叶片1速度", ts[11]);
							map.put("叶片2速度", ts[12]);
							map.put("叶片3速度", ts[13]);
							map.put("变桨电机1温度", ts[14]);
							map.put("变桨电机2温度", ts[15]);
							map.put("变桨电机3温度", ts[16]);
							map.put("x方向加速度", ts[17]);
							map.put("y方向加速度", ts[18]);
							map.put("环境温度", ts[19]);
							map.put("机舱温度", ts[20]);
							map.put("ng5_1温度", ts[21]);
							map.put("ng5_2温度", ts[22]);
							map.put("ng5_3温度", ts[23]);
							map.put("ng5_1充电器直流电流", ts[24]);
							map.put("ng5_2充电器直流电流", ts[25]);
							map.put("ng5_3充电器直流电流", ts[26]);
							postTelemetry(new ObjectMapper().writeValueAsString(map));
							Thread.sleep(this.timeInterval);
						}
						br.close();
						ips.close();
						Thread.sleep(this.timeInterval);
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}	
	
class PostDataThread implements Runnable{
	private int timeInterval = 1000;
	private String postType = "telemetry";	
	public PostDataThread(int interval,String type){
		this.timeInterval = interval;
		this.postType = type;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while(true){
					switch(this.postType){
					case "telemetry":
						postTelemetry();
						break;
					case "property":
						postProperty();
						break;
					}
					Thread.sleep(this.timeInterval);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
