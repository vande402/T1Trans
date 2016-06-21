package com.org.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.org.util.DBconnect;

public class GetUpdateTableAction extends ActionSupport{

	private static Connection  con= null;
	PrintWriter out = null;
	String message = null;
	Statement stmt = null;
	HttpServletRequest request = null;
	HttpServletResponse response = null;
	
	public void test(){
		
		PrintWriter pw =null;
		JSONObject usersList = new JSONObject();
		System.out.println("t-e-s-t-------------------------");
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		
		String selval = request.getParameter("selval");
		String query2 =null;
		
		if(selval.equals("1") )
			query2 = "SELECT * from merg";
		if(selval.equals("2"))
			query2 = "SELECT * from merg";
		
		
		try {
			pw = response.getWriter();
			con = DBconnect.getConnectionStatus();
			
			stmt=con.createStatement();
	    	ResultSet rs = stmt.executeQuery(query2);
	    	int i =0;
	    	
	    			
	    			while(rs.next())
			        {
		            	//idName = rs.getString("A1");
		            	
		            	JSONObject userTypeInfo = new JSONObject();
						System.out.println("11111111111111111111111111");
						userTypeInfo.put("idC",selval );
						userTypeInfo.put("id", rs.getString("id"));
						userTypeInfo.put("A1", rs.getString("A1"));
						userTypeInfo.put("A2", rs.getString("A2"));
						userTypeInfo.put("A3", rs.getString("A3"));
						userTypeInfo.put("A4", rs.getString("A4"));
						userTypeInfo.put("A5", rs.getString("A5"));
						userTypeInfo.put("A6", rs.getString("A6"));
						userTypeInfo.put("A7", rs.getString("A7"));
						userTypeInfo.put("A8", rs.getString("A8"));
						userTypeInfo.put("A9", rs.getString("A9"));
						userTypeInfo.put("A10", rs.getString("A10"));
						userTypeInfo.put("A11", rs.getString("A11"));
						userTypeInfo.put("A12", rs.getString("A12"));
						userTypeInfo.put("A13", rs.getString("A13"));
						userTypeInfo.put("A12", rs.getString("A14"));
						userTypeInfo.put("A15", rs.getString("A15"));
						userTypeInfo.put("A16", rs.getString("A16"));
						userTypeInfo.put("A17", rs.getString("A17"));
						userTypeInfo.put("A18", rs.getString("A18"));
						userTypeInfo.put("A19", rs.getString("A19"));
						userTypeInfo.put("A20", rs.getString("A20"));
						userTypeInfo.put("A21", rs.getString("A21"));
						userTypeInfo.put("A22", rs.getString("A22"));
						userTypeInfo.put("A23", rs.getString("A23"));
						userTypeInfo.put("A24", rs.getString("A24"));
						userTypeInfo.put("A25", rs.getString("A25"));
						userTypeInfo.put("A26", rs.getString("A26"));
						userTypeInfo.put("A27", rs.getString("A27"));
						userTypeInfo.put("A28", rs.getString("A28"));
						userTypeInfo.put("A29", rs.getString("A29"));
						userTypeInfo.put("A30", rs.getString("A30"));
						userTypeInfo.put("A31", rs.getString("A31"));
						userTypeInfo.put("A32", rs.getString("A32"));
						userTypeInfo.put("A33", rs.getString("A33"));
						userTypeInfo.put("A34", rs.getString("A34"));
						userTypeInfo.put("A35", rs.getString("A35"));
						userTypeInfo.put("A36", rs.getString("A36"));

						if(selval.equals("1") ){
							
							userTypeInfo.put("A37", rs.getString("A37"));
							userTypeInfo.put("A38", rs.getString("A38"));
							userTypeInfo.put("A39", rs.getString("A39"));
							userTypeInfo.put("A40", rs.getString("A40"));
							userTypeInfo.put("A41", rs.getString("A41"));
							userTypeInfo.put("A42", rs.getString("A42"));
							userTypeInfo.put("A43", rs.getString("A43"));
							userTypeInfo.put("A44", rs.getString("A44"));
							userTypeInfo.put("A45", rs.getString("A45"));
							userTypeInfo.put("A46", rs.getString("A46"));
							userTypeInfo.put("A47", rs.getString("A47"));
							userTypeInfo.put("A48", rs.getString("A48"));
							userTypeInfo.put("A49", rs.getString("A49"));
							userTypeInfo.put("A50", rs.getString("A50"));

						}
						usersList.put("service"+i,userTypeInfo);
					i++;
			        }
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 

		pw.println(usersList);    
		System.out.println("rohit is better than keshav " + selval);
	}
	
	
	
	
	public String advertisersCategory1(){
		
		System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
		try{
			request = ServletActionContext.getRequest();
			String text1 = request.getParameter("text1");
			String text2 = request.getParameter("text2");
			String text3 = request.getParameter("text3");
			String text4 = request.getParameter("text4");
			String text5 = request.getParameter("text5");
			String text6 = request.getParameter("text6");
			String text7 = request.getParameter("text7");
			String text8 = request.getParameter("text8");
			String text9 = request.getParameter("text9");
			String text10 = request.getParameter("text10");
			String text11 = request.getParameter("text11");
			String text12 = request.getParameter("text12");
			String allSecName = request.getParameter("allSecName");
			String userffid = request.getParameter("editIdT");
			
			
			
			System.out.println("------text1---------"+text1);
			System.out.println("------text2---------"+text2);
			System.out.println("------text3---------"+text3);
			System.out.println("------text4---------"+text1);
			System.out.println("------text5---------"+text4);
			System.out.println("------text6---------"+text6);
			System.out.println("------text7---------"+text7);
			System.out.println("------text8---------"+text8);
			System.out.println("------text9---------"+text9);
			System.out.println("------text10---------"+text10);
			System.out.println("------text11---------"+text11);
			System.out.println("------text12---------"+text12);
			System.out.println("------allSecName---------"+allSecName);
			System.out.println("------userffid---------"+userffid);
			
			
			
			
			
			//String usersfid = request.getParameter("usersfid");
			
			System.out.println("this is edit row---- === " + userffid);
			
			 con = DBconnect.getConnectionStatus();
			 
			 String SQL_QUERY = "select * from UploadSecondFile where id = '"+allSecName+"'"; 
		        System.out.println("SQL_QUERY==="+SQL_QUERY);
		        stmt=con.createStatement();
		    	ResultSet rs = stmt.executeQuery(SQL_QUERY);
		    	String idName ="";
		        while(rs.next())
		        {
	            	idName = rs.getString("A1");
		        }
			 System.out.println("idName -------"+idName);
			 
		    //stmt = con.createStatement();
		    
		    if (con != null) {
                DatabaseMetaData dm = (DatabaseMetaData) con.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
		    
		    
		    String query = "update FinalUploadFile set  " +
		    		"A1='"+text1+"',A2='"+text2+"',A3='"+text3+"',A4='"+text4+"',A5='"+text5+"',A6='"+text6+
		    		"', A7='"+idName+"',A8='"+text8+"',A9='"+text9+"',A10='"+text10+"',A11='"+text11+"',A12='"+text12+"'" +
		    		" , secID = '"+allSecName+"' where id='"+userffid+"';";
				//pstCheckExist = con.prepareStatement(query);
		    
			System.out.println("------query1--------"+query);
				
			     stmt.executeUpdate(query);
			  
		     
		     
		     message=SUCCESS;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("message==="+message);
		out.println(message);	
		return null;
	}
	
public void title(){
		
		PrintWriter pw =null;
		JSONObject usersList = new JSONObject();
		System.out.println("t-e-s-t-------------------------");
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
		
		String selval = request.getParameter("selval");
		String query2 =null;
		
	
			query2 = "SELECT * from title";
		
		
		
		try {
			pw = response.getWriter();
			con = DBconnect.getConnectionStatus();
			
			stmt=con.createStatement();
	    	ResultSet rs = stmt.executeQuery(query2);
	    	int i =0;
	    	
	    			
	    			while(rs.next())
			        {
		            	//idName = rs.getString("A1");
		            	
		            	JSONObject userTypeInfo = new JSONObject();
						System.out.println("11111111111111111111111111");
						userTypeInfo.put("idC",selval );
						userTypeInfo.put("id", rs.getString("id"));
						userTypeInfo.put("A1", rs.getString("A1"));
						userTypeInfo.put("A2", rs.getString("A2"));
						userTypeInfo.put("A3", rs.getString("A3"));
						userTypeInfo.put("A4", rs.getString("A4"));
						userTypeInfo.put("A5", rs.getString("A5"));
						userTypeInfo.put("A6", rs.getString("A6"));
						userTypeInfo.put("A7", rs.getString("A7"));
						userTypeInfo.put("A8", rs.getString("A8"));
						userTypeInfo.put("A9", rs.getString("A9"));
						userTypeInfo.put("A10", rs.getString("A10"));
						userTypeInfo.put("A11", rs.getString("A11"));
						userTypeInfo.put("A12", rs.getString("A12"));
						userTypeInfo.put("A13", rs.getString("A13"));
						userTypeInfo.put("A12", rs.getString("A14"));
						userTypeInfo.put("A15", rs.getString("A15"));
						userTypeInfo.put("A16", rs.getString("A16"));
						userTypeInfo.put("A17", rs.getString("A17"));
						userTypeInfo.put("A18", rs.getString("A18"));
						userTypeInfo.put("A19", rs.getString("A19"));
						userTypeInfo.put("A20", rs.getString("A20"));
						userTypeInfo.put("A21", rs.getString("A21"));
						userTypeInfo.put("A22", rs.getString("A22"));
						userTypeInfo.put("A23", rs.getString("A23"));
						userTypeInfo.put("A24", rs.getString("A24"));
						userTypeInfo.put("A25", rs.getString("A25"));
						userTypeInfo.put("A26", rs.getString("A26"));
						userTypeInfo.put("A27", rs.getString("A27"));
						userTypeInfo.put("A28", rs.getString("A28"));
						userTypeInfo.put("A29", rs.getString("A29"));
						userTypeInfo.put("A30", rs.getString("A30"));
						userTypeInfo.put("A31", rs.getString("A31"));
						userTypeInfo.put("A32", rs.getString("A32"));
						userTypeInfo.put("A33", rs.getString("A33"));
						userTypeInfo.put("A34", rs.getString("A34"));
						userTypeInfo.put("A35", rs.getString("A35"));
						userTypeInfo.put("A36", rs.getString("A36"));

						if(selval.equals("1") ){
							
							userTypeInfo.put("A37", rs.getString("A37"));
							userTypeInfo.put("A38", rs.getString("A38"));
							userTypeInfo.put("A39", rs.getString("A39"));
							userTypeInfo.put("A40", rs.getString("A40"));
							userTypeInfo.put("A41", rs.getString("A41"));
							userTypeInfo.put("A42", rs.getString("A42"));
							userTypeInfo.put("A43", rs.getString("A43"));
							userTypeInfo.put("A44", rs.getString("A44"));
							userTypeInfo.put("A45", rs.getString("A45"));
							userTypeInfo.put("A46", rs.getString("A46"));
							userTypeInfo.put("A47", rs.getString("A47"));
							userTypeInfo.put("A48", rs.getString("A48"));
							userTypeInfo.put("A49", rs.getString("A49"));
							userTypeInfo.put("A50", rs.getString("A50"));

						}
						usersList.put("service"+i,userTypeInfo);
					i++;
			        }
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 

		pw.println(usersList);    
		System.out.println("rohit is better than keshav " + selval);
	}
	
}


