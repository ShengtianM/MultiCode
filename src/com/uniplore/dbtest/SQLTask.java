package com.uniplore.dbtest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniplore.tools.TestJdbcRequest;

import io.prometheus.client.Collector;

/**
 * 批量插入工具类
 * @author tian
 *
 */
public class SQLTask extends Collector implements Runnable {
	private TestJdbcRequest tjr;
	private int thredIndex;
	private int batchSize;
	private double costTime=0;
	Logger logger = LoggerFactory.getLogger(SQLTask.class);
	
	public SQLTask(int i,TestJdbcRequest tjr,int batchSize) {
		this.tjr = tjr;
		this.thredIndex = i;
		this.batchSize = batchSize;
	}

	@Override
	public void run() {
		Connection conn = null;
		PreparedStatement st = null;
		try {			
			conn = tjr.getConnection();
			 
			st = conn.prepareStatement("insert into cj_gjsj_copy1(GJBS,ZDBS,YXDNBBS)"
					+ "values(?,?,?)");
			conn.setAutoCommit(false);
			long beginTime = System.currentTimeMillis();
			int i =1;
			for(i=1;i<=10000;i++){
				st.setString(1,"GJBS"+i+"-"+thredIndex);
				st.setString(2, "ZDBS"+i+"-"+thredIndex);
				st.setString(3, "YXDNBBS"+i+"-"+thredIndex);
				st.addBatch();
				if(i%batchSize == 0){
					st.executeBatch();
					conn.commit();
				}				
			}
			if(i%batchSize!=0){
				st.executeBatch();
				conn.commit();
			}
			long endTime = System.currentTimeMillis(); 
			costTime = (endTime - beginTime)*1.0/1000;
			logger.info("Thread "+this.thredIndex +",Cost time is :"+costTime+"s");
			tjr.putCost((endTime - beginTime)*1.0/1000,this.thredIndex);
		} catch (Exception e) {
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

	@Override
	public List<MetricFamilySamples> collect() {
		List<MetricFamilySamples> mfs = new ArrayList<MetricFamilySamples>();

        String metricName = "executiontime"+this.thredIndex;

        MetricFamilySamples.Sample sample = new MetricFamilySamples.Sample(metricName, Arrays.asList("thread"), Arrays.asList(String.valueOf(this.thredIndex)),costTime );
        
        MetricFamilySamples samples = new MetricFamilySamples(metricName, Type.GAUGE, "help", Arrays.asList(sample));

        mfs.add(samples);
        return mfs;
	}
	


}
