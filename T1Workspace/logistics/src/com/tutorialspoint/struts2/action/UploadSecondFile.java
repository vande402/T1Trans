package com.tutorialspoint.struts2.action;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.org.util.DBconnect;

public class UploadSecondFile extends ActionSupport{
	   private File myFile;
	   private String myFileContentType;
	   private String myFileFileName;
	   private String destPath;
	   
	   
	   private HttpServletRequest servletRequest;
	   private static Connection  con= null;
	   private Statement stmt1=null;
		
	   
		private PreparedStatement pstCheckExist = null;
	   
	   public String execute()
	   {
	      /* Copy file to a safe location */
		   destPath = "E:/OKKYOProject/OKKYOProject_14-08-2015_new/WebContent/imageUploads/";
	      
	      try{
	    	  con = DBconnect.getConnectionStatus();
	    	  //String filePath = servletRequest.getSession().getServletContext().getRealPath("/");
	          //System.out.println("Server path:" + filePath);
	    	  
	    	 String kp = ServletActionContext.getRequest().getContextPath();
	    	  System.out.println("-----kp-222---"+kp);
	     	 System.out.println("Src File name: " + myFile);
	     	 System.out.println("Dst File name: " + myFileFileName);
	     	 System.out.println("-----keshav-------");
	     	 System.out.println("File Name is:"+getMyFileFileName());  
	     	 System.out.println("File Content Type is:"+getMyFileContentType()); 
	     	 System.out.println( File.separator );
	     	 
	     	FileInputStream file = new FileInputStream(myFile);
	     	//Create Workbook instance holding reference to .xlsx file
	     	System.out.println("new------------------%%%"+file);
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			//Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);
			int Count = 0;
			String s1 = null ;
			String sValue0 = null;
			String sValue1 = null;
			String sValue2 = null;
			String sValue3 = null;
			String sValue4 = null;
			String sValue5 = null, sValue6 = null, sValue7 = null, sValue8 = null , sValue9 = null, sValue10 = null
					,sValue11 = null, sValue12 = null, sValue13 = null;
			//Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			//Iterator<Row> rowIterator = sheet.iterator();
			/*
			while (rowIterator.hasNext()) 
			{*/
			
			 for (Row row : sheet) {
		            // avoid first row as it is header
				 	// count first row cell no. 
		            if (row.getRowNum() == 0) {
		            	System.out.println("====NumberOfCells======"+row.getPhysicalNumberOfCells());
		            }

				 for (int count = 0; count < row.getLastCellNum(); count++) {
		                Cell cell = row.getCell(count);
		                System.out.println("cell==========="+cell);
		               // whenever we get blank cell value, we avoid it and continues the loop
		                if(count==0){if (cell == null) {sValue0 = "--";continue;
			               }}
		                
		                if(count==1){if (cell == null) {sValue1 = "--";continue;
		               }}
		                if(count==2){if (cell == null) {sValue2 = "--";continue;
			               }}
		                if(count==3){if (cell == null) {sValue3 = "--";continue;
			               }}
		                if(count==4){if (cell == null) {sValue4 = "--";continue;
			            }}
		                if(count==5){if (cell == null) { sValue5 = "--";continue;
			             }}
		                if(count==6){ if (cell == null) { sValue6 = "--"; continue;
	                	}}
		                if(count==7){ if (cell == null) { sValue7 = "--"; continue;
	                	}}
		                if(count==8){ if (cell == null) { sValue8 = "--"; continue;
	                	}}
		                if(count==9){ if (cell == null) { sValue9 = "--" ; continue;
	                	}}
		                if(count==10){ if (cell == null) { sValue10 = "--"; continue;
	                	}}
		                if(count==11){ if (cell == null) { sValue11 = "--"; continue;
	                	}}
		                if(count==12){ if (cell == null) { sValue12 = "--"; continue;
	                	}}
		                if(count==13){ if (cell == null) { sValue13 = "--"; continue;
	                	}}
		                
				//For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();
//				System.out.println("------------ keshav --------");
				

					switch (cell.getCellType()) 
					{
						case Cell.CELL_TYPE_NUMERIC:
							s1=Double.toString(cell.getNumericCellValue());
							System.out.println("111--"+cell.getNumericCellValue() + "\t");
							break;
						case Cell.CELL_TYPE_STRING:
							s1=cell.getStringCellValue();
							System.out.println("222--"+cell.getStringCellValue() + "\t");
							break;

					}
					
					if(count==0)
						sValue0 = s1;
					if(count==1)
						sValue1 = s1;
					if(count==2)
						sValue2 = s1;
					if(count==3)
						sValue3 = s1;
					if(count==4)
						sValue4 = s1;
					if(count==5)
						sValue5 = s1;
					if(count==6)
						sValue6 = s1;
					if(count==7)
						sValue7 = s1;
					if(count==8)
						sValue8 = s1;
					if(count==9)
						sValue9 = s1;
					if(count==10)
						sValue10 = s1;
					if(count==11)
						sValue11 = s1;
					if(count==12)
						sValue12 = s1;
					if(count==13)
						sValue13 = s1;
					
					//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
					//String connectionUrl = "jdbc:sqlserver://127.0.0.1:3307/test";
			       // String user = "sa";
			       // String pass = "keshav";
					
			//	} 
					//j++;
				}
				
				System.out.println("====="+sValue0+"-----"+sValue1+"---"+sValue2+""+sValue3+""+sValue4+""+sValue5+"");
				/*if(Count!=0){
				pstCheckExist = con.prepareStatement("insert into ExUpFile (ID,NAME,LASTNAME)values("+sValue0+","+sValue1+","+sValue2+")");
				pstCheckExist.executeUpdate();
				}*/
				if(Count!=1){
				String query = "insert into lindex (A1,A2,A3,A4,A5,A6,A7,A8,A9,A10,A11,A12,A13)values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	        	System.out.println(query);	        	
	        	pstCheckExist = con.prepareStatement(query);            	
	        	pstCheckExist.setString(1, sValue0);
	        	pstCheckExist.setString(2, sValue1);
	        	pstCheckExist.setString(3, sValue2);
	        	pstCheckExist.setString(4, sValue3);
	        	pstCheckExist.setString(5, sValue4);
	        	pstCheckExist.setString(6, sValue5);
	        	pstCheckExist.setString(7, sValue6);
	        	pstCheckExist.setString(8, sValue7);
	        	pstCheckExist.setString(9, sValue8);
	        	pstCheckExist.setString(10, sValue9);
	        	pstCheckExist.setString(11, sValue10);
	        	pstCheckExist.setString(12, sValue11);
	        	pstCheckExist.setString(13, sValue12);
	        	
	        	pstCheckExist.executeUpdate();
				}
				if(row.getRowNum()==1){
					
				String	query2 ="select id from title";

					 String tID = null ;      	
		        	
		        	
		        	stmt1=con.createStatement();
			    	ResultSet rs = stmt1.executeQuery(query2);
			    	
			    	while(rs.next())
			        {

			    		tID = rs.getString("id");
			        }
			    	
			    	
			    	String query = " update title set A38='"+sValue0+"',A39='"+sValue1+"',A40='"+sValue2+"',A41='"+sValue3+"',A42='"+sValue4+"',A43='"+sValue5+"'," +
							" A44='"+sValue6+"', A45='"+sValue7+"',A46='"+sValue8+"',A47='"+sValue9+"',A48='"+sValue10+"',A49='"+sValue11+"',A50='"+sValue12+"' " +
							"where id = "+tID+";";
		        	System.out.println(query);	 
			    	
			    	
		        
		        //	resultset = stmt1.executeQuery(query);
		        	pstCheckExist = con.prepareStatement(query);	
			    	pstCheckExist.executeUpdate();
					}
	
				
				System.out.println("======Count======="+Count++);
				System.out.println("");
			 }
			 
			 
		    	//String rohit = String.format("roh\"i\"t");
		    	 String queryv = "UPDATE lindex SET A3=RTRIM(REVERSE(SUBSTRING(REVERSE(A3),LOCATE(\" \",REVERSE(A3))))), A3=RTRIM(REVERSE(SUBSTRING(REVERSE(A3),LOCATE(\" \",REVERSE(A3))))), A2=RTRIM(REVERSE(SUBSTRING(REVERSE(A2),LOCATE(\" \",REVERSE(A2))))), A2=RTRIM(REVERSE(SUBSTRING(REVERSE(A2),LOCATE(\" \",REVERSE(A2))))); " ;
			    	System.out.println(queryv);	        	
			    	pstCheckExist = con.prepareStatement(queryv);	
			    	pstCheckExist.executeUpdate();
			    	
			    	
			    	
			file.close();
	     	//FilesUtil.saveFile(getMyFile(),getMyFileFileName(),kp+File.separator+getMyFileContentType());
	      }catch(Exception e){
	         e.printStackTrace();
	         return ERROR;
	      }

	      return SUCCESS;
	   }
	   public File getMyFile() {
	      return myFile;
	   }
	   public void setMyFile(File myFile) {
	      this.myFile = myFile;
	   }
	   public String getMyFileContentType() {
	      return myFileContentType;
	   }
	   public void setMyFileContentType(String myFileContentType) {
	      this.myFileContentType = myFileContentType;
	   }
	   public String getMyFileFileName() {
	      return myFileFileName;
	   }
	   public void setMyFileFileName(String myFileFileName) {
	      this.myFileFileName = myFileFileName;
	   }
	   
	   public void setServletRequest(HttpServletRequest servletRequest) {
	      this.servletRequest = servletRequest;

	   }
	}
