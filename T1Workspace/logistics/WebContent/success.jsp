<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.sql.*"%>
<html>
<head>
<title>File Upload Success</title>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-ui.js"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script> --%>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/success.js"></script> --%>

</head>
<body>
	You have successfully uploaded
	<s:property value="myFileFileName" />

	<%
		java.sql.Connection con;
		System.out.println("keeeeeeeeeeeeeeevvvvvv");

		java.sql.Statement stmt = null;
		java.sql.ResultSet rs = null;
		java.sql.PreparedStatement pst = null;


		try {
		System.out.println("keeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee222");
// for sql server

 	/*	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String connectionUrl = "jdbc:sqlserver://192.168.0.231;database=test";
		String user = "scubeadmin";
		String pass = "admin@123"; 
	*/	
		
// for MySql

 		Class.forName("com.mysql.jdbc.Driver");
		String connectionUrl = "jdbc:mysql://127.0.0.1:3307/test";
		String user = "root";
		String pass = "secret_root_password"; 
 
		con = java.sql.DriverManager.getConnection(connectionUrl, user,pass);
			 
			 
			System.out.println("keeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee222");
			
			System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");

			stmt = con.createStatement();

			/*  if (con != null) {
			     DatabaseMetaData dm = (DatabaseMetaData) con.getMetaData();
			     System.out.println("Driver name: " + dm.getDriverName());
			     System.out.println("Driver version: " + dm.getDriverVersion());
			     System.out.println("Product name: " + dm.getDatabaseProductName());
			     System.out.println("Product version: " + dm.getDatabaseProductVersion());
			 } */

			String query =  "select A1,A2,A3,A4,A5,A6,A7,A8,A9,A10,A11,A12,A13,A14,A15,A16,A17,A18,A19,A20,A21,A22 from try1;";
			//pstCheckExist = con.prepareStatement(query);

			rs = stmt.executeQuery(query);
			//STEP 5: Extract data from result set
	%>
		
		
		<%
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	%>

aaaaa
	Pleace uplode next file.
	<div id="keshav"></div>
	<center> <form action="uploadSecondFile" method="post" enctype="multipart/form-data">
		<label for="myFile">Upload Lindex file</label> 
		<input type="file" name="myFile" /> 
		<input type="submit" value="Upload" />
	</form></center>
<iframe src="https://leandex.leanlogistics.com/index.html?validationCode=CyHULeFEK-j168SAs-r5KVFm6glRIhnOrwgYuKT8-XpTxlHpS1aqW914_rhmhZst"
 onload="this.width=screen.width; this.height=screen.height;" ></iframe>
  
</body>
</html>