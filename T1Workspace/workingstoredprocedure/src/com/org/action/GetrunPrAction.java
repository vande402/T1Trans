package com.org.action;

import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONObject;


import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import com.opensymphony.xwork2.ActionSupport;
import com.org.util.DBconnect;

public class GetrunPrAction extends ActionSupport{

	private static Connection  con= null;
	PrintWriter out = null;
	String message = null;
	Statement stmt = null;
	HttpServletRequest request = null;
	private static  PreparedStatement pst = null; 
	private static  ResultSet rs;
	
	
	 public String runprocedure(){
			
		 JSONObject usersList = new JSONObject();
		 
			System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
			try{
				request = ServletActionContext.getRequest();
				out = ServletActionContext.getResponse().getWriter();
				String text1 = request.getParameter("text1");
				
				con = DBconnect.getConnectionStatus();
				//pst	=con.prepareStatement("{call rohit();}");
				//pst.setString(1,getSelDate);			
				CallableStatement pst = con.prepareCall("{call sp_T1_getCostMatrix()}");


				rs	=pst.executeQuery();
				
				/*int i	= 0;  
			while(rs.next()){
					JSONObject userTypeInfo = new JSONObject();
					System.out.println("11111111111111111111111111"+i);
					System.out.println("11111111111111111111111111"+rs.getString("A1"));
				    userTypeInfo.put("ReID", rs.getString("A1"));
					userTypeInfo.put("ReON", rs.getString("A2"));
					userTypeInfo.put("NoOfHost", rs.getString("A3"));
	
					usersList.put("Endpoi"+i,userTypeInfo);
				i++;
			}	*/
			}catch(Exception e){
				e.printStackTrace();
			}
		//	System.out.println("message==="+usersList);
			//out.println(usersList);	
			return null;
		}

}
