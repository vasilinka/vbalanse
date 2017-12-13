package by.vbalanse.vaadin.component;

import by.vbalanse.vaadin.component.sample.BookExampleBundle;
import by.vbalanse.vaadin.component.sample.TableExample;
import com.github.wolfie.popupextension.PopupExtension;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.LayoutEvents;
import com.vaadin.ui.*;

public class PopupViewExample extends CustomComponent implements BookExampleBundle {
  private static final long serialVersionUID = 9106115858126838561L;

  public void init(String context) {
    VerticalLayout layout = new VerticalLayout();
    addStyleName("expandratioexample");

    if ("basic".equals(context))
      ;//basic(layout);
    else if ("composition".equals(context))
      composition(layout);
    else
      layout.addComponent(new Label("Invalid context: " + context));

    setCompositionRoot(layout);
  }

  public static String basicDescription =
      "";
  void basic(VerticalLayout rootlayout) {
    // BEGIN-EXAMPLE: layout.popupview.basic
    // END-EXAMPLE: layout.popupview.basic
  }

  void composition(VerticalLayout layout) {
    // BEGIN-EXAMPLE: layout.popupview.composition
    TextField tf = new TextField();
    layout.addComponent(tf);

    Table table = new Table(null, TableExample.generateContent());
    table.setSelectable(true);

    final PopupView popup = new PopupView("Small", table);
    layout.addComponent(popup);

    tf.addListener(new Property.ValueChangeListener() {
      private static final long serialVersionUID = -7331971790077682727L;

      public void valueChange(ValueChangeEvent event) {
        popup.setPopupVisible(true);
      }
    });
    final ClickLabel button = new ClickLabel("Click Me");
    final PopupExtension.PopupExtensionManualBundle popupExtension = PopupExtension.extendWithManualBundle(button);
    button.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
      public void layoutClick(LayoutEvents.LayoutClickEvent event) {
        popupExtension.getPopupExtension().open();
      }
    });
    layout.addComponent(button);

    Table table2 = new Table(null, TableExample.generateContent());
    table2.setSelectable(true);

    popupExtension.getPopupExtension().setContent(table2);
    popupExtension.getPopupExtension().setAnchor(Alignment.BOTTOM_RIGHT);
    popupExtension.getPopupExtension().setDirection(Alignment.BOTTOM_RIGHT);
    tf.setImmediate(true);
    // END-EXAMPLE: layout.popupview.composition
  }
}