$(document).ready(function() {
	console.log("usertype..............uploaimageForm");
	/*$.getJSON("readCategory",function(res) {
		console.log("----------category---------",res);
	});*/
	
	$("#allSecName").change(function(){
	    var selectedCountry = $("#allSecName option:selected").val();
	   // alert("You have selected the country - " + selectedCountry);
	    
		$.ajax({
	    	type: "post",
		 	url: "SelectData",
	        data:{  
	        	flge : "getData",
	        	selectedCountry : selectedCountry
	        },
	        success : function(result) {
	    	 	console.log(" SelectData Information......:",result);
	    	 	var val = $.parseJSON(result);
	    	 	
	    	 	if(val==""){
	    	 		
	    	 	}else{
		    	 	$.each( $.parseJSON(result),function (key, value){
		    	 		 $("#editA8").val(value.A2);
		    	 		 $("#editA9").val(value.A3);
		    	 		 $("#editA10").val(value.A4);
		    	 		 $("#editA11").val(value.A5);
		    	 		 $("#editA12").val(value.A6);
		    	 	});
	    	 	}
	        }
		});
	});
});
	

$(document).on("click", "#editIconComment", function(e){
	
	
	
	console.log("editIconComment clicked.");
	
	userIdT = $(this).parent().attr("useridT");
	userIdE = $(this).parent().attr("useridE");
	var fid = $(this).parent().attr("fid");
	var sid = $(this).parent().attr("sid");
	
	console.log("fid clicked.--------------",fid);
	console.log("sid clicked.--------------",sid);
	
	selectAllValues(sid);
	
	console.log("editIconComment clicked.--------------",userIdT);
	console.log("editIconComment clicked.--------------",userIdE);
	
	var updateTr = $(this).parent().parent().parent();
	
	console.log("editIconComment clicked.--------------",updateTr);
	console.log("userIdT clicked.--------------",userIdT);
	
	var A1 	= $(updateTr).find("td:eq(1)").text();
	var A2 	= $(updateTr).find("td:eq(2)").text();
	var A3 	= $(updateTr).find("td:eq(3)").text();
	var A4 	= $(updateTr).find("td:eq(4)").text();
	var A5 	= $(updateTr).find("td:eq(5)").text();
	var A6 	= $(updateTr).find("td:eq(6)").text();
	
	
	
	var A7 	= $(updateTr).find("td:eq(7)").text();
	var A8 	= $(updateTr).find("td:eq(8)").text();
	var A9 	= $(updateTr).find("td:eq(9)").text();
	var A10 = $(updateTr).find("td:eq(10)").text();
	var A11 = $(updateTr).find("td:eq(11)").text();
	var A12 = $(updateTr).find("td:eq(12)").text();
	
/*	console.log("usr_name clicked.--------------",usr_name);
	console.log("usr_emailId clicked.--------------",usr_emailId);
	console.log("usr_mobNo clicked.--------------",usr_mobNo);
	console.log("usr_role clicked.--------------",usr_role);
	 */
	$("#fid").val(fid);
	$("#sid").val(sid);
	$("#editIdT").val(userIdT);
	$("#userIdE").val(userIdE);
	 $("#editA1").val(A1);
	 $("#editA2").val(A2);
	 $("#editA3").val(A3);		 
	 $("#editA4").val(A4);
	 $("#editA5").val(A5);
	 $("#editA6").val(A6);
	 //alert(A7);
	 $("#allSecName").val(A7);
	 $("#editA7").val(A7);
	 $("#editA8").val(A8);
	 $("#editA9").val(A9);
	 $("#editA10").val(A10);
	 $("#editA11").val(A11);
	 $("#editA12").val(A12);
	 
	
	$("#editPopupScreen").show();
});


$(document).on("click", "#btn-update-userupdate", function(e){
	console.log("update-AppointmentInfo button clicked.");	
	
	//window.location.reload();
	
	console.log("Hiii-----6");
    var text1 = $("#editA1").val();
    var text2 = $("#editA2").val();
    var text3 = $("#editA3").val();
    var text4 = $("#editA4").val();
    var text5 = $("#editA5").val();
    var text6 = $("#editA6").val();
    var text7 = $("#editA7").val();
    var text8 = $("#editA8").val();
    var text9 = $("#editA9").val();
    var text10 = $("#editA10").val();
    var text11 = $("#editA11").val();
    var text12 = $("#editA12").val();
    
    var editIdT = $("#editIdT").val();
    var allSecName = $("#allSecName").val();
    //$('#updateBookingInfo').hide();
    
    console.log("editIdT-----"+editIdT);
    console.log("allSecName-----"+allSecName);
    
    console.log("text1-----"+text1+"text2-----"+text2+"text3-----"+text3+"text4-----"+text4);
    console.log("text5-----"+text5+"text6-----"+text6+"text7-----"+text7+"text8-----"+text8);
    console.log("text9-----"+text9+"text10-----"+text10+"text11-----"+text11+"text12-----"+text12);
    /*$('#processImage').show();*/
    alert("Download")
$.ajax({
    	type: "post",
	 	url: "ReportController",
        data: {
        		"text1" 	:	text1,
    	 	   	"text2" 	:	text2,
    	 	    "text3" 	:	text3,
    	 	    "text4" 	:	text4,
		   	 	"text5" 	:	text5,
		 	    "text6" 	:	text6,
		 	    "text7" 	:	text7,
		 	    "text8" 	:	text8,
		 	    "text9" 	:	text9,
		    	"text10"	:	text10,
		   	 	"text11"	:	text11,
		 	    "text12"	:	text12,
		 	    "allSecName":	allSecName,
		 	    "editIdT"	:	editIdT
        }, 	 
    success : function(result) {
    	 	console.log("update customer Information......:",result);
     }
});	
setTimeout(explode, 5000);

});	

function selectAllValues(sid){
	$.ajax({
    	type: "post",
	 	url: "SelectSecondFileData",
        data:  flge="getData",
        success : function(result) {
    	 	console.log(" selectAllValues Information......:",result);
    	 	$("#allSecName").empty();
    	 	$.each( $.parseJSON(result),function (key, value){
    	 		console.log(value);
    	 		console.log(value.A1);
    	 		$("#allSecName").append("<option value="+value.ID+">"+value.A1+"</option>");
    	 	});
    	 	console.log("------------ allSecName --------------- ",sid);
    	 $("#allSecName").val(sid);
     }
	
	});
}


function explode(){
	
	window.location.reload();
	$("#editPopupScreen").hide();
	var url ="index";
		
	window.location.href = url;
	  /*alert("Boom!");*/
	}

$(document).on("click", "#btnExport", function(e) {
	console.log("export to excel called");
	//exportTableToCSV.apply(this, [$('#box-table-b'), 'export.csv']);
	console.log("sxcxz",$('#FinalTable').html());
	exportTableToCSV.apply(this, [$('#FinalTable'), 'FinalReport.csv']);
});

function exportTableToCSV($table, filename) {
    var $headers = $table.find('tr:has(th)')
        ,$rows = $table.find('tr:has(td)')

        // Temporary delimiter characters unlikely to be typed by keyboard
        // This is to avoid accidentally splitting the actual contents
        ,tmpColDelim = String.fromCharCode(11) // vertical tab character
        ,tmpRowDelim = String.fromCharCode(0) // null character

        // actual delimiter characters for CSV format
        ,colDelim = '","'
        ,rowDelim = '"\r\n"';

        // Grab text from table into CSV formatted string
        var csv = '"';
        csv += formatRows($headers.map(grabRow));
        csv += rowDelim;
        csv += formatRows($rows.map(grabRow)) + '"';

        // Data URI
        var csvData = 'data:application/csv;charset=utf-8,' + encodeURIComponent(csv);

    $(this)
        .attr({
        'download': filename
            ,'href': csvData
            //,'target' : '_blank' //if you want it to open in a new window
    });

    //------------------------------------------------------------
    // Helper Functions 
    //------------------------------------------------------------
    // Format the output so it has the appropriate delimiters
    function formatRows(rows){
        return rows.get().join(tmpRowDelim)
            .split(tmpRowDelim).join(rowDelim)
            .split(tmpColDelim).join(colDelim);
    }
    // Grab and format a row from the table
    function grabRow(i,row){
         
        var $row = $(row);
        //for some reason $cols = $row.find('td') || $row.find('th') won't work...
        var $cols = $row.find('td'); 
        if(!$cols.length) $cols = $row.find('th');  

        return $cols.map(grabCol)
                    .get().join(tmpColDelim);
    }
    // Grab and format a column from the table 
    function grabCol(j,col){
        var $col = $(col),
            $text = $col.text();

        return $text.replace('"', '""'); // escape double quotes

    }
}


