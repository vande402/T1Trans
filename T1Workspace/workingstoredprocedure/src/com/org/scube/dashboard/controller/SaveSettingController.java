package com.org.scube.dashboard.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.org.scube.dashboard.model.SaveSettingModel;



/**
 * Servlet implementation class SaveSettingController
 */
@WebServlet("/saveSettingController")
public class SaveSettingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(SaveSettingController.class);
	private String UPLOAD_DIRECTORY = "";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveSettingController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String flag = request.getParameter("flag").trim();
		
			if("dataSaveSettingMaster".equals(flag)){
			
				String logInUser 		= request.getParameter("logInUser");
				String dataToSave 		= request.getParameter("dataToSave");
				
					log.info("---=== scubeAppNameLable ===---"+logInUser);
					System.out.println("---=== scubeAppNameLable ===---"+logInUser);
					System.out.println("---=== dataToSave ===---"+dataToSave);
					log.info("scubeAppNameLable"+logInUser);
					log.info("dataToSave"+dataToSave);
			try {
				String returnString = SaveSettingModel.SaveOrgSettingDataToDB(logInUser,dataToSave);
				response.setContentType("html/text");
				pw.println(returnString);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("selectSaveSettingData".equals(flag)){
			
			JSONObject returnString ;
			try {
				returnString = SaveSettingModel.SelectOrgSettingDataToDB();
				response.setContentType("html/text");
				pw.println(returnString);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter pw = response.getWriter();

		String scubeAppNameLable = request.getParameter("scubeAppNameLable");

		System.out.println("keshav");
		log.info("hi...");
		if (ServletFileUpload.isMultipartContent(request)) {
			log.info("hi...");
			try {
				@SuppressWarnings("unchecked")
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				log.info(multiparts);
				log.info("hi..." + multiparts.size());

				System.out.println(multiparts);
				System.out.println("hi..." + multiparts.size());
				
				for (FileItem item : multiparts) {
					ServletContext servletContext = request.getServletContext();
					
					String relativeWebPath = "/imageUploads";
					String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);
					String strServer = request.getPathInfo();
					
					/** Comment by keshav 24-12-2015
					*   The method getRealPath(String) from the type ServletRequest is deprecated  
					*   UPLOAD_DIRECTORY = request.getRealPath("static/imageUploads");
					*/
					UPLOAD_DIRECTORY = request.getSession().getServletContext().getRealPath("static/imageUploads");
					
					String name = new File(item.getName()).getName();

					log.info(UPLOAD_DIRECTORY + File.separator + scubeAppNameLable + name);
					
					System.out.println("path-----------------"+UPLOAD_DIRECTORY + File.separator + scubeAppNameLable + name);
					
					File checkFile = new File(UPLOAD_DIRECTORY + File.separator	+ scubeAppNameLable + name);
					
					if (checkFile.exists()) {
						pw.println("static/imageUploads/" + scubeAppNameLable + name);
					} else {
						item.write(new File(UPLOAD_DIRECTORY + File.separator
								+ scubeAppNameLable + name));
						pw.println("static/imageUploads/" + scubeAppNameLable + name);
					}

				}

			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				log.info(e);
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.info(e);
				e.printStackTrace();
			}
	}
	}
}

