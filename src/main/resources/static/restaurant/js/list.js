$(document).ready(function () {

    $("#search-form").submit(function (event) {

        event.preventDefault();

        fire_ajax_submit();

    });
    $("#bth-list").on("click", function (event) {

        event.preventDefault();

        fire_ajax_list_submit();

    });
    $("#bth-3rdExercise").on("click", function (event) {

        event.preventDefault();

        fire_ajax_3rd_exercise_submit();

    });
    

});

function fire_ajax_submit() {
    var search = {}
    if($("#name").val() !== ""){
    	search["nameLike"] = $("#name").val();
    	search["nameEquals"] = $("#name").val();
        search["sort"] = $("#sort").val();
        search["page"] = $("#page").val();
        search["pageSize"] = $("#pageSize").val();

        $("#btn-search").prop("disabled", true);
        $('#list-group').html("");

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/api/restaurant/search",
            data: JSON.stringify(search),
            dataType: 'json',
            cache: false,
            timeout: 600000,
            beforeSend: function(){
            	$('#message').html("").removeClass("alert-danger");
            	$('#list-group').html("");
            },
            success: function (data) {
            	var list = "";
            	if(data.binaryStatus == "success"){
            		if(data.result.length != 0){
            			$.each(data.result, function(i, item) {
                        	list += "<li class=\"list-group-item\">"+item.name +"<span class=\"badge\">"+item.rating+"</span></li>";
                        });
                		$('#list-group').html(list);
            		}else{
            			$('#message').html("No se encontraron elementos a mostrar").addClass("alert-danger");
            		}
            		
            	}
            	if(data.binaryStatus == "error"){
            		list += "<ul>"
                	$.each(data.result, function(i, item) {
                        list += "<li>"+item +"</li>";
                    });
                	list += "</ul>"
                	$('#message').html(list).addClass("alert-danger");
            	}
            	$("#btn-search").prop("disabled", false);
                
            },
            error: function (e) {
            	$('#message').html("Se ha producido un error interno").addClass("alert-danger");
                $("#btn-search").prop("disabled", false);

            }
        });
    }else{
    	$('#list-group').html("");
    	$('#message').html("INTRODUCE un valor").addClass("alert-danger");
    }
    

}

function fire_ajax_list_submit(){
	$.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/restaurant/list",
        cache: false,
        timeout: 600000,
        beforeSend: function(){
        	$('#message').html("").removeClass("alert-danger");
        },
        success: function (data) {
        	var list = "";
        	if(data.binaryStatus == "success"){
        		$.each(data.result, function(i, item) {
                	list += "<li class=\"list-group-item\">"+item.name +"<span class=\"badge\">"+item.rating+"</span></li>";
                });
        		$('#list-group').html(list);
        	}
        	if(data.binaryStatus == "error"){
        		list += "<ul>"
            	$.each(data.result, function(i, item) {
                    list += "<li>"+item +"</li>";
                });
            	list += "</ul>"
            	$('#message').html(list).addClass("alert-danger");
        	}
        	$("#btn-search").prop("disabled", false);
            
        },
        error: function (e) {
        	$('#message').html("Se ha producido un error interno").addClass("alert-danger");
            $("#btn-search").prop("disabled", false);
        }
    });
}

function fire_ajax_3rd_exercise_submit(){
	$.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/api/restaurant/thirdExercise",
        cache: false,
        timeout: 600000,
        beforeSend: function(){
        	$('#message').html("").removeClass("alert-danger");
        },
        success: function (data) {
        	var list = "";
        	if(data.binaryStatus == "success"){
        		$.each(data.result, function(i, item) {
                	list += "<li class=\"list-group-item\">"+item.name +"<span class=\"badge\">"+item.rating+"</span></li>";
                });
        		$('#list-group').html(list);
        	}
        	if(data.binaryStatus == "error"){
        		list += "<ul>"
            	$.each(data.result, function(i, item) {
                    list += "<li>"+item +"</li>";
                });
            	list += "</ul>"
            	$('#message').html(list).addClass("alert-danger");
        	}
        	$("#btn-search").prop("disabled", false);
            
        },
        error: function (e) {
        	$('#message').html("Se ha producido un error interno").addClass("alert-danger");
            $("#btn-search").prop("disabled", false);
        }
    });
}
