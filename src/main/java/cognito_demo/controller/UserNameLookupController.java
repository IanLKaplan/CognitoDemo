/** \file
 * 
 * Nov 14, 2018
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

import com.google.common.base.Strings;

import cognito_demo.services.UserInfo;
import cognito_demo.util.EmailValidator;

@Controller
public class UserNameLookupController extends AuthenticationBase {

    @GetMapping("username_lookup")
    public String userNameLookup(Model model) {
        String nextPage = "username_lookup";
        // nothing happening here...
        return nextPage;
    }
    
    
    @PostMapping("username_lookup_form")
    public String userNameLookupForm(@RequestParam("email_address") String emailAddr,
                                                   RedirectAttributes redirect) {
        String nextPage = "redirect:username_lookup";
        if (Strings.isNullOrEmpty(emailAddr)) {
            redirect.addFlashAttribute("email_error", "Please enter your email address");
        } else {
            if (EmailValidator.isValid(emailAddr)) {
                try {
                  UserInfo info = authService.findUserByEmailAddr(emailAddr);  
                  if (info != null) {
                      String userName = info.getUserName();
                      if (userName != null) {
                          redirect.addFlashAttribute("user_name_val", userName);
                          redirect.addFlashAttribute("login_message", "Your user name is " + userName );
                          nextPage = "redirect:/";
                      } else {
                          redirect.addFlashAttribute("email_error", "The user name lookup was unsuccessful (returned null)");
                      }
                  } else {
                      redirect.addFlashAttribute("email_error", "No user has been found with that email address");
                  }
                } catch (Exception e) {
                    redirect.addFlashAttribute("email_error", e.getLocalizedMessage());
                }
            } else {
                redirect.addFlashAttribute("email_error", "There is a problem with the format of the email address");
            }
        }
        return nextPage;
    }
}
