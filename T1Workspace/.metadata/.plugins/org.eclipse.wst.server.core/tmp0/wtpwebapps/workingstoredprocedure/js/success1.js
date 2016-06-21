$(document).on("click", "#editIconComment", function(e){
	console.log("editIconComment clicked.");
	
	userffid = $(this).parent().attr("userffid");
	usersfid = $(this).parent().attr("usersfid");
	var updateTr = $(this).parent().parent();
	
	console.log("userffid------------------->",userffid);
	console.log("usersfid------------------->",usersfid);
	console.log("updateTr------------------->",updateTr);
	var text1				= $(updateTr).find("td:eq(1)").text();
    var text2 				= $(updateTr).find("td:eq(2)").text();
    var text3 				= $(updateTr).find("td:eq(3)").text();
    var text4 				= $(updateTr).find("td:eq(4)").text();
    var text5 				= $(updateTr).find("td:eq(5)").text();
    var text6 				= $(updateTr).find("td:eq(6)").text();
    var text7 				= $(updateTr).find("td:eq(7)").text();
    var text8 				= $(updateTr).find("td:eq(8)").text();
    var text9 				= $(updateTr).find("td:eq(9)").text();
    var text10 				= $(updateTr).find("td:eq(10)").text();
    var text11				= $(updateTr).find("td:eq(11)").text();
    var text12 				= $(updateTr).find("td:eq(12)").text();
    var text13 				= $(updateTr).find("td:eq(13)").text();
    var text14 				= $(updateTr).find("td:eq(14)").text();
    var text15 				= $(updateTr).find("td:eq(15)").text();
    var text16 				= $(updateTr).find("td:eq(16)").text();
    
	console.log("text1------------------->",text1);
	console.log("text2------------------->",text2);
	console.log("text3------------------->",text3);
	console.log("text4------------------->",text4);
	console.log("text5------------------->",text5);
	console.log("text6------------------->",text6);
	
	$("#text1").val(text1);
	$("#text2").val(text2);
	$("#text3").val(text3);
	$("#text4").val(text4);
	$("#text5").val(text5);
	$("#text6").val(text6);
	$("#text7").val(text7);
	$("#text8").val(text8);
	$("#text9").val(text9);
	$("#text10").val(text10);
	$("#text11").val(text11);
	$("#text12").val(text12);
	$("#text13").val(text13);
	$("#text14").val(text14);
	$("#text15").val(text15);
	$("#text16").val(text16);
	
	$("#editPopupScreen").show();
	
//	var txtAppointmentToDate = $(updateTr).find("td:eq(1)").text();
});

$(document).on("click", "#btn-cancel-AppointmentInfo", function(e){
	console.log("cancel button clicked.");	
	$("#editPopupScreen").hide();
});

$(document).on("click", "#btn-update-AppointmentInfo", function(e){
	console.log("update-AppointmentInfo button clicked.");	
	
	//window.location.reload();
	
	console.log("Hiii-----6");
    var text1 = $("#text1").val();
    var text2 = $("#text2").val();
    var text3 = $("#text3").val();
    var text4 = $("#text4").val();
    var text5 = $("#text5").val();
    var text6 = $("#text6").val();
    var text7 = $("#text7").val();
    var text8 = $("#text8").val();
    var text9 = $("#text9").val();
    var text10 = $("#text10").val();
    var text11 = $("#text11").val();
    var text12 = $("#text12").val();
    
    //$('#updateBookingInfo').hide();
    
    console.log("text1-----"+text1+"text2-----"+text2+"text3-----"+text3+"text4-----"+text4);
    console.log("text5-----"+text5+"text6-----"+text6+"text7-----"+text7+"text8-----"+text8);
    console.log("text9-----"+text9+"text10-----"+text10+"text11-----"+text11+"text12-----"+text12);
    /*$('#processImage').show();*/
   // alert("keshav")
$.ajax({
    	type: "post",
	 	url: "ReportController",
        data:  "text1="+text1+
    	 	    "&text2="+text2+
    	 	    "&text3="+text3+
   	 	    	"&text4="+text4+
		   	 	"&text5="+text5+
		 	    "&text6="+text6+
		 	    "&text7="+text7+
	   	 	    "&text8="+text8+
	   	 	    "&text9="+text9+
  	 	    	"&text10="+text10+
		   	 	"&text11="+text11+
		 	    "&text12="+text12+
		 	    "&userffid="+userffid+
		 	    "&usersfid="+usersfid,
    success : function(result) {
    	 	console.log("update customer Information......:",data);
     }
});	
window.location.reload();
$("#editPopupScreen").hide();
var url ="index";
	
window.location.href = url;

});