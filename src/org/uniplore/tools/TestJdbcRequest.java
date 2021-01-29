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

import com.alibaba.druid.pool.DruidDataSource;

public class TestJdbcRequest {
	

	public final static String user ="root";
	public final static String pwd ="****";
	//public final static String url ="jdbc:postgresql://192.168.103.76:5432/tpch1g";
	public final static String url ="jdbc:mysql://192.168.103.88:3390/tpch1g";
	//public final static String driver ="org.postgresql.Driver";//
	public final static String driver ="com.mysql.jdbc.Driver";
	public List<List<Double>> costList = new ArrayList<>(10);
	public final static String sqlFilePath = "d://sql";
	public int execNum = 0;
	public static int concurrencyNum = 20;
	public static int sqlNum = 10;
	
	private static DruidDataSource dataSource;
	public TestJdbcRequest() {
	}
	
	public static void main(String[] args) {
		TestJdbcRequest tjr = new TestJdbcRequest();
		try {
			int num =concurrencyNum*sqlNum;
			
			tjr.init(user,pwd,url,driver);

			for(int i=0;i<=sqlNum;i++) {
				tjr.costList.add(new ArrayList<>());
			}
			//tjr.beginTest(tjr,num,1);			
			///tjr.beginComplexTest(tjr,num);
			tjr.beginComplexSQLFile(tjr, num,sqlFilePath);
			while(true) {
				if(tjr.execNum == num) {
					for(int i=1;i<=sqlNum;i++) {
						DoubleSummaryStatistics collect = tjr.costList.get(i).stream().collect(Collectors.summarizingDouble(value->value));
						RecordJdbcTestResult.recordResult("TiDB1R", "1q"+i, collect.getMax(), collect.getMin(), collect.getAverage(), concurrencyNum);
						System.out.println("sql:q"+i+",执行次数："+collect.getCount()+",最大值："+collect.getMax()+",最小值："+collect.getMin()+",平均值："+collect.getAverage());
					}
					break;
				}
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public void beginTest(TestJdbcRequest tjr,int num,int type) throws Exception{
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(concurrencyNum);
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
	
	public void beginComplexSQLFile(TestJdbcRequest tjr,int num,String path) throws Exception{
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(concurrencyNum);

		for(int i = 0;i<num;i=i+sqlNum) {
			MultiSQLTask sqltask = new MultiSQLTask(i/sqlNum+1,path,i,i+sqlNum,tjr);
			fixedThreadPool.execute(sqltask);
			//sqltask.run();
		}
	}
	
	public void testMaxConn(TestJdbcRequest tjr,int num) throws Exception{
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

		String runSql = "select 1";

		for(int i = 0;i<num;i++) {
			SQLTask sqltask = new SQLTask(i,runSql,Arrays.asList(),tjr);
			fixedThreadPool.execute(sqltask);

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
		dataSource = new DruidDataSource();
		dataSource.setUsername(user);
		dataSource.setPassword(pwd);
		dataSource.setUrl(url);
		dataSource.setDriverClassName(driver);
		dataSource.setInitialSize(concurrencyNum);
		dataSource.setMinIdle(concurrencyNum);
		dataSource.setMaxActive(concurrencyNum*3);
		dataSource.setMaxCreateTaskCount(50);
		dataSource.setMaxWait(60000);

		// 重连设置
//		dataSource.setAcquireRetryAttempts(3);
//		dataSource.setAcquireRetryDelay(1000);
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
	
	public synchronized final void putCost(double cost,int i) throws Exception {
		try {
			this.costList.get(i).add(cost);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized final void addExecCost() throws Exception {
		try {
			this.execNum++;
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
