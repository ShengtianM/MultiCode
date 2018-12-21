package org.uniplore.iot;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
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
public class PostDataByMqtt extends AbstractPostData{
	private MqttConnectOptions options = null;
	private String broker = null;
	private String mqttMessage = null;
	
	public PostDataByMqtt(String device,String ip){
		super(device,ip,"9999");
		options = new MqttConnectOptions();
		//是否清空session
		options.setCleanSession(true);
		if(this.deviceId !=""){
			//设置连接用户名及密码
			options.setUserName(this.deviceId);
			//options.setPassword(pwd.toCharArray());
		}
		
		//设置超时时间 单位秒
		options.setConnectionTimeout(10);
		//设置会话心跳时间
		options.setKeepAliveInterval(20);
		broker = "tcp://"+ip;
	}
	
	public PostDataByMqtt(String device,String ip,String port){
		super(device,ip,port);
		options = new MqttConnectOptions();
		//是否清空session
		options.setCleanSession(true);
		if(this.deviceId !=""){
			//设置连接用户名及密码
			//options.setUserName("admin");
			//options.setPassword("admin".toCharArray());
		}
		
		//设置超时时间 单位秒
		options.setConnectionTimeout(10);
		//设置会话心跳时间
		options.setKeepAliveInterval(20);
		broker = "tcp://"+ip+":"+port;
	}

	/* (non-Javadoc)
	 * @see com.uniplore.mybatisdemo.AbstractPostData#postProperty()
	 */
	@Override
	public void postProperty() {

		String topic = "v1/devices/me/attributes";
		//String topic = "sensors";
		try {
			//broker为主机名，deviceId即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置deviceId的保存形式，默认为以内存保存 
			MqttClient mqc = new MqttClient(broker, this.deviceId);			
			//建立连接
			mqc.connect(options);
			Map<String,Object> map = new HashMap<>();
	        map.put("time",new Date().toString());
	        map.put("deviceId",this.deviceId);
	        map.put("active", Math.random()>0.5?true:false);

	        ObjectMapper objectMapper = new ObjectMapper();
	        //创建消息
	        MqttMessage msg = new MqttMessage(objectMapper.writeValueAsString(map).getBytes());
	        //设置消息服务质量
	        msg.setQos(1);
	        //发布消息
	        mqc.publish(topic, msg);
	        
	        
	        //断开连接
	        mqc.disconnect();
	        //关闭客户端
	        mqc.close();
		} catch (MqttException | JsonProcessingException me) {
			// TODO Auto-generated catch block
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();

		}
		
	}
	
	public void postPropertyToGateWay() {

		//String topic = "v1/devices/me/attributes";
		String topic = "sensors";
		try {
			//broker为主机名，deviceId即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置deviceId的保存形式，默认为以内存保存 
			MqttClient mqc = new MqttClient(broker, String.valueOf(System.currentTimeMillis()));			
			//建立连接
			mqc.connect(options);

			Map<String,Object> map = new HashMap<>();
	        //map.put("time",new Date().toString());
	        //map.put("deviceId",this.deviceId);
	        //map.put("active", Math.random()>0.5?true:false);
			map.put("serialNumber", "SN-001");
			map.put("model", "T1000");
			map.put("temperature", 36.6);
	        ObjectMapper objectMapper = new ObjectMapper();
	        //创建消息
	        MqttMessage msg = new MqttMessage(objectMapper.writeValueAsString(map).getBytes());
	        //设置消息服务质量
	        msg.setQos(1);
	        //发布消息
	        mqc.publish(topic, msg);
	        
	        
	        //断开连接
	        mqc.disconnect();
	        //关闭客户端
	        mqc.close();
		} catch (MqttException | JsonProcessingException me) {
			// TODO Auto-generated catch block
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();

		}
		
	}
	
	

	/* (non-Javadoc)
	 * @see com.uniplore.mybatisdemo.AbstractPostData#postTelemetry()
	 */
	@Override
	public void postTelemetry() {
		String topic = "v1/devices/me/telemetry";
		try {
			//broker为主机名，deviceId即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置deviceId的保存形式，默认为以内存保存 
			MqttClient mqc = new MqttClient(broker, this.deviceId,new MemoryPersistence());			
			//建立连接
			mqc.connect(options);

	        //创建消息
	        MqttMessage msg = new MqttMessage(TestDataGenertor.genJsonData().getBytes());
	        //设置消息服务质量
	        msg.setQos(1);
	        //发布消息
	        mqc.publish(topic, msg);
	        
	        //断开连接
	        mqc.disconnect();
	        //关闭客户端
	        mqc.close();
		} catch (MqttException | JsonProcessingException me) {
			// TODO Auto-generated catch block
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();

		}
		
	}

	/* (non-Javadoc)
	 * @see com.uniplore.mybatisdemo.AbstractPostData#getDataByKeys(java.util.List, java.util.List)
	 */
	@Override
	public String getDataByKeys(List<String> clientKeys, List<String> sharedKeys) {
//		String tempClientKey="clientKeys=";
//        String sharedKey = "sharedKeys=";
//        for(int i=clientKeys.size()-1;i>=0;i--){
//        	if(i!=0){
//        		tempClientKey = tempClientKey+clientKeys.get(i)+",";
//        	}else{
//        		tempClientKey = tempClientKey+clientKeys.get(i)+"&";
//        	}        	
//        }
//        for(int i=sharedKeys.size()-1;i>=0;i--){
//        	if(i!=0){
//        		sharedKey = sharedKey+sharedKeys.get(i)+",";
//        	}else{
//        		sharedKey = sharedKey+sharedKeys.get(i);
//        	}        	
//        }
		
		String topic = "v1/devices/me/attributes/response/+";
		try {
			//broker为主机名，deviceId即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置deviceId的保存形式，默认为以内存保存 
			MqttClient mqc = new MqttClient(broker, this.deviceId,new MemoryPersistence());			
			
			mqc.setCallback(new MqttCallback() {
				
				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					System.out.println("topic:"+topic);
                    System.out.println("Qos:"+message.getQos());
                    System.out.println("message content:"+new String(message.getPayload()));
                    
                    mqttMessage = message.getPayload().toString();				
				}
				
				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					System.out.println("deliveryComplete---------"+ token.isComplete());
					
				}
				
				@Override
				public void connectionLost(Throwable cause) {
					System.out.println("connectionLost:"+cause.getMessage());
					
				}
			});
			//建立连接
			mqc.connect(options);
			
	        //订阅消息
	        mqc.subscribe(topic);
	        
	        //mqc.publish("v1/devices/me/attributes/request/1", 
	        		//new MqttMessage(("{\"clientKeys\":"+tempClientKey+", \"sharedKeys\":"+sharedKey+"}").getBytes()));

		} catch (MqttException me) {
			// TODO Auto-generated catch block
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();

		}
		return null;
	}
	
	
	/*
	 * 订阅属性更新消息
	 */
	public void attributeSubscribe(){
		String topic = "v1/devices/me/attributes/response/+";
		try {
			//broker为主机名，deviceId即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置deviceId的保存形式，默认为以内存保存 
			MqttClient mqc = new MqttClient(broker, this.deviceId,new MemoryPersistence());			
			
			mqc.setCallback(new MqttCallback() {
				
				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					System.out.println("topic:"+topic);
                    System.out.println("Qos:"+message.getQos());
                    System.out.println("message content:"+new String(message.getPayload()));
                    
                    mqttMessage = message.getPayload().toString();				
				}
				
				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					System.out.println("deliveryComplete---------"+ token.isComplete());
					
				}
				
				@Override
				public void connectionLost(Throwable cause) {
					System.out.println("connectionLost:"+cause.getMessage());
					
				}
			});
			//建立连接
			mqc.connect(options);
			
	        //订阅消息
	        mqc.subscribe(topic);
	        
	        //mqc.publish("v1/devices/me/attributes/request/1", 
	        		//new MqttMessage(("{\"clientKeys\":"+tempClientKey+", \"sharedKeys\":"+sharedKey+"}").getBytes()));

		} catch (MqttException me) {
			// TODO Auto-generated catch block
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();

		}
		
	}

	/* (non-Javadoc)
	 * @see com.uniplore.mybatisdemo.AbstractPostData#subscribeRpcFromServer()
	 * 订阅服务端RPC命令消息
	 */
	@Override
	public void subscribeRpcFromServer() {
		String topic = "v1/devices/me/rpc/request/+";
		try {
			//broker为主机名，deviceId即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置deviceId的保存形式，默认为以内存保存 
			MqttClient mqc = new MqttClient(broker, this.deviceId,new MemoryPersistence());			
			
			mqc.setCallback(new MqttCallback() {
				
				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					System.out.println("topic:"+topic);
                    System.out.println("Qos:"+message.getQos());
                    System.out.println("rpc message content:"+new String(message.getPayload()));
                    
                    mqttMessage = message.getPayload().toString();				
				}
				
				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					System.out.println("deliveryComplete---------"+ token.isComplete());
					
				}
				
				@Override
				public void connectionLost(Throwable cause) {
					System.out.println("connectionLost:"+cause.getMessage());
					
				}
			});
			//建立连接
			mqc.connect(options);
			
	        //订阅消息
	        mqc.subscribe(topic);
	        
	        //mqc.publish("v1/devices/me/attributes/request/1", 
	        		//new MqttMessage(("{\"clientKeys\":"+tempClientKey+", \"sharedKeys\":"+sharedKey+"}").getBytes()));

		} catch (MqttException me) {
			// TODO Auto-generated catch block
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();

		}
		
	}

	/* (non-Javadoc)
	 * @see com.uniplore.mybatisdemo.AbstractPostData#sendRpcToServer()
	 */
	@Override
	public void sendRpcToServer() {
		String topic = "v1/devices/me/rpc/request/";
		try {
			//broker为主机名，deviceId即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置deviceId的保存形式，默认为以内存保存 
			MqttClient mqc = new MqttClient(broker, this.deviceId,new MemoryPersistence());			
			//建立连接
			mqc.connect(options);

	        //创建消息
	        MqttMessage msg = new MqttMessage(TestDataGenertor.genRpcCmdJsonData().getBytes());
	        //设置消息服务质量
	        msg.setQos(1);
	        
	        //消息顺序
	        int requestId = 1;
	        topic= topic +"/"+requestId;
	        //发布消息
	        mqc.publish(topic, msg);
	        
	        //断开连接
	        mqc.disconnect();
	        //关闭客户端
	        mqc.close();
		} catch (MqttException | JsonProcessingException me) {
			// TODO Auto-generated catch block
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();

		}
		
	}
	
	
}
