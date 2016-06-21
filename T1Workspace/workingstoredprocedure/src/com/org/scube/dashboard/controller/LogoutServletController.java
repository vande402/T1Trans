package com.org.scube.dashboard.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutServletController")
public class LogoutServletController extends HttpServlet {
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		
		 PrintWriter out;
		try {
			out = response.getWriter();
		
		 HttpSession session=request.getSession();  
		 
		 session.setAttribute("UserName", null); 
	     session.invalidate(); 
	     out.print("loggedOut");  
         
	     out.close(); 
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}

}
