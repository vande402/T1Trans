var emailBody = "";
	
var ajaxAsyncOption = true;

$(document).ready(function(){

	console.log("Doc ready.. login js");

});

$(document).on("focusin", ".form-control", function() {
	$("#errorMsg").empty();
	$("#errorMsgModal").empty();
});



$(document).on("click", "#submitBtn", function() {
	
	if(checkValidation()){
		console.log("Call ajax...");
		 validateFromDB();
	}
	else{
		   console.log("Error mode...");
	}
});

/* **************************** Forget password *************************************** */
/*$(document).on("click", "#forgetPassBtn", function() {
	$("#errorMsgModal").empty();
	$("#forget-email").val("");
	$("#forget-email-modal").modal('show');
});
$(document).on("click", "#forget-submit-btn", function() {
	var email = $("#forget-email").val();
	var $thisVar = $(this);
	if(chkForgetEmailVali(email)){
		
		console.log("$thisVar",$thisVar);
		$(document).ajaxStart(function() {
			  
			console.log("$thisVar...",$thisVar);
			console.log("ajax is startinnggggggg......");
			var $btn = $thisVar.button('loading');
	    	console.log("callellddddd......");
	    	
			});
		$(document).ajaxStop(function() {
			console.log("$this33Var...",$thisVar);
			console.log("ajax is stoppppppppp......");
	  	     console.log("stoppppppppp  callellddddd......");
	  	     $thisVar.button('reset');
	  
	 
			}); 
		validateEmailFromDB();
	}
	else{
		console.log("In errrr mode..");
	}
});*/
/* **************************** End *************************************** */



function checkValidation(){
	var uName = $("#userName").val();
	var pswd = $("#password").val();
	console.log("uName : ",uName,"pswd : ",pswd);
	if(chkEmailVali(uName)){
		if(chknormalFieldVali(pswd)){
			return true;
		}
		else{
			return false;
		}
	}
	else{
		return false;
	}
	
	
}
function validateFromDB(){
	var uName = $("#userName").val();
	var pswd = $("#password").val();
	$.ajax({
		 url: "LoginView?r="+Math.random(),
	     type: "Post",
	     global: false,
	     data: {
	    	 		"flag" : "validateCredential",
	    	 		"userName" : uName,
	    	 		"password" : pswd
	    },
	     success: function(data){
	    	 console.log("data : ",data);
	    	 if(data == null || data == ""){
	    		console.log("empty object show error message...");
	    		$("#errorMsg").text("Invalid Credentials.");
	    	 }
	    	 else{
	    		 	displayMessage(data);
	    	 }
	     },
	     error: function (e) {
	    	  console.log("here error",e);
	     }
	     ,async: ajaxAsyncOption
   });
}
/*function validateEmailFromDB(){
	var email =  $("#forget-email").val();
	$.ajax({
		 url: "LoginView?r="+Math.random(),
	     type: "Post",
	     data: {
	    	 		"flag" : "validateForgetEmail",
	    	 		"forgetEmail" : email,
	    },
	     success: function(data){
	    	 console.log("data : ",data);
	    	 if(data == null || data == ""){
	    		console.log("empty object show error message...");
	    	 }
	    	 else{
	    		 	showForgetMailResponseData(data);
	    	 }
	     },
	     error: function (e) {
	    	  console.log("here error",e);
	     }
	     ,async:false
   });
}
function showForgetMailResponseData(data){
	console.log("showForgetMailResponseData..",data["userType"]);
	var userType = data["userType"];
	userType = userType.trim();
	if(userType === "invalid"){
		createForgetMailDangerClass();
		$("#errorMsgModal").text("Email address does not exist.");
	}
	else if(userType === "admin"){
		 createForgetMailSuccessClass();
		 //$("#errorMsgModal").text("Ohh you admin");
		 sendForgetEmailMsg(data["adminEmail"],data["adminPassword"],data["adminName"]);
		
	}
	else{
		createForgetMailSuccessClass();
		sendForgetEmailMsg(data["engineerEmail"],data["engineerPassword"],data["engineerName"]);
		//$("#errorMsgModal").text("Ohh you Engineer");
		
	}
}
function sendForgetEmailMsg(email,pass,name){
	 emailBody = "<html><head></head><body><table style='border: 1px solid;padding: 2px;'><tr><td>"
		 + "<table style='border: 1px solid;padding: 4px;'><tr><td>Dear "+name+",<br><br></td></tr>"
		 + "<tr><td colspan='2'>Your Username is :"+email+",<br></td></tr>"
		 + "<tr><td colspan='2'>Your Password is :"+pass+",<br></td></tr>"
		 + "<tr><td colspan='2'><a href='http://localhost:8003/IncidentRaisingSystem/Login.html'>Please Click Here to Login your account</a></td></tr>"
		 + "<tr><td colspan='2'>Regards,CD Infoware.</td></tr>"
		 + "</table></td></tr></table></body></html>";
	
	 var emailData = {
			"forgetEmail" : email,
			"forgetPassword":pass,
			"emailBody" : emailBody
	};
	
	 console.log("emailData",emailData);
	$.ajax({
		 url: "LoginView?r="+Math.random(),
	     type: "Post",
	     data: {
	    	 		"flag" : "sendForgetEmailMsg",
	    	 		"emailData" :  JSON.stringify(emailData),
	    	 		
	    },
	     success: function(data){
	    	 console.log("Email; successss......");
	    	 createForgetMailSuccessClass();
	    	 $("#errorMsgModal").text("Password recovery Mail has been sent to your Email Address.");
	    	 
	     },
	     error: function (e) {
	    	  console.log("here error",e);
	     }
	     ,async:true
  });
	
}
*/
function displayMessage(data){
	console.log("hereeee..",data["userType"]);
	var userType = data["userType"];
	userType = userType.trim();
	if(userType === "invalid"){
		 createDangerClass();
		$("#errorMsg").text("Invalid Credentials.");
	}
	else if(userType === "Admin"){
		 createSuccessClass();
		$("#errorMsg").text("Welcome "+userType);
		window.open("adminSettingAccordion.jsp","_self");
		openMyIndex(data);
		//createLocalStorage(data);
	}
	else{
		createSuccessClass();
		$("#errorMsg").text("Welcome "+userType);
		window.open("adminSettingAccordion.jsp","_self");
		//createLocalStorage(data);
	}
}

function chknormalFieldVali(pwd){
	if(pwd === "" || pwd === null){
		console.log("pwd error..");
		createDangerClass();
		$("#errorMsg").text("Please enter your password.");
		 return false;
	}
	else{
		  console.log("pwd correct..");
		 $("#errorMsg").empty();
		  return true;
	 }
}
function chkEmailVali(uName){
	if(uName === "" || uName === null){
		createDangerClass();
		$("#errorMsg").text("Please enter your Email address.");
		return false;
	}
	else{
			
			return chkEmailFormat(uName);
		}
}
function chkEmailFormat(uName){
    uName = uName.trim();
	var atpos=uName.indexOf("@");
	var dotpos=uName.lastIndexOf(".");
	if (atpos<1 || dotpos<atpos+2 || dotpos+2>=uName.length)
	  {      
		createDangerClass();
		$("#errorMsg").text("Please enter valid Email address.");   
		return false;
	  }
	else 
		{
			$("#errorMsg").empty();
		    return true;
		}

}
/*function chkForgetEmailVali(uName){
	if(uName === "" || uName === null){
		createForgetMailDangerClass();
		$("#errorMsgModal").text("Please enter your Email address.");
		return false;
	}
	else{
			
			return chkForgetEmailFormat(uName);
		}
}
function chkForgetEmailFormat(uName){
    uName = uName.trim();
	var atpos=uName.indexOf("@");
	var dotpos=uName.lastIndexOf(".");
	if (atpos<1 || dotpos<atpos+2 || dotpos+2>=uName.length)
	  {      
		createForgetMailDangerClass();
		$("#errorMsgModal").text("Please enter valid Email address.");   
		return false;
	  }
	else 
		{
			$("#errorMsg").empty();
		    return true;
		}

}*/

function createDangerClass(){
	$("#errorMsg").empty();
	$("#errorMsg").removeClass();
	$("#errorMsg").addClass("text-danger");
}
/*function createForgetMailDangerClass(){
	$("#errorMsgModal").empty();
	$("#errorMsgModal").removeClass();
	$("#errorMsgModal").addClass("text-danger");
}
function createForgetMailSuccessClass(){
	$("#errorMsgModal").empty();
	$("#errorMsgModal").removeClass();
	$("#errorMsgModal").addClass("text-success");
}*/
function createSuccessClass(){
	$("#errorMsg").empty();
	$("#errorMsg").removeClass();
	$("#errorMsg").addClass("text-success");
}


/* ******************** for LocalStorage ********************************** */

/*function createLocalStorage(data){
	destroyLocalStorage();
	localStorage.setItem('userInfo',JSON.stringify(data));
	
}
function destroyLocalStorage(){
	localStorage.removeItem('userInfo');
}
function getLocalStorage(){
	var userInfo = JSON.parse(localStorage.getItem('userInfo'));
	return userInfo;
}*/

/* ********************** end LocalStorage ******************************** */

//$('input').keydown(function(e) {
$(document).on("keydown", "input", function(e) {
	console.log("....... enter key pressed ..........");
    if (e.keyCode == 13) {
        //$(this).closest('form').submit();
    	$('#submitBtn').click();
    }
});