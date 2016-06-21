$(document).ready(function() {
	alert("keshav");
	$(function() {
		$('#keshav').html('');
		alert("keshav");
		console.log("----------category---------");
		/*$.getJSON("readStates",function(res) {*/
		$.ajax({
			type : "POST",
			url : "readStates",
			success : function(result) {
			alert("keshav");
			console.log("result=================",result);
			console.log("result=================",result.contacts);
			console.log("----------category---------",result.contacts.length);
			$('#keshav').append();
			}
		});
	});
});