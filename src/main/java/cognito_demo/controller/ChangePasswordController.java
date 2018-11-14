/** \file
 * 
 * Oct 8, 2018
 *
 * Copyright Ian Kaplan 2018
 *
 * @author Ian Kaplan, www.bearcave.com, iank@bearcave.com
 */
package cognito_demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amazonaws.services.cognitoidp.model.InvalidPasswordException;
import com.google.common.base.Strings;

import cognito_demo.services.PasswordRequest;
import cognito_demo.services.UserInfo;

@Controller
public class ChangePasswordController extends AuthenticationBase {

    /**
     * Display the change password form.  This form is displayed differently for a forgotten password and
     * for a password change for a logged in user (e.g., a user with an active session). The changeType
     * variable controls the way the form is displayed.
     * 
     * @return
     */
    @GetMapping("change_password")
    public ModelAndView changePassword( ModelMap modelMap, HttpServletRequest request ) {
        if (request.getSession().getAttribute(USER_SESSION_ATTR) != null) {
            // if the user is logged in (they know their password) then the change is just an old to new password
            // change without emailing the password.
            UserInfo info = (UserInfo)request.getSession().getAttribute(USER_SESSION_ATTR);
            if (info != null) {
                modelMap.addAttribute("user_name_val", info.getUserName());
            }
        }
        return new ModelAndView("change_password", modelMap );
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
    @PostMapping("change_password_form")
    public String changePasswordForm(@RequestParam("user_name") final String userName,
                                     @RequestParam("old_password") final String oldPassword,
                                     @RequestParam("new_password") final String newPassword,
                                     @RequestParam("verify_password") final String verifyPassword,
                                     RedirectAttributes redirect,
                                     HttpServletRequest request ) {
        boolean hasErrors = false;
        String userNameArg = null;
        String oldPasswordArg = null;
        String newPasswordArg = null;
        String verifyPasswordArg = null;
        
        // By default, if there's an error, we go back to the change_password page.
        String nextPage = "redirect:change_password";
        if (Strings.isNullOrEmpty(userName)) {
            redirect.addFlashAttribute("user_name_error", "Please supply a user name");
            hasErrors = true;
        } else {
            userNameArg = userName.trim();
            redirect.addFlashAttribute("user_name_val", userNameArg);
        }
        if (! hasErrors) {
            if (Strings.isNullOrEmpty(oldPassword)) {
                redirect.addFlashAttribute("old_password_error", "Please supply your current password");
                hasErrors = true;
            } else {
                oldPasswordArg = oldPassword.trim();
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
                PasswordRequest passwordRequest = new PasswordRequest(userNameArg, oldPasswordArg, newPasswordArg);
                if (request.getSession().getAttribute(USER_SESSION_ATTR) == null) {
                    // this is a forgotten password and we are not logged in
                    authService.changeFromTemporaryPassword(passwordRequest);
                    redirect.addFlashAttribute("user_name_val", userNameArg);
                    nextPage = "redirect:/";
                } else {
                   // this is a change password and we are logged in
                   authService.changePassword(passwordRequest);
                   nextPage = "redirect:/application";
                }
            } catch (InvalidPasswordException e) {
                redirect.addFlashAttribute("change_password_error", "Bad password error: " + e.getErrorMessage());
            } catch (Exception e) {
                redirect.addFlashAttribute("change_password_error", "Error encountered in changing password: " + e.getLocalizedMessage());
            }
        }
        return nextPage;
    }
}
