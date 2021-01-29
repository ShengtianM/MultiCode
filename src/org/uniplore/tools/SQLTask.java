package org.uniplore.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 瓦片下载任务类
 * @author tian
 *
 */
public class SQLTask implements Runnable {
	private String sql;
	private List<String> params;
	private TestJdbcRequest tjr;
	private int thredIndex;
	Logger logger = LoggerFactory.getLogger(SQLTask.class);
	
	public SQLTask(int i,String sql,List<String> params,TestJdbcRequest tjr) {
		this.sql = sql;
		this.params = params;
		this.tjr = tjr;
		this.thredIndex = i;
	}

	@Override
	public void run() {
		if(!sql.equalsIgnoreCase("-1")) {
			try {			
			Connection conn = tjr.getConnection();
			long beginTime = System.currentTimeMillis(); 
			PreparedStatement st = conn.prepareStatement(sql);
			for(int i=1;i<=params.size();i++){
				st.setString(i, params.get(i-1));
				//st.setString(i, "YHBH999"+params.get(i-1));
			}
			
			ResultSet rt = st.executeQuery();
			if (rt.next()) {				
			}
			rt.close();
			st.close();
			conn.close();
			long endTime = System.currentTimeMillis(); 
			logger.info("Thread "+this.thredIndex +",Cost time is :"+(endTime - beginTime)*1.0/1000+"s,"+"ExecNum is:"+tjr.execNum);
			tjr.putCost((endTime - beginTime)*1.0/1000,this.thredIndex);
			tjr.addExecCost();
		} catch (Exception e) {
			e.printStackTrace();
		}
			}

	}
	


}
