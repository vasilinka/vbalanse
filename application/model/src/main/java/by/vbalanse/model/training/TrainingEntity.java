package by.vbalanse.model.training;

import by.vbalanse.model.common.AbstractManagedEntity;
import by.vbalanse.model.geography.CityEntity;
import by.vbalanse.model.psychologist.PsychologistEntity;
import by.vbalanse.model.storage.attachment.AttachmentImageEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Entity(name = TrainingEntity.TABLE_NAME)
public class TrainingEntity extends AbstractManagedEntity {
  public static final String TABLE_NAME = "trn_training";
  public static final String COLUMN_TITLE = "title_";
  public static final String COLUMN_DESCRIPTION = "description_";
  public static final String COLUMN_DATE_OF_START = "date_of_start_";
  public static final String COLUMN_CONTACTS = "contacts_";
  public static final String COLUMN_TIME_ORGANIZATION_TYPE = "time_organization_type_";
  public static final String COLUMN_TRAINING_TYPE = "training_type_";
  public static final String COLUMN_PRICE = "price_";
  public static final String COLUMN_HOURS_OF_TRAINING = "hours_of_training_";
  public static final String COLUMN_PERIOD_OF_MEET = "period_of_meet_";
  public static final String COLUMN_MIN_COUNT_OF_MEMBERS = "min_count_of_members_";
  public static final String COLUMN_CITY = "fk_" + CityEntity.TABLE_NAME;
  private static final String COLUMN_FK_IMAGE = "fk_" + AttachmentImageEntity.TABLE_NAME;

  @NotNull
  @Column(name = COLUMN_TITLE)
  private String title;

  @Column(name = COLUMN_DESCRIPTION)
  private String description;

  //can be nullable because some groups po mere nabora
  @Column(name = COLUMN_DATE_OF_START)
  private Date dateOfStart;

  @NotNull
  @Column(name = COLUMN_TIME_ORGANIZATION_TYPE)
  private TrainingTimeOrganizationTypeEnum timeOrganizationType;

  @NotNull
  @Enumerated(value = EnumType.STRING)
  @Column(name = COLUMN_TRAINING_TYPE)
  private TrainingTypeEnum trainingType;

  @Column(name = COLUMN_MIN_COUNT_OF_MEMBERS)
  private Integer minCountOfMembers;

  @NotNull
  @Column(name = COLUMN_CONTACTS)
  private String contacts;
  //todo: think of currency - or have several
  @Column(name = COLUMN_PRICE)
  private String price;
  //todo: think of prepared hours of training - three hours in the evening, two days a month
  @Column(name = COLUMN_HOURS_OF_TRAINING)
  private String hoursOfTraining;
  //todo: think of prepared periods - once a week, twice a week, once a month, several times a year
  @Column(name = COLUMN_PERIOD_OF_MEET)
  private String periodOfMeet;

  //@Min(1)
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinColumn(table = "authors", name = "training_id", referencedColumnName = "author_id")
  private Set<PsychologistEntity> authors;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_IMAGE)
  private AttachmentImageEntity image;

  @NotNull
  @ManyToOne
  @JoinColumn(name = COLUMN_CITY)
  private CityEntity city;

  public TrainingEntity() {
  }

  public TrainingEntity(String title, String description, Date dateOfStart, TrainingTimeOrganizationTypeEnum timeOrganizationType, TrainingTypeEnum trainingType, Integer minCountOfMembers, String contacts, String price, String hoursOfTraining, String periodOfMeet, Set<PsychologistEntity> authors, AttachmentImageEntity image, CityEntity city) {
    this.title = title;
    this.description = description;
    this.dateOfStart = dateOfStart;
    this.timeOrganizationType = timeOrganizationType;
    this.trainingType = trainingType;
    this.minCountOfMembers = minCountOfMembers;
    this.contacts = contacts;
    this.price = price;
    this.hoursOfTraining = hoursOfTraining;
    this.periodOfMeet = periodOfMeet;
    this.authors = authors;
    this.image = image;
    this.city = city;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getDateOfStart() {
    return dateOfStart;
  }

  public void setDateOfStart(Date dateOfStart) {
    this.dateOfStart = dateOfStart;
  }

  public TrainingTimeOrganizationTypeEnum getTimeOrganizationType() {
    return timeOrganizationType;
  }

  public void setTimeOrganizationType(TrainingTimeOrganizationTypeEnum code) {
    this.timeOrganizationType = code;
  }

  public TrainingTypeEnum getTrainingType() {
    return trainingType;
  }

  public void setTrainingType(TrainingTypeEnum trainingType) {
    this.trainingType = trainingType;
  }

  public Integer getMinCountOfMembers() {
    return minCountOfMembers;
  }

  public void setMinCountOfMembers(Integer minCountOfMembers) {
    this.minCountOfMembers = minCountOfMembers;
  }

  public String getContacts() {
    return contacts;
  }

  public void setContacts(String contacts) {
    this.contacts = contacts;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getHoursOfTraining() {
    return hoursOfTraining;
  }

  public void setHoursOfTraining(String hoursOfTraining) {
    this.hoursOfTraining = hoursOfTraining;
  }

  public String getPeriodOfMeet() {
    return periodOfMeet;
  }

  public void setPeriodOfMeet(String periodOfMeet) {
    this.periodOfMeet = periodOfMeet;
  }

  public Set<PsychologistEntity> getAuthors() {
    return authors;
  }

  public void setAuthors(Set<PsychologistEntity> authors) {
    this.authors = authors;
  }

  public CityEntity getCity() {
    return city;
  }

  public void setCity(CityEntity city) {
    this.city = city;
  }

  public AttachmentImageEntity getImage() {
    return image;
  }

  public void setImage(AttachmentImageEntity image) {
    this.image = image;
  }
}
