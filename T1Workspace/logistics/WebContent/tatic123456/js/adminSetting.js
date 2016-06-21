var orgImageUrl = "";
var dataUserdataArr={};

//var scubeAppNameLable = getURLParameter("scubeAppNameLable");
var logInUser = "scubeuser8@gmail.com";
console.log("logInUser-------------------",logInUser);
var userName = "";
var now = new Date();
var day = ("0" + now.getDate()).slice(-2);
var month = ("0" + (now.getMonth() + 1)).slice(-2);
var temp = now.getDate() - 1;
var today = (day) + "-" + (month) + "-" + now.getFullYear();
console.log("today-------------------",today);
var demoData = "";

// save master data 
$(document).on("click", "#btnSaveData-js", function(event){
	
 	console.log("-------------------btnSaveData");
	var flag = true;
	console.log(flag);
	// add data in json object
	dataUserdataArr ["orgImage"] = $("#orgImage-js").attr("src"); 
	dataUserdataArr ["unSolDay"] = $("#unresolvedDay-js").val();	
	
		console.log("dataUserdataArr--------------------",dataUserdataArr);
		console.log(dataUserdataArr);
	
	 	console.log("-------------------saveOrgSetting");
		 $.ajax({
			 url: "saveSettingController?r="+Math.random(),
		     type: "Get",
		     data: {
		    	 "logInUser"	:	logInUser,
		    	 "flag"			:	"dataSaveSettingMaster",
		    	 "dataToSave"	:	JSON.stringify(dataUserdataArr)
		    },
		     success: function(data){
		    	 console.log("saveSetting",data);
		    	 data = $.trim(data);
		    	 	if(data == "success"){
		    	 		$("#infoTd").empty();
			    		$("#infoTd").append("Data updated successfully.");
			    		location.reload();
		    	 		//window.opener.document.getElementById("SomeChangeOccure").click();
		    	 	}
		     },
		     error: function () {
		    	 console.log("here error");
		    	// sessionStorage.setItem("userlogin"+origCompId,"");
		     }
			
		});
 });

//loading and show the image 


$(document).on('change','input[type=file]',function(e) {
	console.log("show file");
	$in = $(this);
	var selFile = $in.val();
	console.log("selFile-----------------",selFile);
	//alert("selFile"+selFile);
	var imageRegex = /([^\s]+(?=\.(jpg|gif|png|bmp))\.\2)/gm;
	if (selFile != null && selFile.toLowerCase().match(imageRegex)) {

		$(this).submit();
	} else {
		$("#infoTd").empty();
		$("#infoTd").append("Unsuported Formate");
	}
});
$(document).on("submit", "#uploaimageForm-js", function(event){
	
	 console.log("usertype..............uploaimageForm-js");
	 console.log("scubeAppNameLable..............111111",logInUser);
	var options ={
		
		dataType : "json",
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		
		url : "saveSettingController?scubeAppNameLable=" + today + "&r=" + Math.random(),
		
		success : function(xhr) {
			// alert('Thanks for your comment success!'
			// +
			// xhr.responseText);
			// alert('Thanks for your comment!');

		},
		error : function(xhr) {
			//alert('Thanks for your comment!');
		},
		complete : function(xhr) {
			var data = xhr.responseText;
			console.log(data);
			if (null != data.match(/Alreadyexists/g)) {
				console.log("data....", data);
				$("#infoTd").empty();
				$("#infoTd").append("Image is allready Exist");
			} else {
				console.log("data....else");
/*				if(scubeAppNameLable =="DemoApppropertyUploadforEndUser"){
					console.log("data....scubeAppNameLable");
					demoData["orgImageUrl"] = data;
						localStorage.setItem("DemoApppropertyUploadforEndUser",JSON.stringify(demoData));	
				}
*/
				$("#orgImage-js").attr("src",data);
			}
		}
	};
		$('#uploaimageForm-js').ajaxSubmit(options);
		$("#uploadBtn").val("");
		return false;
		
});



$(document).ready(function() {
	
	 $.ajax({
		 url: "saveSettingController?r="+Math.random(),
	     type: "Get",
	     data: {
	    	 "flag"			:	"selectSaveSettingData"
	    },
	     success: function(data){
	    	 console.log("selectSaveSettingData---------",data);
	    	 $.each(JSON.parse(data), function(key, value) {
	    		 
	    			console.log("No-data found in parse.------key------"+key);
	    			console.log("No-data found in parse.------value------"+value.empty);
	    		 
		    	 
		    	 if(value.empty == "empty"){

		    		 var dataTr = "<h4><strong>Your Company Logo :</strong></h4>";
		 				dataTr += "<div class='col-md-offset-1'><img src='static/images/blank.jpg' style='width: 120px;border: 1px solid black;'></div>";
		 				dataTr += "</div>";
		 				dataTr += "<div class='form-group'>";
		 				dataTr += "<h4><strong>Issues Resolved Days : </strong></h4>";
		 				dataTr += "<div  class='col-md-offset-1' id='unresolvedDayVal' ><strong style='color: red;'> not selected <strong> </div></div>";
	 	 		
		 	 		console.log("dataTr---------",dataTr);
	 	 		$("#selectUpLoadFile-js").append(dataTr);
	    		 
		    	 }else{
		    		 console.log("No-data found in parse.------------"+value.Com_Logo);
		    		 console.log("dataTr----else-----")
		    			 var dataTr = "<h4><strong>Your Company Logo : </strong></h4>";
		    				dataTr += "<div class='col-md-offset-1'><img src="+value.Com_Logo+" style='width: 120px;border: 1px solid black;'></div>";
		    			 	dataTr += "</div>";
		    			 	dataTr += "<div class='form-group'>";
		    			 	dataTr += "<h4><strong>Issues Resolved Days : </strong></h4>";
		    				dataTr += "<div  class='col-md-offset-1' id='unresolvedDayVal' > <strong>"+value.Unresolved_Day+"</strong> </div></div>";
		    				
		    				
		    				console.log("dataTr---------",dataTr);
		    				
		    		$("#selectUpLoadFile-js").append(dataTr);
		    	 }
	    	 }); 
	    	 
	     },
	     error: function () {
	    	 console.log("here error");
	    	// sessionStorage.setItem("userlogin"+origCompId,"");
	     }
		
	});
});


function fillData (tableDataArr){


	
}


// end loding show ...


//not use 
/*function AuthonticateUser(){
 	console.log("-------------------AuthonticateUser");
	var returnval="";
	$.ajax({
		 url: serverUrl+"/ScubeLogin/scubeUserLogin?r="+Math.random(),
	     type: "Post",
	     data: {
	    	 "txtuserName":localStorage.getItem("tempEmailId"),
	    	 "txtpassword":localStorage.getItem("tempPassword"),
	    	 "scubeAppNameLable":scubeAppNameLable
	    },
	     success: function(data){
	    	 	console.log("responceCode....",data);
	    	 	data = $.trim(data);
	    	 	if(data =="Login Succsess"){
	    	 		$("#detaildiv").empty();
	    	 		returnval = true;
					
	    	 	}else if(data =="Login Failed"){
	    	 		returnval = false;
	    	 	}
	    	 	else if(data =="inValidUSer"){
	    	 		returnval = false;
	    	 	}
	     },
	     error: function () {
	    	 console.log("here error");
	    	 
	     },async:false
	});

	return returnval;
}
*/
