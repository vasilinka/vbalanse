package by.vbalanse.rest;

import by.vbalanse.rest.error.ExceptionJSONInfo;
import by.vbalanse.servlet.EmployeeNotFoundException;
import by.vbalanse.servlet.TokenTransfer;
import by.vbalanse.servlet.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Controller
@Path("/user")
public class UserService2 {
  @Autowired
  private UserDetailsService userService;

  @Autowired
  @Qualifier("authenticationManager")
  private AuthenticationManager authManager;


  /**
   * Retrieves the currently logged in user.
   *
   * @return A transfer containing the username and the roles.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public UserTransfer getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal = authentication.getPrincipal();
    if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
      throw new WebApplicationException(401);
    }
    UserDetails userDetails = (UserDetails) principal;

    return new UserTransfer(userDetails.getUsername(), this.createRoleMap(userDetails));
  }


  /**
   * Authenticates a user and creates an authentication token.
   *
   * @param username The name of the user.
   * @param password The password of the user.
   * @return A transfer containing the authentication token.
   */
  @Path("authenticate")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public
  @ResponseBody
  TokenTransfer authenticate(@FormParam(value = "username") String username, @FormParam(value = "password") String password) throws EmployeeNotFoundException {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(username, password);
    try {
      Authentication authentication = this.authManager.authenticate(authenticationToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (BadCredentialsException e) {
      //throw new WebApplicationException(e, 402);
      throw new EmployeeNotFoundException(1);
    }

		/*
     * Reload user as password of authentication principal will be null after authorization and
		 * password is needed for token generation
		 */
    UserDetails userDetails = this.userService.loadUserByUsername(username);

    return new TokenTransfer(TokenUtils.createToken(userDetails));
  }

  //@Path(value = "exception1")
  @RequestMapping(value = "exception1")
  public String exception1()
  {
    throw new NullPointerException("Exception1 as plain text with <strong>html</strong> tags");
  }


  private Map<String, Boolean> createRoleMap(UserDetails userDetails) {
    Map<String, Boolean> roles = new HashMap<String, Boolean>();
    for (GrantedAuthority authority : userDetails.getAuthorities()) {
      roles.put(authority.getAuthority(), Boolean.TRUE);
    }

    return roles;
  }

  @ExceptionHandler(NullPointerException.class)
  public @ResponseBody
  ExceptionJSONInfo handleBadCredentialsException(HttpServletRequest request, Exception ex){

    ExceptionJSONInfo response = new ExceptionJSONInfo();
    response.setUrl(request.getRequestURL().toString());
    response.setMessage(ex.getMessage());
    response.setStatus(401);

    return response;
  }

}
