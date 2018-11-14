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

<title>Reset your password</title>
</head>
<body>
<div class="container">
<div style="padding-top: 1em;"></div>
    <div class="row">
		<div class="col-md-12">
	 		<h2 align="center">Reset your password</h2>
		</div>
	</div> <!-- row -->
	<div style="padding-top: 1em;"></div>
	 <!-- Post to the login form -->
		<form action="/forgot_password_form" method="post">
		    <!-- User Name -->
			<c:if test="${user_name_error != null && user_name_error.length() > 0}">
				<div class="form-group row">
					<div class="col-sm-4"></div>
					<div class="col-sm-8">
						<span class="inputError">${user_name_error}</span>
					</div>
				</div>
			</c:if>
			<div class="form-group row">
				<label for="title" class="col-sm-4 col-form-label">User name:</label>
				<div class="col-sm-8">
						<input type="text" id="user_name" name="user_name" placeholder="User name">
				</div> <!-- col-md-8 -->
			</div> <!--  row -->
			<div class="form-group row">
				<div class="col-sm-4"></div>
				<div class="col-sm-8">
				    <span>
					    <button type="submit" class="btn btn-primary">Reset password</button>
					</span>
					<span>
						<a href="<c:url value="/" />" id="cancel" name="cancel" class="btn btn-primary">Cancel</a>
					</span>
				</div>
			</div>
	   </form>
</div> <!-- container -->
</body>
</html>