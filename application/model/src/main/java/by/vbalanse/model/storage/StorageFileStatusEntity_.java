package by.vbalanse.model.storage;

import by.vbalanse.model.common.AbstractManagedEntity_;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2014-08-12T19:39:08")
@StaticMetamodel(StorageFileStatusEntity.class)
public class StorageFileStatusEntity_ extends AbstractManagedEntity_ {

    public static volatile SingularAttribute<StorageFileStatusEntity, Boolean> storageFileAbstractOnly;
    public static volatile SingularAttribute<StorageFileStatusEntity, Boolean> notTempStorageFileWithNoParent;
    public static volatile SingularAttribute<StorageFileStatusEntity, Boolean> attachmentWithTempStorageFile;

}