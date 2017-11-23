package org.uniplore;

public class SDKDataSourceForCloud {

	public SDKDataSourceForCloud() {
		// TODO Auto-generated constructor stub
	}
	
	public static void getDataSource(String BIURL, String BIusername,
			String BIpassword) {

		// SpagoBI的登录账号和密码
		BIusername = "biadmin";
		BIpassword = "biadmin";
//		BIURL = "http://localhost:8080";
		BIURL = "http://192.168.100.214:8080";

		String endPoint = BIURL+"/Uniplore/sdk/DataSourcesSDKService";
		try {
			// 生成数据源代理
			//DataSourcesSDKServiceProxy proxy = new DataSourcesSDKServiceProxy(
					//BIusername, BIpassword);
			//proxy.setEndpoint(endPoint);
			// 生成数据源
			//SDKDataSource ds[] = proxy.getDataSources();
			//for (int i = 0; i < ds.length; i++) {
//				System.out.println("数据源描述：" + ds[i].getDescr());
//				System.out.println("数据源地址：" + ds[i].getUrlConnection());
//				System.out.println("数据源账号：" + ds[i].getName());
//				System.out.println("数据源密码：" + ds[i].getPwd());
//				System.out.println("数据源驱动：" + ds[i].getDriver());
//			}

			System.out.println("-------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
