package org.vaadin.addons.component.gwt;

import com.vaadin.server.AbstractExtension;
import com.vaadin.ui.PasswordField;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class CapsLockWarning extends AbstractExtension {
  // You could pass it in the constructor
  public CapsLockWarning(PasswordField field) {
    super.extend(field);
  }

  // Or in an extend() method
  public void extend(PasswordField field) {
    super.extend(field);
  }

  // Or with a static helper
  public static void addTo(PasswordField field) {
    new CapsLockWarning(field).extend(field);
  }
}
