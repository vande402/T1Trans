package com.org.scube.dashboard.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.org.scube.dashboard.dto.UserInfoData;
import com.org.scube.dashboard.util.MailUtil;



import compDash.DBconnect;

public class LoginModel {

	private static  	Connection 			conn 			= null;
	private static  	PreparedStatement 	pst 			= null; 
	private static  	ResultSet 			rs;
	private static 		JSONObject 			jsonObject;
	HttpSession 							httpSession 	= null;
	HttpServletRequest 						request;
	
	UserInfoData infoData 	= new UserInfoData(); 
	
	
	public static JSONObject validateCredential(String userName,String password) throws ParseException, ClassNotFoundException, IOException, SQLException{

		
		JSONObject userTypeInfo = new JSONObject();
		//userTypeInfo = null;
		 String query = "select * from SCUBE_USER_MASTER where Usr_Email =? and Usr_Password = ? ";
		 try{
		 conn = DBconnect.getConnectionStatus();
		   pst = conn.prepareStatement(query);
		   pst.setString(1, userName);
		   pst.setString(2, password);
		   rs = pst.executeQuery();
		   if(rs.next()){
			   System.out.println("Usr_Role_Tab========================="+rs.getString("Usr_Role_Tab"));
				   userTypeInfo.put("userType", rs.getString("Usr_Role_Tab"));
	               userTypeInfo.put("adminEmail",rs.getString("Usr_Name"));
	               userTypeInfo.put("adminName",rs.getString("Usr_Email"));
               
               return userTypeInfo;
		   }
		   System.out.println("userTypeInfo-----------"+userTypeInfo);
		   System.out.println("userTypeInfo-----##------"+userTypeInfo.isEmpty());
		   if(userTypeInfo.isEmpty())
		   {
			   userTypeInfo = null;
		   }
/*		   else{
			   	  return checkEngineerTable(userName,password);
		   }*/
		   
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return userTypeInfo;

	}
	public UserInfoData createSessionCredential(String userName,String password){
		
		String query = "select * from SCUBE_USER_MASTER where Usr_Email =? and Usr_Password = ? ";
		
		String userRoleValue 	= null;
		String userEmailValue 	= null;
		String userNameValue 	= null;
		String userMobileValue 	= null;
		
		
		
		 try{
			 conn = DBconnect.getConnectionStatus();
			 pst = conn.prepareStatement(query);
			 pst.setString(1, userName);
			 pst.setString(2, password);
			 rs = pst.executeQuery();
		   if(rs.next()){
			   System.out.println("Usr_Role_Tab========================="+rs.getString("Usr_Role_Tab"));
			   userRoleValue 	= rs.getString("Usr_Role_Tab");
			   userNameValue 	= rs.getString("Usr_Name");
			   userEmailValue 	= rs.getString("Usr_Email");
			   userMobileValue 	= rs.getString("Usr_Phone_NO");
		   }
		   infoData.setUserName(userNameValue);
		   infoData.setUserEmailID(userEmailValue);
		   infoData.setUserRole(userRoleValue);
		   infoData.setUserMobile(userMobileValue);
		   
		   System.out.println("user--------Value==="+infoData.getUserRole());
		   
		   System.out.println("userRoleValue==="+userRoleValue);
		   System.out.println("userNameValue==="+userNameValue);
		   System.out.println("userEmailValue==="+userEmailValue);
		   System.out.println("userMobileValue==="+userMobileValue);
		   
		}catch(Exception e){
			 e.printStackTrace();
		}
		return infoData;
	}
	
	public static JSONObject validateEmailCredential(String userName) throws ParseException, ClassNotFoundException, IOException, SQLException{

		
		JSONObject userTypeInfo = new JSONObject();
		//userTypeInfo = null;
		 String query = "select * from SCUBE_USER_MASTER where Usr_Email =? ";
		 try{
		 conn = DBconnect.getConnectionStatus();
		   pst = conn.prepareStatement(query);
		   pst.setString(1, userName);
		   rs = pst.executeQuery();
		   if(rs.next()){
			   System.out.println("Usr_Role_Tab========================="+rs.getString("Usr_Role_Tab"));
				   userTypeInfo.put("Usr_Password", rs.getString("Usr_Password"));
				   userTypeInfo.put("Usr_Email", rs.getString("Usr_Email"));
				   userTypeInfo.put("userNameValue", rs.getString("Usr_Name"));
               
		   }
		   System.out.println("userTypeInfo-----------"+userTypeInfo);
		   System.out.println("userTypeInfo-----##------"+userTypeInfo.isEmpty());
		   
		   
		   
		   
		   if(userTypeInfo.isEmpty())
		   {
			   userTypeInfo = null;
		   }
		   
		   return userTypeInfo;
		   
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return userTypeInfo;

	}
	public static JSONObject validateSendmailCredential (String userEmail) throws ParseException, ClassNotFoundException, IOException, SQLException{

		
		JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(userEmail);
 try{
            // get a String from the JSON object
        String forgetEmail = (String) jsonObject.get("forgetEmail");
        System.out.println("The forgetEmail name is: " +forgetEmail);
        String forgetPassword = (String) jsonObject.get("forgetPassword");
        System.out.println("The forgetEmail name is: " +forgetPassword);
        String emailBody = (String) jsonObject.get("emailBody");
        System.out.println("The first name is: " + emailBody);

		
		
    	String[] recipients = new String[]{forgetEmail};  			
        String[] bccRecipients = new String[]{forgetEmail};
		
        String subject = "Hi this is forget Password Mail";  	        
        String messageBody = emailBody;
		
        MailUtil mailUtil = new MailUtil();
	    mailUtil.sendMail(recipients, bccRecipients, subject, messageBody); 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return jsonObject;

	}
}