package by.vbalanse.model.article;

import by.vbalanse.model.article.ArticleCategoryEntity;
import by.vbalanse.model.common.AbstractManagedEntity_;
import by.vbalanse.model.storage.attachment.AttachmentImageEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2014-08-12T19:39:08")
@StaticMetamodel(ArticleCategoryEntity.class)
public class ArticleCategoryEntity_ extends AbstractManagedEntity_ {

    public static volatile SingularAttribute<ArticleCategoryEntity, String> title;
    public static volatile SingularAttribute<ArticleCategoryEntity, ArticleCategoryEntity> category;
    public static volatile SingularAttribute<ArticleCategoryEntity, AttachmentImageEntity> image;
    public static volatile SetAttribute<ArticleCategoryEntity, ArticleCategoryEntity> subCateories;

}