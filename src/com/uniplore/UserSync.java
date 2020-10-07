package com.uniplore;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Scanner;

public class UserSync {

	private static String srcUser="root";
	private static String srcPassword="***";
	private static String srcDriver="com.mysql.jdbc.Driver";
	private static String srcUrl="jdbc:mysql://172.16.10.20:3306/demo";
	
	private static String targetUser="root";
	private static String targetPassword="***";
	private static String targetDriver="com.mysql.jdbc.Driver";
	private static String targetUrl="jdbc:mysql://172.16.10.20:3306/demo";
	
	
	private static String dimUser="root";
	private static String dimPassword="***";
	private static String dimDriver="com.mysql.jdbc.Driver";
	private static String dimUrl="jdbc:mysql://172.16.10.20:3306/demo";
	
	
	private static int upUserNum=0;
	private static int insUserNum=0;
	private static int errorUserNum=0;
	private static int upGroupNum=0;
	private static int insGroupNum=0;
	private static int errorGroupNum=0;
	private static int upUserGroupNum=0;
	private static int insUserGroupNum=0;
	
	private static int errorOrgNum=0;
	private static int upOrgNum=0;
	private static int insOrgNum=0;
	
	public UserSync() {
	}
	
	public void getConfig(){
		Properties pro = new Properties();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("syncConfig.properties");
		try {
			pro.load(inputStream);
			srcUser=pro.getProperty("srcUser");
			srcPassword=pro.getProperty("srcPassword");
			srcUrl = pro.getProperty("srcUrl");
			srcDriver = pro.getProperty("srcDriver");
			
			targetUser=pro.getProperty("targetUser");
			targetPassword=pro.getProperty("targetPassword");
			targetUrl=pro.getProperty("targetUrl");	
			targetDriver = pro.getProperty("targetDriver");
			
			dimUser=pro.getProperty("dimUser");
			dimPassword=pro.getProperty("dimPassword");
			dimUrl=pro.getProperty("dimUrl");	
			dimDriver = pro.getProperty("dimDriver");
			inputStream.close();
		} catch (Exception e) {
			System.out.println("配置文件读取失败，将使用内置配置");
		} 
	}
	
	public void syncUserData(){
		upUserNum=0;
		insUserNum=0;
		try{
			Class.forName(srcDriver);
			Connection srcConn = DriverManager.getConnection(srcUrl, srcUser, srcPassword);
			PreparedStatement ps = srcConn.prepareStatement("select LOGIN_ID,LOGIN_NAME,PASS_WORD,"
					+ "DEPARTMENT,CREATE_DATE,MODIF_USER,MODIF_DATE,STATUS,PBC_CODE,"
					+ "USER_TYPE from t_sc_login_user");
			ResultSet rs = ps.executeQuery();
			
			Class.forName(targetDriver);
			Connection targetConn = DriverManager.getConnection(targetUrl, targetUser, targetPassword);
			PreparedStatement targetInsertPs = targetConn.prepareStatement("insert into charge_user("
					+ "type,user_type,parent_id,name,email,password,create_date,account_state,user_ext_info,user_org_code)"
					+ "values(4,?,1,?,?,?,?,?,?,?)");
			
			PreparedStatement targetUpdatePs = targetConn.prepareStatement("update charge_user "
					+ "set user_type=?,parent_id=1,email=?,password=?,create_date=?,account_state=?"
					+ " where name=? and user_ext_info=? and user_org_code=?");
			
			while(rs.next()){
				try{
					String userName = rs.getString("LOGIN_NAME");
					String deptId=rs.getString("DEPARTMENT");
					String orgCode=rs.getString("PBC_CODE");
				if(!isExistUser(userName,deptId,orgCode)){
					//insert
					String userType="B";
					
						userType = rs.getString("USER_TYPE");
						if(userType.contains("A")){
							targetInsertPs.setString(1, "GA");
						}else{
							targetInsertPs.setString(1, "U");
						}
						
						targetInsertPs.setString(2, userName);
						targetInsertPs.setString(3, userName);
						targetInsertPs.setString(4, Des3Util.encode(rs.getString("PASS_WORD")));
						targetInsertPs.setDate(5, rs.getDate("CREATE_DATE"));
						targetInsertPs.setInt(6, Integer.parseInt(rs.getString("STATUS")));
						targetInsertPs.setString(7,deptId);
						targetInsertPs.setString(8,orgCode);
						targetInsertPs.addBatch();
						insUserNum++;							
				}else{
					//update
					String userType="B";
						userType = rs.getString("USER_TYPE");
						if(userType.contains("A")){
							targetUpdatePs.setString(1,"GA");
						}else{
							targetUpdatePs.setString(1, "U");
						}
						
						targetUpdatePs.setString(2, userName);
						targetUpdatePs.setString(3, Des3Util.encode(rs.getString("PASS_WORD")));
						targetUpdatePs.setDate(4, rs.getDate("CREATE_DATE"));
						targetUpdatePs.setInt(5, Integer.parseInt(rs.getString("STATUS")));
						targetUpdatePs.setString(6,userName);
						targetUpdatePs.setString(7,deptId);
						targetUpdatePs.setString(8,orgCode);
						targetUpdatePs.addBatch();
						upUserNum++;
					}
				}catch(Exception e){
						errorUserNum++;
				}					
					
			}
			targetInsertPs.executeBatch();
			targetUpdatePs.executeBatch();
			rs.close();
			ps.close();
			targetInsertPs.close();
			targetUpdatePs.close();
			srcConn.close();
			targetConn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean isExistUser(String userName,String department,String orgCode){
		boolean flag = false;
		try{
			Class.forName(targetDriver);
			Connection targetConn = DriverManager.getConnection(targetUrl, targetUser, targetPassword);
			PreparedStatement ps = targetConn.prepareStatement("select count(*) from charge_user "
					+ "where name=? and user_ext_info=? and user_org_code=?");
			ps.setString(1, userName);
			ps.setString(2, department);
			ps.setString(1, orgCode);
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			if(rs.getInt(1)>0){
				flag = true;
			}
			rs.close();
			ps.close();
			targetConn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean isExistGroup(String department){
		boolean flag = false;
		try{
			Class.forName(targetDriver);
			Connection targetConn = DriverManager.getConnection(targetUrl, targetUser, targetPassword);
			PreparedStatement ps = targetConn.prepareStatement("select count(*) from group_table where group_extinfo=?");
			ps.setString(1, department);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			if(rs.getInt(1)>0){
				flag = true;
			}
			rs.close();
			ps.close();
			targetConn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	public void syncGroupData(){
		upUserGroupNum=0;
		insUserGroupNum=0;
		try{
			Class.forName(srcDriver);
			Connection srcConn = DriverManager.getConnection(srcUrl, srcUser, srcPassword);
			PreparedStatement ps = srcConn.prepareStatement("select DEPARTMENT,DEPARTMENT_DESC,"
					+ "CREATE_USER,CREATE_DATE,MODIF_USER,MODIF_DATE,STATUS,ORG_DEPT_MAPPING_FLAG,"
					+ "PARENT_DEPT from t_sc_department");
			ResultSet rs = ps.executeQuery();
			
			Class.forName(targetDriver);
			Connection targetConn = DriverManager.getConnection(targetUrl, targetUser, targetPassword);
			PreparedStatement targetPs = targetConn.prepareStatement("insert into group_table("
					+ "group_name,create_date,modif_date,status,group_extinfo)values(?,?,?,?,?)");
			
			PreparedStatement targetUpdatePs = targetConn.prepareStatement("update group_table "
					+ "set group_name=?,create_date=?,modif_date=?,status=?"
					+ " where group_extinfo=? ");
			
			while(rs.next()){
				
				try{
				String department = rs.getString("DEPARTMENT");
				if(!isExistGroup(department)){
					//insert
					targetPs.setString(1, rs.getString("DEPARTMENT_DESC"));
					targetPs.setDate(2, rs.getDate("CREATE_DATE"));
					targetPs.setDate(3, rs.getDate("MODIF_DATE"));
					targetPs.setInt(4,Integer.parseInt(rs.getString("STATUS")));
					targetPs.setString(5, department);
					targetPs.addBatch();
					insGroupNum++;
				}else{
					//update
					targetUpdatePs.setString(1, rs.getString("DEPARTMENT_DESC"));
					targetUpdatePs.setDate(2, rs.getDate("CREATE_DATE"));
					targetUpdatePs.setDate(3, rs.getDate("MODIF_DATE"));
					targetUpdatePs.setInt(4,Integer.parseInt(rs.getString("STATUS")));
					targetUpdatePs.setString(5, department);
					targetUpdatePs.addBatch();
					upGroupNum++;
				}
			}catch(Exception e){
				errorGroupNum++;
			}
				
			}
			targetPs.executeBatch();
			targetUpdatePs.executeBatch();
			rs.close();
			ps.close();
			targetUpdatePs.close();
			targetPs.close();
			srcConn.close();
			targetConn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean isExistUserGroup(int userId,int groupId){
		boolean flag = false;
		try{
			Class.forName(targetDriver);
			Connection targetConn = DriverManager.getConnection(targetUrl, targetUser, targetPassword);
			PreparedStatement ps = targetConn.prepareStatement("select count(*) from user_group "
					+ "where user_id=? and group_id=?");
			ps.setInt(1, userId);
			ps.setInt(2, groupId);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			if(rs.getInt(1)>0){
				flag = true;
			}
			rs.close();
			ps.close();
			targetConn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	public void syncUserGroupData(){
		upUserGroupNum=0;
		insUserGroupNum=0;
		try{
			Class.forName(targetDriver);
			Connection targetConn = DriverManager.getConnection(targetUrl, targetUser, targetPassword);
			PreparedStatement ps = targetConn.prepareStatement("select id,account_state,group_id,"
					+ "group_name from charge_user as u,group_table as g "
					+ "where u.user_ext_info=g.group_extinfo");
			ResultSet rs = ps.executeQuery();
			
			PreparedStatement targetPs = targetConn.prepareStatement("insert into user_group("
					+ "user_id,group_id,group_name,confirmed)values(?,?,?,?)");
			
			PreparedStatement targetUpdatePs = targetConn.prepareStatement("update user_group "
					+ "set group_name=?,confirmed=?"
					+ " where user_id=? and group_id=? ");
			
			while(rs.next()){
				int userId = rs.getInt("id");
				int groupId = rs.getInt("group_id");
				if(!isExistUserGroup(userId,groupId)){
					//insert
					targetPs.setInt(1, userId);
					targetPs.setInt(2, groupId);
					targetPs.setString(3, rs.getString("group_name"));
					targetPs.setInt(4,rs.getInt("account_state"));
					targetPs.addBatch();
					insUserGroupNum++;
				}else{
					//update
					targetUpdatePs.setString(1, rs.getString("group_name"));
					targetUpdatePs.setInt(2,rs.getInt("account_state"));
					targetUpdatePs.setInt(3, userId);
					targetUpdatePs.setInt(4, groupId);
					targetUpdatePs.addBatch();
					upUserGroupNum++;
				}
				
				
			}
			targetPs.executeBatch();
			targetUpdatePs.executeBatch();
			rs.close();
			ps.close();
			targetUpdatePs.close();
			targetPs.close();
			targetConn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public void syncOrgData(){
		upOrgNum=0;
		insOrgNum=0;
		try{
			Class.forName(dimDriver);
			Connection dimConn = DriverManager.getConnection(dimUrl, dimUser, dimPassword);
			PreparedStatement ps = dimConn.prepareStatement("select DISTINCT ORG_ID,ORG_DSCR"
					+ " from t_org_biz_lvl where IS_ACTIVE='1' GROUP BY ORG_ID");
			ResultSet rs = ps.executeQuery();
			
			Class.forName(targetDriver);
			Connection targetConn = DriverManager.getConnection(targetUrl, targetUser, targetPassword);
			PreparedStatement targetInsertPs = targetConn.prepareStatement("insert into pbc_code_desc("
					+ "pbc_code,pbc_org_desc)"
					+ "values(?,?)");
			
			PreparedStatement targetUpdatePs = targetConn.prepareStatement("update pbc_code_desc "
					+ "set pbc_org_desc=?"
					+ " where pbc_code=?");
			
			while(rs.next()){
				try{
					String pbcCode=rs.getString("ORG_ID");
				
					if(!isExistPbcCode(pbcCode)){
					//insert
	
						targetInsertPs.setString(1, pbcCode);
						targetInsertPs.setString(2, rs.getString("ORG_DSCR"));
						targetInsertPs.addBatch();
						insOrgNum++;							
					}else{
					//update
							
						targetUpdatePs.setString(1, pbcCode);
						targetUpdatePs.setString(2, rs.getString("ORG_DSCR"));
						targetUpdatePs.addBatch();
						upOrgNum++;
					}
				}catch(Exception e){
						errorOrgNum++;
				}					
					
			}
			targetInsertPs.executeBatch();
			targetUpdatePs.executeBatch();
			rs.close();
			ps.close();
			targetInsertPs.close();
			targetUpdatePs.close();
			dimConn.close();
			targetConn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean isExistPbcCode(String pbcCode){
		boolean flag = false;
		try{
			Class.forName(targetDriver);
			Connection targetConn = DriverManager.getConnection(targetUrl, targetUser, targetPassword);
			PreparedStatement ps = targetConn.prepareStatement("select count(*) from pbc_code_desc "
					+ "where pbc_code=?");
			ps.setString(1, pbcCode);
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			if(rs.getInt(1)>0){
				flag = true;
			}
			rs.close();
			ps.close();
			targetConn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	public static void main(String[] args) {
		UserSync usc = new UserSync();
		usc.getConfig();
		
		while(true){
			System.out.println("*****************************");
			System.out.println("***1     加载配置                             ");
			System.out.println("***2     同步账号数据                      ");
			System.out.println("***3     同步机构数据                      ");
			System.out.println("***0     退出系统                             ");
			System.out.println("*****************************");
			System.out.println("请选择（0-3）:");
			Scanner input = new Scanner(System.in);
			int cs = input.nextInt();
			switch(cs){
			case 1:
				System.out.println("读取配置文件");
				usc.getConfig();
				break;
			case 2:
				System.out.println("开始同步");
				System.out.println("同步角色数据");
				usc.syncGroupData();
				System.out.println("角色同步数据完毕，更新"+upGroupNum+"条记录，新增"+insGroupNum+"条记录,不符合规则为"+errorGroupNum+"条");
				System.out.println("同步用户数据");
				usc.syncUserData();
				System.out.println("用户数据同步完毕，更新"+upUserNum+"条记录，新增"+insUserNum+"条记录,不符合规则为"+errorUserNum+"条");
				System.out.println("增加用户权限数据");
				usc.syncUserGroupData();
				System.out.println("用户权限数据同步完毕，更新"+upUserGroupNum+"条记录，新增"+insUserGroupNum+"条记录。");
				break;
			case 3:
				System.out.println("同步机构数据");
				usc.syncOrgData();
				System.out.println("机构数据同步完毕，更新"+upOrgNum+"条记录，新增"+insOrgNum+"条记录,不符合规则为"+errorOrgNum+"条");
				break;
			case 0:
				System.out.println("退出！");
				System.exit(0);
				break;
			default:
				System.out.println("选项错误，请重新输入！");
				break;
			}
		}		
		
	}

}
