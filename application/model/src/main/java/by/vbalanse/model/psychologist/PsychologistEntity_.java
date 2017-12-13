package by.vbalanse.model.psychologist;

import by.vbalanse.model.common.AbstractManagedEntity_;
import by.vbalanse.model.storage.attachment.AttachmentImageEntity;
import by.vbalanse.model.storage.attachment.AttachmentVideoEntity;
import by.vbalanse.model.storage.embed.EmbedHtmlEntity;
import by.vbalanse.model.user.UserEntity;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.2.0.v20110202-r8913", date="2014-08-12T19:39:08")
@StaticMetamodel(PsychologistEntity.class)
public class PsychologistEntity_ extends AbstractManagedEntity_ {

  public static volatile SingularAttribute<PsychologistEntity, Date> dateOfBirth;
  public static volatile SingularAttribute<PsychologistEntity, UserEntity> userEntity;
  public static volatile SingularAttribute<PsychologistEntity, String> phone;
  public static volatile SingularAttribute<PsychologistEntity, String> university;
  public static volatile SingularAttribute<PsychologistEntity, Boolean> universityApproved;
  public static volatile SingularAttribute<PsychologistEntity, Integer> hoursOfGroupTherapy;
  public static volatile SingularAttribute<PsychologistEntity, String> education;
  public static volatile SingularAttribute<PsychologistEntity, AttachmentImageEntity> photo;
  public static volatile SingularAttribute<PsychologistEntity, EmbedHtmlEntity> embedVideo;
  public static volatile SetAttribute<PsychologistEntity, WithWhomWorks> personalTherapyWays;
  public static volatile SingularAttribute<PsychologistEntity, Integer> hoursPersonalTherapy;
  public static volatile SingularAttribute<PsychologistEntity, String> siteUrl;
  public static volatile SingularAttribute<PsychologistEntity, AttachmentVideoEntity> video;
  public static volatile SingularAttribute<PsychologistEntity, String> skype;
  public static volatile SingularAttribute<PsychologistEntity, Integer> hoursOfPractice;

}