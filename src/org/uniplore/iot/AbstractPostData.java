package org.uniplore.iot;

import java.util.List;

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
	
	public abstract String getDataByKeys(List<String> clientKeys,List<String> sharedKeys);
	
	public void postTelemetryThread(int interval,String type){
    	PostDataThread pdt = new PostDataThread(interval,type);
    	pdt.run();
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
