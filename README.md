# CognitoDemo
## Introduction
CognitoDemo is a Java application, constructed with the Spring framework (Spring Boot and Spring MVC). This application shows how Amazon Web Services (AWS) Cognito can be used for server side authentication.

AWS Cognito is a multi-faceted authentication service. Cognito reduces the amount of software that has to be written to support login, account creation and user information update. Cognito is a "pay as you go" service, which does not incur the 24-hour a day, 7-days a week cost of an authentication service that is supported by a database server.

One barrier to leveraging Cognito in a Java application is the lack of documentation when it comes to writing Java code that references the Cognito API. This application includes the AuthenticationService object which contains all (most) of the functions you will need to implement server side authentication using Cognito.

This open source software was developed by [Topstone Software Consulting](http://topstonesoftware.com). An article that discusses Cognito and this demonstration application can be found [here](http://topstonesoftware.com/publications/authentication_with_aws_cognito.html).

## Application Structure

The Cognito demonstration application is a Spring application. It consists of three major components:

<ul>
                <li><b>Controllers</b><br/>The Spring controllers provide the server side logic for the web pages (e.g., form
                input validation and processing). Most of the controllers have separate functions that process
                HTTP GET and POST operations.</li>
                <li><b>View</b><br/>The view contains the Java Server Pages (JSP) web pages. Each web page corresponds to
                a controller class.</li>
                <li><b>Services</b><br/>The <tt>services</tt> package contains the code that supports the controllers. In this
                demo application the core class is <tt>AuthenticationService</tt>, which implements the
                AuthenticationInterface. The <tt>AuthenticationService</tt> class contains the code that interfaces with
                    Cognito.
                </li>
</ul>
            
Please see the Topstone Software article on this application for more detail.
