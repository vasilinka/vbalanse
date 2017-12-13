package by.vbalanse.model.user;

import by.vbalanse.model.common.AbstractEntity_;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2014-08-12T19:39:08")
@StaticMetamodel(UserForgotPasswordEntity.class)
public class UserForgotPasswordEntity_ extends AbstractEntity_ {

    public static volatile SingularAttribute<UserForgotPasswordEntity, Date> dateOfCreate;
    public static volatile SingularAttribute<UserForgotPasswordEntity, Long> casUserId;
    public static volatile SingularAttribute<UserForgotPasswordEntity, String> key;

}