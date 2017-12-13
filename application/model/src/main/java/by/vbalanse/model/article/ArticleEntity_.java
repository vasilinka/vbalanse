package by.vbalanse.model.article;

import by.vbalanse.model.article.ArticleCategoryEntity;
import by.vbalanse.model.article.TargetAuditoryEntity;
import by.vbalanse.model.common.AbstractManagedEntity_;
import by.vbalanse.model.user.UserEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2014-08-12T19:39:08")
@StaticMetamodel(ArticleEntity.class)
public class ArticleEntity_ extends AbstractManagedEntity_ {

    public static volatile SingularAttribute<ArticleEntity, UserEntity> author;
    public static volatile SingularAttribute<ArticleEntity, String> title;
    public static volatile SingularAttribute<ArticleEntity, String> text;
    public static volatile SetAttribute<ArticleEntity, ArticleCategoryEntity> categories;
    public static volatile SetAttribute<ArticleEntity, TargetAuditoryEntity> auditories;
    public static volatile SingularAttribute<ArticleEntity, Date> dateOfPublish;

}