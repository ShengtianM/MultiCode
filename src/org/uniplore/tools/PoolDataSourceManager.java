package org.uniplore.tools;

import java.sql.Connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class PoolDataSourceManager {
	private static ComboPooledDataSource dataSource;

	public PoolDataSourceManager() {
		
	}
	
	/**
	 * 
	 * @param user
	 * @param pwd
	 * @param url
	 * @param driver
	 * @throws Exception
	 */
	public void init(String user,String pwd,String url,String driver) throws Exception{
		dataSource = new ComboPooledDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(pwd);
		dataSource.setJdbcUrl(url);
		dataSource.setDriverClass(driver);
		dataSource.setInitialPoolSize(10);
		dataSource.setMinPoolSize(5);
		dataSource.setMaxPoolSize(25);
		dataSource.setMaxStatements(50);
		dataSource.setMaxIdleTime(60);

		// 重连设置
		dataSource.setAcquireRetryAttempts(3);
		dataSource.setAcquireRetryDelay(1000);
	}
	
	public synchronized final Connection getConnection() throws Exception {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			// conn = druidDataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}
