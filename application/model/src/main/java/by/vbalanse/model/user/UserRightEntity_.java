package by.vbalanse.model.user;

import by.vbalanse.model.common.AbstractManagedEntity_;
import by.vbalanse.model.user.UserEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2014-08-12T19:39:07")
@StaticMetamodel(UserRightEntity.class)
public class UserRightEntity_ extends AbstractManagedEntity_ {

    public static volatile SingularAttribute<UserRightEntity, String> codeOfRight;
    public static volatile SingularAttribute<UserRightEntity, Boolean> allow;
    public static volatile SingularAttribute<UserRightEntity, UserEntity> user;
    public static volatile SingularAttribute<UserRightEntity, String> codeOfAsset;

}