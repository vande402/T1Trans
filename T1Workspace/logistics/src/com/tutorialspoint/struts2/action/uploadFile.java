package com.tutorialspoint.struts2.action;

import java.io.File;
import java.io.FileReader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import com.opencsv.CSVReader;
import com.opensymphony.xwork2.ActionSupport;
import com.org.util.DBconnect;;




public class uploadFile extends ActionSupport{
	private static final long serialVersionUID = 7062690225820508458L;
   private File myFile;
	
   @SuppressWarnings("resource")
public String execute()
   {
      
	   try {CSVReader reader = new CSVReader(new FileReader(myFile), ','); 
               Connection connection = DBconnect.getConnectionStatus();
	
			String insertQuery = "Insert into DAT_Lane_hist ([PC-Miler Practical Mileage],[Spot Avg Linehaul Rate] ,[Spot Low Linehaul Rate],[Spot High Linehaul Rate],[Spot Fuel Surcharge],[Spot Time Frame],[Spot Origin Geo Expansion],[Spot Destination Geo Expansion],[Spot_num of Companies],[Spot_num of Reports],[Spot Linehaul Rate StdDev],[Spot Your Own Avg Linehaul Rate],[Spot Your Own # of Reports] ,[Spot Error],[Orig City],[Orig State] ,[Orig Postal Code],[Dest City],[Dest State],[Dest Postal Code] ,[Truck Type]) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = connection.prepareStatement(insertQuery);
			String[] rowData = null;
			int i = 0;
			System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
			while((rowData = reader.readNext()) != null)
			{
				for (String data : rowData)
				{
						pstmt.setString((i % 3) + 1, data);

						if (++i % 3 == 0)
								pstmt.addBatch();// add batch

						if (i % 30 == 0)// insert when the batch size is 10
								pstmt.executeBatch();
				}
			}
			System.out.println("Data Successfully Uploaded");
			 return SUCCESS;
	}
	catch (Exception e)
	{
			e.printStackTrace();
			 return ERROR;
	}
   }}