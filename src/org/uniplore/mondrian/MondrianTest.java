package org.uniplore.mondrian;

import java.io.PrintWriter;

import mondrian.olap.Connection;
import mondrian.olap.DriverManager;
import mondrian.olap.Query;
import mondrian.olap.Result;

public class MondrianTest {

	public MondrianTest() {
	}

	/**
	 * 测试MDX语句
	 */
	public void testMdx(){
		Connection connection =  DriverManager.getConnection(  
      "Provider=mondrian;"+  
      "Jdbc=jdbc:mysql://192.168.100.172:3306/bankdata; JdbcUser=root;"+  
      "JdbcPassword=mysql;"+  
      "Catalog=file:///G:/SchemaDesigner/xml/bankolap.xml;"+  
      "JdbcDriver=com.mysql.jdbc.Driver", null);  

		Query query = connection.parseQuery(  
				"SELECT {[Measures].[AvgLoanMoney]} ON COLUMNS, {[Gender].[女], [Gender].[男]} ON ROWS FROM [loan]");  
 
		Result result = connection.execute(query);  
		PrintWriter pw = new PrintWriter(System.out);  
		result.print(pw);  
		pw.flush();
	}

}
