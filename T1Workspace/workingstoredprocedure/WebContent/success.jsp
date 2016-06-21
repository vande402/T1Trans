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

 <script type="text/javascript" src="<%=request.getContextPath()%>/js/successNew.js"></script> 

<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>


</head>
<body>

	Submit to run stored procedure
	<div id="keshav"></div>
	<form>
	
	<button type="button" id="runPr" class="btn btn-success btn-lg">RUN</button>
	
		</form>
	

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
		<td><label>Free Shipping: </label></td>
		
		
	<table style="border: 1px solid #101010;" id ="fstTd">
			
		<%
			while (rs.next()) {
					//Retrieve by column name
					String A1 = rs.getString("A1");
					String A2 = rs.getString("A2");
					String A3 = rs.getString("A3");
					String A4 = rs.getString("A4");
					String A5 = rs.getString("A5");
					String A6 = rs.getString("A6");
					String A7 = rs.getString("A7");
					String A8 = rs.getString("A8");
					String A9 = rs.getString("A9");
					String A10 = rs.getString("A10");
					String A11 = rs.getString("A11");
					String A12 = rs.getString("A12");
					String A13 = rs.getString("A13");
					String A14 = rs.getString("A14");
					String A15 = rs.getString("A15");
					String A16 = rs.getString("A16");
					String A17 = rs.getString("A17");
					String A18 = rs.getString("A18");
					String A19 = rs.getString("A19");
					String A20 = rs.getString("A20");
					String A21 = rs.getString("A21");
					String A22 = rs.getString("A22");
					
					//Display values
					System.out.print("A1" + A1);
					System.out.print(", A2" + A2);
					System.out.print(", A3 " + A3);
					System.out.print(", A4 " + A4);
					System.out.print(", A5 " + A5);
					System.out.print(", A6 " + A6);

					System.out.println("");
		%>

		

			

  		
		<tr style="background-color: #ECECEC;">
		
			<%-- <td><%=rs.getString("id")%></td> --%>
			<td><%=rs.getString("A1")%></td> <td><%=rs.getString("A2")%></td> <td><%=rs.getString("A3")%></td>
			<td><%=rs.getString("A4")%></td> <td><%=rs.getString("A5")%></td> <td><%=rs.getString("A6")%></td>
			<td><%=rs.getString("A7")%></td> <td><%=rs.getString("A8")%></td> <td><%=rs.getString("A9")%></td>
			<td><%=rs.getString("A10")%></td> <td><%=rs.getString("A11")%></td> <td><%=rs.getString("A12")%></td>
			<td><%=rs.getString("A13")%></td> <td><%=rs.getString("A14")%></td> <td><%=rs.getString("A15")%></td>
			<td><%=rs.getString("A16")%></td> <td><%=rs.getString("A17")%></td> <td><%=rs.getString("A18")%></td>
			<td><%=rs.getString("A19")%></td> <td><%=rs.getString("A20")%></td> <td><%=rs.getString("A21")%></td>
			<td><%=rs.getString("A22")%></td> 
		</tr>

		<%
			}
		%>
	</table>
	
	
	
	
	<%
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	%>
<div id="afterRun"></div>
</body>
</html>