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

<title>Delete Your Account</title>
</head>
<body>
<div class="container">
<div style="padding-top: 1em;"></div>
    <div class="row">
		<div class="col-md-12">
	 		<h2 align="center">Delete your account</h2>
		</div>
	</div> <!-- row -->
    <form action="/delete_account_form" method="post">
    <!-- Password -->
	<c:if test="${password_error != null && password_error.length() > 0}">
		 <div class="form-group row">
			<div class="col-sm-4"></div>
				<span class="inputError">${password_error}</span>
				<div class="col-sm-8"></div>
			</div>
	</c:if>
	<div class="form-group row">
		<label for="password" class="col-sm-2 col-form-label">Password:</label>
		<div class="col-sm-6">
			<input type="password" id="password" name="password" placeholder="Password">
		</div> <!-- col-sm-6 -->
		<div class="col-sm-4"></div>
	</div> <!--  form-group row -->
	<!--  form-group row -->
	<div class="form-group row">
		<div class="col-sm-2"></div>
	    <div class="col-sm-6">
	        <span>
			    <button type="submit" class="btn btn-primary">Delete My Account</button>
			</span>
			<span>
				<a href="<c:url value="/" />" id="cancel" name="cancel" class="btn btn-primary">Cancel</a>
			</span>
		</div>
		<div class="col-sm-4"></div>
	</div>
    </form>
</div>
</body>
</html>