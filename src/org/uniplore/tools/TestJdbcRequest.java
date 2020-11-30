package org.uniplore.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class TestJdbcRequest {
	
	public final String user ="root";
	public final String pwd ="tidb";
	public final String url ="jdbc:mysql://192.168.103.88:3390/elec?allowMultiQueries=true";
	public final String driver ="com.mysql.jdbc.Driver";
	
	private static ComboPooledDataSource dataSource;
	public TestJdbcRequest() {
	}
	
	public static void main(String[] args) {
		TestJdbcRequest tjr = new TestJdbcRequest();
		try {
			tjr.init();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public void beginTest(){
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(100);
	}
	
	public void init() throws Exception{
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
	
	
	public void getById(int id) {
		try {
			Connection conn = getConnection();
			PreparedStatement st = conn.prepareStatement("select khjld.JLDBH,khjld.JLDMC,kh.YHMC "
					+ "from kh_jld khjld,kh_ydkh kh "
					+ "where khjld.`YHBH` = kh.`YHBH` and khjld.JLDBH =?");
			st.setString(1, "JLDBH"+id);
			ResultSet rt = st.executeQuery();
			while (rt.next()) {				
			}
			rt.close();
			st.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getByLikeId(int id) {
		try {
			Connection conn = getConnection();
			PreparedStatement st = conn.prepareStatement("select khjld.JLDBH,khjld.JLDMC,kh.YHMC "
					+ "from kh_jld khjld,kh_ydkh kh "
					+ "where khjld.`YHBH` = kh.`YHBH` and kh.YHBH like ?");
			st.setString(1, "YHBH"+id+"%");
			ResultSet rt = st.executeQuery();
			while (rt.next()) {				
			}
			rt.close();
			st.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
