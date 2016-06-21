package com.org.action;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
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
}
