/**
 * Copyright 2009-2013 Oy Vaadin Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package by.vbalanse.vaadin;

import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
import by.vbalanse.vaadin.training.MyConverterFactory;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import net.sf.ehcache.transaction.local.TransactionListener;
import org.springframework.context.annotation.Scope;

@Theme(value = "mytheme")
public class AdminUI extends UI {

  public static final String PERSISTENCE_UNIT = "vbalansePU";

  @Override
  protected void init(VaadinRequest request) {
    SpringContextHelper springContextHelper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
    AdminView adminView = (AdminView) springContextHelper.getBean("adminView");
    //VaadinSession.getCurrent().setConverterFactory(new MyConverterFactory());
    setContent(adminView);
  }
}
