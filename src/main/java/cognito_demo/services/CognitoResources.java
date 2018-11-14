/** \file
 * 
 * Oct 9, 2018
 *
 * Copyright Ian Kaplan 2018
 *
 * @author Ian Kaplan, www.bearcave.com, iank@bearcave.com
 */
package cognito_demo.services;

import com.amazonaws.regions.Regions;

/**
 * <h4>
 * CognitoResources
 * </h4>
 * <p>
 * Constants that are used by for AWS Cognito authentication.
 * </p>
 * <p>
 * Nov 13, 2018
 * </p>
 * 
 * @author Ian Kaplan, iank@bearcave.com
 */
public interface CognitoResources {
    public final static String LOCATION = "custom:location";
    public final static String EMAIL = "email";
    // Cognito IAM ID for full Cognito access
    public final static String cognitoID = "Your Cognito IAM ID goes here";
    // Cognito IAM "secret key" for full Cognito access
    public final static String cognitoKey = "Your Cognito IAM secret key goes here";
    public final static String poolID = "Your Cognito Pool ID goes here";
    public final static String clientID = "Your Cognito client ID goes here";
    // Replace this with the AWS region for your application
    public final static Regions region = Regions.EU_CENTRAL_1;
}
