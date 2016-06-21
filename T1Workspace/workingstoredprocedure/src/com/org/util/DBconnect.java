package com.org.util;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//import org.apache.log4j.Logger;

 
public class DBconnect {
	  private static Connection  conn= null;
	  public static Properties prop = new Properties();
	  public static InputStream inputStream = DBconnect.class.getClassLoader().getResourceAsStream("/database.properties");
	  //public static Logger log = Logger.getLogger(DBconnect.class);
		 
	  public static Connection getConnectionStatus() throws IOException, ClassNotFoundException, SQLException{
    	  
		  prop.load(inputStream); 
    	  String driver = prop.getProperty("driver").trim();  
    	  String url = prop.getProperty("url").trim();  
    	  String user = prop.getProperty("user").trim();  
    	  String password = prop.getProperty("password").trim();
    	  
    	  System.out.println("driver="+driver+"--"+url+"--"+user+"--"+password);
    	  
    	  
    	//  log.info(driver.trim());
//    	  log.info(url.trim());
    	  Class.forName(driver);
    	  conn = DriverManager.getConnection(url,user,password);
	//	  log.info("conn-Object created"+conn);
		  //log.info("DataBase connected");
	    return conn;
}
	  
}
