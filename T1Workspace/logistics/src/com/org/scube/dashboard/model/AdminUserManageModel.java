package com.org.scube.dashboard.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONObject;

import com.org.scube.dashboard.util.AutoGenerateString;
import com.org.scube.dashboard.util.MailUtil;

import compDash.DBconnect;

public class AdminUserManageModel {

	private static  Connection conn = null;
	private static  ResultSet rs;
	private static	PreparedStatement ps;
	private static  Statement CompStmt;
	private static  String  SQL_QUERY = null;
	
	@SuppressWarnings("unchecked")
	public static JSONObject addNewUserCredential(String userName,String userEmail,String userMobile,String role){
		
		JSONObject userTypeInfo = new JSONObject();
		
		   try {
			   conn = DBconnect.getConnectionStatus();
			   
		        System.out.println("userEmail--------------------"+userEmail);
		        if(findEmailExistance(userEmail))
		        {
		        		System.out.println("email id is already in use");
		        		userTypeInfo.put("result","alreadyUse");
					    return userTypeInfo ;
		        }
		        else
		        {
		        	String[] recipients = new String[]{userEmail};  			
			        String[] bccRecipients = new String[]{userEmail};
			        
					String userPassword="";
					String Email = userEmail;
					int emaillength = Email.length();
					userPassword = AutoGenerateString.randomString(emaillength);			
			  
					 
			        String subject = "Hi this is test Mail";  	        
			        String messageBody = "Account created with the username-- as "+userEmail+" And your password is "+userPassword; 
		        	
			        System.out.println("insert into SCUBE_USER_MASTER " +
			        		"(Usr_Role_Tab,Usr_Name,Usr_Email, Usr_Phone_NO,Usr_Password,Created_By,Created_Dt,Updated_By,Updated_Dt,IsDeleted)"+
			        		"values('"+role+"','"+userName+"','"+userEmail+"','"+userMobile+"','"+userPassword+
		        					"','"+userEmail+"',NOW(),'"+userEmail+"',NOW(),'N');");

			        
			        ps=conn.prepareStatement("insert into SCUBE_USER_MASTER " +
			        		"(Usr_Role_Tab,Usr_Name,Usr_Email, Usr_Phone_NO,Usr_Password,Created_By,Created_Dt,Updated_By,Updated_Dt,IsDeleted)"+
			        		"values('"+role+"','"+userName+"','"+userEmail+"','"+userMobile+"','"+userPassword+
		        					"','"+userEmail+"',NOW(),'"+userEmail+"',NOW(),'N');");

			        
			        
		        	/* ps=conn.prepareStatement("insert into SCUBE_USER_MASTER " +
		        			"values("+role+","+userName+","+userEmail+","+userMobile+","+userPassword+
		        					","+userEmail+",NOW(),"+userEmail+",NOW(),'N'");  
		        	
			       ps=conn.prepareStatement("insert into SCUBE_USER_MASTER values(?,?,?,?,?,?,NOW(),?,NOW(),?)");  
					  
					ps.setString(1,role);  //role
					ps.setString(2,userName);  //name
					ps.setString(3,userEmail);  //email
					ps.setString(4,userMobile); //ph no
					ps.setString(5,userPassword);  //pwd 
					ps.setString(6,userEmail);  //created by
					ps.setString(7,userEmail);//update by
					ps.setString(8,"N");  //is delete
					
					*/
					
					int i=ps.executeUpdate();  
					if(i>0)  
					System.out.println("successfully inserted...");  
					/*sending mail to new user starts*/
			 						        
			    	MailUtil mailUtil = new MailUtil();
				    mailUtil.sendMail(recipients, bccRecipients, subject, messageBody); 
			        
					/*sending mail to new user ends*/
				    userTypeInfo.put("result","success");
				    return userTypeInfo ;
		        }
		   } catch (Exception e) {
				e.printStackTrace();
			}
		return userTypeInfo;
	}
	
	
	@SuppressWarnings("unchecked")
	public static JSONObject editUserCredential(String userName,String userEmail,String userMobile,String role,String userId){
		
		JSONObject userTypeInfo = new JSONObject();
		   try {
			   conn = DBconnect.getConnectionStatus();
			   
		        System.out.println("userEmail--------------------"+userEmail);
		 
		        SQL_QUERY = "Update SCUBE_USER_MASTER SET Usr_Name = '"+userName+"',Usr_Phone_NO = '"+userMobile+"'," +
		        			"Usr_Role_Tab = '"+role+"', Updated_By='"+userEmail+"',Updated_Dt=GETDATE() where ID = '"+userId+"' and Usr_Email = '"+userEmail+"'";  
					  
					int i=CompStmt.executeUpdate(SQL_QUERY);
					if(i>0)  
					System.out.println("successfully inserted...");  
			 						        
				    userTypeInfo.put("result","success");
				    return userTypeInfo ;
		//        }
	   // else end 
		   } catch (Exception e) {
				e.printStackTrace();
			}
		return userTypeInfo;
	}
	
	
	@SuppressWarnings("unchecked")
	public static JSONObject deleteUserCredential(String userId){
		
		JSONObject userTypeInfo = new JSONObject();
		
		   try {
			   conn = DBconnect.getConnectionStatus();
		
/*		        ps=conn.prepareStatement("Update SCUBE_USER_MASTER SET Updated_By='"+userEmail+"',Updated_Dt=GETDATE(),IsDeleted='N' where ID = '"+userId+"'");  
				System.out.println("Update SCUBE_USER_MASTER SET Updated_By='"+userEmail+"',Updated_Dt=GETDATE(),IsDeleted='N' where ID = '"+userId+"'");*/
			
/*		        ps=conn.prepareStatement("Update SCUBE_USER_MASTER SET Updated_Dt=GETDATE(),IsDeleted='Y' where ID = '"+userId+"'");  
				System.out.println("Update SCUBE_USER_MASTER SET Updated_Dt=GETDATE(),IsDeleted='Y' where ID = '"+userId+"'");*/
				
			   SQL_QUERY = "delete SCUBE_USER_MASTER where ID='" + userId +"'"; 
		       
			   System.out.println("SQL_QUERY==="+SQL_QUERY);
			       
				CompStmt=conn.createStatement();
					
			        
				int i=CompStmt.executeUpdate(SQL_QUERY);  
				if(i>0)  
				System.out.println("successfully inserted...");  
				userTypeInfo.put("result","success");
			    return userTypeInfo ;
		   } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return userTypeInfo;
	}
	
	
	
	
	public static boolean findEmailExistance(String EmailId) 
	{
		
		boolean flag=false;
		int icount=0;
		try
			{
			
				conn=DBconnect.getConnectionStatus();
		        SQL_QUERY = "Select count(*) as Total from SCUBE_USER_MASTER  where Usr_Email='" + EmailId +"'"; 
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

	public static JSONObject SelectAllData(){
		
		JSONObject usersList = new JSONObject();
		
		   try {
			   String ID;
			   String A1;
				conn=DBconnect.getConnectionStatus();
		        SQL_QUERY = "select id, s.A1 , NamCount from UploadSecondFile s inner join " + 
				"(select A1, COUNT(*) AS NamCount from UploadSecondFile group by A1) as b on s.A1 = b.A1 "; 
		        System.out.println("SQL_QUERY==="+SQL_QUERY);
				CompStmt=conn.createStatement();
		        int i =0;
				rs = CompStmt.executeQuery(SQL_QUERY);
		        while(rs.next())
		        {
					JSONObject userTypeInfo = new JSONObject();
					
		            	userTypeInfo.put("ID", rs.getString("ID"));
		            	
		            	userTypeInfo.put("A1", rs.getString("A1"));
		            	usersList.put("service"+i,userTypeInfo);
		            	i++;
		        }
					/*sending mail to new user ends*/
		        usersList.put("result","success");
				    return usersList ;
		   } catch (Exception e) {
				e.printStackTrace();
			}
		return usersList;
	}
	public static JSONObject SelectselectedData(String selCou){
		
		JSONObject usersList = new JSONObject();
		
		   try {
			   String ID;
			   String A1;
				conn=DBconnect.getConnectionStatus();
		        SQL_QUERY = "select * from UploadSecondFile where id = '"+selCou+"'"; 
		        System.out.println("SQL_QUERY==="+SQL_QUERY);
				CompStmt=conn.createStatement();
		        int i =0;
				rs = CompStmt.executeQuery(SQL_QUERY);
		        while(rs.next())
		        {
					JSONObject userTypeInfo = new JSONObject();
	            	userTypeInfo.put("ID", rs.getString("ID"));
	            	userTypeInfo.put("A2", rs.getString("A2"));
	            	userTypeInfo.put("A3", rs.getString("A3"));
	            	userTypeInfo.put("A4", rs.getString("A4"));
	            	userTypeInfo.put("A5", rs.getString("A5"));
	            	userTypeInfo.put("A6", rs.getString("A6"));
//	            	userTypeInfo.put("firID", rs.getString("firID"));
//	            	userTypeInfo.put("secID", rs.getString("secID"));
	            	
	            	usersList.put("service"+i,userTypeInfo);
	            	i++;
		        }
		        usersList.put("result","success");
				    return usersList ;
		   } catch (Exception e) {
				e.printStackTrace();
			}
		return usersList;
	}
}
