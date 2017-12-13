package by.vbalanse.model.storage;

import by.vbalanse.model.common.AbstractManagedEntity_;
import by.vbalanse.model.storage.attachment.AbstractAttachmentEntity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.util.Date;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@StaticMetamodel(AbstractStorageFileEntity.class)
public class AbstractStorageFileEntity_ extends AbstractManagedEntity_ {
  public static volatile SingularAttribute<AbstractAttachmentEntity, String> realFileName;
  public static volatile SingularAttribute<AbstractAttachmentEntity, String> extension;
  public static volatile SingularAttribute<AbstractAttachmentEntity, Boolean> temp;
  public static volatile SingularAttribute<AbstractAttachmentEntity, Date> dateOfLastUpdate;
  public static volatile SingularAttribute<AbstractAttachmentEntity, Date> dateOfCreate;
  public static volatile SingularAttribute<AbstractAttachmentEntity, Long> creatorId;
  public static volatile SingularAttribute<AbstractAttachmentEntity, StorageFolderEntity> folder;
}
