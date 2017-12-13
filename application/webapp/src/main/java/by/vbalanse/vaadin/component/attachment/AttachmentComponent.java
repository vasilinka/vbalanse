package by.vbalanse.vaadin.component.attachment;

import by.vbalanse.dao.storage.attachment.AttachmentDao;
import by.vbalanse.facade.storage.attachment.AttachmentFacade;
import by.vbalanse.facade.storage.attachment.AttachmentParamsImpl;
import by.vbalanse.model.storage.AbstractStorageFileEntity;
import by.vbalanse.model.storage.attachment.AbstractAttachmentEntity;
import by.vbalanse.model.storage.attachment.AttachmentImageEntity;
import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
import com.vaadin.data.Property;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.event.LayoutEvents;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 *         todo: make policy for properties change
 */
public class AttachmentComponent extends CustomField<AbstractAttachmentEntity> implements Field<AbstractAttachmentEntity> {

  private VerticalLayout container;
  private AbstractAttachmentEntity attachmentEntity;
  private AttachmentPreviewComponent attachmentPreviewComponent;
  VerticalLayout currentAttachmentLayout;
  EditAttachmentWindow editAttachmentWindow = new EditAttachmentWindow();
  SpringContextHelper helper;

  public AttachmentComponent(AbstractAttachmentEntity attachmentEntity) {
    this.attachmentEntity = attachmentEntity;
    buildMainView();
  }

  public void setAttachmentTypes(List<AttachmentType> attachmentTypes) {

  }

  public void setAttachmentType(AttachmentType attachmentType) {
    //todo: support of only image or make one more component to do that
  }

  private void buildMainView() {
    addAttachListener(new AttachListener() {
      public void attach(AttachEvent event) {
        ServletContext servletContext = VaadinServlet.getCurrent().getServletContext();
        helper = new SpringContextHelper(servletContext);
      }
    });
    container = new VerticalLayout();
    currentAttachmentLayout = new VerticalLayout();
    setCaption("Закачать приложение");
    container.addComponent(currentAttachmentLayout);
    changeAttachment();
  }

  private void changeAttachment() {
    currentAttachmentLayout.removeAllComponents();
    if (attachmentEntity != null) {
      AttachmentPreviewComponent previewAttachment = new AttachmentPreviewComponent(attachmentEntity);
      currentAttachmentLayout.addComponent(previewAttachment);
    } else {
      currentAttachmentLayout.addComponent(new Label("Добавить приложение"));
    }
    currentAttachmentLayout.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
      public void layoutClick(LayoutEvents.LayoutClickEvent event) {
        if (editAttachmentWindow.isAttached()) {
          editAttachmentWindow.close();
        }
        UI.getCurrent().addWindow(editAttachmentWindow);
      }
    });
  }

  public void setAttachment(AbstractAttachmentEntity attachmentEntity) {
    this.attachmentEntity = attachmentEntity;
    changeAttachment();
  }

  public AbstractAttachmentEntity getAttachment() {
    return attachmentEntity;
  }

  public static String getFileUrl(AbstractStorageFileEntity file) {
    //return "../" + file.getFolder().getPath() + '/' + file.getFileName();
    return "/" + file.getFolder().getPath() + '/' + file.getFileName();
  }

  @Override
  protected Component initContent() {
    return container;
  }

  @Override
  public Class<? extends AbstractAttachmentEntity> getType() {
    return AbstractAttachmentEntity.class;
  }

  private class EditAttachmentWindow extends Window {
    private StoragePreviewComponent storagePreviewComponent;
    private UploadComponent uploadComponent;
    public boolean attachmentChanged;

    private EditAttachmentWindow(/*AbstractAttachmentEntity attachmentEntity1*/) {
      VerticalLayout components = new VerticalLayout();
      setCaption("Редактировать " + ((attachmentEntity != null) ? attachmentEntity.getName() : " новое прилжожение"));
      HorizontalLayout buttonsPanel = new HorizontalLayout();
      buttonsPanel.addComponent(new Button("Сохранить", new Button.ClickListener() {

        public void buttonClick(Button.ClickEvent event) {
          AttachmentFacade attachmentFacade = (AttachmentFacade) helper.getBean("attachmentFacade");
          AttachmentDao attachmentDao = (AttachmentDao) helper.getBean("attachmentDao");

          AbstractStorageFileEntity storageFileEntity = uploadComponent.getStorageFileEntity();
          if (attachmentEntity != null) {
            attachmentDao.delete(attachmentEntity);
          }
          //todo: normal error handling

          attachmentEntity = attachmentFacade.saveAttachment(
              new AttachmentParamsImpl(storageFileEntity.getFileName(), "", storageFileEntity.getId(), AttachmentImageEntity.DEFAULT_THUMBNAIL_IMAGE_WIDTH), AbstractStorageFileEntity.IMAGE_FOLDER);
          setValue(attachmentEntity);
          close();
        }
      }));
      buttonsPanel.addComponent(new Button("Отменить", new Button.ClickListener() {
        public void buttonClick(Button.ClickEvent event) {
          close();
        }
      }));
      uploadComponent = new UploadComponent();
      uploadComponent.addValueChangeHandler(new UploadComponent.UploadFinishedListener() {
        public void onUpload(AbstractStorageFileEntity storageFileEntity) {
          storagePreviewComponent.setStorageFileEntity(storageFileEntity);
        }
      });
      storagePreviewComponent = new StoragePreviewComponent(null);
      components.addComponent(buttonsPanel);
      components.addComponent(storagePreviewComponent);
      components.addComponent(uploadComponent);
      setContent(components);
    }

    public AbstractStorageFileEntity getValue() {
      if (attachmentChanged) {
        return uploadComponent.getStorageFileEntity();
      } else {
        return null;
      }
    }
  }

  public void setValue(AbstractAttachmentEntity newValue) throws ReadOnlyException,
      Converter.ConversionException {
    super.setValue(newValue);
    //markAsDirty();
    setAttachment(newValue);
  }

  @Override
  public void setPropertyDataSource(Property newDataSource) {
    super.setPropertyDataSource(newDataSource);
    AbstractAttachmentEntity value = (AbstractAttachmentEntity) newDataSource.getValue();
    setAttachment(value);
  }


}
