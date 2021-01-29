package com.uniplore.dbtest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.DoubleSummaryStatistics;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.uniplore.tools.TestJdbcRequest;

import io.prometheus.client.exporter.HTTPServer;

public class DbTest {
	public final static String user ="gpadmin";//"root";//"postgres";//
	public final static String pwd ="gpadmin";//"Mysql_123";//"postgres";//
	public final static String url ="jdbc:postgresql://192.168.100.19:5432/test";//"jdbc:mysql://192.168.0.20/swtest";//
	public final static String driver ="org.postgresql.Driver";//"com.mysql.jdbc.Driver";//
	public DbTest() {
		
		
	}
	
	public static void main(String[] args) {
		DbTest tjr = new DbTest();
		TestJdbcRequest jdbcManager = new TestJdbcRequest();
		
		try {
			HTTPServer server = new HTTPServer(1234);			
			int num =2;
			int batchSize = 1000;
			jdbcManager.init(user,pwd,url,driver);
			long beginTime = System.currentTimeMillis();
			tjr.beginComplexTest(jdbcManager, num,batchSize);
			
//			tjr.consumerMessage(jdbcManager);
			while(true) {
				if(jdbcManager.costList.size()==num) {
					DoubleSummaryStatistics collect = jdbcManager.costList.get(0).stream().collect(Collectors.summarizingDouble(value->value));
					System.out.println("执行次数："+collect.getCount()+",最大值："+collect.getMax()+",最小值："+collect.getMin()+",平均值："+collect.getAverage());
					long endTime = System.currentTimeMillis(); 
					System.out.println("Cost time is :"+(endTime - beginTime)*1.0/1000+"s");
					break;
				}
				Thread.sleep(1000);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	public void init(){
	}
	
	public void beginComplexTest(TestJdbcRequest tjr,int num,int batchSize) throws Exception{
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

		for(int i = 0;i<num;i++) {
			SQLTask sqltask = new SQLTask(i,tjr,batchSize);
			sqltask.register();
			//sqltask.run();
			fixedThreadPool.execute(sqltask);
		}
	}
	
	public void consumerMessage(TestJdbcRequest tjr){		
		Connection conn = null;
		PreparedStatement st = null;
		try{
			conn = tjr.getConnection();
			 
			st = conn.prepareStatement("insert into cj_gjsj_copy1(GJBS,ZDBS,YXDNBBS)"
					+ "values(?,?,?)");
			conn.setAutoCommit(false);
			long beginTime = System.currentTimeMillis();
			int i =0;
			for(int j=0;j<1000;i++,j++){
				st.setString(1,"GJBS"+j);
				st.setString(2, "ZDBS"+j);
				st.setString(3, "YXDNBBS"+j);
				st.addBatch();
				if(i%300 == 0){
					st.executeBatch();
					conn.commit();
				}
			}
			if(i%300!=0){
				st.executeBatch();
				conn.commit();
			}
			
			long endTime = System.currentTimeMillis(); 
			System.out.println("Cost time is :"+(endTime - beginTime)*1.0/1000+"s");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

	
	public void writeToTiDB(TestJdbcRequest tjr){
		try {			
			Connection conn = tjr.getConnection();
			 
			PreparedStatement st = conn.prepareStatement("insert into cj_gjsj_copy1(GJBS,ZDBS,YXDNBBS)"
					+ "values(?,?,?)");
			st.setString(1,"");
			st.setString(2, "");
			st.setString(3, "");
			st.addBatch();
			long beginTime = System.currentTimeMillis();
			st.executeBatch();

			
			long endTime = System.currentTimeMillis(); 
			System.out.println("Cost time is :"+(endTime - beginTime)*1.0/1000+"s");
			st.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
