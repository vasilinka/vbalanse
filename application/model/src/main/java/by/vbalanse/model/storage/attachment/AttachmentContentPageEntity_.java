package by.vbalanse.model.storage.attachment;

import by.vbalanse.model.storage.StorageFileArchiveEntity;
import by.vbalanse.model.storage.StorageSubfolderEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2014-08-12T19:39:08")
@StaticMetamodel(AttachmentContentPageEntity.class)
public class AttachmentContentPageEntity_ extends AbstractAttachmentEntity_ {

    public static volatile SingularAttribute<AttachmentContentPageEntity, StorageSubfolderEntity> previewFolder;
    public static volatile SingularAttribute<AttachmentContentPageEntity, StorageFileArchiveEntity> archiveFile;

}