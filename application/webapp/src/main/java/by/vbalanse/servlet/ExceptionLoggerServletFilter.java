package by.vbalanse.servlet;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class ExceptionLoggerServletFilter implements Filter {

  //private CommonAccessLogFormatter requestFormat = new CommonAccessLogFormatter();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
      ServletException {
    try {
      chain.doFilter(request, response);
    } catch (Exception e) {
      StringBuilder sb = new StringBuilder();
      sb.append("Very Bad shit has happened:");
//      if (request instanceof HttpServletRequest)
//        requestFormat.appendLogEntry((HttpServletRequest) request, sb);
      log.error(sb.toString(), e);
      throw new ServletException(e);
    }
  }

  @Override
  public void destroy() {
  }


  private static final Logger log = LoggerFactory.getLogger(ExceptionLoggerServletFilter.class);

}
