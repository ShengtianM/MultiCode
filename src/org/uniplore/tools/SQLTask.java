package org.uniplore.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * 瓦片下载任务类
 * @author tian
 *
 */
public class SQLTask implements Runnable {
	private String sql;
	private List<String> params;
	private Connection connection;

	public SQLTask(String sql,List<String> params,Connection connection) {
		this.sql = sql;
		this.params = params;
		this.connection = connection;
	}

	@Override
	public void run() {
		try {
			Connection conn = connection;
			PreparedStatement st = conn.prepareStatement(sql);
			for(int i=1;i<=params.size();i++){
				st.setString(i, "JLDBH"+params.get(i-1));
			}
			
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
