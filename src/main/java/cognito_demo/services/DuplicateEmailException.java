/** \file
 * 
 * Oct 23, 2018
 *
 * Copyright Ian Kaplan 2018
 *
 * @author Ian Kaplan, www.bearcave.com, iank@bearcave.com
 */
package cognito_demo.services;

import com.amazonaws.services.cognitoidp.model.AWSCognitoIdentityProviderException;

/**
 * <h4>
 * DuplicateEmailException
 * </h4>
 * <p>
 * An exception designed for the case where there is more than one user in the Cognito database
 * with a given email address.
 * </p>
 * <p>
 * Oct 23, 2018
 * </p>
 * 
 * @author Ian Kaplan, iank@bearcave.com
 */
public class DuplicateEmailException extends AWSCognitoIdentityProviderException {

    /**
     * long
     */
    private static final long serialVersionUID = 6561571810771139916L;

    public DuplicateEmailException(final String errorMessage) {
        super(errorMessage);
    }
}
