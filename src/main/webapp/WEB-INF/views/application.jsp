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

<title>Delete Account</title>
</head>
<body>
<div class="container">
<div style="padding-top: 1em;"></div>
    <div class="row">
		<div class="col-md-12">
	 		<h2 align="center">Hi ${user_info.getUserName()}, you are now logged into your application</h2>
		</div>
	</div> <!-- row -->
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-10">
		  <h4>Profile information</h4>
		  <div>Email: ${user_info.getEmailAddr() }</div>
		  <div>Location: ${user_info.getLocation() }</div>
	    </div>
	</div>
	<div style="padding-top: 1em;"></div>
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-10">
		   <ul>
		   	  <li><a href="<c:url value="/change_password" />">Change your password</a></li>
		   	  <li><a href="<c:url value="/change_email" />">Change your email address</a></li>
		      <li><a href="<c:url value="/change_profile" />">Change your profile information</a></li>
		      <li><a href="<c:url value="/delete_account" />">Delete your account</a></li>
		   </ul>
		</div>
	</div>
	<form action="/logout_form" method="post">
		<div class="form-group row">
				<div class="col-sm-2"></div>
				<div class="col-sm-10">
					<button type="submit" class="btn btn-primary">Logout</button>
				</div>
		</div>
	</form>
</div>
</body>
</html>