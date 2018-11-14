/** \file
 * 
 * Oct 29, 2018
 *
 * Copyright Ian Kaplan 2018
 *
 * @author Ian Kaplan, www.bearcave.com, iank@bearcave.com
 */
package cognito_demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.amazonaws.services.cognitoidp.model.InvalidParameterException;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.google.common.base.Strings;

/**
 * <h4>
 * ForgotPasswordController
 * </h4>
 * <p>
 * This controller handles the page and form that allows a user to request a password reset.
 * Cognito will email a code to the user's email. This code can be used to create a new password.
 * The form for the password reset (using the code) is on the reset_password page.
 * </p>
 * <p>
 * Oct 31, 2018
 * </p>
 * 
 * @author Ian Kaplan, iank@bearcave.com
 */
@Controller
public class ForgotPasswordController extends AuthenticationBase {

    @GetMapping("/forgot_password")
    public String passwordResetPage( Model model ) {
        return "forgot_password";
    }
    
    
    /**
     * Given a user name, send an email with a password reset code.
     * 
     * @param user
     * @param redirect
     * @return
     */
    @PostMapping("/forgot_password_form")
    public String resetPasswordForm(@RequestParam("user_name") String user,
                                     RedirectAttributes redirect,
                                     HttpServletRequest request) {
        boolean hasErrors = false;
        String userNameArg = null;
        String nextPage = "redirect:forgot_password";
        if (Strings.isNullOrEmpty(user)) {
            redirect.addFlashAttribute("user_name_error", "Please supply a user name");
            hasErrors = true;
        } else {
            userNameArg = user.trim();
        }
        if (! hasErrors) {
            try {
                authService.forgotPassword(userNameArg);
                redirect.addFlashAttribute("message", "A reset code has been sent to your email address");
                redirect.addFlashAttribute("user_name_val", userNameArg);
                redirect.addFlashAttribute("change_type", "forgotten_password");
                // Log out of the session...
                request.getSession().setAttribute(super.USER_SESSION_ATTR, null);
                nextPage = "redirect:reset_password"; // Go to the form to reset the password.
            } catch (UserNotFoundException e) {
                redirect.addFlashAttribute("user_name_error", "Sorry, couldn't find the user " + userNameArg );
            } catch(InvalidParameterException e) {
                redirect.addFlashAttribute("user_name_error", "Cannot reset password for the user as there is no registered/verified email or phone_number");
            } catch (Exception e) {
                redirect.addFlashAttribute("user_name_error", "User name error: " + e.getClass().getName() + " " + e.getLocalizedMessage());
            }
        }
        return nextPage;
    }
}
