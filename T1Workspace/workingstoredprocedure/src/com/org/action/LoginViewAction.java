package com.org.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.org.scube.dashboard.dto.UserInfoData;
import com.org.scube.dashboard.model.LoginModel;

public class LoginViewAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7062690225820508458L;
	
	private static Connection  con= null;
	PrintWriter pw = null;
	String message = null;
	Statement stmt = null;
	HttpServletRequest request = null;
	

	LoginModel loginModel = new LoginModel();
	
	public String userAuthanticate(){
		
		System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
		try{
			request = ServletActionContext.getRequest();
	
		String flag = request.getParameter("flag");
		
		
		if(flag.equals("validateCredential")){
		
			 String userName = request.getParameter("userName");
			 String password = request.getParameter("password");
			 
			 UserInfoData infoData 	= new UserInfoData(); 
			 
			System.out.println("password-------------"+password);
			
			  
				    JSONObject jsonBunch= LoginModel.validateCredential(userName, password);
				    //response.setContentType("application/json");
					//String status1 = status.toString();
				    //log.info("jsonBunch."+jsonBunch);
				    
				    infoData = loginModel.createSessionCredential(userName, password);
				    
				    System.out.println("jsonBunch-------------"+jsonBunch);
				    
				    HttpSession session = request.getSession(false); 
				    System.out.println("------++@@@@++--------"+infoData.getUserRole());
			        if(session!=null)  
			        session.setAttribute("UserRole", infoData.getUserRole()); 
			        session.setAttribute("UserName", infoData.getUserName());
			        session.setAttribute("UserEmail", infoData.getUserEmailID());
			        session.setAttribute("UserMobile", infoData.getUserMobile());
			        
			        System.out.println(session.getAttribute("UserName"));
			        
					pw.println(jsonBunch);
		}		
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
		}
}
		

