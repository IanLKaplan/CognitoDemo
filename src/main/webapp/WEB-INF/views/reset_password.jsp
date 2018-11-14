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

<title>Reset Password</title>
</head>
<body>
<div class="container">
<div style="padding-top: 1em;"></div>
    <div class="row">
		<div class="col-md-12">
	 		<h2 align="center">Reset Forgotten Password</h2>
		</div>
	</div> <!-- row -->
	<div style="padding-top: 1em;"></div>
	<div class="row">
	    <div class="col-md-2"></div>
		<div class="col-md-8">
		<div class="box">
		    This form is used to reset a user's password with an emailed code. You can use the 
		    <a href="<c:url value="/forgot_password" />">forgotten password</a> form to request a reset code.
		</div>
		</div>
	     <div class="col-md-2"></div>
	</div>
	<div style="padding-top: 1em;"></div>
	<c:if test="${reset_password_error != null && reset_password_error.length() > 0}">
		<div class="row">
				<div class="col-md-2"></div>
				<div class="col-md-8">
					<div class="box inputError">${reset_password_error}</div>
				</div>
				<div class="col-md-2"></div>
			</div>
	</c:if>
	<form action="/reset_password_form" method="post">
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
			<!--  if the user name is OK, fill it in so the user doesn't have to keep retyping it if, for
			      example, the password and the duplicate password don't match. -->
		    <div class="col-sm-8">
			<c:choose>
			   <c:when test="${user_name_val != null && user_name_val.length() > 0}">
			   	    <input type="text" id="user_name" name="user_name" value="${user_name_val}">
			   </c:when>
			   <c:otherwise>
				     <input type="text" id="user_name" name="user_name" placeholder="User name">
			   </c:otherwise>
			</c:choose>
			</div> <!-- col-md-8 -->
		</div> <!--  form-group row -->
		<!-- Reset code -->
		<c:if test="${reset_code_error != null && reset_code_error.length() > 0}">
		   <div class="form-group row">
		   	   <div class="col-sm-4"></div>
		   	   <div class="col-sm-8">
		   	   	   <span class="inputError">${reset_code_error}</span>
		       </div>
		   </div>
		</c:if>
		<div class="form-group row">
			<label for="title" class="col-sm-4 col-form-label">Password Reset Code:</label>
			<div class="col-sm-8">
				<input type="text" id="reset_code" name="reset_code" placeholder="Reset Code">
			</div> <!-- col-sm-8 -->
		</div> <!--  form-group row -->
		<!-- New Password -->
		<c:if test="${new_password_error != null && new_password_error.length() > 0}">
		   <div class="form-group row">
		   	   <div class="col-sm-4"></div>
		   	   	   <span class="inputError">${new_password_error}</span>
		       <div class="col-sm-8">
		       </div>
		   </div>
		</c:if>
		<div class="form-group row">
			<label for="title" class="col-sm-4 col-form-label">New Password:</label>
			<div class="col-sm-8">
				<input type="password" id="new_password" name="new_password" placeholder="Password">
			</div> <!-- col-sm-8 -->
		</div> <!--  form-group row -->
		<!-- Confirm Password -->
		<c:if test="${verify_password_error != null && verify_password_error.length() > 0}">
		   <div class="form-group row">
		   	   <div class="col-sm-4"></div>
		   	   	   <span class="inputError">${verify_password_error}</span>
		       <div class="col-sm-8">
		       </div>
		   </div>
		</c:if>
		<div class="form-group row">
			<label for="title" class="col-sm-4 col-form-label">Verify Password:</label>
			<div class="col-sm-8">
				<input type="password" id="verify_password" name="verify_password" placeholder="Password">
			</div> <!-- col-sm-8 -->
		</div> <!--  form-group row -->
		<div class="form-group row">
				<div class="col-sm-4"></div>
				<div class="col-sm-8">
				   <span>
					    <button type="submit" class="btn btn-primary">Reset Password</button>
					</span>
					<span>
					    <a href="<c:url value="/" />" id="cancel" name="cancel" class="btn btn-primary">Cancel</a>
					</span>
				</div>
		</div>
	</form>
</div> <!--  container  -->
</body>
</html>