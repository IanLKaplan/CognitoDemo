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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.base.Strings;

import cognito_demo.services.UserInfo;
import cognito_demo.util.EmailValidator;

@Controller
public class CreateUser extends AuthenticationBase { 
    /**
     * This is the web page display method (GET only)
     * @param model
     * @return
     */
    @GetMapping("/create_user")
    public String create_user(ModelMap model) {
        return "/create_user";
    }

    /**
     * <p>
     * This method handles the data from the create user form.
     * </p>
     * 
     * @param userName
     * @param emailAddr
     * @param location
     * @param redirect
     * @return
     * 
     * <p>
     * Flash attributes:
     * </p>
     * <ul>
     * <li>userNameError</li>
     * <li>userNameVal</li>
     * <li>emailError</li>
     * <li>locationError</li>
     * <li>createUserError</li>
     * </ul>
     */
    @PostMapping("/create_user_form")
    public String new_user(@RequestParam("user_name") final String userName,
                           @RequestParam("email") final String emailAddr,
                           @RequestParam("location") final String location,
                           RedirectAttributes redirect ) {
        String newPage = "redirect:create_user";
        String userNameArg = null;
        String emailArg = null;
        boolean badArgument = false;
        // Validate the user name
        if (Strings.isNullOrEmpty(userName)) {
            redirect.addFlashAttribute("userNameError", "A user name is required");
            badArgument = true;
        } else {
            userNameArg = userName.trim();
            if (userNameArg.length() >= USER_NAME_MIN_LENGTH) {
                try {
                    if (! authService.hasUser(userNameArg)) {
                        // provide a default value for the user name so the user doesn't have to type it again when correcting errors.
                        redirect.addFlashAttribute("userNameVal", userNameArg);
                    } else {
                        redirect.addFlashAttribute("userNameError", "The user name \"" + userNameArg + "\" is already in use.");
                        badArgument = true;
                    }
                } catch (Exception e) {
                    redirect.addFlashAttribute("userNameError", "Error looking up user name: " + e.getLocalizedMessage());
                }
            } else {
                redirect.addFlashAttribute("userNameError", "User names must be at least " + USER_NAME_MIN_LENGTH + " characters");
                badArgument = true;
            }
        }
        if (! badArgument) { // user name is OK
            if (Strings.isNullOrEmpty(emailAddr)) {
                redirect.addFlashAttribute("emailError", "Please provide an email address");
                badArgument = true;
            } else {
                emailArg = emailAddr.trim();
                if (EmailValidator.isValid(emailArg)) {
                    // Check to see if the email address is already in use
                    UserInfo info = authService.findUserByEmailAddr(emailArg);
                    if (info != null) { // there is a user with that email address
                        redirect.addFlashAttribute("emailError", "That email address is already in use");
                        badArgument = true;
                    }
                } else {
                    redirect.addFlashAttribute("emailError", "Email address is not properly formed");
                    badArgument = true;
                }
            }
        }
        if (! badArgument) {
            if (Strings.isNullOrEmpty(location)) {
                redirect.addFlashAttribute("locationError", "Please provide your location");
                badArgument = true;
            }
        }
        /*
         *  If the arguments are OK, create a new user in the database.
         */
        if (! badArgument) { 
            try {
                UserInfo userInfo = new UserInfo(userNameArg, emailArg, location);
                authService.createNewUser(userInfo);
                redirect.addFlashAttribute("login_message", "Your user name is " + 
                                            userNameArg + ". Please check your email for your temporary password");
                redirect.addFlashAttribute("user_name_val", userNameArg);
                newPage = "redirect:/";
            } catch (Exception e) {
                redirect.addFlashAttribute("createUserError", "Error creating new user: " + e.getLocalizedMessage());
            }
        }
        return newPage;
    }
}
