package com.org.scube.dashboard.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.org.scube.dashboard.dto.UserInfoData;
import com.org.scube.dashboard.model.LoginModel;


public class LoginView extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7471417828426252483L;

	PrintWriter out = null;
	HttpServletRequest request; 
	String message = null;
	LoginModel loginModel = new LoginModel();
	
	HttpSession 							httpSession 	= null;
	

		
		public String UserLoginView(){
			 try {
				out = ServletActionContext.getResponse().getWriter();
				request = ServletActionContext.getRequest();
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
			        
			        out.println(jsonBunch);
			        message= SUCCESS;
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message= ERROR;
			}
			return null;
		}
		public String ForgetPassword(){
			 try {
				out = ServletActionContext.getResponse().getWriter();
				request = ServletActionContext.getRequest();
			 String userEmailID = request.getParameter("forgetEmail");
			 
			 UserInfoData infoData 	= new UserInfoData(); 
			 
			 
				    JSONObject jsonBunch= LoginModel.validateEmailCredential(userEmailID);
				    
				    System.out.println("jsonBunch-------------"+jsonBunch);
				    
			        
			        out.println(jsonBunch);
			        message= SUCCESS;
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message= ERROR;
			}
			return null;
		}
		public String ForgetPasswordEmail(){
			 try {
				out = ServletActionContext.getResponse().getWriter();
				request = ServletActionContext.getRequest();
			 String userEmailID = request.getParameter("emailData");
			 System.out.println("userEmailID----------------------------------------------"+userEmailID);
			 UserInfoData infoData 	= new UserInfoData(); 
			 
			 
				    JSONObject jsonBunch= LoginModel.validateSendmailCredential(userEmailID);
				    
				    System.out.println("jsonBunch-------------"+jsonBunch);
				    
			        
			        out.println(jsonBunch);
			        message= SUCCESS;
					
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				message= ERROR;
			}
			return null;
		}
		
}
