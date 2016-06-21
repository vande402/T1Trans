$(document).ready(function() {
});

var ajaxAsyncOption = true;

$(document).on("click", "#adminAddNewUser-js", function() {
	
	var uName = $("#newUserName").val();
	var uEmail = $("#newUserEmail").val();
	var uMob = $("#newUserMmobile").val();
	
	if(checkValidationAdd(uName,uEmail,uMob)){
		console.log("Call ajax...");
		 validateFromDBAdd(uName,uEmail,uMob);
	}
	else{
		   console.log("Error mode...");
	}
});

// show update data 

$(document).on("click", "#editIconComment", function(e){
	console.log("editIconComment clicked.");
	
	userId = $(this).parent().attr("userid");
	console.log("editIconComment clicked.--------------",userId);
	var updateTr = $(this).parent().parent().parent().parent();
	
	console.log("editIconComment clicked.--------------",updateTr);
	var usr_name = $(updateTr).find("td:eq(0)").text();
	var usr_emailId = $(updateTr).find("td:eq(1)").text();
	var usr_mobNo =$(updateTr).find("td:eq(2)").text();
	var usr_role =$(updateTr).find("td:eq(3)").text();
	
	console.log("usr_name clicked.--------------",usr_name);
	console.log("usr_emailId clicked.--------------",usr_emailId);
	console.log("usr_mobNo clicked.--------------",usr_mobNo);
	console.log("usr_role clicked.--------------",usr_role);
	 
	 $("#editUserId").val(userId);
	 $("#editUserName").val(usr_name);
	 $("#editUserEmail").val(usr_emailId);		 
	 $("#editUserMobile").val(usr_mobNo);
	
	 
	 if (usr_role.indexOf("Admin") >= 0)
	 {		 
		 $('#chkAdmin').prop('checked', true);
	 }
	 else
	 {
		 $('#chkAdmin').prop('checked', false);
	 }
	 
	 if (usr_role.indexOf("User") >= 0)
	 {		 
		 $('#chkUser').prop('checked', true);
	 }
	 else
	 {
		 $('#chkUser').prop('checked', false);
	 }
	 
	
	$("#editPopupScreen").show();
});



// delete data 
$(document).on("click", "#deleteIconComment", function(e){
	console.log("deleteIconComment clicked.");
	
	$("#userListData").load();
	uId = $(this).parent().attr("userId1");
	console.log("editIconComment clicked.--------------",uId);
	
    $.ajax({
		 url: "AdminManageAllUser?r="+Math.random(),
	     type: "Post",
	     global: false,
	     data: {
	    	 		"flag" 			:	"deleteUser",
	    	 		"userId"		:	uId
		    },
	   	success: function(data){
	   	 	console.log("update user Information......:",data);
	   	 	
	   	 	if(data == null || data == ""){
	
	   	 	}else{
	   	 		displayMessageSinUP(data);
	   	 	}
	    },
	    error: function () {
	   	 console.log("No-data found in ReportController.");
	    },async:false
	});
});

//save update data

$(document).on("click", "#btn-update-userupdate", function(e)
{
	console.log("update button---->clicked.--------------"+userId);		
	
	var uName = $("#editUserName").val();
	var uEmail = $("#editUserEmail").val();
	var uMob = $("#editUserMobile").val();
	
	
	if(checkValidationNew(uName,uEmail,uMob))
	{
		updateUserMasterNew(uName,uEmail,uMob);
	}	
	else
	{
	    console.log("Invalid customer data input");
	}	
});


function checkValidationAdd(usr_Name,usr_Email,usr_Mob){
/*	var uName = $("#newUserName").val();
	var uEmail = $("#newUserEmail").val();
	var uMob = $("#newUserMmobile").val();*/
	
	var uName 	= usr_Name;
	var uEmail 	= usr_Email;
	var uMob 	= usr_Mob;
	
	console.log("uName : ",uName,"uEmail : ",uEmail,"uMob : ",uMob);
/*	if(chkNullVal(uName)){
		if(chkNullVal(pswd)){
			if(chkNullVal(pswd)){
				return true;
			}else
				return false;
		}else
			return false;
	}else
		return false;
}*/

/*	function chkNullVal(val){
		if(val === "" || val === null){
			createDangerClass();
			$("#errorMsg").text("Please enter your Email address.");
			return false;
		}
		else{
				
				return chkEmailFormat(uName);
			}
	}*/
	
	if(chkNamNullVal(uName)){
		if(chkEmlNullVal(uEmail)){
			if(chkMobNullVal(uMob)){
				return true;
			}else
				return false;
		}else
			return false;
	}else
		return false;
}


function validateFromDBAdd(add_usr_Name,add_usr_Email,add_usr_Mob){
/*	var uName = $("#newUserName").val();
	var uEmail = $("#newUserEmail").val();
	var uMob = $("#newUserMmobile").val();*/
	
	var uName 	= add_usr_Name;
	var uEmail 	= add_usr_Email;
	var uMob 	= add_usr_Mob;
	
	var uType = $("input[name=radAdmin]:checked").val();
	
	console.log("AdminAddUser   ====    uName : ",uName,"uEmail : ",uEmail,"uMob : ",uMob ,"uType : ",uType);
	
	$.ajax({
		 url: "AdminManageAllUser?r="+Math.random(),
	     type: "Post",
	     global: false,
	     data: {
	    	 		"flag" : "newUserAdd",
	    	 		"userName" : uName,
	    	 		"userEmail" : uEmail,
	    	 		"userMobile" : uMob,
	    	 		"userType"	:	uType
	    },
	     success: function(data){
	    	 console.log("data : ",data);
	    	 if(data == null || data == ""){
	    		console.log("empty object show error message...");
	    	 }
	    	 else{
	    		 	displayMessageSinUP(data);
	    	 }
	     },
	     error: function (e) {
	    	  console.log("here error",e);
	     }
	     ,async: ajaxAsyncOption
   });
}


function updateUserMasterNew(edit_usr_Name,edit_usr_Email,edit_usr_Mob){
	/*	var uName = $("#newUserName").val();
		var uEmail = $("#newUserEmail").val();
		var uMob = $("#newUserMmobile").val();*/
		
		var uName 	= edit_usr_Name;
		var uEmail 	= edit_usr_Email;
		var uMob 	= edit_usr_Mob;
		var uId = $("#editUserId").val();
		var uType = $("input[name=editChkRole]:checked").val();
		
		console.log("empty object show error message...",uName);
		console.log("empty object show error message...",uEmail);
		console.log("empty object show error message...",uMob);
		console.log("empty object show error message...",uType);
		
	    $.ajax({
			 url: "AdminManageAllUser?r="+Math.random(),
		     type: "Post",
		     global: false,
		     data: {
		    	 		"flag" 			:	"editUser",
		    	 		"userName" 		:	uName,
		    	 		"userEmail" 	:	uEmail,
		    	 		"userMobile" 	: 	uMob,
		    	 		"userType"		:	uType,
		    	 		"userId"		:	uId
		    },
	   
	    	success: function(data){
	    	 	console.log("update user Information......:",data);
	    	 	
	    	 	if(data == null || data == ""){

	    	 	}else{
	    	 		displayMessageSinUP(data);
	    	 	}
	     },
	     error: function () {
	    	 console.log("No-data found in ReportController.");
	    	
	     },async:false
	});
}






function chkNamNullVal(val){
	if(val === "" || val === null){
		createDangerClass();
		$("#errorMsg").text("Please enter your Name address.");
		return false;
	}
	else{
			return true;
		}
}
function chkEmlNullVal(val){
	if(val === "" || val === null){
		createDangerClass();
		$("#errorMsg").text("Please enter your Email address.");
		return false;
	}
	else{
			return chkEmailFormat(val);
		}
}
function chkMobNullVal(val){
	if(val === "" || val === null){
		createDangerClass();
		$("#errorMsg").text("Please enter your Mobile address.");
		return false;
	}
	else{
			return true;
		}
}






function createDangerClass(){
	$("#errorMsg").empty();
	$("#errorMsg").removeClass();
	$("#errorMsg").addClass("text-danger");
}


function chkEmailFormat(val){
	val = val.trim();
	var atpos=val.indexOf("@");
	var dotpos=val.lastIndexOf(".");
	if (atpos<1 || dotpos<atpos+2 || dotpos+2>=val.length)
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



function displayMessageSinUP(data){
	console.log("hereeee..",data["result"]);
	var userType = data["result"];
	userType = userType.trim();
	if(userType === "alreadyUse"){
		 createDangerClass();
		$("#errorMsg").text("Email id is already in use.");
	}
	else if(userType === "success"){
		 createSuccessClass();
		$("#errorMsg").text("Successful add a new user.");
			location.reload();
		/*window.open("index.jsp?data="+data,"_self");
		openMyIndex(data);
		createLocalStorage(data);*/
	}
	else{
		createSuccessClass();
		$("#errorMsg").text("Welcome ");
/*		window.open("index.jsp","_self");
		createLocalStorage(data);*/
	}
}
function createSuccessClass(){
	$("#errorMsg").empty();
	$("#errorMsg").removeClass();
	$("#errorMsg").addClass("text-success");
}