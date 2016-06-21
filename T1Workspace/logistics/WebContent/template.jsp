<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
<script type="text/javascript">


$(document).on("click", "#submitBtn", function() {
	
	var url ="home";

	window.location.href = url;
});



</script>
</head>
<body>

<center> <form action="uploadFile" method="post" enctype="multipart/form-data">
      <label for="myFile">Upload original result file</label>
      <input type="file" name="myFile" />
      <input type="submit" value="Upload"/>
     
      
   </form></center>



<div class="panel-heading">
						<strong> Sign in to continue</strong>
						<button type="button" class="btn btn-block btn-primary btn-lg" id="submitBtn"> 
										<i class="fa fa-fw fa-user-pluss"></i>&nbsp;&nbsp;Sign up
									</button>
					</div>
</body>
</html>