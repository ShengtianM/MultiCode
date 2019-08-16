package org.uniplore.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GetSchemaByRest {
	private static final Logger log =
	 LoggerFactory.getLogger(GetSchemaByRest.class);
	// private DatasourceService datasourceService;
	// private SessionService sessionService;
	private String kapHost;
	private String kapPort;
	private String kapName;
	private String kapPassword;

	public GetSchemaByRest(String kaphost, String kapport, String kapName, String kapPassword) {

		this.kapHost = kaphost;
		this.kapPort = kapport;
		this.kapName = kapName;
		this.kapPassword = kapPassword;
	}

		
	public String getCurrentTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	public String getProjectsApi(String host, String port) {
		return "http://" + host + ":" + port + "/kylin/api/projects/readable";
	}

	public String getCubeInstanceApi(String paramString1, String paramString2) {
		return "http://" + paramString1 + ":" + paramString2 + "/kylin/api/cubes";
	}

	private String getCubeDescApi(String paramString1, String paramString2) {
		return "http://" + paramString1 + ":" + paramString2 + "/kylin/api/cube_desc/{cubeName}/desc";
	}

	private String getModelApi(String paramString1, String paramString2) {
		return "http://" + paramString1 + ":" + paramString2 + "/kylin/api/model/";
	}

	

	
	private String getModel(String modelName, byte[] paramArrayOfByte, String paramString2) {
		String str1 = modelName == null ? "" : modelName;

		String uri = paramString2 + str1;

		CloseableHttpClient localCloseableHttpClient = HttpClientBuilder.create().build();
		HttpGet localHttpGet = prepareHttpGet(uri, paramArrayOfByte);
		HttpResponse localHttpResponse;
		BufferedReader localBufferedReader;
		StringBuffer localStringBuffer=null;
		try {
			localHttpResponse = localCloseableHttpClient.execute(localHttpGet);
			int i = localHttpResponse.getStatusLine().getStatusCode();
			localBufferedReader = new BufferedReader(new InputStreamReader(localHttpResponse.getEntity().getContent()));

			localStringBuffer = new StringBuffer();
			String str3 = "";
			while ((str3 = localBufferedReader.readLine()) != null) {
				localStringBuffer.append(str3);
			}
			log.info("Request DataModelDesc of DataModelDesc[" + modelName +
					 "] Response Code : " + i);
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		 

		return localStringBuffer.toString();
	}

	private String getDesc(String cubeName, byte[] paramArrayOfByte, String paramString2) {

		String str1 = paramString2.replaceAll("\\{cubeName\\}", cubeName);
		CloseableHttpClient localCloseableHttpClient = HttpClientBuilder.create().build();
		HttpGet localHttpGet = prepareHttpGet(str1, paramArrayOfByte);
		HttpResponse localHttpResponse;
		StringBuffer localStringBuffer=null;
		try {
			localHttpResponse = localCloseableHttpClient.execute(localHttpGet);
			int i = localHttpResponse.getStatusLine().getStatusCode();
			BufferedReader localBufferedReader = new BufferedReader(
					new InputStreamReader(localHttpResponse.getEntity().getContent()));

			localStringBuffer = new StringBuffer();
			String str2 = "";
			while ((str2 = localBufferedReader.readLine()) != null) {
				localStringBuffer.append(str2);
			}
			log.info("Request CubeDesc of CubeInstance[" + cubeName + "]Response Code : " + i);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		return localStringBuffer.toString();
	}

	

	private HttpGet prepareHttpGet(String uri, byte[] paramArrayOfByte) {
		HttpGet localHttpGet = new HttpGet(uri);

		localHttpGet.setHeader("Authorization", "Basic " + new String(paramArrayOfByte));
		localHttpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		localHttpGet.setHeader("Accept-Language", "en-US,en;q=0.5");
		return localHttpGet;
	}

	public static void main(String[] args) {
		GetSchemaByRest gcb = new GetSchemaByRest("172.16.11.21", "7070",
				"ADMIN", "KYLIN");
		try {
			byte[] paramArrayOfByte = Base64.encodeBase64(new String("ADMIN" + ":" +
					"KYLIN").getBytes("UTF-8"));
			String modelDesc = gcb.getModel("kylin_sales_model", paramArrayOfByte,
					gcb.getModelApi("172.16.11.21", "7070"));
			String cubeDesc = gcb.getDesc("kylin_sales_cube", paramArrayOfByte,
					gcb.getCubeDescApi("172.16.11.21", "7070"));
			log.info("modelDesc:"+modelDesc);
			log.info("cubeDesc:"+cubeDesc);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
	 }
	
	 }

}