package compDash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import java.sql.Statement;




public class GetAttributes {

	Connection con;
	PreparedStatement pst;
	CallableStatement cstmt;
	ResultSet rsComp;
	Statement CompStmt;
	
	//gives total no of hosts 
	public String getTotHostCount() throws IOException, ClassNotFoundException, SQLException
	{
		String strTotalHostQry="Select count(*) as HostCount from InventoryHosts";
		con=null;rsComp=null;pst=null;CompStmt=null;
		String strTotcnt=null;
		try
		{
			con=DBconnect.getConnectionStatus();
			CompStmt=con.createStatement();
			
			rsComp=CompStmt.executeQuery(strTotalHostQry);
			while(rsComp.next())
			{
				strTotcnt=rsComp.getString(1);		
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in getCompliantCount"+e.toString());
		}
		try{
			if(pst!=null)
			{
				pst.close();
			}
			
			if(CompStmt!=null)
			{
				CompStmt.close();
			}
					
			if(con!=null)
			{
				con.close();
			}
		}
		finally{
			
		}

		return strTotcnt;
	}
	
	
	
	
	public String getCompliantCount(HttpServletRequest req,String strScanDate,String strTabId) throws IOException, ClassNotFoundException, SQLException
	{
		String strCompliantCnt="0";//,strNCompliantCnt="0";	
		String strCompliantQuery="";//,strNCompliantQuery="";
		//strCompliantQuery="select count(DISTINCT(IPAddress)) as Count from ReportDetails where alert like 'installed'";
		
		strCompliantQuery="select CompCnt from PieChartCompCnt where Insp_Dt like '%"+strScanDate+"%' and TabId ="+strTabId;

		con=null;rsComp=null;pst=null;CompStmt=null;
		
		try
		{
			con=DBconnect.getConnectionStatus();
			CompStmt=con.createStatement();
			
			/*
			pst=con.prepareStatement("{call GETREPORTS(?,?)}");
			pst.setString(1,strScanDate);
			pst.setString(2,strTabId);
			pst.executeQuery();
			*/
			
			rsComp=CompStmt.executeQuery(strCompliantQuery);
			while(rsComp.next())
			{
				strCompliantCnt=rsComp.getString("CompCnt");
				//System.out.println("strCompliantCnt="+strCompliantCnt);
			}

			
		}
		catch(Exception e)
		{
			System.out.println("Exception in getCompliantCount"+e.toString());
		}
		try{
			if(pst!=null)
			{
				pst.close();
			}
			
			if(CompStmt!=null)
			{
				CompStmt.close();
			}
					
			if(con!=null)
			{
				con.close();
			}
		}
		finally{
			
		}

		return strCompliantCnt;
	} 
	
	// gives count of scanned host in selected dates inspection
	public String getScanCount(HttpServletRequest req,String strScanDate) throws IOException, ClassNotFoundException, SQLException
	{
		String strScanCnt="";
		
		String strScannedHost="Select count(*) as ScanCount from Sec_ScannedHosts where InspectionId in (select ID from Sec_Inspection si where convert(VARCHAR(40),si.[datetime],120) like '%"+strScanDate+"%')";
		
		try
		{		
			con=DBconnect.getConnectionStatus();
			CompStmt=con.createStatement();			
			
			rsComp=CompStmt.executeQuery(strScannedHost);
			while(rsComp.next())
			{
				strScanCnt=rsComp.getString("ScanCount");
				//System.out.println("strScanCnt="+strScanCnt);

			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in getScanCount"+e.toString());
		}
		try
		{
			if(pst!=null)
			{
				pst.close();
			}
			
			if(CompStmt!=null)
			{
				CompStmt.close();
			}
					
			if(con!=null)
			{
				con.close();
			}
		}
		finally
		{
			
		}
		return strScanCnt;
	}
	
	/*
	public  void getIssuesCount(HttpServletRequest req,String strScanDate) throws IOException, ClassNotFoundException, SQLException
	{
		
		con=null;rsComp=null;pst=null;CompStmt=null;
		
		try
		{
			con=DBconnect.getConnectionStatus();
			CompStmt=con.createStatement();
			
			pst=con.prepareStatement("{call GETLINECHART(?)}");
			pst.setString(1,strScanDate);			
			pst.executeQuery();
			
			//System.out.println("here");
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in getIssuesCount"+e.toString());
		}
		try{
			if(pst!=null)
			{
				pst.close();
			}
			
			if(CompStmt!=null)
			{
				CompStmt.close();
			}
					
			if(con!=null)
			{
				con.close();
			}
		}
		finally{
			
		}		
		
	} */
	
	/*gives count of total complaint endpoints */
	
	public int getCompliantEptCount(HttpServletRequest req,String strScanDate) throws IOException, ClassNotFoundException, SQLException
	{
		
		con=null;rsComp=null;pst=null;cstmt=null;
		int icnt=0;
		try
		{
			con=DBconnect.getConnectionStatus();
			CompStmt=con.createStatement();
			String strquery="select Comp_Cnt from Insps_TotalCompCnt where Insp_Dt like '%"+strScanDate+"%'";
			String strScanCnt="";
		
			/*cstmt=con.prepareCall("{call GETALLENDPTCOMPLIANCE(?,?)}");
			cstmt.setString(1,strScanDate);	
			cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
			cstmt.executeUpdate();
			
			 icnt=cstmt.getInt(2);*/
			
				rsComp=CompStmt.executeQuery(strquery);
				while(rsComp.next())
				{
					strScanCnt=rsComp.getString("Comp_Cnt");
					System.out.println("TOTAL COMPLIANT COUNT="+strScanCnt);

				}
				icnt=Integer.parseInt(strScanCnt);
				
		}
		catch(Exception e)
		{
			System.out.println("Exception in getCompliantEptCount"+e.toString());
		}
		try{
			if(cstmt!=null)
			{
				cstmt.close();
			}
			
			
					
			if(con!=null)
			{
				con.close();
			}
		}
		finally{
			
		}		
		return icnt;
	} 
	
	/*for single endpoint detailed report*/
	public ResultSet getInspectionHosts(HttpServletRequest req,String strScanDate) throws IOException, ClassNotFoundException, SQLException
	{
		
		con=null;ResultSet rsTemp=null;pst=null;cstmt=null;
		int icnt=0;
		
		String strQuery="Select DISTINCT(SH.Name) hostname,SH.Ip ipaddress from Sec_ScannedHosts SNH ,Sec_Hosts SH where SH.ID=SNH.HostID and  SNH.InspectionId in (select ID from Sec_Inspection si where convert(VARCHAR(40),si.[datetime],120) like '%"+strScanDate+"%')";	
		try
		{						
			con=DBconnect.getConnectionStatus();
			CompStmt=con.createStatement();						
			rsTemp=CompStmt.executeQuery(strQuery);
			//System.out.println("here");
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in getInspectionHosts"+e.toString());
		}		
		return rsTemp;
	}
	
	
	public ResultSet getUnscannedHosts(HttpServletRequest req,String strScanDate) throws IOException, ClassNotFoundException, SQLException
	{
		
		con=null;ResultSet rsTemp=null;pst=null;cstmt=null;
		int icnt=0;
		
		String strQuery="Select DISTINCT(SH.Name) hostname,SH.Ip ipaddress from Sec_UnscannedHosts SNH ,Sec_Hosts SH where SH.ID=SNH.HostID and  SNH.InspectionId in (select ID from Sec_Inspection si where convert(VARCHAR(40),si.[datetime],120) like '%"+strScanDate+"%')";	
		try
		{						
			con=DBconnect.getConnectionStatus();
			CompStmt=con.createStatement();						
			rsTemp=CompStmt.executeQuery(strQuery);
			//System.out.println("here");
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in getInspectionHosts"+e.toString());
		}		
		return rsTemp;
	}
	
	
	public int getUnscannedHostsCount(HttpServletRequest req,String strScanDate) throws IOException, ClassNotFoundException, SQLException
	{
		
		con=null;ResultSet rsTemp=null;pst=null;cstmt=null;
		int icnt=0;
		
		String strQuery="Select count(*) unscancount from Sec_UnscannedHosts SNH  where SNH.InspectionId in (select ID from Sec_Inspection si where convert(VARCHAR(40),si.[datetime],120) like '%"+strScanDate+"%')";	
		try
		{						
			con=DBconnect.getConnectionStatus();
			CompStmt=con.createStatement();						
			rsTemp=CompStmt.executeQuery(strQuery);
			//System.out.println("here");
			
			while(rsTemp.next())
			{
				icnt=Integer.parseInt(rsTemp.getString("unscancount"));				
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in getInspectionHosts"+e.toString());
		}		
		return icnt;
	}
	
	
	/*for single endpoint detailed report
	 * GIVES DATA FOR SELECTED END POINTS DETAILS
	 * */
	public ResultSet getEndPointData(HttpServletRequest req,String strScanDate,String strIpAddr) throws IOException, ClassNotFoundException, SQLException
	{
		
		con=null;ResultSet rsTemp=null;pst=null;cstmt=null;
		
		//System.out.println("getEndPointData--strScanDate"+strScanDate+"strIpAddr"+strIpAddr);
		String tempDate='%'+strScanDate+'%';
		String tempHost='%'+strIpAddr;
		
		
		try
		{
			con=DBconnect.getConnectionStatus();
			
			pst=con.prepareStatement("{call GETSINGLE_ENDPTDETAIL(?,?)}");
			pst.setString(1,tempDate);
			pst.setString(2,tempHost);	
			
			rsTemp=pst.executeQuery();
			
			//System.out.println("here");
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in getEndPointData"+e.toString());
		}
		
		
		finally{
			
		}		
		return rsTemp;
	} 
	
	
	public ResultSet getUnscannedEndPointData(HttpServletRequest req,String strScanDate,String strIpAddr) throws IOException, ClassNotFoundException, SQLException
	{
		
		con=null;ResultSet rsTemp=null;pst=null;cstmt=null;
		
		//System.out.println("getEndPointData--strScanDate"+strScanDate+"strIpAddr"+strIpAddr);
		String tempDate='%'+strScanDate+'%';
		String tempHost='%'+strIpAddr;
		
		
		try
		{
			con=DBconnect.getConnectionStatus();
			
			pst=con.prepareStatement("{call SCUBE_UNSCANNED_HOST_DETAIL(?)}");
			pst.setString(1,tempDate);
			//pst.setString(2,tempHost);	
			
			rsTemp=pst.executeQuery();
			
			//System.out.println("here");
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in getEndPointData"+e.toString());
		}
		
		
		finally{
			
		}		
		return rsTemp;
	} 

	
	
/*gives count of repeat issues in between selected inspection date and last 7th scan date */
	
	public String getBarChartIssueCnt(HttpServletRequest req,String strScanDate) throws IOException, ClassNotFoundException, SQLException
	{
		
		con=null;rsComp=null;pst=null;cstmt=null;
		String strcnt="";
		try
		{
			con=DBconnect.getConnectionStatus();
			System.out.println("GETBARCHART= strScanDate="+strScanDate);
			
			
			cstmt=con.prepareCall("{call GETBARCHART(?,?)}");
			cstmt.setString(1,strScanDate);	
			cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
			//cstmt.executeQuery();
			
			cstmt.executeUpdate();
			
			strcnt=cstmt.getString(2);
			
			
			
			
			System.out.println("GETBARCHART=="+strcnt);
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in strBarChartIssueCnt"+e.toString());
		}
		try{
			if(cstmt!=null)
			{
				cstmt.close();
			}
			
			
					
			if(con!=null)
			{
				con.close();
			}
		}
		finally{
			
		}		
		return strcnt;
	} 
	
	
	public String getCompliantInstalled(HttpServletRequest req,String strScanDate,String strTabId) throws IOException, ClassNotFoundException, SQLException
	{
		String strCompliantCnt="0";//,strNCompliantCnt="0";	
		String strCompliantQuery="";//,strNCompliantQuery="";
		strCompliantQuery="select count(DISTINCT(IPAddress)) as Count from ReportDetails where alert IN('installed','exists')";

		con=null;rsComp=null;pst=null;CompStmt=null;
		
		try
		{
			con=DBconnect.getConnectionStatus();
			CompStmt=con.createStatement();
						
			rsComp=CompStmt.executeQuery(strCompliantQuery);
			while(rsComp.next())
			{
				strCompliantCnt=rsComp.getString("Count");				
			}

			
		}
		catch(Exception e)
		{
			System.out.println("Exception in getCompliantCountEach"+e.toString());
		}
		try{
			if(pst!=null)
			{
				pst.close();
			}
			
			if(CompStmt!=null)
			{
				CompStmt.close();
			}
					
			if(con!=null)
			{
				con.close();
			}
		}
		finally{
			
		}

		return strCompliantCnt;
	} 
	
	
	public String getCompliantNotInstalled(HttpServletRequest req,String strScanDate,String strTabId) throws IOException, ClassNotFoundException, SQLException
	{
		String strCompliantCnt="0";//,strNCompliantCnt="0";	
		String strCompliantQuery="";//,strNCompliantQuery="";
		strCompliantQuery="select count(DISTINCT(IPAddress)) as Count from ReportDetails where alert like 'not installed'";

		con=null;rsComp=null;pst=null;CompStmt=null;
		
		try
		{
			con=DBconnect.getConnectionStatus();
			CompStmt=con.createStatement();
						
			rsComp=CompStmt.executeQuery(strCompliantQuery);
			while(rsComp.next())
			{
				strCompliantCnt=rsComp.getString("Count");				
			}

			
		}
		catch(Exception e)
		{
			System.out.println("Exception in getCompliantCountEach"+e.toString());
		}
		try{
			if(pst!=null)
			{
				pst.close();
			}
			
			if(CompStmt!=null)
			{
				CompStmt.close();
			}
					
			if(con!=null)
			{
				con.close();
			}
		}
		finally{
			
		}

		return strCompliantCnt;
	} 

	
	
	public ResultSet getCveData(HttpServletRequest req) throws IOException, ClassNotFoundException, SQLException
	{
		
		con=null;pst=null;cstmt=null;
		/*String sql_qry="SELECT  H.ComputerName AS Endpoint, H.IP AS IPAddress, dbo.cve.cveid, dbo.cve.score, dbo.cve.description, A.Name AS ApplicationName,  HA.Version AS ApplicationVersion "+
						"FROM dbo.InventoryHosts AS H INNER JOIN  dbo.InventoryHostApplications AS HA ON H.ID = HA.HostID INNER JOIN  dbo.InventoryApplicationInfo AS A ON A.ID = HA.ApplicationID INNER JOIN "+
                        "dbo.cve ON dbo.cve.version = HA.Version AND A.Name LIKE dbo.cve.ApplicationDetectionString WHERE        (A.IsUpdate = 0)";
*/		
		//String sql_qry="select TOP 1000 * from ViewCVE";		

		String sql_qry="select TOP 100 * from ViewCVE";
		
		try
		{
			con=DBconnect.getConnectionStatus();			
			CompStmt=con.createStatement();
			rsComp = CompStmt.executeQuery(sql_qry);
			
			/* export to excel" */
			/* 
			HSSFWorkbook workbook = new HSSFWorkbook();
		    HSSFSheet sheet = workbook.createSheet("lawix10");
		    HSSFRow rowhead = sheet.createRow((short) 0);
		    rowhead.createCell((short) 0).setCellValue("EndPoint");
		    rowhead.createCell((short) 1).setCellValue("IPAddress");
		    rowhead.createCell((short) 2).setCellValue("CVE ID");
		    rowhead.createCell((short) 3).setCellValue("Score");
		    rowhead.createCell((short) 4).setCellValue("Description");
		    rowhead.createCell((short) 5).setCellValue("Application");		    
		    rowhead.createCell((short) 6).setCellValue("Version");
		    
		    
		    int i = 1;
		    while (rsComp.next())
		    {
		        HSSFRow row = sheet.createRow((short) i);
		        row.createCell((short) 0).setCellValue(rsComp.getString("Endpoint"));
		        row.createCell((short) 1).setCellValue(rsComp.getString("IPAddress"));
		        row.createCell((short) 2).setCellValue(rsComp.getString("cveid"));		        
		        row.createCell((short) 3).setCellValue(rsComp.getString("score"));
		        row.createCell((short) 4).setCellValue(rsComp.getString("description"));
		        row.createCell((short) 5).setCellValue(rsComp.getString("application"));
		        row.createCell((short) 6).setCellValue(rsComp.getString("version"));
		        
		        i++;
		    }
		    String yemi = "D:/test1.xls";
		    FileOutputStream fileOut = new FileOutputStream(yemi);
		    
		 //   FileOutputStream fileOut=new FileOutputStream(new File("ExcelSheet.xls"));
		    workbook.write(fileOut);
		    fileOut.close();
		*/
			/* export ends */
			
			
			
			//System.out.println("here");
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in getEndPointData"+e.toString());
		}
		
		/*try{
			if(cstmt!=null)
			{
				cstmt.close();
			}						
					
			if(con!=null)
			{
				con.close();
			}
		}*/
		finally{
			
		}		
		return rsComp;
	} 
	

	public ResultSet getTop10HostbyIssue(HttpServletRequest req,String strScanDate) throws IOException, ClassNotFoundException, SQLException
	{
		
		con=null;ResultSet rsTemp=null;pst=null;cstmt=null;	
		String tempDate='%'+strScanDate+'%';
		//String tempHost='%'+strIpAddr;
		
		
		try
		{
			con=DBconnect.getConnectionStatus();
			
			pst=con.prepareStatement("{call SCUBE_TOP10HOST_BYISSUE(?)}");
			pst.setString(1,tempDate);			
			
			rsTemp=pst.executeQuery();
						
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in getTop10HostbyIssue"+e.toString());
		}
		
		
		finally{
			
		}		
		return rsTemp;
	} 
	
	public ResultSet getOsPieChart(HttpServletRequest req) throws IOException, ClassNotFoundException, SQLException
	{
		
		con=null;ResultSet rsTemp=null;pst=null;cstmt=null;	
	//	String tempDate='%'+strScanDate+'%';
		//String tempHost='%'+strIpAddr;
				
		try
		{
			con=DBconnect.getConnectionStatus();
			
			pst=con.prepareStatement("{call Inventory_OSDistributionPieChart()}");
			//pst.setString(1,tempDate);			
			
			rsTemp=pst.executeQuery();
						
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in getOsPieChart"+e.toString());
		}
		
		
		finally{
			
		}		
		return rsTemp;
	} 
	
	
	public ResultSet getTop10HighCveMcs(HttpServletRequest req) throws IOException, ClassNotFoundException, SQLException
	{
		
		con=null;ResultSet rsTemp=null;pst=null;CompStmt=null;
		String strQuery="select top 10 Endpoint,IPaddress,count(CVEid) cnt from ViewCVE where CAST(Score as float)  between 7 and 10 group by Endpoint,IPAddress order by cnt desc";		
		try
		{
			con=DBconnect.getConnectionStatus();			
			CompStmt=con.createStatement();			
			rsTemp=CompStmt.executeQuery(strQuery);
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in getTop10HighCveMcs"+e.toString());
		}
		
		
		finally{
			
		}		
		return rsTemp;
	} 
	
	
	
	
	
	
	public ResultSet getTop10HighCve(HttpServletRequest req) throws IOException, ClassNotFoundException, SQLException
	{
		
		con=null;ResultSet rsTemp=null;pst=null;CompStmt=null;
		String strQuery="select top 10 CVEid,description, count(CVEid) cnt from ViewCVE where CAST(Score as float)  between 7 and 10 group by CVEid,description order by cnt desc";		
		try
		{
			con=DBconnect.getConnectionStatus();			
			CompStmt=con.createStatement();			
			rsTemp=CompStmt.executeQuery(strQuery);
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in getTop10HighCve"+e.toString());
		}
		
		
		finally{
			
		}		
		return rsTemp;
	} 
	
	public int getSeverityWiseCnt(String start,String end) throws IOException, ClassNotFoundException, SQLException
	{
		
		con=null;ResultSet rsTemp=null;pst=null;CompStmt=null;int recCount=0;
		String strQuery="select count(*) as cnt from ViewCVE where CAST(Score as float)  between " +start +" and "+ end;		
		try
		{
			con=DBconnect.getConnectionStatus();			
			CompStmt=con.createStatement();			
			rsTemp=CompStmt.executeQuery(strQuery);
			while (rsTemp.next())
		    {
				recCount=rsTemp.getInt("cnt");
		    }
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in getSeverityWiseCnt"+e.toString());
		}
		
		
		finally{
			
		}		
		return recCount;
	} 
	
	
	
/*	select * from ViewCVE where CAST(Score as float)  between 0 and 3.9
	select * from ViewCVE where CAST(Score as float)  between 4 and 6.9
	select * from ViewCVE where CAST(Score as float)  between 7 and 10 */
	
	
	
	
}
