package by.vbalanse.model.storage.attachment;

import by.vbalanse.model.storage.StorageFileImageEntity;
import by.vbalanse.model.storage.StorageFileVideoEntity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@StaticMetamodel( AttachmentVideoEntity.class )
public class AttachmentVideoEntity_ extends AbstractAttachmentEntity_ {
  public static volatile SingularAttribute<AbstractAttachmentEntity, StorageFileVideoEntity> mp4VideoFile;
  public static volatile SingularAttribute<AbstractAttachmentEntity, StorageFileVideoEntity> flvVideoFile;
  public static volatile SingularAttribute<AbstractAttachmentEntity, StorageFileImageEntity> videoPreviewImageFile;
  public static volatile SingularAttribute<AbstractAttachmentEntity, Short> convertingFinished;
  public static volatile SingularAttribute<AbstractAttachmentEntity, Long> converterTaskId;
}
