package org.uniplore.iot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author MinShengtian
 *
 * 2018年12月3日
 */
public class PostData extends AbstractPostData{
	
	public PostData(String device,String ip,String port){
		super(device, ip, port);
	}
	
	/**  
     * 设备端属性上传  
     */  
    public void postProperty() {  
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost    
        HttpPost httppost = new HttpPost("http://"+serverIp+":"+serverPort+"/api/v1/"+deviceId+"/attributes");  
        // 创建参数队列    
        Map<String,Object> map = new HashMap<>();
        map.put("time",new Date().toString());
        map.put("deviceId",this.deviceId);
        map.put("active", Math.random()>0.5?true:false);
        ObjectMapper objectMapper = new ObjectMapper();
        //formparams.add(new BasicNameValuePair("tel", "17"));  
        //formparams.add(new BasicNameValuePair("password", "123456"));  
        //UrlEncodedFormEntity uefEntity;  
        try {  
            //uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
        	StringEntity jsonEntity = new StringEntity(objectMapper.writeValueAsString(map),HTTP.UTF_8);
            httppost.setEntity(jsonEntity);  
            System.out.println("executing request " + httppost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    //System.out.println("Response status: " + entity.getContentEncoding());  
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }
	
	
	/**  
     * 设备记录上传  
     */  
    public void postTelemetry() {  
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost    
        HttpPost httppost = new HttpPost("http://"+serverIp+":"+serverPort+"/api/v1/"+deviceId+"/telemetry");  
        // 创建参数队列    
        //List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        //formparams.add(new BasicNameValuePair("tel", "17"));  
        //formparams.add(new BasicNameValuePair("password", "123456"));  
        //UrlEncodedFormEntity uefEntity;
        httppost.setHeader("Content-Type", "application/json");
        try {  
            //uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
        	StringEntity jsonEntity = new StringEntity(TestDataGenertor.genJsonData(),ContentType.create("application/json", "utf-8"));
            httppost.setEntity(jsonEntity);  
            //System.out.println("executing request " + httppost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                    //System.out.println("Response status: " + entity.getContentEncoding());  
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }
    
    /**  
     * 获取属性 
     */  
    public String getDataByKeys(List<String> clientKeys,List<String> sharedKeys) {  
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String tempClientKey="clientKeys=";
        String sharedKey = "sharedKeys=";
        String result = null;
        for(int i=clientKeys.size()-1;i>=0;i--){
        	if(i!=0){
        		tempClientKey = tempClientKey+clientKeys.get(i)+",";
        	}else{
        		tempClientKey = tempClientKey+clientKeys.get(i)+"&";
        	}        	
        }
        for(int i=sharedKeys.size()-1;i>=0;i--){
        	if(i!=0){
        		sharedKey = sharedKey+sharedKeys.get(i)+",";
        	}else{
        		sharedKey = sharedKey+sharedKeys.get(i);
        	}        	
        }
        String uri = "http://"+serverIp+":"+serverPort+"/api/v1/"+deviceId+"/attributes?"+
                tempClientKey+sharedKey;
        System.out.println("url:"+uri);
        // 创建httpget   
        HttpGet httpget = new HttpGet(uri);
        RequestConfig requestConfig = RequestConfig.custom()
        		.setConnectTimeout(5000)//设置连接超时时间
        		.setConnectionRequestTimeout(5000)//设置请求超时时间
        		.setSocketTimeout(5000)
        		.setRedirectsEnabled(true)//默认允许自动重定向
        		.build();
        httpget.setConfig(requestConfig);
 
        try {  
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {
            	if(response.getStatusLine().getStatusCode() == 200){
            		HttpEntity entity = response.getEntity();  
                    if (entity != null) {  
                    	result = EntityUtils.toString(entity);
                        System.out.println("Response status: " +result );  
                    }
            	}
                  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
        return result;
    }

    

	public String getRpcFromServer() {
		String result = null;
		// 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        String uri = "http://"+serverIp+":"+serverPort+"/api/v1/"+deviceId+"/rpc";
        System.out.println("url:"+uri);
        // 创建httpget   
        HttpGet httpget = new HttpGet(uri);
        RequestConfig requestConfig = RequestConfig.custom()
        		.setConnectTimeout(5000)//设置连接超时时间
        		.setConnectionRequestTimeout(5000)//设置请求超时时间
        		.setSocketTimeout(5000)
        		.setRedirectsEnabled(true)//默认允许自动重定向
        		.build();
        httpget.setConfig(requestConfig);
 
        try {  
            CloseableHttpResponse response = httpclient.execute(httpget);  
            try {
            	if(response.getStatusLine().getStatusCode() == 200){
            		HttpEntity entity = response.getEntity();  
                    if (entity != null) {  
                    	result = EntityUtils.toString(entity);
                        System.out.println("Response status: " +result );  
                    }
            	}
                  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
        return result;
		
	}

	/* (non-Javadoc)
	 * @see com.uniplore.mybatisdemo.AbstractPostData#sendRpcToServer()
	 */
	@Override
	public void sendRpcToServer() {
		// 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        // 创建httppost    
        HttpPost httppost = new HttpPost("http://"+serverIp+":"+serverPort+"/api/v1/"+deviceId+"/rpc");  
        // 创建参数队列    
        //List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        //formparams.add(new BasicNameValuePair("tel", "17"));  
        //formparams.add(new BasicNameValuePair("password", "123456"));  
        //UrlEncodedFormEntity uefEntity;
        httppost.setHeader("Content-Type", "application/json");
        try {  
            //uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
        	StringEntity jsonEntity = new StringEntity(TestDataGenertor.genRpcCmdJsonData(),ContentType.create("application/json", "utf-8"));
            httppost.setEntity(jsonEntity);  
            //System.out.println("executing request " + httppost.getURI());  
            CloseableHttpResponse response = httpclient.execute(httppost);  
            try {  
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
                	String result = EntityUtils.toString(entity);
                    System.out.println("Response status: " +result );   
                }  
            } finally {  
                response.close();  
            }  
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e1) {  
            e1.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }
		
	}

	/* (non-Javadoc)
	 * @see com.uniplore.mybatisdemo.AbstractPostData#subscribeRpcFromServer()
	 */
	@Override
	public void subscribeRpcFromServer() {
		// TODO Auto-generated method stub
		
	}
    
    
    
}
