package by.vbalanse.vaadin.component.attachment;

import by.vbalanse.model.storage.attachment.*;
import com.vaadin.event.LayoutEvents;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class AttachmentPreviewComponent extends CustomComponent {
  private final AbstractAttachmentEntity attachmentEntity;
  VerticalLayout containerEmbeddedResource;

  public AttachmentPreviewComponent(AbstractAttachmentEntity attachmentEntity) {
    this.attachmentEntity = attachmentEntity;
    buildMainView();
  }

  public void buildMainView() {
    containerEmbeddedResource = new VerticalLayout();
    if (attachmentEntity != null) {
      if (attachmentEntity instanceof AttachmentImageEntity) {
        AttachmentImageEntity imageEntity = (AttachmentImageEntity) attachmentEntity;
        Embedded imageComponent = new Embedded(imageEntity.getName(), new ExternalResource(AttachmentComponent.getFileUrl(imageEntity.getImageThumbnailFile())));
        containerEmbeddedResource.addComponent(imageComponent);
      } else if (attachmentEntity instanceof AttachmentVideoEntity) {
        AttachmentVideoEntity videoEntity = (AttachmentVideoEntity) attachmentEntity;
        //todo: think we need video or not - use youtube and vimeo instead
        Video videoComponent = new Video(videoEntity.getName(), new ExternalResource(AttachmentComponent.getFileUrl(videoEntity.getMp4VideoFile())));
        containerEmbeddedResource.addComponent(videoComponent);
      } else if (attachmentEntity instanceof AttachmentAudioEntity) {
        AttachmentAudioEntity audioEntity = (AttachmentAudioEntity) attachmentEntity;
        Audio audioComponent = new Audio(audioEntity.getName(), new ExternalResource(AttachmentComponent.getFileUrl(audioEntity.getAudioFile())));
        containerEmbeddedResource.addComponent(audioComponent);
      } else if (attachmentEntity instanceof AttachmentDocumentEntity) {
        AttachmentDocumentEntity documentEntity = (AttachmentDocumentEntity) attachmentEntity;
        //todo: implement document view
      } else if (attachmentEntity instanceof AttachmentFileEntity) {
        AttachmentFileEntity fileEntity = (AttachmentFileEntity) attachmentEntity;
        //todo: implement file view
      } else if (attachmentEntity instanceof AttachmentContentPageEntity) {
        AttachmentContentPageEntity contentPageEntity = (AttachmentContentPageEntity) attachmentEntity;
        //todo: implement content page view
      }
      setCompositionRoot(containerEmbeddedResource);
    }
  }

  public void addClickListener(LayoutEvents.LayoutClickListener clickListener) {
    containerEmbeddedResource.addLayoutClickListener(clickListener);

  }
}
