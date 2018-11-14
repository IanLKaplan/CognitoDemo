/** \file
 * 
 * Oct 19, 2018
 *
 * Copyright Ian Kaplan 2018
 *
 * @author Ian Kaplan, www.bearcave.com, iank@bearcave.com
 */
package cognito_demo.services;

import com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException;

/**
 * <h4>
 * AuthenticationInterface
 * </h4>
 * <p>
 * An interface for authentication. This interface is designed to abstract the actual implementation of
 * authentication.
 * </p>
 * <p>
 * Nov 13, 2018
 * </p>
 * 
 * @author Ian Kaplan, iank@bearcave.com
 */
public interface AuthenticationInterface {

    /**
     * <p>
     * Create a new user.
     * </p>
     * 
     */
    void createNewUser(UserInfo userInfo) throws Exception;
    
    
    /**
     * <p>
     * Delete a user. Since deleting a user can be a "big deal" a password is required.
     * </p>
     * @param userName
     * @param password
     */
    void deleteUser(String userName, String password) throws Exception;
    
    
    /**
     * Find a user by email address.
     * 
     * @param email
     * @return
     * @throws Exception
     */
    UserInfo findUserByEmailAddr(final String email) throws Exception;

    /**
     * <p>
     * Update selected user attributes.
     * </p>
     * 
     * @param newInfo
     * @throws AWSCognitoIdentityProviderException
     */
    void updateUserAttributes(UserInfo newInfo) throws Exception;

    /**
     * <p>
     * Log a user into the system
     * </p>
     * @param userName
     * @param password
     * @return a LoginInfo object may include special context information.
     */
    LoginInfo userLogin(String userName, String password) throws Exception;

    /**
     * <p>
     * Log the user out.
     * </p>
     * @param userName
     */
    void userLogout(String userName) throws Exception;

    /**
     * <p>
    * Change the user's password from a temporary password to a new (permanent) password.
    * </p>
    */
    public void changeFromTemporaryPassword(final PasswordRequest passwordRequest) throws Exception;
    
    /**
     * Support for resetting the user's password in the event that it was forgotten.
     * 
     * @param userName
     * @param resetCode
     * @throws Exception
     */
    void forgotPassword(final String userName) throws Exception;
    
    
    /**
     * <p>
     * Get the information associated with the user. 
     * </p>
     * 
     * @param userName the name of the user
     * @return a UserInfo object if the information could be retrieved, null otherwise.
     */
    UserInfo getUserInfo(String userName) throws Exception; // getUserInfo

    /**
     * <p>
     * Determine whether a user with userName exists in the login database.
     * </p>
     * 
     * @param userName
     * @return true if the user exists, false otherwise.
     */
    boolean hasUser(String userName);


    /**
     * <p>
     * Reset a user's password using an authentication code.
     * </p>
     * 
     * @param resetRequest
     * @throws Exception
     */
    void resetPassword(ResetPasswordRequest resetRequest) throws Exception;

    /**
     * <p>
     * Change the password for a logged in user. Unlike the forgotten password logic, this does not require an 
     * authentication code.
     * </p>
     * 
     * @param passwordRequest
     * @throws Exception
     */
    void changePassword(PasswordRequest passwordRequest) throws Exception;

    /**
     * <p>
     * Change the email address of a logged in user.
     * </p>
     * @param userName
     * @param newEmailAddr
     * @throws Exception
     */
    void changeEmail(String userName, String newEmailAddr) throws Exception;

}
