package org.uniplore.ignite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.flywaydb.core.Flyway;

public class IgniteTest{
	
	public IgniteTest() {
	}

	public static void main(String[] args) {
		//changeIngitePwd();
		IgniteManager igm =new IgniteManager();
		try {
			//igm.insertDataToIgnite();
			igm.getDataWithAggregate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testFlyway(){
		Flyway flyway = new Flyway();
		flyway.setDataSource("jdbc:mysql://localhost:3306/demo", "root", "mysql");
		//flyway.setBaselineOnMigrate(true);
		flyway.migrate();
	}
	
	public static void runIgnite(){
		CacheConfiguration cacheCfg = new CacheConfiguration("myCache");
		cacheCfg.setCacheMode(CacheMode.PARTITIONED);
		IgniteConfiguration cfg = new IgniteConfiguration();
		cfg.setCacheConfiguration(cacheCfg);
		// Start Ignite node.
		Ignition.start(cfg);
	}
	
	
	public static void SearchDataToIgnite(){
		try{
		Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
		// Open JDBC connection
		Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1;user=ignite;password=uniplore_601");
		// Get data
		Statement stmt = conn.createStatement();
		    ResultSet rs =
		    stmt.executeQuery("SELECT * from" +
		    " datasetmeta");
		    
		      while (rs.next())
		         System.out.println(rs.getInt(1) + ", " + rs.getInt(2));
		    
		stmt.close();
		conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void insertDataToIgnite(){
		try{
			Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
			// Open JDBC connection
			Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");
			// Populate City table
			PreparedStatement stmt =
			conn.prepareStatement("INSERT INTO City (id, name) VALUES (?, ?)");
			    stmt.setLong(1, 1L);
			    stmt.setString(2, "Forest Hill");
			    stmt.executeUpdate();
			    stmt.setLong(1, 2L);
			    stmt.setString(2, "Denver");
			    stmt.executeUpdate();
			    stmt.setLong(1, 3L);
			    stmt.setString(2, "St. Petersburg");
			    stmt.executeUpdate();

			// Populate Person table
			PreparedStatement stmt1 =
			conn.prepareStatement("INSERT INTO Person (id, name, city_id) VALUES (?, ?, ?)");
			    stmt1.setLong(1, 1L);
			    stmt1.setString(2, "John Doe");
			    stmt1.setLong(3, 3L);
			    stmt1.executeUpdate();
			    stmt1.setLong(1, 2L);
			    stmt1.setString(2, "Jane Roe");
			    stmt1.setLong(3, 2L);
			    stmt1.executeUpdate();
			    stmt1.setLong(1, 3L);
			    stmt1.setString(2, "Mary Major");
			    stmt1.setLong(3, 1L);
			    stmt1.executeUpdate();
			    stmt1.setLong(1, 4L);
			    stmt1.setString(2, "Richard Miles");
			    stmt1.setLong(3, 2L);
			    stmt1.executeUpdate();
		}catch(Exception e){
			
		}
	}
	
	public static void ConnectIgnite(){
		// Register JDBC driver.
		try {
			Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
		

		// Open JDBC connection.
			Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1/");

		// Create database tables.
			Statement stmt = conn.createStatement();

		    // Create table based on REPLICATED template.
		    stmt.executeUpdate("CREATE TABLE City (" + 
		    " id LONG PRIMARY KEY, name VARCHAR) " +
		    " WITH \"template=replicated\"");

		    // Create table based on PARTITIONED template with one backup.
		    stmt.executeUpdate("CREATE TABLE Person (" +
		    " id LONG, name VARCHAR, city_id LONG, " +
		    " PRIMARY KEY (id, city_id)) " +
		    " WITH \"backups=1, affinityKey=city_id\"");
		  
		    // Create an index on the City table.
		    stmt.executeUpdate("CREATE INDEX idx_city_name ON City (name)");

		    // Create an index on the Person table.
		    stmt.executeUpdate("CREATE INDEX idx_person_name ON Person (name)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void changeIngitePwd(){
		try{
			Class.forName("org.apache.ignite.IgniteJdbcThinDriver");
			// Open JDBC connection
			Connection conn = DriverManager.getConnection("jdbc:ignite:thin://127.0.0.1;user=ignite;password=uniplore_601");
			// Populate City table
			
			Statement stmt = conn.createStatement();

			stmt.executeUpdate("alter USER \"ignite\" with password 'ignite';");
			System.out.println("正在修改pwd");
			stmt.close();
			conn.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void run() {
		CacheConfiguration cacheCfg = new CacheConfiguration("myCache");
		cacheCfg.setCacheMode(CacheMode.PARTITIONED);
		IgniteConfiguration cfg = new IgniteConfiguration();
		cfg.setCacheConfiguration(cacheCfg);
		// Start Ignite node.
		Ignition.start(cfg);
	}

}
