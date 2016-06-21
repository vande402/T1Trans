$(document).ready(function() {
	
	$(document).on("click", "#runPr", function(e){
		
		$('#afterRun').empty();
		$('#afterRunBody').empty();
		$.ajax({
			type : "POST",
			url : "runPrAction",
			success : function(result) {
			
			console.log("result=================",result);
			console.log("result=================",result.contacts);
			
			$('#fstTd').hide();
			$('#afterRun').show();
			
			$('#afterRun').append("<table style='border: 1px solid #101010;'>" +
					"<thead>" +
					"<th>rohit</th>" +
					"</thead>" +
					"<tbody id ='afterRunBody'></tbody>")

			
			$.each( $.parseJSON(result),function (key, value){
				
				$('#afterRunBody').append("<tr><td>"+value.NoOfHost+"</td></tr>");
   	 		});
			
			}
		});
	});
});