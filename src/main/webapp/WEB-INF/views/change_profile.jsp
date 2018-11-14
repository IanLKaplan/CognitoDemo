<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" 
	     rel="stylesheet" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" 
	     crossorigin="anonymous">

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" 
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" 
            crossorigin="anonymous"></script>

    <link rel="stylesheet" href="resources/css/kube-6.5.2.min.css">
     <link rel="stylesheet" href="resources/css/main.css">

<title>Change Profile Information</title>
</head>
<body>
<div class="container">
<div style="padding-top: 1em;"></div>
    <div class="row">
		<div class="col-md-12">
	 		<h2 align="center">Change Profile Information</h2>
		</div>
	</div> <!-- row -->
	<div style="padding-top: 1em;"></div>
	<form action="/change_profile_form" method="post">
		<!-- Location -->
		<c:if test="${location_error != null && location_error.length() > 0}">
		   <div class="form-group row">
		   	   <div class="col-sm-4"></div>
		   	   <div class="col-sm-8">
		   	   	   <span class="inputError">${location_error}</span>
		       </div>
		   </div>
		</c:if>
		<div class="form-group row">
			<label for="location" class="col-sm-4 col-form-label">Location</label>
			<select class="custom-select col-sm-8" name="location" id="location">
      				<option selected>${user_info.getLocation() }</option>
      				<option value="Bonaire">Bonaire</option>
      				<option value="Aruba">Aruba</option>
      			    <option value="Curacao">Curacao</option>
      			    <option value="Trinidad">Trinidad</option>
      			    <option value="Tobago">Tobago</option>
      			    <option value="Grenada">Grenada</option>
    		</select> <!-- col-sm-8 -->
		</div> <!-- form-group row -->
		<div class="form-group row">
				<div class="col-sm-4"></div>
				<div class="col-sm-8">
				    <span>
					   <button type="submit" class="btn btn-primary">Update</button>
					</span>
					<span>
					    <a href="/application" id="cancel" name="cancel" class="btn btn-primary">Cancel</a>
					</span>
				</div>
		</div>
	</form>
</div> <!--  container  -->
</body>
</html>