

var userId = "";

$(document).ready(function() {

	console.log("--=======----");
	function getReportDataOnChange(){
		console.log("--=======----");
	}
	var instanceId = "scubeuser8@gmail.com";
	
	$(function() {
		$.ajax({
			 url: "selectAllUserController",
			 type: "post",
			 data: { 
	    	 	    "userId": instanceId,
	    	 	   /*"currentDate":selectedToDate,
	    	        "fromDate":selectedFromDate,*/
	    	        /*"serviceName": serviceName,*/
	    	        "flag":"getAllUserData" 
	    	    },
    	     success: function(data){
    	    	 console.log("data in Report-End:",data);
    	    	 
    	    	 fillData (data);
    	    	 
    	     	},
	     	 error: function () {
	     		 console.log("No-data found in ReportController.");
	     },async:false
		});
	});
});//doc ready

function fillData (tableDataArr){
	var i = 1;

	$.each(JSON.parse(tableDataArr), function(key, value) {
		console.log("No-data found in parse.------------"+key);
		console.log("No-data found in parse.------------"+value.usr_Name);
		 var dataTr = "<tr id='rowPrint"+i+"'>";
		 	dataTr += "<td>"+value.usr_Name+"</td>";							
		 	dataTr += "<td>"+value.Usr_EmailId+"</td>";							
	 		dataTr += "<td>"+value.Usr_MobNo+"</td>";							
 			dataTr += "<td >"+value.Usr_Role+"</td>";							
			dataTr += "<td class='text-center'>";
			dataTr += "<span class='step size-24'>";
			dataTr += "<a data-toggle='modal' data-target='#loginSection'  userid="+value.Usr_Id+">";
			dataTr += "<i class='icon ion-compose'  id='editIconComment'></i></a></span>";
			dataTr += "</td>";
			dataTr += "<td class='text-center' >";
			dataTr += "<span class='step size-32'><a userId1='"+value.Usr_Id+"' ><i class='icon ion-android-delete' id='deleteIconComment' ></i></a></span>";
			dataTr += "</td>";
			dataTr += "</tr>"; 
	  $(".reportTableToPrint-js").append(dataTr);
	i++;
	});
	
}











