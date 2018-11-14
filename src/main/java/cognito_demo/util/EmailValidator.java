/** \file
 * 
 * Oct 24, 2018
 *
 * Copyright Ian Kaplan 2018
 *
 * @author Ian Kaplan, www.bearcave.com, iank@bearcave.com
 */
package cognito_demo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h4>
 * EmailValidator
 * </h4>
 * <p>
 * Validate the syntax of an email address (of course this code cannot assure that the email address actually connects
 * to a working email account).
 * </p>
 * <p>
 * This code is based on code published on the code in this post:
 * </p>
 * <pre>
 * https://www.baeldung.com/registration-with-spring-mvc-and-spring-security
 * </pre>
 * <p>
 * The original code is copyright (c) 2017 Baeldung. The code is available under the MIT open source license
 * (see https://github.com/Baeldung/spring-security-registration/blob/master/License.md).
 * </p>
 * <p>
 * Oct 24, 2018
 * </p>
 * 
 * @author Ian Kaplan, iank@bearcave.com
 */
public class EmailValidator  {
  private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$"; 
  private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
  
  public static boolean isValid(String email) {
      Matcher matcher = pattern.matcher(email);
      return matcher.matches();
  }
  
}