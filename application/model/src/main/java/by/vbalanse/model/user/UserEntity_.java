package by.vbalanse.model.user;

import by.vbalanse.model.common.AbstractManagedEntity_;
import by.vbalanse.model.user.RoleEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2014-08-12T19:39:08")
@StaticMetamodel(UserEntity.class)
public class UserEntity_ extends AbstractManagedEntity_ {

    //public static volatile SingularAttribute<UserEntity, Long> id;
    public static volatile SingularAttribute<UserEntity, String> lastName;
    public static volatile SingularAttribute<UserEntity, String> email;
    public static volatile SingularAttribute<UserEntity, Date> lastActivityDate;
    public static volatile SingularAttribute<UserEntity, String> passwordMD5hash;
    public static volatile SingularAttribute<UserEntity, RoleEntity> role;
    public static volatile SingularAttribute<UserEntity, String> firstName;

}