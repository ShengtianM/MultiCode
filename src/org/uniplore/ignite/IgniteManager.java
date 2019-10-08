package org.uniplore.ignite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IgniteManager {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private String selectSql = "select zdlbbzh,zdlbmxdm,zdlbmxmc,floatnum,timedate from tb_zd_lbmx_copy1";
	

	public IgniteManager() {
		
	}
	
	/**
	 * 将数据插入到Ignite
	 * 
	 * @param databasePO
	 * @param dsPO
	 * @param sql
	 * @param request
	 * @throws Exception
	 */
	public void insertDataToIgnite()
			throws Exception {
			buildDataSetSQL();
			long startTime = System.currentTimeMillis();
			String insertSql = "";
			String insertValSql = "";
			List<String> colTypeList = new ArrayList<String>();
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			Connection igniteconn = null;
			PreparedStatement stmt = null;
			try {
				// 获取原始数据源连接参数

				conn = getOriginConnection();

				insertSql = "insert into tb_zd_lbmx (colid,";
				insertValSql = " values(?,";

				igniteconn = getIgniteConnect();
				// igniteconn.setAutoCommit(false);
				if (conn != null && igniteconn != null) {
					ps = conn.prepareStatement(selectSql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					rs = ps.executeQuery();
					// 获取列数
					ResultSetMetaData metaData = rs.getMetaData();
					int colCount = metaData.getColumnCount();
					for (int i = 1; i <= colCount; i++) {
						// String tempColName=metaData.getColumnName(i+1);
						colTypeList.add(metaData.getColumnTypeName(i));
						insertSql = insertSql + metaData.getColumnLabel(i);
						insertValSql = insertValSql + " ?";
						if (i != colCount) {
							insertSql = insertSql + ",";
							insertValSql = insertValSql + ",";
						} else {
							insertSql = insertSql + ")";
							insertValSql = insertValSql + ")";
						}
					}
					insertSql = insertSql + insertValSql;

					stmt = igniteconn.prepareStatement(insertSql);
					int rowNum = 0;
					boolean flag = false;
					while (rs.next()) {
						stmt.setInt(1, rowNum);
						for (int j = 1; j <= colCount; j++) {
							switch (colTypeList.get(j - 1)) {
							case "CHAR":
							case "NCHAR":
							case "VARCHAR":
							case "NVARCHAR":
							case "LONGVARCHAR":
							case "TEXT":
								stmt.setString(j + 1, rs.getString(j));
								break;
							case "REAL":
							case "FLOAT":
								stmt.setFloat(j + 1, rs.getFloat(j));
								break;
							case "BIGSERIAL":
							case "DOUBLE":
								stmt.setDouble(j + 1, rs.getDouble(j));
								break;
							case "TINYINT":
							case "SMALLINT":
								stmt.setShort(j + 1, rs.getShort(j));
								break;
							case "SERIAL":
							case "INT":
							case "INTEGER":
								stmt.setInt(j + 1, rs.getInt(j));
								break;
							case "BOOLEAN":
								stmt.setBoolean(j + 1, rs.getBoolean(j));
								break;
							case "BIT":
								stmt.setByte(j + 1, rs.getByte(j));
								break;
							case "BIGINT":
								stmt.setLong(j + 1, rs.getLong(j));
								break;
							case "BINARY":
							case "VARBINARY":
							case "LONGVARBINARY":
								stmt.setBytes(j + 1, rs.getBytes(j));
								break;
							case "NUMBER":
							case "DECIMAL":
							case "NUMERIC":
								stmt.setBigDecimal(j + 1, rs.getBigDecimal(j));
								break;
							case "DATE":
								stmt.setDate(j + 1, rs.getDate(j));
								break;
							case "TIME":
								stmt.setTime(j + 1, rs.getTime(j));
								break;
							case "DATETIME":
							case "TIMESTAMP":
								stmt.setTimestamp(j + 1, rs.getTimestamp(j));
								break;
							case "BLOB":
								stmt.setBlob(j + 1, rs.getBlob(j));
								break;
							default:
								stmt.setString(j + 1, rs.getString(j));
								break;
							}
						}

						stmt.addBatch();
						rowNum++;
						flag = true;
						if (rowNum % 40000 == 0) {
							stmt.executeBatch();
							flag = false;
						}
					}
					if (flag) {
						stmt.executeBatch();
					}
				}
				System.out.println("数据插入所用时间：" + (System.currentTimeMillis() - startTime)/1000.0 + "s");
			
				getDataWithAggregate();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			} finally {
				if(rs!=null){
					rs.close();
				}
				if(ps!=null){
					ps.close();
				}
				if(conn!=null){
					conn.close();
				}
				
				if (stmt != null) {
					stmt.close();
				}
				
				if(igniteconn!=null){
					igniteconn.close();
				}
			}
		}

	
	/**
	 * 建立数据集存储表
	 * 
	 * @param databasePO
	 * @param dsId
	 * @param datasetSql
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void buildDataSetSQL()
			throws Exception {
		String buildSql = "";
		Connection conn = null;
		Connection igniteCon = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Statement stmt = null;
		long startTime = System.currentTimeMillis();
		try {

			// 获取原始数据源连接参数
			conn = getOriginConnection();

			buildSql = "drop table if exists tb_zd_lbmx; " + "create table tb_zd_lbmx (colid INT PRIMARY KEY,";
			if (conn != null) {
				String tempLimitSql = selectSql+" limit 1";
				ps = conn.prepareStatement(tempLimitSql);
				rs = ps.executeQuery();
				// 获取列数
				ResultSetMetaData metaData = rs.getMetaData();
				int colCount = metaData.getColumnCount();
				// 根据数据集的信息构建ignite中数据集结构
				for (int i = 1; i <= colCount; i++) {
					String tempColName = metaData.getColumnLabel(i);
					String tempColType = metaData.getColumnTypeName(i);
					// 避免将serial类型字段默认为primarykey
					if (tempColType.equalsIgnoreCase("serial") || tempColType.equalsIgnoreCase("bigserial")) {
						tempColType = "int";
					}

					if (tempColType.equalsIgnoreCase("Date")) {
						tempColType = "TIMESTAMP";
					}
					buildSql = buildSql + " " + tempColName + " " + tempColType;
					if (i != colCount) {
						buildSql = buildSql + ",";
					} else {
						buildSql = buildSql + ")";
					}
				}
				buildSql = buildSql + " WITH \"template=PARTITIONED\"";
			}
			igniteCon = getIgniteConnect();
			stmt = igniteCon.createStatement();
			stmt.executeUpdate(buildSql);
			System.out.println("数据删除所用时间：" + (System.currentTimeMillis() - startTime)/1000.0 + "s");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(conn!=null){
				conn.close();
			}
			
			if (stmt != null) {
				stmt.close();
			}
			
			if(igniteCon!=null){
				igniteCon.close();
			}
			
		}
	}
	
	public void getDataWithAggregateAndTimeInv() throws Exception{
		long startTime = System.currentTimeMillis();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		PreparedStatement stmt = null;
		try {
			// 获取原始数据源连接参数

			conn = getIgniteConnect();

			if (conn != null) {
				ps = conn.prepareStatement("select zdlbbzh,sum(floatnum),timedate from tb_zd_lbmx group by zdlbbzh");
				rs = ps.executeQuery();					
			}
			System.out.println("数据查询所用时间：" + (System.currentTimeMillis() - startTime)/1000.0 + "s");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(conn!=null){
				conn.close();
			}
			
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	
	public void getDataWithAggregate() throws Exception{
		long startTime = System.currentTimeMillis();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		PreparedStatement stmt = null;
		try {
			// 获取原始数据源连接参数

			conn = getIgniteConnect();

			if (conn != null) {
				
//				String sql= "SELECT zdlbbzh, SUM(floatnum) "
//						+ "FROM tb_zd_lbmx GROUP BY zdlbbzh LIMIT 2000";
				String sql ="SELECT  zdlbbzh, SUM(floatnum)"
						+ " FROM tb_zd_lbmx "
						+ "WHERE TO_TIMESTAMP(TO_CHAR(timedate,'yyyy-mm-dd'),'yyyy-mm-dd') >= '2018-09-01 00:00:00' AND TO_TIMESTAMP(TO_CHAR(timedate,'yyyy-mm-dd'),'yyyy-mm-dd') <= '2019-10-30 00:00:00'  GROUP BY zdlbbzh LIMIT 200000";
				
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();					
			}
			System.out.println("数据查询所用时间：" + (System.currentTimeMillis() - startTime)/1000.0 + "s");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		} finally {
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(conn!=null){
				conn.close();
			}
			
			if (stmt != null) {
				stmt.close();
			}
		}
	}
	
	
	public Connection getOriginConnection() {
		Connection conn = null;
		// 获取数据源连接参数
		String driver = "org.postgresql.Driver";
		String url="jdbc:postgresql://localhost:5432/rhip";
		String username = "postgres";
		String password = "postgres";	

		try {					
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			System.out.println("从原始数据源获取连接失败");
			e.printStackTrace();
		}
		return conn;
	}
	
	
	/**
	 * 从连接池返回数据库连接
	 * 
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("finally")
	public Connection getIgniteConnect() throws Exception {
		Connection conn = null;
		
			if (conn == null) {
				Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
				// Open JDBC connection
				conn = DriverManager.getConnection("jdbc:ignite:thin://172.16.10.20;user=ignite;password=ignite");
			return conn;
		}else{
			return null;
		}

	}


}
