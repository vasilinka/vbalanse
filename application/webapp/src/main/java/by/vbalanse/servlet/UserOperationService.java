package by.vbalanse.servlet;

import by.vbalanse.facade.ValidationException;
import by.vbalanse.facade.user.UserActivateWrongUrl;
import by.vbalanse.facade.user.UserFacade;
import by.vbalanse.facade.user.UserFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Controller
@RequestMapping("/user")
public class UserOperationService {

  @Autowired
  private UserFacade userFacade;

  @RequestMapping(value = "activate", method = RequestMethod.GET)
  public String activate(HttpServletRequest request, HttpServletResponse response/*, Model model*/) throws UserActivateWrongUrl {
    String activateParameter = request.getParameter(UserFacadeImpl.ACTIVATE_LINK_PARAMETER);
    if (activateParameter == null) {
      throw new UserActivateWrongUrl("Не указан параметр активации");
    }
    boolean activate = userFacade.saveActivate(activateParameter);
    if (activate) {
      request.setAttribute("message", "Вы успешно активированы.");
    } else {
      request.setAttribute("message", "Возможно, вы были активированы раньше.");
    }
    return "activationResult";
  }

}
