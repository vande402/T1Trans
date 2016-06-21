package com.org.scube.dashboard.controller;

import java.io.IOException;

import compDash.DBconnect;

import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.opensymphony.xwork2.ActionSupport;
import com.org.scube.dashboard.model.AdminUserManageModel;
import com.org.scube.dashboard.model.LoginModel;
import com.org.scube.dashboard.util.EncoderAndDecoder;
import com.org.scube.dashboard.util.MailUtil;


public class AdminUserManageView extends ActionSupport {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3991121240732741516L;
	PrintWriter out = null;
	HttpServletRequest request; 
	String message = null;
	LoginModel loginModel = new LoginModel();
	
	HttpSession 							httpSession 	= null;
	

		
		public String AddNewUser(){
	
			try {
				out = ServletActionContext.getResponse().getWriter();
				request = ServletActionContext.getRequest();
		 
				 String userName = request.getParameter("userName");
				 String userEmail = request.getParameter("userEmail");
				 String userMobile = request.getParameter("userMobile");
				 String userType = "user";
			 
			 	System.out.println("userName == "+userName+"===userEmail==="+userEmail+"===userMobile==="+userMobile);

			 	JSONObject jsonBunch = AdminUserManageModel.addNewUserCredential(userName,userEmail,userMobile,userType);
				 	
				 	System.out.println("jsonBunch=========="+jsonBunch);
				 	out.println(jsonBunch);
			 	} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 	
/*		 if(flag.equals("editUser")){
			 
			 String userName = request.getParameter("userName");
			 String userEmail = request.getParameter("userEmail");
			 String userMobile = request.getParameter("userMobile");
			 String userType = request.getParameter("userType");
			 String userId = request.getParameter("userId");
			 
			 System.out.println("edit userName == "+userName+"===userEmail==="+userEmail+"===userMobile==="+userMobile+"===userType==="+userType+"===userId==="+userId);
			 try {	
				 	JSONObject jsonBunch = AdminUserManageModel.editUserCredential(userName,userEmail,userMobile,userType,userId);
				 	
				 	System.out.println("jsonBunch=========="+jsonBunch);
				 	pw.println(jsonBunch);
					response.setContentType("application/json");
			 	} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }*/
			return null;
	}
		public String SelectData(){
			
			try {
				out = ServletActionContext.getResponse().getWriter();
				request = ServletActionContext.getRequest();
		 
			 	JSONObject jsonBunch = AdminUserManageModel.SelectAllData();
				 	
				 	System.out.println("jsonBunch=========="+jsonBunch);
				 	out.println(jsonBunch);
			 	} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return null;
		}
		
		public String getSeleteData(){
			
			System.out.println("keshav----------------------");
			try {
				out = ServletActionContext.getResponse().getWriter();
				request = ServletActionContext.getRequest();
		 
				String selCou = request.getParameter("selectedCountry");
				System.out.println("selCou---------"+selCou);
			 	JSONObject jsonBunch = AdminUserManageModel.SelectselectedData(selCou);
				 	
				 	System.out.println("jsonBunch=========="+jsonBunch);
				 	out.println(jsonBunch);
			 	} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return null;
		}
}
