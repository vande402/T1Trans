package com.org.scube.dashboard.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import compDash.DBconnect;
/**
 * @author SCUBE
 *
 */
public class SaveSettingModel {
	private static Logger log = Logger.getLogger(SaveSettingModel.class);
	private static Connection conn ;
	private static PreparedStatement pst ;
	private static ResultSet rs ;
	private static Statement CompStmt;
	private static String SQL_QUERY = null;
	
	
	
	public static String SaveOrgSettingDataToDB(String logInUser,String jsonString) throws ParseException, ClassNotFoundException, IOException, SQLException{
		
		String reString = "faild";
		JSONParser jsonParser = new JSONParser();
		Object object=jsonParser.parse(jsonString);
		JSONObject jsonObject=(JSONObject)object;
		
		log.info(jsonObject);
		String orgImage = (String)jsonObject.get("orgImage");
		String unSolDay = (String)jsonObject.get("unSolDay");
		
		conn = DBconnect.getConnectionStatus();
		
		int status;
		
		if( findEmailExistance()){
			
			SQL_QUERY = "Update ADMIN_VIEW_SETTINGS SET Com_Logo = '"+orgImage+"',Unresolved_Day = '"+unSolDay+"'";  
			  
			status=CompStmt.executeUpdate(SQL_QUERY);
			
		}else{
		
			SQL_QUERY = "insert into ADMIN_VIEW_SETTINGS (Com_Logo,Unresolved_Day,Created_By,Created_Dt,Updated_By,Updated_Dt) values(?,?,?,getdate(),?,getdate())";
				
				System.out.println(SQL_QUERY);
				
				pst = conn.prepareStatement(SQL_QUERY);
				pst.setString(1,orgImage);
				pst.setString(2,unSolDay);
				pst.setString(3,logInUser);
				pst.setString(4,logInUser);
		
			status=pst.executeUpdate();
			log.info("updateStatus"+status);
			
		}
		
		if(status ==1){
			reString = "success";
		}
		
		return reString;
	}
	
// select data image all other values  
	@SuppressWarnings("unchecked")
	static public JSONObject SelectOrgSettingDataToDB(){
		
		JSONObject usersList = new JSONObject();
		int i=0;
		
		SQL_QUERY = "select * from ADMIN_VIEW_SETTINGS";
		
		try {
			conn = DBconnect.getConnectionStatus();
			pst = conn.prepareStatement(SQL_QUERY);
			rs = pst.executeQuery();
			
				while(rs.next()){
					JSONObject userTypeInfo = new JSONObject();

					userTypeInfo.put("ID", rs.getString("ID"));
					userTypeInfo.put("Com_Logo", rs.getString("Com_Logo"));
					userTypeInfo.put("Unresolved_Day", rs.getString("Unresolved_Day"));
	
					usersList.put("result"+i,userTypeInfo);
				i++;
			}	
				if(usersList.isEmpty()){
					JSONObject userTypeInfo = new JSONObject();
					System.out.println("this is empty");
					userTypeInfo.put("empty", "empty");
					usersList.put("result",userTypeInfo);
				}
			return usersList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usersList;
	}
	
	//Data Exist or not 
	
	public static boolean findEmailExistance() 
	{
		boolean flag=false;
		int icount=0;
		try
		{
			conn=DBconnect.getConnectionStatus();
			
	        SQL_QUERY = "Select count(*) as Total from ADMIN_VIEW_SETTINGS "; 
	        System.out.println("SQL_QUERY==="+SQL_QUERY);
	       
			CompStmt=conn.createStatement();
	        
			rs = CompStmt.executeQuery(SQL_QUERY);
	        while(rs.next())
	        {
	            	icount = rs.getInt("Total");
	        }
	        System.out.println("icount="+icount);
	        
	        if (icount > 0) 
	        {
	           flag=true;
	        }
		}catch (Exception e){
		e.printStackTrace();
		}
		System.out.println("flag===="+flag);
		return flag;
	}
}


