package by.vbalanse.vaadin.component.attachment;

import by.vbalanse.model.storage.*;
import by.vbalanse.model.storage.attachment.*;
import com.vaadin.event.LayoutEvents;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class StoragePreviewComponent extends CustomComponent {
  private AbstractStorageFileEntity storageFileEntity;
  VerticalLayout containerEmbeddedResource;

  public StoragePreviewComponent(AbstractStorageFileEntity storageFileEntity) {
    this.storageFileEntity = storageFileEntity;
    containerEmbeddedResource = new VerticalLayout();
    setCompositionRoot(containerEmbeddedResource);
    buildMainView();
  }

  public void buildMainView() {
    containerEmbeddedResource.removeAllComponents();
    if (storageFileEntity != null) {
      if (storageFileEntity instanceof StorageFileImageEntity) {
        StorageFileImageEntity imageEntity = (StorageFileImageEntity) storageFileEntity;
        Embedded imageComponent = new Embedded(imageEntity.getFileName(), new ExternalResource(AttachmentComponent.getFileUrl(imageEntity)));
        containerEmbeddedResource.addComponent(imageComponent);
      } else if (storageFileEntity instanceof StorageFileVideoEntity) {
        StorageFileVideoEntity videoEntity = (StorageFileVideoEntity) storageFileEntity;
        //todo: think we need video or not - use youtube and vimeo instead
        Video videoComponent = new Video(videoEntity.getFileName(), new ExternalResource(AttachmentComponent.getFileUrl(videoEntity)));
        containerEmbeddedResource.addComponent(videoComponent);
      } else if (storageFileEntity instanceof StorageFileAudioEntity) {
        StorageFileAudioEntity audioEntity = (StorageFileAudioEntity) storageFileEntity;
        Audio audioComponent = new Audio(audioEntity.getFileName(), new ExternalResource(AttachmentComponent.getFileUrl(audioEntity)));
        containerEmbeddedResource.addComponent(audioComponent);
      } else if (storageFileEntity instanceof StorageFileArchiveEntity) {
        StorageFileArchiveEntity documentEntity = (StorageFileArchiveEntity) storageFileEntity;
        //todo: implement document view
      }
    }
  }

  public void addClickListener(LayoutEvents.LayoutClickListener clickListener) {
    containerEmbeddedResource.addLayoutClickListener(clickListener);
  }

  public void setStorageFileEntity(AbstractStorageFileEntity storageFileEntity) {
    this.storageFileEntity = storageFileEntity;
    buildMainView();
  }
}
