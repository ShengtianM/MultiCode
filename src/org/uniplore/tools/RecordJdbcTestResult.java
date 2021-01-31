package org.uniplore.tools;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class RecordJdbcTestResult {
	

	public final static String user ="gpadmin";
	public final static String pwd ="******";
	public final static String url ="jdbc:postgresql://192.168.100.19:5432/testDB";
	public final static String driver ="org.postgresql.Driver";//
	
	private static ComboPooledDataSource dataSource = null;
	public RecordJdbcTestResult() {
	}	
	

	public static void recordResult(String dbType,String sqlType,double execMax,
			double execMin,double execAvg,int concurrency) throws Exception{
		try {			
			Connection conn = getConnection();
			String sql = "insert into db_test values(?,?,?,?,?,?)";
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, dbType);
			st.setString(2, sqlType);
			st.setDouble(3, execMax);
			st.setDouble(4, execMin);
			st.setDouble(5, execAvg);
			st.setInt(6, concurrency);

			st.executeUpdate();
			st.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @param user
	 * @param pwd
	 * @param url
	 * @param driver
	 * @throws Exception
	 */
	public static void init() throws Exception{
		dataSource = new ComboPooledDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(pwd);
		dataSource.setJdbcUrl(url);
		dataSource.setDriverClass(driver);
		dataSource.setInitialPoolSize(1);
		dataSource.setMinPoolSize(1);
		dataSource.setMaxPoolSize(5);
		dataSource.setMaxStatements(50);
		dataSource.setMaxIdleTime(600);

		// 重连设置
		dataSource.setAcquireRetryAttempts(3);
		dataSource.setAcquireRetryDelay(1000);
	}
	
	public synchronized static final Connection getConnection() throws Exception {
		Connection conn = null;
		try {
			if(dataSource == null){
				init();
			}
			conn = dataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
