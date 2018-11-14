/** \file
 * 
 * Oct 8, 2018
 *
 * Copyright Ian Kaplan 2018
 *
 * @author Ian Kaplan, www.bearcave.com, iank@bearcave.com
 */
package cognito_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amazonaws.services.cognitoidp.model.InvalidPasswordException;
import com.google.common.base.Strings;

import cognito_demo.services.ResetPasswordRequest;

/**
 * <h4>
 * ResetPasswordController
 * </h4>
 * <p>
 * This controller handles the reset password form. This form is used to reset the password using a code that
 * is emailed to the user's email address.
 * </p>
 * <p>
 * Oct 31, 2018
 * </p>
 * 
 * @author Ian Kaplan, iank@bearcave.com
 */
@Controller
public class ResetPasswordController extends AuthenticationBase {

    /**
     * Display the change password form
     * @return
     */
    @GetMapping("reset_password")
    public String changePassword( Model model ) {
        return "reset_password";
    }
    
    
    /**
     * 
     * <p>
     * Flash errors:
     * </p>
     * <ul>
     * <li>user_name_error</li>
     * <li>old_password_error</li>
     * <li>new_password_error</li>
     * <li>verify_password_error</li>
     * <li>change_password_error</li>
     * </ul>
     * 
     * @param oldPassword existing password
     * @param newPassword new password
     * @param verifyPassword verify password (this should be exactly the same as newPassword) and allows us to 
     *        help assure that the password the user typed is the one they intended.
     * @param redirect
     * @return
     */
    @PostMapping("reset_password_form")
    public String changePasswordForm(@RequestParam("user_name") final String userName,
                                     @RequestParam("reset_code") final String resetCode,
                                     @RequestParam("new_password") final String newPassword,
                                     @RequestParam("verify_password") final String verifyPassword,
                                     RedirectAttributes redirect) {
        boolean hasErrors = false;
        String userNameArg = null;
        String resetCodeArg = null;
        String newPasswordArg = null;
        String verifyPasswordArg = null;
        
        // By default, if there's an error, we go back to the change_password page.
        String nextPage = "redirect:reset_password";
        if (Strings.isNullOrEmpty(userName)) {
            redirect.addFlashAttribute("user_name_error", "Please supply a user name");
            hasErrors = true;
        } else {
            userNameArg = userName.trim();
            redirect.addFlashAttribute("user_name_val", userNameArg);
        }
        if (! hasErrors) {
            if (Strings.isNullOrEmpty(resetCode)) {
                redirect.addFlashAttribute("reset_code_error", "Please supply the reset code");
                hasErrors = true;
            } else {
                resetCodeArg = resetCode.trim();
            }
        }
        if (! hasErrors) {
            if (Strings.isNullOrEmpty(newPassword)) {
                redirect.addFlashAttribute("new_password_error", "Please supply a new password");
                hasErrors = true;
            } else {
                newPasswordArg = newPassword.trim();
                if (newPasswordArg.length() < PASSWORD_MIN_LENGTH) {
                    redirect.addFlashAttribute("new_password_error", 
                                               "New password must be at least " + PASSWORD_MIN_LENGTH + " characters long");
                    hasErrors = true;
                } 
            } 
        }
        if (! hasErrors) {
            if (Strings.isNullOrEmpty(verifyPassword)) {
                redirect.addFlashAttribute("verify_password_error", "Please enter your new password again");
                hasErrors = true;
            } else {
                verifyPasswordArg = verifyPassword.trim();
                if (! newPasswordArg.equals(verifyPasswordArg)) {
                    redirect.addFlashAttribute("verify_password_error", "Passwords do not match");
                    hasErrors = true;
                } 
            }
        }
        /*
         * If all arguments are OK, change the password
         *  
         */
        if (! hasErrors) {
            try {
                ResetPasswordRequest resetRequest = new ResetPasswordRequest(userNameArg, resetCodeArg, newPasswordArg);
                authService.resetPassword( resetRequest );
                redirect.addFlashAttribute("user_name_val", userName);
                nextPage = "redirect:/";
            } catch (InvalidPasswordException e) {
                redirect.addFlashAttribute("reset_password_error", "Bad password error: " + e.getErrorMessage());
            } catch (Exception e) {
                redirect.addFlashAttribute("reset_password_error", "Error encountered reseting password: " + e.getLocalizedMessage());
            }
        }
        return nextPage;
    }
}
