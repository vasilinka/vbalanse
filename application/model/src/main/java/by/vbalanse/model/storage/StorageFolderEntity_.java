package by.vbalanse.model.storage;

import by.vbalanse.model.storage.attachment.AbstractAttachmentEntity;
import by.vbalanse.model.storage.attachment.AbstractAttachmentEntity_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@StaticMetamodel(StorageFolderEntity.class)
public class StorageFolderEntity_ extends AbstractAttachmentEntity_ {
  public static volatile SingularAttribute<AbstractAttachmentEntity, String> code;
  public static volatile SingularAttribute<AbstractAttachmentEntity, String> path;
  public static volatile SingularAttribute<AbstractAttachmentEntity, Boolean> fullPath;
}
