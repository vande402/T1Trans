<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<title>File Upload Success</title>
</head>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css">


<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>

<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/inter.js"></script>

<style>
.example-modal .modal {
	position: relative;
	top: auto;
	bottom: auto;
	right: auto;
	left: auto;
	display: block;
	z-index: 1;
}

.example-modal .modal {
	background: transparent !important;
}
</style>


<body>
<div>
You have successfully uploaded inter <s:property value="myFileFileName"/>

<%
java.sql.Connection con;
java.sql.Statement stmt=null;
java.sql.ResultSet rs=null;
java.sql.PreparedStatement pst=null;

java.sql.Statement stmt1=null;
java.sql.ResultSet rs1=null;
java.sql.PreparedStatement pst1=null;



try{
	System.out.println("keeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee8888883");
// for MySql
	Class.forName("com.mysql.jdbc.Driver");
		String connectionUrl 	= "jdbc:mysql://127.0.0.1:3307/test";
		String user = "root";
		String pass = "secret_root_password"; 
 		
// for Sql 
/*		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
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
	
	
/*	String firstcol= request.getParameter("select_coulmn1");
    String secondcol = request.getParameter("select_coulmn2");
    String firstcolsec= request.getParameter("select_coulmn3");
    String secondcolsec = request.getParameter("select_coulmn4");
   
    String part1 = "SELECT f.id as A0TId ,f.A1 as A1T ,f.A2 as A2T,f.A3 as A3T,f.A4 as A4T, f.A5 as A5T, f.A6 as A6T,"+ 
    				"s.id as A0EId ,s.A1 as A1E ,s.A2 as A2E,s.A3 as A3E,s.A4 as A4E,s.A5 as A5E,s.A6 as A6E  FROM UploadFirstFile f LEFT JOIN UploadSecondFile s  ON ";
    
  	String s = " f." , s1 = " =s.", semi = ";", AND = " and ";
    String query2 = part1 + s + firstcol  + s1 + secondcol + AND + s+ secondcolsec +s1 + firstcolsec + semi;
    System.out.println(query2);
    */
  
  String  query2 = "SELECT * from FinalUploadFile";
	//pstCheckExist = con.prepareStatement(query);

	
    rs1 = stmt1.executeQuery(query2);
    //STEP 5: Extract data from result se
      %>  
      <br> 
    <table style="border: 1px solid #101010;" id="FinalTable">
   <%      
    while(rs1.next()){
    	  String A1Count = rs1.getString("NamCount");
    	  
    	  
    	  System.out.println("-11-A1Count--"+A1Count);
    	 /*  if(A1Count.equalsIgnoreCase(null)){
    		  System.out.println("--equalsIgnoreCase--");
    		  A1Count="0";
    	  } */
    	  if(A1Count == null){
    		  System.out.println("--==--");
    		  A1Count="0";
    	  }
    	  
    	  System.out.println("-22-A1Count--"+A1Count);
    	  int count = Integer.parseInt(A1Count);

    	  System.out.println("--count--"+count);
       %>  
         
    	   <tr style="background-color: #ECECEC;"> 
    	  
       
       <td><a id ="valueall" data-toggle='modal' data-target='#loginSection' useridT='<%=rs1.getString("Id")%>' fid="<%=rs1.getString("firID")%>" sid ="<%=rs1.getString("secID")%>">
       
		<i class='icon ion-compose'  id='editIconComment' >edit</i></a></td>
       <td><%=rs1.getString("A1")%></td><td><%=rs1.getString("A2")%></td><td><%=rs1.getString("A3")%></td><td><%=rs1.getString("A4")%></td><td><%=rs1.getString("A5")%></td><td><%=rs1.getString("A6")%></td>
        
<%
if(count>=2){
	
	System.out.println("if");
	%>
	<td><%= rs1.getString("A7")+"*" %></td>
	<%
		}else{
			System.out.println("else");
			%>
		<td><%= rs1.getString("A7") %></td>	
		<% 
		}
	%>
	<td><%= rs1.getString("A8") %></td><td><%=rs1.getString("A9") %></td><td><%=rs1.getString("A10") %></td><td><%=rs1.getString("A11") %></td>
	
	<td><%=rs1.getString("NamCount") %></td>
	<% 
     }
   %> 
   
   </tr>
  </table> 
  
  <div><a href="#" id="btnExport" title="Export to Excel"><i class="fa fa-file-excel-o">Download</i></a></div>
  
  <div class="modal fade" id="loginSection" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="example-modal" id="editPopupScreen"  class="editPopupScreen">
					<div class="modal">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-label="Close">
										<span aria-hidden="true">&times;</span>
									</button>
									<h4 class="modal-title">
										<i class="fa fa-fw fa-edit"></i>&nbsp;&nbsp;Edit User Information
									</h4>
								</div> 
								<div class="modal-body" id="updateUserInfo">
									<div class="box-body" id="tblEditBookingInfo">
										<div class="form-group">
											<input type="hidden" class="form-control" name="editIdT" id="editIdT" />
											<input type="hidden" class="form-control" name="userIdE" id="userIdE" />
											<input type="hidden" class="form-control" name="fid" id="fid" />
											<input type="hidden" class="form-control" name="sid" id="sid" />
											
											<input type="text" class="form-control" name="editA1" id="editA1" required />
										</div>
										<div class="form-group">
											<input type="text" class="form-control" name="editA2" id="editA2" required />
										</div>
										<div class="form-group">
											<input type="text" class="form-control" name="editA3" id="editA3" required />
										</div>
 										<div class="form-group">
											<input type="text" class="form-control" name="editA4" id="editA4" required />
										</div>
										<div class="form-group">
											<input type="text" class="form-control" name="editA5" id="editA5" required />
										</div>
										<div class="form-group">
											<input type="text" class="form-control" name="editA6" id="editA6" required />
										</div>
										
										<div class="form-group">
											<!-- <input type="text" class="form-control" name="editA7" id="editA7" required /> -->
											<select class="form-control" id="allSecName"></select>
										</div>
										<div class="form-group">
											<input type="text" class="form-control" name="editA8" id="editA8" required />
										</div>
										<div class="form-group">
											<input type="text" class="form-control" name="editA9" id="editA9" required />
										</div>
										<div class="form-group">
											<input type="text" class="form-control" name="editA10" id="editA10" required />
										</div>
										<div class="form-group">
											<input type="text" class="form-control" name="editA11" id="editA11" required />
										</div>
										<div class="form-group">
											<input type="text" class="form-control" name="editA12" id="editA12" required />
										</div>
									</div>
									<!-- /.box-body -->
								</div>
								<div class="modal-footer">
									<div class="col-md-3 col-md-offset-3">
										<button class="btn btn-lg btn-danger btn-block"
											id="btn-cancel-userupdate" type="submit" data-dismiss="modal"
										>Cancel</button>
									</div>
									<div class="col-md-3 col-md-offset-1">
										<button class="btn btn-lg btn-success btn-block"
											id="btn-update-userupdate"  type="submit">Update</button>
									</div>
								</div>
							</div>
							<!-- /.modal-content -->
						</div>
						<!-- /.modal-dialog -->
					</div>
					<!-- /.modal -->
				</div>
			</div>
			<!-- /.Edit User -->
  
  
  
  
  
  
<%
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
%>
</div>
</body>
</html>

