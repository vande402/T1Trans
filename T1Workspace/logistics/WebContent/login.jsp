<%@page import="java.sql.ResultSetMetaData"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>

<link rel="stylesheet" href="css/bootstrap.min.css"/>
<link href="//cdn.datatables.net/plug-ins/f2c75b7247b/integration/bootstrap/3/dataTables.bootstrap.css" rel="stylesheet" >

<link href="/favicon.ico"  type="image/x-icon">
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="css/login.css" rel="stylesheet">



</head>
<body>
<i class='fa fa-cog fa-spin fa-1x fa-fw' id='progressBar' style="display: none;position: absolute;left: 47%;top: 25%;font-size: 60pt;color: #337ab7;z-index: 10;"></i>
<!--
    you can substitue the span of reauth email for a input with the email and
    include the remember me checkbox
    -->
    <div class="container">
	<div class="login-box">
			<div class="col-md-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<strong> Sign in to continue</strong>
						<button type="button" class="btn btn-block btn-primary btn-lg" data-toggle="modal" data-target="#add_userdiv"> 
										<i class="fa fa-fw fa-user-plus"></i>&nbsp;&nbsp;Sign up
									</button>
					</div>
					<div class="panel-body">
						<form role="form">
							<fieldset>
								<div class="row">
									<div class="center-block">
										<img class="profile-img"
											src="static/images/logo2.png" alt="">
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12 col-md-10  col-md-offset-1 ">
										<div class="form-group">
											<div class="input-group">
												<span class="input-group-addon">
													<i class="glyphicon glyphicon-user"></i>
												</span> 
												<input class="form-control" placeholder="Username" name="userName" type="text" id ="userName" autofocus>
											</div>
										</div>
										<div class="form-group">
											<div class="input-group">
												<span class="input-group-addon">
													<i class="glyphicon glyphicon-lock"></i>
												</span>
												<input class="form-control" placeholder="Password" name="password" type="password" id ="password">
											</div>
										</div>
										<p  id="errorMsg"></p>
										<div class="form-group">
											<a class="btn btn-lg btn-primary btn-block" id="submitBtn">Sign in</a>
										</div>
									</div>
								</div>
							</fieldset>
						</form>
					</div>
					<div class="panel-footer">
						<a href="#"  id="forgetPassBtn">Forget Password ?</a>
					</div>
                </div>
			</div>
		</div>
	</div>
	
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
											
											<!-- <div class="checkbox">
												<label> 
													<input type="radio" value="Admin" id="radAdm" name="radAdmin">Admin
													&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<input type="radio" value="User" id="radUser" name="radAdmin"> User
												</label> 
											</div> -->
										</div>
										<p  id="errorMsgSingUp"></p>
									</div>
									<div class="modal-footer">									
										<div class="col-md-3 col-md-offset-3">
												<button class="btn btn-lg btn-danger btn-block " data-dismiss="modal" aria-label="Close"  type="button" >Cancel</button>
										</div>
										
										<div class="col-md-3 col-md-offset-1">
											<button class="btn btn-lg btn-success btn-block" id="adminAddNewUser-js" >Submit</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
	
	
	
	
	
	<!--Moidal container starts here  -->
	 <div class="modal fade" id="forget-email-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel" align="center">Forget password prompt</h4>
	      </div>
	      <div class="modal-body">
	        		<div class="container" style="width: 100%;">
	<div class="panel panel-primary" id="form-panel">
     <div class="panel-heading">
       <h3 class="panel-title" align="center" id="modal-incident-id">Please Enter your account Email address</h3>
     </div>
   <div class="panel-body form-panel">
		<h5><strong><sup><span class="glyphicon glyphicon-asterisk requirefield-asterisk"></span></sup>Required Field</strong></h5>
		<form id="resolvedStatusForm" class= "form-horizontal" role="form">
			  <div id="system-group" class="form-group">
			     	   <label for="system-no" class="col-sm-4 control-label">Email address<sup><span class="glyphicon glyphicon-asterisk requirefield-asterisk"></span></sup>:</label>
			    		<div class="input-group col-sm-8">
	                        <input type="text" class="form-control required" id="forget-email" placeholder="john@gmail.com"> 
	                        <!-- <textarea class="form-control"  id="system-no"></textarea> -->
	            	</div>
	            	<!-- <p id="errorMsgModal"></p> -->
			  </div>
			  <div id="error-group" class="form-group">
			  
			       <label class=" col-sm-4 control-label" id="errorMsgModal"></label>
			  </div>
		</form>	  

	 </div>
	 <!-- panel end --> 
	</div>
	<!-- end container div --> 
</div>  
	      </div>
	      <div class="modal-footer">
	          <button type="button" class="btn btn-primary" id=forget-submit-btn data-loading-text="<i class='fa fa-cog fa-spin fa-1x fa-fw'></i>&nbsp;Loading...">Submit</button>
	          <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
	        <!-- <button type="button" class="btn btn-primary">Save changes</button> -->
	      </div>
	    </div>
	  </div>
 </div>
<script src="js/jquery/jquery.min.js"></script>

<script src="js/bootstrap.js"></script>
<script src="js/login.js"></script>
<!-- <script src="static/js/home.js"></script>
 -->

</body>
</html>