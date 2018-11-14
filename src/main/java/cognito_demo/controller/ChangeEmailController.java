/** \file
 * 
 * Nov 12, 2018
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

import com.google.common.base.Strings;

import cognito_demo.services.UserInfo;
import cognito_demo.util.EmailValidator;

/**
 * <h4>
 * ChangeEmailController
 * </h4>
 * <p>
 * Change the user's email address.
 * </p>
 * <p>
 * Nov 12, 2018
 * </p>
 * 
 * @author Ian Kaplan, iank@bearcave.com
 */
@Controller
public class ChangeEmailController extends AuthenticationBase {

    @GetMapping("/change_email")
    public ModelAndView changeEmailRequest(ModelMap model,  HttpServletRequest request) {
        String nextPage = "change_email";
        UserInfo info = (UserInfo)request.getSession().getAttribute(USER_SESSION_ATTR);
        if (info != null) {
            String currentEmailAddr = info.getEmailAddr();
            model.addAttribute("current_email", currentEmailAddr);
        } else {
            nextPage = "index";
        }
        return new ModelAndView(nextPage, model);
    }
    
    
    @PostMapping("/change_email_form")
    public String changeEmailRequestForm(@RequestParam("email_addr") final String newEmailAddr,
                                         RedirectAttributes redirect, 
                                         HttpServletRequest request) {
        String nextPage = "redirect:change_email";
        UserInfo info = (UserInfo)request.getSession().getAttribute(USER_SESSION_ATTR);
        if (info != null) {
            if (Strings.isNullOrEmpty(newEmailAddr)) {
                redirect.addFlashAttribute("email_addr_error", "Please provide an email address");
            } else if (EmailValidator.isValid(newEmailAddr)) {
                // Get the current email address from the Cognito database
                UserInfo cognitoInfo = authService.getUserInfo( info.getUserName() );
                if (! cognitoInfo.getEmailAddr().equals(newEmailAddr)) {
                    try {
                        authService.changeEmail(info.getUserName(), newEmailAddr);
                        UserInfo newInfo = authService.getUserInfo( info.getUserName() );
                        request.getSession().setAttribute(USER_SESSION_ATTR, newInfo);
                        nextPage = "redirect:application";
                    } catch (Exception e) {
                        redirect.addFlashAttribute("email_addr_error", e.getLocalizedMessage());
                    }
                } else {
                    redirect.addFlashAttribute("email_addr_error", "The new email address is the same as the address in the database");
                }
            } else {
                redirect.addFlashAttribute("email_addr_error", "Badly formatted email address");
            }
        } else {
            // Someone submitted a POST without being logged in, so redirect to the index page
            nextPage = "redirect:/";
        }
        return nextPage;
    }
    
}
