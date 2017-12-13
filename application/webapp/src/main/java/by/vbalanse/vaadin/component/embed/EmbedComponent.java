package by.vbalanse.vaadin.component.embed;

import by.vbalanse.model.storage.embed.EmbedHtmlEntity;
import com.vaadin.data.Property;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class EmbedComponent extends CustomField<EmbedHtmlEntity> {

  private VerticalLayout container;
  TextArea embedHtml;
  Button previewButton;
  PreviewEmbedWindow window;

  public EmbedComponent() {
    buildMainView();
  }

  private void buildMainView() {
    container = new VerticalLayout();
    embedHtml = new TextArea();
    setCaption("HTML-код видео");
    previewButton = new Button("Предпросмотр", new Button.ClickListener() {
      public void buttonClick(Button.ClickEvent event) {
        if (!embedHtml.getValue().isEmpty()) {
          if (window == null) {
            window = new PreviewEmbedWindow();
          }
          window.setEmbedValue(embedHtml.getValue());
          UI.getCurrent().addWindow(window);
        } else {
          embedHtml.setComponentError(new UserError("Заполните HTML-код видео"));
        }
      }
    });
    container.addComponent(embedHtml);
    container.addComponent(previewButton);
  }

  @Override
  protected Component initContent() {
    return container;
  }

  @Override
  public Class<? extends EmbedHtmlEntity> getType() {
    return EmbedHtmlEntity.class;
  }

  public void setValue(EmbedHtmlEntity newValue) throws ReadOnlyException,
      Converter.ConversionException {
    super.setValue(newValue);
    //markAsDirty();
    setEmbedded(newValue);
  }

  @Override
  public void setPropertyDataSource(Property newDataSource) {
    super.setPropertyDataSource(newDataSource);
    EmbedHtmlEntity value = (EmbedHtmlEntity) newDataSource.getValue();
    setEmbedded(value);
  }

  private void setEmbedded(EmbedHtmlEntity value) {
    if (value != null) {
      embedHtml.setValue(value.getEmbedHtml());
    } else {
      embedHtml.setValue("");
    }

  }

  private class PreviewEmbedWindow extends Window {
    private Label previewRegion;

    private PreviewEmbedWindow() {
      buildMainView();
    }

    private void buildMainView() {
      previewRegion = new Label("", ContentMode.HTML);
      setContent(previewRegion);
    }

    public void setEmbedValue(String embedValue) {
      previewRegion.setValue(embedValue);
    }
  }


}
