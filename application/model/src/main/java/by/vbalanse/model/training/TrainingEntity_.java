package by.vbalanse.model.training;

import by.vbalanse.model.common.AbstractManagedEntity_;
import by.vbalanse.model.geography.CityEntity;
import by.vbalanse.model.psychologist.PsychologistEntity;
import by.vbalanse.model.storage.attachment.AttachmentImageEntity;
import by.vbalanse.model.training.TrainingTimeOrganizationTypeEnum;
import by.vbalanse.model.training.TrainingTypeEnum;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2014-08-12T19:39:07")
@StaticMetamodel(TrainingEntity.class)
public class TrainingEntity_ extends AbstractManagedEntity_ {

    public static volatile SingularAttribute<TrainingEntity, String> hoursOfTraining;
    public static volatile SetAttribute<TrainingEntity, PsychologistEntity> authors;
    public static volatile SingularAttribute<TrainingEntity, String> title;
    public static volatile SingularAttribute<TrainingEntity, String> price;
    public static volatile SingularAttribute<TrainingEntity, TrainingTypeEnum> trainingType;
    public static volatile SingularAttribute<TrainingEntity, Date> dateOfStart;
    public static volatile SingularAttribute<TrainingEntity, String> description;
    public static volatile SingularAttribute<TrainingEntity, AttachmentImageEntity> image;
    public static volatile SingularAttribute<TrainingEntity, String> periodOfMeet;
    public static volatile SingularAttribute<TrainingEntity, Integer> minCountOfMembers;
    public static volatile SingularAttribute<TrainingEntity, TrainingTimeOrganizationTypeEnum> timeOrganizationType;
    public static volatile SingularAttribute<TrainingEntity, String> contacts;
    public static volatile SingularAttribute<TrainingEntity, CityEntity> city;

}