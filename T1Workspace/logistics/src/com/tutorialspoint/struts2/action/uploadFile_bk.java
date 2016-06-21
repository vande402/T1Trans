package com.tutorialspoint.struts2.action;




import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.formula.functions.Count;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.omg.CORBA.portable.InputStream;

import java.io.IOException; 
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.tutorialspoint.struts2.util.FilesUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.org.util.DBconnect;

public class uploadFile_bk extends ActionSupport{
   private File myFile;
   private String myFileContentType;
   private String myFileFileName;
   private String destPath;
   
   
   private HttpServletRequest servletRequest;
   private static Connection  con= null;
   
	private Connection conn = null;
	private PreparedStatement pstCheckExist = null;
	private ResultSet resultset = null;
	
	
   public String execute()
   {
      /* Copy file to a safe location */
	   destPath = "E:/OKKYOProject/OKKYOProject_14-08-2015_new/WebContent/imageUploads/";
	   
	   
	 	
      try{
    	  
    	  
    	  con = DBconnect.getConnectionStatus();
    	  //String filePath = servletRequest.getSession().getServletContext().getRealPath("/");
          //System.out.println("Server path:" + filePath);
    	  
    	 String kp = ServletActionContext.getRequest().getContextPath();
    	 System.out.println("-----kp111s----"+kp);
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
		String sValue5 = null;
		
		//Connection con = DriverManager.getConnection("jdbc::mysql://localhost:3306/test", "student", "student");
		
		
        String query1 = "delete from UploadFirstFile;";
    	System.out.println(query1);	        	
    	pstCheckExist = con.prepareStatement(query1);	
    	pstCheckExist.executeUpdate();
    	
    	String query2 = "delete from UploadSecondFile;";
    	System.out.println(query2);	        	
    	pstCheckExist = con.prepareStatement(query2);	
    	pstCheckExist.executeUpdate();
    	
		//Iterate through each rows one by one
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) 
		{
			Row row = rowIterator.next();
			//For each row, iterate through all the columns
			Iterator<Cell> cellIterator = row.cellIterator();
			System.out.println("------------ rohit --------");
			
			//for(int j=0;j<3;){
			
			while (cellIterator.hasNext()) 
			{
				for(int i=0;i<6;i++){
				//System.out.println("keshav ----- ");
				
				Cell cell = cellIterator.next();
				//Check the cell type and format accordingly
				if (cell.getCellType() == cell.CELL_TYPE_BLANK ){
					System.out.println("DOne");
				}
				switch (cell.getCellType()) 
				{
					case Cell.CELL_TYPE_NUMERIC:
						if(cell.getNumericCellValue() != 0)
								s1=Double.toString(cell.getNumericCellValue());
						//System.out.print("111--"+cell.getNumericCellValue() + "\t");
						break;
					case Cell.CELL_TYPE_STRING:
						s1=cell.getStringCellValue();
						//System.out.print("222--"+cell.getStringCellValue() + "\t");
						break;
					case Cell.CELL_TYPE_BLANK:
						s1=cell.toString();
						break;
				}
				/*	switch (cell.getColumnIndex()) 
					{
					case 0:
						System.out.print("111-\t"+cell.getStringCellValue());
						break;
					case 1:
						
						System.out.print("222--\t"+cell.getStringCellValue());
						break;
					case 2:
						System.out.print("333--\t"+cell.getStringCellValue());
						break;
				}*/
				//s1=s1.trim();
				System.out.println("======s1123======="+i+"--"+Count+"--"+s1);
				//System.out.println("======s1===j===="+j+"--"+s1);
				//System.out.println("==Rohit");
				if(i==0)
					sValue0 = s1;
				if(i==1)
					sValue1 = s1;
				if(i==2)
					sValue2 = s1;
				if(i==3)
					sValue3 = s1;
				if(i==4){
					if(s1.equalsIgnoreCase(""))
					{
						//System.out.println("==nul26");
						sValue4 = "---";
					}else{
						if(s1.equalsIgnoreCase("null"))
							{
								//System.out.println("==nul27");
								sValue4 = "-";
							}else{
									sValue4 = s1;
								}
				}
					//System.out.println("==nul27"+sValue4);
			}
				if(i==5)					
				{
					if(s1.equalsIgnoreCase(""))
					{
						//System.out.println("==nul28");
						sValue5 = "---";
					}else{
						if(s1.equalsIgnoreCase("null"))
							{
								System.out.println("==nul29");
								sValue5 = "-";
							}else{
									sValue5 = s1;
								}
				}				
			}
				/*
		         
		         {
		        	 System.out.println("A55null");
		         }
		         if( sValue3=="" || sValue3 == null)
		         {
		        	 System.out.println("A5ual55%%%%");
		         }*/


				

				
		//	} 
				//j++;
			}
			}
			System.out.println("====="+sValue0+"-----"+sValue1+"---"+sValue2+"---"+sValue3+"---"+sValue4+"---"+sValue5+"");
			/*if(Count!=0){
			pstCheckExist = con.prepareStatement("insert into ExUpFile (ID,NAME,LASTNAME)values("+sValue0+","+sValue1+","+sValue2+")");
			pstCheckExist.executeUpdate();
			}*/
			if(Count!=0){
			String query = "insert into UploadFirstFile (A1,A2,A3,A4,A5,A6)values (?,?,?,?,?,?);";
        	System.out.println(query);	        	
        	pstCheckExist = con.prepareStatement(query);            	
        	pstCheckExist.setString(1, sValue0);
        	pstCheckExist.setString(2, sValue1);
        	pstCheckExist.setString(3, sValue2);
        	pstCheckExist.setString(4, sValue3);
        	pstCheckExist.setString(5, sValue4);
        	pstCheckExist.setString(6, sValue5);
        	pstCheckExist.executeUpdate();
			}
			
			
			System.out.println("======Count======="+Count++);
			System.out.println("");
		}
		file.close();
     	//FilesUtil.saveFile(getMyFile(),getMyFileFileName(),kp+File.separator+getMyFileContentType());
      }catch(Exception e){
         e.printStackTrace();
         return ERROR;
      }
System.out.println("rohitis grat programer ");
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