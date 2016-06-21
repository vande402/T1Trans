<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
<title>File Upload Success</title>
</head>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.min.js"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/js/success1_new.js"></script> --%>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/inter.js"></script>

<body>
<div id="selectDiv">
<!-- <form name="index"> -->
<input type="radio" name="file" id = "A" value = "1" >All three
<input type="radio" name="file" id = "B" value = "2"> Original and DAT
<input type="submit" value="submit" id="submit" >
<!--   </form> -->
 </div>
<div id="resultDiv" style="display:none">

   <table class="table table-bordered" id ="FinalTable">  
			<thead id = "final_Tble_hed">
				  
				 </thead> 
				 <tbody id= "FinalTablebody">		 
				 <tbody>    

  </table> 
  
  <div><a href="#" id="btnExport" title="Export to Excel"><i class="fa fa-file-excel-o">Download</i></a></div>
  
 </div>

</body>
</html>