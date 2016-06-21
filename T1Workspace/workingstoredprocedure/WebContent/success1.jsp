<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<title>File Upload Success</title>
</head>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/success1_new.js"></script> --%>
<body>
<form action="frm" method="get" >
You have successfully uploaded <s:property value="myFileFileName"/>

<%
java.sql.Connection con;
java.sql.Statement stmt=null;
java.sql.ResultSet rs=null;
java.sql.PreparedStatement pst=null;

java.sql.Statement stmt1=null;
java.sql.ResultSet rs1=null;
java.sql.PreparedStatement pst1=null;



try{
	System.out.println("keeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee3333");

// for MySql
	Class.forName("com.mysql.jdbc.Driver");
		String connectionUrl 	= "jdbc:mysql://127.0.0.1:3307/test";
		String user = "root";
		String pass = "secret_root_password"; 
 
// for Sql 
		
	/*	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		String connectionUrl = "jdbc:sqlserver://192.168.0.231;database=test";
		String user = "scubeadmin";
		String pass = "admin@123"; 
		*/
				
    con = java.sql.DriverManager.getConnection(connectionUrl, user, pass);
    
    stmt = con.createStatement();
    stmt1 = con.createStatement();
   /*  if (con != null) {
        DatabaseMetaData dm = (DatabaseMetaData) con.getMetaData();
        System.out.println("Driver name: " + dm.getDriverName());
        System.out.println("Driver version: " + dm.getDriverVersion());
        System.out.println("Product name: " + dm.getDatabaseProductName());
        System.out.println("Product version: " + dm.getDatabaseProductVersion());
    } */
	
	 String query = "Select A1,A2,A3,A4,A5,A6 from UploadFirstFile;";
      rs = stmt.executeQuery(query);
      System.out.println("keecheckpost");
 
      
     %>
     <%! int day = 3; %>    
     <br>
   <table style="border: 1px solid #101010;"> 
    
	
   <%         
      while(rs.next()){
         //Retrieve by column name
    	 String A1  = rs.getString("A1");
    	 String A2 = rs.getString("A2");
         String A3 = rs.getString("A3");
         String A4 = rs.getString("A4");
         String A5 = rs.getString("A5");
         String A6 = rs.getString("A6");
                 //Display values
         System.out.print("A1" + A1);
         System.out.print(", A2" + A2);
         System.out.print(", A3 " + A3);
         System.out.print(", A4 " + A4);
         System.out.print(", A5 " + A5);
         System.out.print(", A6 " + A6);
         System.out.println("");     
         
         if(rs.getRow() == 1){
             %>
    	        <tr>
		<td><input type="radio" name="select_coulmn1" id="select_coulmn1" value="A1"> <input type="radio" name="select_coulmn4" id="select_coulmn4" value="A1"><%= A1 %> </td>
		<td><input type="radio" name="select_coulmn1" id="select_coulmn1" value="A2"><input type="radio" name="select_coulmn4" id="select_coulmn4" value="A2"><%= A2 %> </td>
		<td><input type="radio" name="select_coulmn1" id="select_coulmn1" value="A3"> <input type="radio" name="select_coulmn4" id="select_coulmn4" value="A3"><%= A3 %>  </td>
		<td><input type="radio" name="select_coulmn1" id="select_coulmn1" value="A4"> <input type="radio" name="select_coulmn4" id="select_coulmn4" value="A4"><%= A4 %> </td>
		<td><input type="radio" name="select_coulmn1" id="select_coulmn1" value="A5"><input type="radio" name="select_coulmn4" id="select_coulmn4" value="A5"><%= A5 %>  </td>
		<td><input type="radio" name="select_coulmn1" id="select_coulmn1" value="A6"><input type="radio" name="select_coulmn4" id="select_coulmn4" value="A6"><%= A6 %> </td>     
	</tr>
            <% 
            	 
             }
         
         
         %>  
         <tr style="background-color: #ECECEC;">
         <td ><%=rs.getString("A1")%></td>
         <td><%=rs.getString("A2")%></td>
         <td><%=rs.getString("A3")%></td>
         <td><%=rs.getString("A4")%></td>
         <td><%=rs.getString("A5")%></td>
         <td><%=rs.getString("A6")%></td>
         </tr>
<%
      }
   %> 
  </table> 
<%
      String query1 = "Select A1,A2,A3,A4,A5,A6 from UploadSecondFile;";
  	//pstCheckExist = con.prepareStatement(query);

  	System.out.println("keecheckpost345");
        rs1 = stmt1.executeQuery(query1);
        //STEP 5: Extract data from result set
        System.out.println("keecheckpost2");
      %> 
      <br>  
   <table style="border: 1px solid #101010;">
  <%--  <tr>
	   	<td><input type="radio" name="select_coulmn2" id="select_coulmn2" value="A1"><%= rs1.first()).getString("A1") %> <input type="radio" name="select_coulmn3" id="select_coulmn3" value="A1">A1 </td>
		<td><input type="radio" name="select_coulmn2" id="select_coulmn2" value="A2">A2 <input type="radio" name="select_coulmn3" id="select_coulmn3" value="A2">A2</td>
		<td><input type="radio" name="select_coulmn2" id="select_coulmn2" value="A3">A3 <input type="radio" name="select_coulmn3" id="select_coulmn3" value="A3">A3</td>
		<td><input type="radio" name="select_coulmn2" id="select_coulmn2" value="A4">A4 <input type="radio" name="select_coulmn3" id="select_coulmn3" value="A4">A4</td>
		<td><input type="radio" name="select_coulmn2" id="select_coulmn2" value="A5">A5 <input type="radio" name="select_coulmn3" id="select_coulmn3" value="A5">A5</td>
		<td><input type="radio" name="select_coulmn2" id="select_coulmn2" value="A6">A6 <input type="radio" name="select_coulmn3" id="select_coulmn3" value="A6">A6</td>       
   </tr> --%>
   <%          
        while(rs1.next()){
        	System.out.println("keecheckpost45");
           //Retrieve by column name
      	  String A1  = rs1.getString("A1");
      	System.out.print("A1" + A1);
    	  String A2 = rs1.getString("A2");
    	  System.out.print(", A2" + A2);
         String A3 = rs1.getString("A3");
         System.out.print(", A3 " + A3);
         String A4 = rs1.getString("A4");
         System.out.print(", A4 " + A4);
         String A5 = rs1.getString("A5");
         System.out.print(", A5 " + A5);
         String A6 = rs1.getString("A6");
         System.out.print(", A6 " + A6);
         //Display values
         
         if(rs1.getRow() == 1){
         %>
	        <tr>
	     	   	<td><input type="radio" name="select_coulmn2" id="select_coulmn2" value="A1"><input type="radio" name="select_coulmn3" id="select_coulmn3" value="A1"><%= A1 %>  </td>
	     		<td><input type="radio" name="select_coulmn2" id="select_coulmn2" value="A2"> <input type="radio" name="select_coulmn3" id="select_coulmn3" value="A2"><%= A2 %></td>
	     		<td><input type="radio" name="select_coulmn2" id="select_coulmn2" value="A3"> <input type="radio" name="select_coulmn3" id="select_coulmn3" value="A3"><%= A3 %></td>
	     		<td><input type="radio" name="select_coulmn2" id="select_coulmn2" value="A4"> <input type="radio" name="select_coulmn3" id="select_coulmn3" value="A4"><%= A4 %></td>
	     		<td><input type="radio" name="select_coulmn2" id="select_coulmn2" value="A5"> <input type="radio" name="select_coulmn3" id="select_coulmn3" value="A5"><%= A5 %></td>
	     		<td><input type="radio" name="select_coulmn2" id="select_coulmn2" value="A6"> <input type="radio" name="select_coulmn3" id="select_coulmn3" value="A6"><%= A6 %></td>       
	        </tr>
        <% 
        	 
         }
         
         
         
         //System.out.print(", A5 " + A5);
        
         System.out.println("");        
                    %>  
           <tr style="background-color: #ECECEC;">
           <td><%=rs1.getString("A1")%></td>
           <td><%=rs1.getString("A2")%></td>
           <td><%=rs1.getString("A3")%></td>
           <td><%=rs1.getString("A4")%></td>
           <td><%=rs1.getString("A5")%></td>
           <td><%=rs1.getString("A6")%></td>
           </tr>
  <%
        } 
   %> 
   
  <tr>
  <td><input type="submit" value="Submit"></td>
  </tr>
</table>
<%

} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
%>
</form>
</body>
</html>