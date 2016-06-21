<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>File Upload</title>
</head>
<body>
  <center> <form action="uploadFirstFile" method="post" enctype="multipart/form-data">
      <label for="myFile">Upload DAT result file</label>
      <input type="file" name="myFile" />
      <input type="submit" value="Upload"/>
      <label for="myFile">Login:- teetsj </label>
      <label for="myFile">password:-trans@212
       </label>
   </form></center>
   <iframe src="https://rateview.dat.com/app/#/multilane" onload="this.width=screen.width; this.height=screen.height;" ></iframe>
   
</body>
</html>