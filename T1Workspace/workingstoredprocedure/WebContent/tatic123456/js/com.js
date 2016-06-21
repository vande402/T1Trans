$(document).ready(function() {
	
});



$(document).on("click", "#LogoutServlet-js", function() {
     $.ajax({
	     url: "LogoutServletController",
		 type: "post",
		 data: {},
	     success: function(data){
	    	 
	    	 data = data.trim();
	    	 if(data === "loggedOut"){
	    		 console.log("data in Report-End:-----------------------",data);
	    		 window.open("login.jsp","_self");
	    	 }
	     }
     });
     
});

$(document).on("click", "#AdminUserManagementServlet-js", function() {
		window.open("adminSettingAccordion.jsp","_self");
});