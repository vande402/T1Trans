<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@include file="validateSession.jsp" %>

<!DOCTYPE html >
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>User Management</title>
<!-- Tell the browser to be responsive to screen width -->
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!-- Bootstrap 3.3.5 -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css">
<link href='https://fonts.googleapis.com/css?family=Kaushan+Script' rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Droid+Serif:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700' rel='stylesheet' type='text/css'>


	<link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">

<link rel="stylesheet" href="static/css/stylesheet.css"/>
<%-- <!-- DataTables -->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/dataTables.bootstrap.css">
<!-- Theme style -->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/AdminLTE.min.css">
<!-- Theme Skin CSS-->
	<link rel="stylesheet" href="<%=request.getContextPath()%>/static/css/skin-blue.min.css"> --%>
<!-- jQuery 2.1.4 -->
<script type="text/javascript" src="//code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript" src="//code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<!-- Bootstrap 3.3.5 -->
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/bootstrap.min.js"></script>
<!-- DataTables -->
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/dataTables.bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="<%=request.getContextPath()%>/static/js/plugins/jquery.slimscroll.min.js"></script>


<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/adminSetting.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/adminUserList.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/adminUserMaster.js"></script> 
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/com.js"></script>

<style type="text/css">

.content {
     min-height: initial;
	}
</style>

</head>

<body class="hold-transition skin-blue sidebar-mini">

<!-- Main Header -->
	<header>
		<div class="container">
			<div class="row">
				<!-- Logo section -->
				<div class="col-md-2 text-center">
						<!-- Logo. -->
					<div class="logo">
						<a href="index.jsp">
						logo</a>
					</div><!-- Logo ends -->
				</div>
		  		<div class="col-md-8">
		  			<h1>name</h1>
		  		</div>
 				<h4> <a id="logIn-js"><%=session.getAttribute("UserName")%></a></h4> 
 				<a href="#"  id="LogoutServlet-js"><small>SignOut</small> </a> 
			</div>
		</div>
	</header>

	<div class="row page-head" style="display: none;">
		<div class="col-md-6">
			<div class="col-md-12">
				<button type="button" class="btn btn-grey">Enterprise</button>
			</div>
		</div>
		<div class="col-md-3 text-right">
			<label style="padding-top:6px;font-weight: normal;font-size: 14px;">Select Inspection Date :&nbsp;</label>
			<select class="form-control select2 pull-right" style="width:auto;" name="selScanDate" id="scandate" onchange="javascript:callReport();" >
			<option value="select" >Scan Date :</option>
			</select>	
		</div>
		<div class="col-md-3 text-right ">
			<div class="col-md-12 endPointsScannedDiv text-right">
			</div>
		</div>
	</div>
<section class="content">
	<!-- content -->
	<div class="main-footer">
		<!-- box -->
<!-- 		<div class="box">
			pie circles
			<div class="row">
				<div class="col-md-12">
					Content Header (Page header)
					<section class="content-header">
						<h1>
							Admin Management <small>Manage your Setting</small>
						</h1>
					</section>
				</div>
			</div>
		</div> -->
		<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
			<div class="panel box">
			    <div class=" page-head" role="tab" id="headingOne">
			      <h3>
			        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo" style="color: white;text-decoration:blink;">
			          Manage your users <small>add ,edit & delete user</small>
			        </a>
			      </h3>
			    </div>
			    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
			      <div class="panel-body">
			        <div class="box-body">
						<div class="row">
							<div class="col-md-12">
								<div class="col-md-2 pull-right">
									<button type="button" class="btn btn-block btn-primary btn-lg" data-toggle="modal" data-target="#add_userdiv"> 
										<i class="fa fa-fw fa-user-plus"></i>&nbsp;&nbsp;ADD USER
									</button>
								</div>
							</div>
						</div>
						<div class="row">
				<!-- /.box-header -->
							<div class="box-body">
								<table id="example1" class="table table-bordered box ">
									<thead style="background-color: #756D7D;color: #130303;">
										<tr>
											<th>User Name</th>
											<th>Mobile No.</th>
											<th>Email Id</th>
											<th>Role Assigned</th>
											<th>Edit</th>
											<th>Delete</th>
										</tr>
									</thead>
									<tbody class=" table-striped reportTableToPrint-js page-head" id="userListData" align="left"></tbody>
								</table>
							</div>
						</div>
					</div>
			</div>
	      </div>
	    </div>
	  </div>
	  <!-- <div class="panel box">
	    <div class=" page-head " role="tab" id="headingTwo">
	      <h3>
	        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne" style="color: white;text-decoration:blink;">
	          Manage your Setting <small>Logo & Days</small>
	        </a>
	      </h3>
	    </div>
	    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
			<div class="panel-body">
				<div class="col-md-6">
						<div class="box">
							<div class="box-body">
								<div class="row">
									<div class="col-md-12">
										<h2>Admin Current Setting</h2>
										<div class="col-md-offset-1">	
											<div id='selectUpLoadFile-js'></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="box">
							<div class="box-body">
								<div class="row">
									<div class="col-md-12">
										<h2>Change Admin Setting</h2>
									<div class="col-md-12">	
										<div class="form-group col-md-offset-1">
											<h4><strong>Upload Logo:</strong></h4>
											<div class="special col-md-5">
												<form id="uploaimageForm-js" method="post" enctype="multipart/form-data">
													<div class="uilib-btn btn-large" style="background-image: url('static/images/upload.png');background-size:90% 100%;padding: 0;">
														<input type="file" class="btn btn-grey" name="uploadBtn" value="Upload" id="uploadBtn"/>
													</div>
												</form>
											</div>
											<div class="col-md-5">
												<img id="orgImage-js" src="static/images/upload_image.png" alt="upload_img" style="width:100px; height:100px;"/>
											</div>
										</div>
									</div>
									<div class="col-md-12">	
										<div class="form-group col-md-offset-1">
											<h4><strong>Issues Resolved Days</strong></h4>
											<div class="col-md-5">
												<input type="number" class="form-control" name="unresolvedDay-js" id="unresolvedDay-js" max="10" min="1"  value="1" />
											</div>
										</div>
									</div>
									<div class="col-md-12">	
										<div class="col-md-3 col-md-offset-3">
											<button class="btn btn-grey" data-dismiss="modal" aria-label="Close"  type="button" >Cancel</button>
										</div>
										<div class="col-md-3 col-md-offset-1">
											<button class="btn btn-grey" id="btnSaveData-js" type="submit">Submit</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div> -->
				<!-- <div class="box"> -->
					
				<!-- ADD User -->
				<div class="modal fade" id="add_userdiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="example-modal" id="add_userdiv1">  
						<div class="modal" style="display: block; color: black;">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										<h4 class="modal-title">
											<i class="fa fa-fw fa-user-plus"></i>&nbsp;&nbsp;Add User
										</h4>
									</div>
									<div class="modal-body">
										<div class="box-body">
											<div class="form-group">
												<input type="text" class="form-control" name="newUserName"	id="newUserName" placeholder="Name" autofocus required />
											</div>
											<div class="form-group">
												<input type="email" class="form-control" name="newUserEmail" id="newUserEmail" placeholder="Email Address" required />
											</div>
											<div class="form-group">
												<input type="text" class="form-control" name="newUserMmobile" id="newUserMmobile" placeholder="Mobile No" required />
											</div>
											
											<div class="checkbox">
												<label> 
													<input type="radio" value="Admin" id="radAdm" name="radAdmin">Admin
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="radio" value="User" id="radUser" name="radAdmin"> User
												</label> 
											</div>
										</div>
										<p  id="errorMsg"></p>
									</div>
									<div class="modal-footer">									
										<div class="col-md-3 col-md-offset-3">
												<button class="btn btn-lg btn-danger btn-block " data-dismiss="modal" aria-label="Close"  type="button" >Cancel</button>
										</div>
										
										<div class="col-md-3 col-md-offset-1">
											<button class="btn btn-lg btn-success btn-block" id="adminAddNewUser-js" type="submit">Submit</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>	
		       <div class="modal fade" id="loginSection" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="example-modal" id="editPopupScreen"  class="editPopupScreen" style="display:none">
					<div class="modal" style="display: block; color: black;">
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
											<input type="hidden" class="form-control" name="editUserId" id="editUserId" />
											<input type="text" class="form-control" name="editUserName" id="editUserName" required />
										</div>
										<div class="form-group">
											<input type="text" class="form-control" name="editUserEmail" id="editUserEmail" disabled="disabled" />
										</div>
										<div class="form-group">
											<input type="text" class="form-control" name="editUserMobile" id="editUserMobile" required />
										</div>
<!-- 										<div class="form-group">
											<input type="text" class="form-control" name="editUserRole" id="editUserRole" required />
										</div> -->
										<div class="checkbox">
											<label> 
												<input type="radio" value="Admin" id="chkAdmin" name="editChkRole">Admin
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												<input type="radio" value="User" id="chkUser" name="editChkRole"> User
											</label> 
										</div>
									</div>
									<!-- /.box-body -->
								</div>
								<div class="modal-footer">
									<div class="col-md-3 col-md-offset-3">
										<button class="btn btn-lg btn-danger btn-block"
											id="btn-cancel-userupdate" type="submit">Cancel</button>
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
		      
		      
		   	</div>
</section>	
			
	<!-- Main Footer -->
	<footer class="content">
		<div class="col-md-12">
				<!-- To the right -->
				<!-- Default to the left -->
			<strong>Copyright &copy; 2015 </strong> All rights reserved.
		</div>
	</footer>
<script type="text/javascript" src="static/js/jquery.form.min.js"></script>


</body>
</html>
