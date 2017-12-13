package by.vbalanse.rest.error;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
import java.io.IOException;
import java.sql.SQLException;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import by.vbalanse.facade.ExpiredResetPasswordParameter;
import by.vbalanse.facade.ValidationException;
import by.vbalanse.servlet.EmployeeNotFoundException;
import com.mchange.rmi.NotAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(SQLException.class)
  public @ResponseBody
  ExceptionJSONInfo handleSQLException(HttpServletRequest request, Exception ex){
    ExceptionJSONInfo response = new ExceptionJSONInfo();
    response.setStatus(415);
    response.setUrl(request.getRequestURL().toString());
    //response.setUrl(request.getRequestURL().toString());
    response.setMessage(ex.getMessage());

    return response;

//    logger.info("SQLException Occured:: URL="+request.getRequestURL());
//    return "database_error";
  }

  @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
  @RequestMapping(produces = "application/json")
  @ExceptionHandler(ValidationException.class)
  public @ResponseBody
  ValidationExceptionJSONInfo handleValidationException(HttpServletRequest request, ValidationException ex){
    logger.error("Validation handler executed");
    ValidationExceptionJSONInfo validationExceptionJSONInfo = new ValidationExceptionJSONInfo(ex.getFieldErrors());
    validationExceptionJSONInfo.setStatus(400);
    validationExceptionJSONInfo.setUrl(request.getRequestURL().toString());
    //response.setUrl(request.getRequestURL().toString());
    validationExceptionJSONInfo.setMessage(ex.getMessage());
    return validationExceptionJSONInfo;
    //returning 404 error code
  }

  @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="IOException occured")
  @ExceptionHandler(IOException.class)
  public void handleIOException(IOException ex){
    logger.error("IOException handler executed", ex);
    //returning 404 error code
  }

  @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(PersistenceException.class)
  public @ResponseBody ExceptionJSONInfo handlePersistenceException(HttpServletRequest request, PersistenceException ex){
    ExceptionJSONInfo response = new ExceptionJSONInfo();
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.setUrl(request.getRequestURL().toString());
    //response.setUrl(request.getRequestURL().toString());
    response.setMessage(ex.getClass() + ": " + ex.getMessage());

    return response;
  }


  @ExceptionHandler(EmployeeNotFoundException.class)
  public @ResponseBody
  ExceptionJSONInfo handleBadCredentialsException(HttpServletRequest request, Exception ex){

    ExceptionJSONInfo response = new ExceptionJSONInfo();
    response.setUrl(request.getRequestURL().toString());
    response.setMessage(ex.getMessage());

    return response;
  }

  @ExceptionHandler(NullPointerException.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public @ResponseBody ExceptionJSONInfo handleNullPointerException(HttpServletRequest request, Exception ex){
    ex.printStackTrace();
    ExceptionJSONInfo response = new ExceptionJSONInfo();
    response.setUrl(request.getRequestURL().toString());
    response.setMessage(ex.getMessage());
    response.setStatus(500);
    return response;
  }

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  public @ResponseBody  ExceptionJSONInfo handleOtherExceptions(HttpServletRequest request, BadCredentialsException ex) throws Exception {
    ExceptionJSONInfo response = new ExceptionJSONInfo();
    response.setUrl(request.getRequestURL().toString());
    response.setMessage("Not correct credentials");
    response.setStatus(401);
    return response;
  }

  @ExceptionHandler(NotAuthorizedException.class)
  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
  public @ResponseBody  ExceptionJSONInfo handleOtherExceptions(HttpServletRequest request, NotAuthorizedException ex) throws Exception {
    ExceptionJSONInfo response = new ExceptionJSONInfo();
    response.setUrl(request.getRequestURL().toString());
    response.setMessage("Not logged");
    response.setStatus(401);
    return response;
  }

  @ExceptionHandler(ExpiredResetPasswordParameter.class)
  @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
  public @ResponseBody  ExceptionJSONInfo handleExpireResetPasswordLink(HttpServletRequest request, ExpiredResetPasswordParameter ex) throws Exception {
    ex.printStackTrace();
    ExceptionJSONInfo response = new ExceptionJSONInfo();
    response.setUrl(request.getRequestURL().toString());
    response.setMessage("Истёк срок действия ссылки");
    response.setStatus(405);
    return response;
  }


  /*@ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public void handleOtherExceptions(HttpServletRequest request, Exception ex) throws Exception {
    logger.error(ex.getMessage());
    ExceptionJSONInfo response = new ExceptionJSONInfo();
    response.setUrl(request.getRequestURL().toString());
    response.setMessage(ex.getClass() + " thrown with message " + ex.getMessage());
    response.setStatus(401);
    return response;
  }*/
}