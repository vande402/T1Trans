package com.org.scube.dashboard.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.org.scube.dashboard.model.GetUserListModel;


@WebServlet("/selectAllUserController")
public class SelectAllUserController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3172776306779610714L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter pw = response.getWriter();
		String flag = request.getParameter("flag");
		
		System.out.println("flag--------------"+flag);
		 if(flag.equals("getAllUserData")){
			 
			 String userId = request.getParameter("userId");
			 System.out.println("userId--------"+userId);
			
			 try {	
				 	JSONObject jsonBunch = GetUserListModel.getAllUserListData(userId);
				 	
				 	System.out.println("jsonBunch=========="+jsonBunch);
				 	pw.println(jsonBunch);
			 	} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 
		 }
		
	}
}
