package by.vbalanse.model.user;

import by.vbalanse.model.common.AbstractManagedEntity_;
import by.vbalanse.model.user.RoleEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2014-08-12T19:39:08")
@StaticMetamodel(RoleRightEntity.class)
public class RoleRightEntity_ extends AbstractManagedEntity_ {

    public static volatile SingularAttribute<RoleRightEntity, String> codeOfRight;
    public static volatile SingularAttribute<RoleRightEntity, RoleEntity> role;
    public static volatile SingularAttribute<RoleRightEntity, String> codeOfAsset;

}