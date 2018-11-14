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

<title>New User</title>
</head>
<body>
<!--
   Create user form 
 -->
<div class="container">
<div style="padding-top: 1em;"></div>
    <div class="row">
		<div class="col-md-12">
	 		<h2 align="center">Create a new user account</h2>
		</div>
	</div> <!-- row -->
    <div class="row">
    	<div class="col-md-2"></div>
		<div class="col-md-10">
		An email with an account creation code will be sent to your email address.
		</div>
	</div>
	<div style="padding-top: 1em;"></div>
	<c:if test="${createUserError != null && createUserError.length() > 0}">
		<div class="row">
				<div class="col-md-2"></div>
				<div class="col-md-8">
					<div class="box inputError">${createUserError}</div>
				</div>
				<div class="col-md-2"></div>
			</div>
		</c:if>
	<div class="row">
	<div class="col-md-12">
	<!-- Post to the login controller -->
			<form action="/create_user_form" method="post">
				<c:if test="${userNameError != null && userNameError.length() > 0}">
				   <div class="form-group row">
				   	   <div class="col-sm-4"></div>
				   	   	<div class="col-sm-8">
				   	   	   <span class="inputError">${userNameError}</span>
				        </div>
				   </div>
				</c:if>
				<div class="form-group row">
					<label for="title" class="col-sm-4 col-form-label">User name:</label>
					<!--  if the user name is OK, fill it in so the user doesn't have to keep retyping it if, for
					      example, the password and the duplicate password don't match. -->
					<c:choose>
					   <c:when test="${userNameVal != null && userNameVal.length() > 0}">
					   	    <input type="text" id="user_name" name="user_name" value="${userNameVal}">
					   </c:when>
					   <c:otherwise>
				          <div class="col-sm-8">
						     <input type="text" id="user_name" name="user_name" placeholder="User name">
					      </div> <!-- col-md-8 -->
					   </c:otherwise>
					</c:choose>
				</div> <!--  form-group row -->
				<c:if test="${emailError != null && emailError.length() > 0}">
				   <div class="form-group row">
				   	   <div class="col-sm-4"></div>
				   	   <div class="col-sm-8">
				   	   	   <span class="inputError">${emailError}</span>
				       </div>
				   </div>
				</c:if>
				<div class="form-group row">
					<label for="title" class="col-sm-4 col-form-label">Email:</label>
					<div class="col-sm-8">
						<input type="text" id="email" name="email" placeholder="Email">
					</div> <!-- col-sm-8 -->
				</div> <!--  form-group row -->
				<c:if test="${locationError != null && locationError.length() > 0}">
				   <div class="form-group row">
				   	   <div class="col-sm-4"></div>
				   	   	   <span class="inputError">${locationError}</span>
				       <div class="col-sm-8">
				       </div>
				   </div>
				</c:if>
				<div class="form-group row">
					<label for="location" class="col-sm-4 col-form-label">Location</label>
					<select class="custom-select col-sm-8" name="location" id="location">
        				<option selected>Location</option>
        				<option value="Bonaire">Bonaire</option>
        				<option value="Aruba">Aruba</option>
        			    <option value="Curacao">Curacao</option>
        			    <option value="Trinidad">Trinidad</option>
        			    <option value="Tobago">Tobago</option>
        			    <option value="Grenada">Grenada</option>
      				</select>
					<!-- col-sm-8 -->
				</div> <!-- form-group row -->
				<div class="form-group row">
					<div class="col-sm-4"></div>
					<div class="col-sm-8">
					    <span>
						    <button type="submit" class="btn btn-primary">Create User</button>
						</span>
						<span>
					       <a href="<c:url value="/" />" id="cancel" name="cancel" class="btn btn-primary">Cancel</a>
					    </span>
					</div>
				</div>
				</form>
			</div> <!-- col-md-12 -->
		</div> <!-- row -->
</div> <!-- container -->
</body>
</html>