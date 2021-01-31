package org.uniplore.tools;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 瓦片下载任务类
 * @author tian
 *
 */
public class MultiSQLTask implements Runnable {
	private String path;
	private String sqlPrex;
	private TestJdbcRequest tjr;
	private int threadIndex;
	private int beginNum = 0;
	private int endNum = 0;
	Logger logger = LoggerFactory.getLogger(MultiSQLTask.class);
	
	public MultiSQLTask(int i,String path,String sqlPrex,int beginNum,int endNum,TestJdbcRequest tjr) {
		this.threadIndex = i;
		this.path = path;
		this.sqlPrex = sqlPrex;
		this.beginNum = beginNum;
		this.endNum = endNum;
		this.tjr = tjr;
	}

	@Override
	public void run() {
		int sqlNum = endNum- beginNum;
		for(int i = beginNum;i<endNum;i++) {
			String realPath = path+File.separator+sqlPrex+(i%sqlNum+1)+".sql";
			String sql = ReadFileContentToString.start(realPath);
			executeSQL(sql,i%sqlNum+1);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
//		for(int i = endNum-1;i>=beginNum;i--) {
//			String realPath = path+File.separator+"2q"+(i%sqlNum+1)+".sql";
//			String sql = ReadFileContentToString.start(realPath);
//			executeSQL(sql,i%sqlNum+1);
//		}
		

	}
	
	
	public void executeSQL(String sql,int index){
		Connection conn = null;
		PreparedStatement st = null;
		if(!sql.equalsIgnoreCase("-1")) {
			try {			
				conn = tjr.getConnection();
				long beginTime = System.currentTimeMillis(); 
				st = conn.prepareStatement(sql);
		
				st.executeQuery();				
				long endTime = System.currentTimeMillis(); 				
				tjr.putCost((endTime - beginTime)*1.0/1000,index);
				logger.info("Thread "+this.threadIndex +",Cost time is :"+(endTime - beginTime)*1.0/1000+"s,"+"ExecNum is:"+tjr.execNum);
				tjr.addExecCost();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				if(st!=null){
					try {
						st.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(conn!=null){
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	


}
