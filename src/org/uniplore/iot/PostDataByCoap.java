package org.uniplore.iot;

import java.util.List;
import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.MediaTypeRegistry;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 通过Coap协议提交数据
 * @author MinShengtian
 *
 * 2018年12月13日
 */
public class PostDataByCoap extends AbstractPostData {

	/**
	 * @param device
	 * @param ip
	 * @param port
	 */
	public PostDataByCoap(String device, String ip, String port) {
		super(device, ip, port);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.uniplore.mybatisdemo.AbstractPostData#postProperty()
	 */
	@Override
	public void postProperty() {
		String uri ="coap://"+this.serverIp+"/api/v1/"+this.deviceId+"/attributes";


	}

	/* (non-Javadoc)
	 * @see com.uniplore.mybatisdemo.AbstractPostData#postTelemetry()
	 */
	@Override
	public void postTelemetry() {
		String uri ="coap://"+this.serverIp+"/api/v1/"+this.deviceId+"/telemetry";
		CoapClient client = new CoapClient(uri);
		try {
			System.out.println("begin post");
			client.post(new CoapHandler(){

				@Override
				public void onError() {
					System.out.println("post on error");
					
				}

				@Override
				public void onLoad(CoapResponse arg0) {
					System.out.println("post on Load");
					
				}
				
			},TestDataGenertor.genJsonData(), MediaTypeRegistry.APPLICATION_JSON);
			System.out.println("post OK");
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see com.uniplore.mybatisdemo.AbstractPostData#getDataByKeys(java.util.List, java.util.List)
	 */
	@Override
	public String getDataByKeys(List<String> clientKeys, List<String> sharedKeys) {
		String uri ="coap://"+this.serverIp+"/api/v1/"+this.deviceId+"/attributes?";
		return null;
	}

	/* (non-Javadoc)
	 * @see com.uniplore.mybatisdemo.AbstractPostData#subscribeRpcFromServer()
	 */
	@Override
	public void subscribeRpcFromServer() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.uniplore.mybatisdemo.AbstractPostData#sendRpcToServer()
	 */
	@Override
	public void sendRpcToServer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postTelemetry(String payload) {
		String uri ="coap://"+this.serverIp+"/api/v1/"+this.deviceId+"/telemetry";
		CoapClient client = new CoapClient(uri);
		try {
			client.post(new CoapHandler(){

				@Override
				public void onError() {
					System.out.println("post on error");
					
				}

				@Override
				public void onLoad(CoapResponse arg0) {
					System.out.println("post on Load");
					
				}
				
			},payload, MediaTypeRegistry.APPLICATION_JSON);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
