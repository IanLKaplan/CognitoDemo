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
 * UserInfo
 * </h4>
 * <p>
 * The UserInfo class packages the information associated with a user. This allows the user information
 * to be abstracted from the underlying user implementation for user authentication.
 * </p>
 * <p>
 * Oct 16, 2018
 * </p>
 * 
 * @author Ian Kaplan, iank@bearcave.com
 */
public class UserInfo {
    private final String userName;
    private final String emailAddr;
    private final String location;
    
    public UserInfo(final String userName, final String emailAddr, final String location) {
        this.userName = userName;
        this.emailAddr = emailAddr;
        this.location = location;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public String getLocation() {
        return location;
    }

}
