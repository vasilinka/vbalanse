package by.vbalanse.vaadin.portal.component;

import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.annotation.PostConstruct;
import java.util.Collection;

/**
 * @author mikolasusla@gmail.com
 */
@org.springframework.stereotype.Component(value = "loginPageView")
@Scope("prototype")
public class LoginPageView extends CustomComponent implements Button.ClickListener {

  private TextField userName;
  private Label errorLabel;
  private PasswordField password;
  private Button loginButton;

  @Autowired
  private AuthenticationManager authenticationManager;

  @PostConstruct
  private void postConstruct() {
    setSizeFull();

    // Create the user input field
    userName = new TextField("User:");
    userName.setWidth("300px");
    userName.setRequired(true);
    userName.setInputPrompt("Your username");
    userName.addValidator(new EmailValidator("Username must be an email address"));
    userName.setInvalidAllowed(false);

    // Create the password input field
    password = new PasswordField("Password:");
    password.setWidth("300px");
    password.addValidator(new PasswordValidator());
    password.setRequired(true);
    password.setValue("");
    password.setNullRepresentation("");

    // Create login button
    loginButton = new Button("Login", this);

    errorLabel = new Label();
    errorLabel.setStyleName("error");
    errorLabel.setVisible(false);

    // Add both to a panel
    VerticalLayout fields = new VerticalLayout(errorLabel, userName, password, loginButton);
    fields.setCaption("Please login");
    fields.setSpacing(true);
    fields.setMargin(new MarginInfo(true, true, true, false));
    fields.setSizeUndefined();

    // The view root layout
    VerticalLayout viewLayout = new VerticalLayout(fields);
    viewLayout.setSizeFull();
    viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
    viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
    setCompositionRoot(viewLayout);
  }

  private static final class PasswordValidator extends AbstractValidator<String> {

    public PasswordValidator() {
      super("The password provided is not valid");
    }

    @Override
    protected boolean isValidValue(String value) {
      // Password must be at least 5 characters long
      return !(value != null && (value.length() < 5));
    }

    @Override
    public Class<String> getType() {
      return String.class;
    }
  }

  @Override
  public void buttonClick(Button.ClickEvent event) {
    errorLabel.setVisible(false);
    if (!userName.isValid() || !password.isValid()) {
      return;
    }

    String username = userName.getValue();
    String password = this.password.getValue();
    Authentication authentication = null;
    try {
      UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
      authentication = getAuthenticationManager().authenticate(authRequest);
    } catch (Exception e) {
      if (e instanceof BadCredentialsException) {
        return;
      }
      if (e instanceof AuthenticationServiceException) {
        if (e.getMessage().equals("No entity found for query")) {
          showError("User not found");
        }
        return;
      }
      e.printStackTrace();
      return;
    }
    if (authentication == null) {
      return;
    }
    SecurityContextHolder.getContext().setAuthentication(authentication);
    // Store the current user in the service session
    DefaultSavedRequest savedRequest = (DefaultSavedRequest) getSession().getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");

    // Navigate to main view
    if (savedRequest != null) {
      getUI().getPage().setLocation(savedRequest.getRequestURI());
    } else {
//      SpringContextHelper springContextHelper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
//      springContextHelper.getBean("")
      Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
      while (authorities.iterator().hasNext()) {
        GrantedAuthority next = authorities.iterator().next();
        if (next.getAuthority().equals("P")) {
          //break;
          getUI().getPage().setLocation("/#!/article");
          break;
        } else if (next.getAuthority().equals("ROLE_A")) {
          getUI().getPage().setLocation("/admin");
          break;
        }
      }
      //if (authentication.getAuthorities())
    }
//        getUI().getNavigator().navigateTo(savedRequest.getRequestURI());
  }

  private void showError(String message) {
    errorLabel.setVisible(true);
    errorLabel.setValue(message);
  }

  private AuthenticationManager getAuthenticationManager() {
    return authenticationManager;
  }
}
