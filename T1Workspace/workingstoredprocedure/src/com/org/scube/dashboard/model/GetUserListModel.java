package com.org.scube.dashboard.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;

import compDash.DBconnect;

public class GetUserListModel {
	
	private static  Connection conn = null;
	private static  ResultSet rs;
	private static	PreparedStatement ps;
	private static  Statement CompStmt;
	private static  PreparedStatement pst = null; 

	@SuppressWarnings("unchecked")
	public static JSONObject getAllUserListData(String userID){
		
		JSONObject usersList = new JSONObject();
		
		String SQL_QUERY = "select * from  SCUBE_USER_MASTER where IsDeleted='N'"; 
		
		System.out.println("SQL_QUERY======"+SQL_QUERY);
		
		 try {
			   conn = DBconnect.getConnectionStatus();
			   int i=0;   
				pst = conn.prepareStatement(SQL_QUERY);
				//pst.setString(1, currentDate);
				rs = pst.executeQuery();
			   
			while(rs.next()){
					JSONObject userTypeInfo = new JSONObject();
					System.out.println("11111111111111111111111111");
					userTypeInfo.put("Usr_Id", rs.getString("ID"));
					userTypeInfo.put("Usr_Role", rs.getString("Usr_Role_Tab"));
					userTypeInfo.put("usr_Name", rs.getString("Usr_Name"));
					userTypeInfo.put("Usr_EmailId", rs.getString("Usr_Email"));
					userTypeInfo.put("Usr_MobNo", rs.getString("Usr_Phone_NO"));

					usersList.put("service"+i,userTypeInfo);
				i++;
			}	
				
		
		return usersList;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return usersList;
	}
}
