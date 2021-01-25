package org.uniplore.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class TestJdbcRequest {
	
	public final static String user ="root";
	public final static String pwd ="tidb";
	public final static String url ="jdbc:mysql://192.168.103.76:4000/elec";
	public final static String driver ="com.mysql.jdbc.Driver";
	public List<Double> costList = new ArrayList<>();
	
	private static ComboPooledDataSource dataSource;
	public TestJdbcRequest() {
	}
	
	public static void main(String[] args) {
		TestJdbcRequest tjr = new TestJdbcRequest();
		try {
			int num =1000;
			tjr.init(user,pwd,url,driver);
			tjr.beginTest(tjr,num,3);
//			tjr.beginComplexTest(tjr,num);
//			while(true) {
//				if(tjr.costList.size()==num) {
//					DoubleSummaryStatistics collect = tjr.costList.stream().collect(Collectors.summarizingDouble(value->value));
//					System.out.println("执行次数："+collect.getCount()+",最大值："+collect.getMax()+",最小值："+collect.getMin()+",平均值："+collect.getAverage());
//					break;
//				}
//				Thread.sleep(1000);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public void beginTest(TestJdbcRequest tjr,int num,int type) throws Exception{
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
		String runSql = null;
		String preParam= null;
		switch(type) {
		case 1:
			runSql = "select khjld.JLDBH,khjld.JLDMC,kh.YHMC "
					+ "from kh_jld khjld,kh_ydkh kh "
					+ "where khjld.`YHBH` = kh.`YHBH` and khjld.JLDBH =?";
			preParam = "JLDBH";
			break;
		case 2:
			runSql = "select khjld.JLDBH,khjld.JLDMC,kh.YHMC "
					+ "from kh_jld khjld,kh_ydkh kh "
					+ "where khjld.`YHBH` = kh.`YHBH` and kh.YHBH like ? limit 100";
			preParam = "YHBH";
			break;
		case 3:
			runSql = "select 1";
		}
		for(int i = 0;i<num;i++) {
			SQLTask sqltask = new SQLTask(i,runSql,
					null//Arrays.asList(preParam+String.valueOf(i+1))
					,tjr);
			fixedThreadPool.execute(sqltask);
			System.out.println("Execute "+i);
		}
	}
	
	public void beginComplexTest(TestJdbcRequest tjr,int num) throws Exception{
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);


			String runSql1 = "select khjld.JLDBH,khjld.JLDMC,kh.YHMC "
					+ "from kh_jld khjld,kh_ydkh kh "
					+ "where khjld.`YHBH` = kh.`YHBH` and khjld.JLDBH =?";
			String preParam1 = "JLDBH";

			String runSql2 = "select khjld.JLDBH,khjld.JLDMC,kh.YHMC "
					+ "from kh_jld khjld,kh_ydkh kh "
					+ "where khjld.`YHBH` = kh.`YHBH` and kh.YHBH like ? limit 100";
			String preParam2 = "YHBH";

		for(int i = 0;i<num;i++) {
			if(i%10<2) {
				SQLTask sqltask = new SQLTask(i,runSql1,Arrays.asList(preParam1+String.valueOf(i+1)),tjr);
				fixedThreadPool.execute(sqltask);
			}else {
				SQLTask sqltask = new SQLTask(i,runSql2,Arrays.asList(preParam2+String.valueOf(i+1)),tjr);
				fixedThreadPool.execute(sqltask);
			}
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
	public void init(String user,String pwd,String url,String driver) throws Exception{
		dataSource = new ComboPooledDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(pwd);
		dataSource.setJdbcUrl(url);
		dataSource.setDriverClass(driver);
		dataSource.setInitialPoolSize(10000);//10
		dataSource.setMinPoolSize(10000);//5
		dataSource.setMaxPoolSize(25000);//25
		dataSource.setMaxStatements(50);//50
		dataSource.setMaxIdleTime(600);//60

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
	
	public synchronized final void putCost(double cost) throws Exception {
		try {
			this.costList.add(cost);
			// conn = druidDataSource.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getById(int id) {
		try {
			Connection conn = getConnection();
			long beginTime = System.currentTimeMillis(); 
			PreparedStatement st = conn.prepareStatement("select khjld.JLDBH,khjld.JLDMC,kh.YHMC "
					+ "from kh_jld khjld,kh_ydkh kh "
					+ "where khjld.`YHBH` = kh.`YHBH` and khjld.JLDBH =?");
			st.setString(1, "JLDBH"+id);
			ResultSet rt = st.executeQuery();
			if (rt.next()) {				
			}
			long endTime = System.currentTimeMillis(); 
			System.out.println("Cost time is :"+(endTime - beginTime)*1.0/1000+"s");
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
