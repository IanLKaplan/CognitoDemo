/** \file
 * 
 * Oct 16, 2018
 *
 * Copyright Ian Kaplan 2018
 *
 * @author Ian Kaplan, www.bearcave.com, iank@bearcave.com
 */
package cognito_demo.services;

public class PasswordRequest {
    private final String userName;
    private final String oldPassword;
    private final String newPassword;
    
    public PasswordRequest(final String userName, final String oldPassword, final String newPassword) {
        this.userName = userName;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getUserName() {
        return userName;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
    
}
