/** \file
 * 
 * Oct 26, 2018
 *
 * Copyright Ian Kaplan 2018
 *
 * @author Ian Kaplan, www.bearcave.com, iank@bearcave.com
 */
package cognito_demo.controller;

import cognito_demo.services.AuthenticationService;

/**
 * <h4>
 * AuthenticationBase
 * </h4>
 * <p>
 * This base class provides the authentication service and some common constants for 
 * the authentication controller subclasses.
 * </p>
 * <p>
 * Oct 26, 2018
 * </p>
 * 
 * @author Ian Kaplan, iank@bearcave.com
 */
public abstract class AuthenticationBase {
    protected static AuthenticationService authService = new AuthenticationService();
    // minimum length for a user name
    protected final static int USER_NAME_MIN_LENGTH = 4;
    protected final static int PASSWORD_MIN_LENGTH = 8;
    // the name of the session attribute containing user information that is set when the user successfully logs in.
    protected final static String USER_SESSION_ATTR = "user_info";
}
