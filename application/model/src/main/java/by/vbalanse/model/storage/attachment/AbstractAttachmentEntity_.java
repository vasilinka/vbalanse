package by.vbalanse.model.storage.attachment;

import by.vbalanse.model.common.AbstractManagedEntity_;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.StaticMetamodel;


/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@StaticMetamodel( AbstractAttachmentEntity.class )
public class AbstractAttachmentEntity_ extends AbstractManagedEntity_ {

  public static volatile SingularAttribute<AbstractAttachmentEntity, String> name;
  public static volatile SingularAttribute<AbstractAttachmentEntity, String> description;
  public static volatile SingularAttribute<AbstractAttachmentEntity, Short> thumbnailWidth;
  public static volatile SingularAttribute<AbstractAttachmentEntity, AttachmentGroupEntity> attachmentGroup;
}
