/** \file
 * 
 * Oct 16, 2018
 *
 * Copyright Ian Kaplan 2018
 *
 * @author Ian Kaplan, www.bearcave.com, iank@bearcave.com
 */
package cognito_demo.services;

/**
 * <h4>
 * LoginInfo
 * </h4>
 * <p>
 * The information that is obtained with the user logs into the system. This class extends the UserInfo
 * class by adding an session token which is needed by operations like changePassword().  Note that the
 * session token is used for the session, while the access token is used to grant access to resources
 * (which is not used in this code).
 * </p>
 * <p>
 * Oct 16, 2018
 * </p>
 * 
 * @author Ian Kaplan, iank@bearcave.com
 */
public class LoginInfo extends UserInfo {
    private Boolean newPasswordRequired = false;
    
    public LoginInfo(final String userName, final String email, final String location) {
        super(userName, email, location);
    }
    
    public LoginInfo(final UserInfo info) {
        this(info.getUserName(), info.getEmailAddr(), info.getLocation());
    }

    public Boolean getNewPasswordRequired() {
        return newPasswordRequired;
    }

    public void setNewPasswordRequired(Boolean newPasswordRequired) {
        this.newPasswordRequired = newPasswordRequired;
    }
    
}
