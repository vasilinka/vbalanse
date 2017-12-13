package by.vbalanse.servlet;

import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class MyNewFilter extends GenericFilterBean {
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (((HttpServletRequest) request).getRequestURI().contains("login.html")) {
      DefaultSavedRequest savedRequest = new DefaultSavedRequest((HttpServletRequest) request, new PortResolverImpl());
      savedRequest.getParameterMap().remove("username");
      savedRequest.getParameterMap().remove("password");

      ((HttpServletRequest) request).getSession().setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequest);
    }
    chain.doFilter(request, response);
  }
}
