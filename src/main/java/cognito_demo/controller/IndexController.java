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
import org.springframework.web.servlet.ModelAndView;

import cognito_demo.services.UserInfo;

@Controller
public class IndexController extends AuthenticationBase {

    /**
     * <p>
     * Main index.html page. This page has the login dialog, which is processed (via a post) by the
     * LoginController
     * </p>
     * 
     * @param model
     * @return
     */
    @GetMapping("/")
    public ModelAndView index(ModelMap model, HttpServletRequest request ) {
        String nextPage = "index";
        UserInfo info = (UserInfo)request.getSession().getAttribute(USER_SESSION_ATTR);
        if (info != null) {
            // the user is already logged in, so redirect to the application page.
            nextPage = "application";
            model.addAttribute(USER_SESSION_ATTR, info);
        }
        return new ModelAndView(nextPage, model); 
    }
}
