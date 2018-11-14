/** \file
 * 
 * Oct 9, 2018
 *
 * Copyright Ian Kaplan 2018
 *
 * @author Ian Kaplan, www.bearcave.com, iank@bearcave.com
 */
package cognito_demo.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.SdkBaseException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException;
import com.amazonaws.services.cognitoidp.model.AdminCreateUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminDeleteUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminGetUserRequest;
import com.amazonaws.services.cognitoidp.model.AdminGetUserResult;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AdminRespondToAuthChallengeRequest;
import com.amazonaws.services.cognitoidp.model.AdminRespondToAuthChallengeResult;
import com.amazonaws.services.cognitoidp.model.AdminUpdateUserAttributesRequest;
import com.amazonaws.services.cognitoidp.model.AdminUserGlobalSignOutRequest;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.ChallengeNameType;
import com.amazonaws.services.cognitoidp.model.ChangePasswordRequest;
import com.amazonaws.services.cognitoidp.model.ChangePasswordResult;
import com.amazonaws.services.cognitoidp.model.CodeDeliveryDetailsType;
import com.amazonaws.services.cognitoidp.model.ConfirmForgotPasswordRequest;
import com.amazonaws.services.cognitoidp.model.ConfirmForgotPasswordResult;
import com.amazonaws.services.cognitoidp.model.ForgotPasswordRequest;
import com.amazonaws.services.cognitoidp.model.ForgotPasswordResult;
import com.amazonaws.services.cognitoidp.model.ListUsersRequest;
import com.amazonaws.services.cognitoidp.model.ListUsersResult;
import com.amazonaws.services.cognitoidp.model.UserType;

/**
 * <h4>
 * AuthenticationService
 * </h4>
 * <p>
 * Provide authentication and other user management services.
 * </p>
 * <p>
 * Oct 16, 2018
 * </p>
 * 
 * @author Ian Kaplan, iank@bearcave.com
 */
public class AuthenticationService implements AuthenticationInterface, CognitoResources {
    private final static String USERNAME = "USERNAME";
    private final static String PASSWORD = "PASSWORD";
    private final static String NEW_PASSWORD = "NEW_PASSWORD";

    protected static AWSCognitoIdentityProvider mIdentityProvider = null;
    
    public AuthenticationService() {
        if (mIdentityProvider == null) {
            mIdentityProvider = getAmazonCognitoIdentityClient();
        }
    }
    
    
    /**
     * <p>
     * Build an AWSCredentials object from the ID and secret key.
     * </p>
     * 
     * @param AWS_ID
     * @param AWS_KEY
     * @return an AWSCredentials object, initialized with the ID and Key.
     */
    protected AWSCredentials getCredentials(String AWS_ID, String AWS_KEY) {
        AWSCredentials credentials = new BasicAWSCredentials( AWS_ID, AWS_KEY );            
        return credentials;
    }
    
    /**
     * <p>
     * Build an AWS cognito identity provider, based on the parameters defined in the CognitoResources interface.
     * </p>
     * 
     * @return
     */
    protected AWSCognitoIdentityProvider getAmazonCognitoIdentityClient() {
        AWSCredentials credentials = getCredentials(cognitoID, cognitoKey);
        AWSCredentialsProvider credProvider = new AWSStaticCredentialsProvider( credentials );
        AWSCognitoIdentityProvider client = AWSCognitoIdentityProviderClientBuilder.standard()
                                                                                    .withCredentials(credProvider)
                                                                                    .withRegion(region)
                                                                                    .build();
        return client;
     }
    
    
    /**
     * <p>
     * Create a new user.
     * </p>
     * <p>
     * When a user is created in the Cognito user pool, the user account will be initially inactive. An email with a 
     * temporary password will be sent to the user's email address. The user will use this temporary password to login
     * (via the login page). They will then be redirected to the change password page.
     * </p>
     * <p>
     * This function assumes that the user does not exist (the user name and email should be unique).
     * </p>
     * <p>
     * The caller should check whether the user already exists and handle that with the appropriate logic.
     * If the user already exists, this method will throw an exception.
     * </p>
     * <p>
     * The email_verified attribute (set to true) is very important. Without setting this attribute, the email address will
     * be treated as unverified. If the email is not verified, an attempt to reset the password will result in an unverified
     * email error.
     * </P>
     * 
     * @param userName The publicly visible user name
     * @param emailAddress the user's email address. This will be the email address that the temporary password will be sent to.
     * @param location The users location (e.g., Bonaire, Aruba, Curacao)
     * @return a UserType object.
     * 
     * @throws AWSCognitoIdentityProviderException This is the parent exception for Cognito errors. The caller should
     *         handle the UsernameExistsException error.
     */
    @Override
    public void createNewUser(final UserInfo userInfo) throws AWSCognitoIdentityProviderException {
        final String emailAddr = userInfo.getEmailAddr();
        if (emailAddr != null && emailAddr.length() > 0) {
            // There should be no user with this email address, so info should be null
            UserInfo info = findUserByEmailAddr( emailAddr );
            if (info == null) {
                AdminCreateUserRequest cognitoRequest = new AdminCreateUserRequest()
                                                        .withUserPoolId(poolID)
                                                        .withUsername(userInfo.getUserName())
                                                        .withUserAttributes(
                                                                new AttributeType()
                                                                   .withName(EMAIL)
                                                                   .withValue(emailAddr),
                                                                new AttributeType()
                                                                    .withName(LOCATION)
                                                                    .withValue(userInfo.getLocation()),
                                                                new AttributeType()
                                                                    .withName("email_verified")
                                                                    .withValue("true")
                                                         );
                // The AdminCreateUserResult resturned by this function doesn't contain useful information so the
                // result is ignored.
                mIdentityProvider.adminCreateUser(cognitoRequest);
            } else {
                // The caller should have checked that the email address is not already used by a user. If this is not
                // done, then it's an exception (e.g., something is wrong).
                throw new DuplicateEmailException("The email address " + emailAddr + " is already in the database" );
            }
        }
    }  // createNewUser
    

    /**
       Delete an existing user. This code assumes that the operation is logically permitted (a user is only
       allowed to delete their own account, after password verification).
     */
    @Override
    public void deleteUser(final String userName, final String password) throws AWSCognitoIdentityProviderException {
        SessionInfo sessionInfo = sessionLogin(userName, password);
        if (sessionInfo != null) {
            AdminDeleteUserRequest deleteRequest = new AdminDeleteUserRequest()
                    .withUsername(userName)
                    .withUserPoolId(poolID);
            // the adminDeleteUserRequest returns an AdminDeleteUserResult which doesn't contain anything useful.
            // So the result is ignored.
            mIdentityProvider.adminDeleteUser(deleteRequest);
        }
    }
    
    
    /**
       Update user attributes associated with the user information stored in Cognito. Note that the email
       attribute is updated separately.
     */
    @Override
    public void updateUserAttributes(final UserInfo newInfo) throws AWSCognitoIdentityProviderException {
       AdminUpdateUserAttributesRequest updateRequest = new AdminUpdateUserAttributesRequest()
                                                            .withUsername(newInfo.getUserName())
                                                            .withUserPoolId(poolID)
                                                            .withUserAttributes(
                                                                    new AttributeType()
                                                                        .withName(LOCATION)
                                                                        .withValue(newInfo.getLocation())
                                                                    );
       mIdentityProvider.adminUpdateUserAttributes(updateRequest);
    }

    /**
     * <blockquote>
     * After a successful authentication, Amazon Cognito returns user pool tokens to your app. 
     * </blockquote>
     * <blockquote>
     * The ID Token contains claims about the identity of the authenticated user such as name, email, and phone_number.
     * </blockquote>
     * 
     * @param userName
     * @param password
     * @return
     * @throws AWSCognitoIdentityProviderException
     */
    protected SessionInfo sessionLogin(final String userName, final String password) throws AWSCognitoIdentityProviderException {
        SessionInfo info = null;
        HashMap<String, String> authParams = new HashMap<String, String>();
        authParams.put("USERNAME", userName);
        authParams.put("PASSWORD", password);
        AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest()
                                                   .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                                                   .withUserPoolId(poolID)
                                                   .withClientId(clientID)
                                                   .withAuthParameters(authParams);
        AdminInitiateAuthResult authResult = mIdentityProvider.adminInitiateAuth(authRequest);
        // If there is a bad username the adminInitiateAuth() call will throw a UserNotFoundException. 
        // Unfortunately the AWS documentation doesn't say what happens if the password is incorrect.
        // Perhaps the NotAuthorizedException is thrown?
        if (authResult != null) {
            final String session = authResult.getSession();
            String accessToken = null;
            AuthenticationResultType resultType = authResult.getAuthenticationResult();
            if (resultType != null) {
                accessToken = resultType.getAccessToken();
            }
            final String challengeResult = authResult.getChallengeName();
            info = new SessionInfo(session, accessToken, challengeResult );
        }
        return info;
    }
    
    /**
     * <p>
     * Log a user into the system using Cognito.
     * </p>
     * @param userName
     * @param password
     * @return a LoginInfo object that includes the access token which is used for user operations like change password.
     * @throws AWSCognitoIdentityProviderException In addition to various other exceptions, the function will throw
     *         the UserNotFoundException if the user is not found in Cognito's database.
     */
    @Override
    public LoginInfo userLogin(final String userName, final String password) throws AWSCognitoIdentityProviderException {
        LoginInfo loginInfo = null;
        SessionInfo sessionInfo = sessionLogin(userName, password);
        // The process of sessionLogin should either return a session ID (if the account has not been verified) or a
        // token ID (if the account has been verified).
        if (sessionInfo != null) {
            UserInfo userInfo = getUserInfo(userName);
            loginInfo = new LoginInfo( userInfo );
            // check to see if the password used was a temporary password. If this is the case, the password
            // must be reset.
            String challengeResult = sessionInfo.getChallengeResult();
            if (challengeResult != null && challengeResult.length() > 0) {
                loginInfo.setNewPasswordRequired( challengeResult.equals(ChallengeNameType.NEW_PASSWORD_REQUIRED.name() ));
            }
        }
        return loginInfo;
    }
    
    
    /**
     * <p>
     * Log the user out.
     * </p>
     * @param userName
     * @throws AWSCognitoIdentityProviderException
     */
    @Override
    public void userLogout(final String userName) throws AWSCognitoIdentityProviderException {
        AdminUserGlobalSignOutRequest signOutRequest = new AdminUserGlobalSignOutRequest()
                                                           .withUsername(userName)
                                                           .withUserPoolId(poolID);
        // The AdminUserGlobalSignOutResult returned by this function does not contain any useful information so the
        // result is ignored.
        mIdentityProvider.adminUserGlobalSignOut(signOutRequest);
    }
    
    
    /**
     * <p>
     * Change the password for a logged in user. Unlike the forgotten password logic, this does not require an 
     * emailed code to change the password.
     * </p>
     * @throws NotAuthorizedException (subclass of AWSCognitoIdentityProviderException) if the password is wrong.
     */
    @Override
    public void changePassword(final PasswordRequest passwordRequest) throws AWSCognitoIdentityProviderException {
        // Signin with the old/temporary password. Apparently this is needed to establish a session for the
        // password change.
        final SessionInfo sessionInfo = sessionLogin(passwordRequest.getUserName(), passwordRequest.getOldPassword());
        if (sessionInfo != null && sessionInfo.getAccessToken() != null) {
            ChangePasswordRequest changeRequest = new ChangePasswordRequest()
                                                      .withAccessToken( sessionInfo.getAccessToken())
                                                      .withPreviousPassword( passwordRequest.getOldPassword())
                                                      .withProposedPassword( passwordRequest.getNewPassword());
            ChangePasswordResult rslt = mIdentityProvider.changePassword(changeRequest);
        } else {
            String msg = "Access token was not returned from session login";
            throw new AWSCognitoIdentityProviderException( msg );
        }
    }
    
    
    /**
     * <p>
     * Change user's email address. The email address is a special attribute since it will be used for communication
     * with the user.
     * </p>
     * <p>
     * In this code the user is allowed to change their email addressed without verification via an emailed code
     * by setting the "email_verified" attribute to true. 
     * </p>
     * <p>
     * Ideally it would be nice to verify the email address via an emailed code, since this assures that the email address
     * is valid and belongs to the user. However, I have been unable to figure out how to do this within the Cognito framework.
     * So we assume that the user entered a correct email address. They can always change it if an error is discovered.
     * </p>
     */
    @Override
    public void changeEmail(final String userName, final String newEmailAddr) throws AWSCognitoIdentityProviderException {
        AdminUpdateUserAttributesRequest updateRequest = new AdminUpdateUserAttributesRequest()
                                                         .withUsername(userName)
                                                         .withUserPoolId(poolID)
                                                         .withUserAttributes(
                                                                 new AttributeType()
                                                                     .withName(EMAIL)
                                                                     .withValue(newEmailAddr),
                                                                new AttributeType()
                                                                     .withName("email_verified")
                                                                     .withValue("true")      
                                                         );
        mIdentityProvider.adminUpdateUserAttributes(updateRequest);
    }
    
    /**
     * <p>
     * Change the user's password from a temporary password to a new (permanent) password.
     * </p>
     * <p>
     * This function is called to set the password using the temporary password emailed to the user's email
     * address.
     * </p>
     *  
     * @param passwordRequest a PasswordRequest object that provides the information needed to change the password.
     * @throws AWSCognitoIdentityProviderException the InvalidPasswordException will be thrown if the user provides
     *         an incorrect old password.
     */
    @Override
    public void changeFromTemporaryPassword(final PasswordRequest passwordRequest) throws AWSCognitoIdentityProviderException {
        // Signin with the old/temporary password. Apparently this is needed to establish a session for the
        // password change.
        final SessionInfo sessionInfo = sessionLogin(passwordRequest.getUserName(), passwordRequest.getOldPassword());
        final String sessionString = sessionInfo.getSession();
        if (sessionString != null && sessionString.length() > 0) {
            Map<String, String> challengeResponses = new HashMap<String, String>();
            challengeResponses.put(USERNAME, passwordRequest.getUserName());
            challengeResponses.put(PASSWORD, passwordRequest.getOldPassword());
            challengeResponses.put(NEW_PASSWORD, passwordRequest.getNewPassword());
            AdminRespondToAuthChallengeRequest changeRequest = new AdminRespondToAuthChallengeRequest()
                                                                   .withChallengeName( ChallengeNameType.NEW_PASSWORD_REQUIRED)
                                                                   .withChallengeResponses(challengeResponses)
                                                                   .withClientId(clientID)
                                                                   .withUserPoolId(poolID)
                                                                   .withSession( sessionString );
            AdminRespondToAuthChallengeResult challengeResponse = mIdentityProvider.adminRespondToAuthChallenge(changeRequest);
        }
    } // changePassword
    
    
    /**
     * <p>
     * Reset the user's password using a code that they received via email.
     * </p>
     * @param resetRequest
     */
    @Override
    public void resetPassword(ResetPasswordRequest resetRequest) throws AWSCognitoIdentityProviderException {
        ConfirmForgotPasswordRequest passwordRequest = new ConfirmForgotPasswordRequest()
                                                           .withUsername(resetRequest.getUserName())
                                                           .withConfirmationCode(resetRequest.getResetCode())
                                                           .withClientId(clientID)
                                                           .withPassword(resetRequest.getNewPassword());
        ConfirmForgotPasswordResult rslt = mIdentityProvider.confirmForgotPassword(passwordRequest);
    }
    
    /**
     * <p>
     * This function is called to reset the user's password if the password has been forgotten. The function will
     * result in an email being sent to the user's email account with a reset code.
     * </p>
     * @param userName the user name for the account that should be reset.
     */
    @Override
    public void forgotPassword(final String userName)  throws AWSCognitoIdentityProviderException {
        ForgotPasswordRequest passwordRequest = new ForgotPasswordRequest()
                                                    .withClientId(clientID)
                                                    .withUsername(userName);
        ForgotPasswordResult rslt = mIdentityProvider.forgotPassword(passwordRequest);
        CodeDeliveryDetailsType delivery = rslt.getCodeDeliveryDetails();
    }
    
   

    /**
     * <p>
     * Get the information associated with the user. 
     * </p>
     * 
     * @param userName the name of the user to be returned
     * @return a UserInfo object if the information could be retreived, null otherwise.
     * @throws AWSCognitoIdentityProviderException if the user didn't exist, then the UserNotFoundException exception
     *         will be thrown. The other exceptions that are subclasses of AWSCognitoIdentityProviderException are
     *         authorization or internal errors.
     */
    @Override
    public UserInfo getUserInfo(final String userName) throws AWSCognitoIdentityProviderException {
        AdminGetUserRequest userRequest = new AdminGetUserRequest()
                                              .withUsername(userName)
                                              .withUserPoolId(poolID);
        AdminGetUserResult userResult = mIdentityProvider.adminGetUser(userRequest);
        List<AttributeType> userAttributes = userResult.getUserAttributes();
        final String rsltUserName = userResult.getUsername();
        String emailAddr = null;
        String location = null;
        for (AttributeType attr : userAttributes) {
            if (attr.getName().equals(EMAIL)) {
                emailAddr = attr.getValue();
            } else if (attr.getName().equals(LOCATION)) {
                location = attr.getValue();
            }
        }
        UserInfo info = null;
        if (rsltUserName != null && emailAddr != null && location != null) {
            info = new UserInfo(rsltUserName, emailAddr, location);
        }
        return info;
    } // getUserInfo
    
    
    
    /**
     * <p>
     * Find a user by their email address.
     * </p>
     * @return a UserInfo object if the lookup succeeded or null if there was no user associated with that email
     *         address.
     */
    @Override
    public UserInfo findUserByEmailAddr(String email) throws  AWSCognitoIdentityProviderException  {
        UserInfo info = null;
        if (email != null && email.length() > 0) {
            final String emailQuery = "email=\"" + email + "\"";
            ListUsersRequest usersRequest = new ListUsersRequest()
                                               .withUserPoolId(poolID)
                                               .withAttributesToGet(EMAIL, LOCATION)
                                               .withFilter(emailQuery);
            ListUsersResult usersRslt = mIdentityProvider.listUsers(usersRequest);
            List<UserType> users = usersRslt.getUsers();
            if (users != null && users.size() > 0) {
                // There should only be a single instance of an email address in the Cognito database
                // (e.g., there should not be multiple users with the same email address).
                if (users.size() == 1) {
                    UserType user = users.get(0);
                    final String userName = user.getUsername();
                    String emailAddr = null;
                    String location = null;
                    List<AttributeType> attributes = user.getAttributes();
                    if (attributes != null) {
                        for (AttributeType attr : attributes) {
                            if (attr.getName().equals(EMAIL)) {
                                emailAddr = attr.getValue();
                            } else if (attr.getName().equals(LOCATION)) {
                                location = attr.getValue();
                            }
                        }
                        if (userName != null && emailAddr != null && location != null) {
                            info = new UserInfo(userName, emailAddr, location);
                        }
                    }
                } else {
                    throw new DuplicateEmailException("More than one user has the email address " + email);
                }
            }
        }
        return info;
    }


    /**
     * <p>
     * Determine whether a user with userName exists in the login database.
     * </p>
     * 
     * @param userName
     * @return true if the user exists, false otherwise.
     */
    @Override
    public boolean hasUser(final String userName) {
        boolean userExists = false;
        try {
            UserInfo info = getUserInfo( userName );
            if (info != null && info.getUserName() != null && info.getUserName().length() > 0 && info.getUserName().equals(userName)) {
                userExists = true;
            }
        }
        catch (SdkBaseException ex) {}
        return userExists;
    }
    
}
