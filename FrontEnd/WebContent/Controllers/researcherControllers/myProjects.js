var $rootUrl = "http://localhost:8080/demorest/webapi/researcherlogin/myProjects/";
var projectId=0;
$(document).ready(function() {
	
	getProjects(2);
	getCategories();
	getProjectRequest(2);

});

function getProjects(id){
	 $.ajax({
        url: $rootUrl+id,
        contentType: 'application/json',
        dataType: 'json',
        type: 'GET',
        success: function(data) {
             projects = data;
			var projectQty=1;
            projects.forEach(function (entry) {
			
            var image=entry.imgPath.replace(/ /g, '%20');

			if(projectQty<=3){
				$("#myProjects").append("<div class='col-sm-4'><a data-toggle='modal' data-target='#viewModal' ><img src="+image+" class='img-responsive' style='width:100% ;height:240px' alt='Image'><p>"+entry.project_name+"</p></a></div>");
			}
			else{
				$("#myProjectsCollapse").append("<div class='col-sm-4'><a data-toggle='modal' data-target='#viewModal' ><img src="+image+" class='img-responsive' style='width:100% ;height:240px' alt='Image'><p>"+entry.project_name+"</p></a></div>");
			}
            projectQty++;
        });

        }
    });
}

function loadProject(id){
	projectId=id;
	 $.ajax({
        url: $rootUrl+"project/"+id,
        contentType: 'application/json',
        dataType: 'json',
        type: 'GET',
        success: function(data) {
            project = data;
		   document.getElementById("categoryListUp").value=project.categoryId;
		   document.getElementById("descriptionUp").value=project.description;

        }
    });
}


function getProjectRequest(id){
	 $.ajax({
        url: $rootUrl+"request/"+id,
        contentType: 'application/json',
        dataType: 'json',
        type: 'GET',
        success: function(data) {
             projectRequests = data;
			projectRequests.forEach(function (entry){
				$('#projectRequestsList')
                         .append($("<option></option>")
                                    .attr("value", entry.project_id)
                                    .text(entry.project_name)); 
                                    });
        }
    });
}

function getCategories(){
	$.ajax({
        url: "http://localhost:8080/demorest/webapi/researcherlogin/Categories/",
        contentType: 'application/json',
        dataType: 'json',
        type: 'GET',
        success: function(data) {
             categories = data;
			categories.forEach(function (entry){
				$('#categoryList')
                         .append($("<option></option>")
                                    .attr("value", entry.id_categories)
                                    .text(entry.description));
				$('#categoryListUp')
                         .append($("<option></option>")
                                    .attr("value", entry.id_categories)
                                    .text(entry.description)); 
                                    });			
        }
    });
}

$(document).ready(function(){
    $('#requestForm').on('submit', function(e){
	e.preventDefault();
			
	var project={};
	project.name=document.getElementById("name").value;
	project.description=document.getElementById("description").value;
	project.category=document.getElementById("categoryList").value;
	project.researcherId=2;
	var formData = new FormData($('#createForm')[0]);


    formData.append('file', $('input[type=file]')[0].files[0]);
    formData.append('obj', JSON.stringify(project));

        $.ajax({
            url: $rootUrl+"resquest",
			data: formData,
	        type: 'POST',
	        contentType: false,
	        processData: false,
            success: function(data){
                alert("Request Sent Succeses!");
				location.reload();
              
            }
        });
    });
})

$(document).ready(function(){
    $('#projectForm').on('submit', function(e){
	e.preventDefault();
			
	var project={};
	project.price=document.getElementById("priceUp").value;
	project.project_id=projectId;
	var formData = new FormData($('#createForm')[0]);


    formData.append('file', $('input[type=file]')[2].files[0]);
    formData.append('obj', JSON.stringify(project));

        $.ajax({
            url: $rootUrl,
			data: formData,
	        type: 'POST',
	        contentType: false,
	        processData: false,
            success: function(data){
	
				var formData = new FormData($('#createForm')[0]);
				  formData.append('file', $('input[type=file]')[1].files[0]);
				  formData.append('obj', JSON.stringify(project));
				
				  $.ajax({
		            url: $rootUrl+"img",
					data: formData,
			        type: 'POST',
			        contentType: false,
			        processData: false,
		            success: function(data){
			
						
						 alert("Project Uploaded Succeses!");
						location.reload();
						
		              
		            }
		        });
				
              
            }
        });
    });
})

function showMoreLess(id){
    if(document.getElementById(id).innerHTML=="See More"){
       document.getElementById(id).innerHTML="See Less";
       }
    else
        document.getElementById(id).innerHTML="See More";
    
}
