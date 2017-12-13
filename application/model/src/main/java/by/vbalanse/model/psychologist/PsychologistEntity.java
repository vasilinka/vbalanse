package by.vbalanse.model.psychologist;

import by.vbalanse.model.common.AbstractManagedEntity;
import by.vbalanse.model.storage.attachment.AttachmentImageEntity;
import by.vbalanse.model.storage.attachment.AttachmentVideoEntity;
import by.vbalanse.model.storage.embed.EmbedHtmlEntity;
import by.vbalanse.model.user.UserEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Entity
@Table(
    name = PsychologistEntity.TABLE_NAME
)
public class PsychologistEntity extends AbstractManagedEntity {

  public static final String TABLE_NAME = "scrt_psychologist";

  private static final String COLUMN_FK_USER = UserEntity.TABLE_NAME + "$" + "user";
  private static final String COLUMN_PHONE = "phone_";
  private static final String COLUMN_SITE_URL = "site_url_";
  private static final String COLUMN_SKYPE = "skype_";
  private static final String COLUMN_DATE_OF_BIRTH = "date_of_birth_";
  private static final String COLUMN_DIRTY = "dirty_";
  private static final String COLUMN_FK_IMAGE_PHOTO = AttachmentImageEntity.TABLE_NAME + "$" + "photo";
  private static final String COLUMN_FK_VIDEO = AttachmentVideoEntity.TABLE_NAME + "$" + "video";
  private static final String COLUMN_FK_EMBED_VIDEO = EmbedHtmlEntity.TABLE_NAME + "$" + "embed_video";
  private static final String COLUMN_EDUCATION = "education_";
  private static final String COLUMN_UNIVERSITY = "university_";
  private static final String COLUMN_UNIVERSITY_APPROVED = "university_approved_";
  private static final String COLUMN_COUNT_HOURS_PERSONAL_THERAPY = "hours_personal_therapy_";
  private static final String COLUMN_COUNT_HOURS_GROUP_THERAPY = "hours_group_therapy_";
  private static final String COLUMN_COUNT_HOURS_PRACTICE = "hours_practice_";

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_USER)
  private UserEntity userEntity;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_IMAGE_PHOTO)
  private AttachmentImageEntity photo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_VIDEO)
  private AttachmentVideoEntity video;

  @OneToOne
  @JoinColumn(name = TABLE_NAME + DELIMITER_INDEX + COLUMN_FK_EMBED_VIDEO)
  private EmbedHtmlEntity embedVideo;

  @Column(name = COLUMN_PHONE)
  private String phone;

  //to think do we need to give go from us/????
  @Column(name = COLUMN_SITE_URL)
  private String siteUrl;

  @Column(name = COLUMN_EDUCATION)
  private String education;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinColumn(table = "psy_gallery_items", name = "image_attachment_id", referencedColumnName = "psychologist_id")
  private List<AttachmentImageEntity> galleryItems;

  @Column(name = COLUMN_UNIVERSITY)
  private String university;

  //????? do we need other social networks
  @NotNull
  @Column(name = COLUMN_SKYPE)
  private String skype;

  @Column(name = COLUMN_DATE_OF_BIRTH)
  private Date dateOfBirth;

  @Column(name = COLUMN_DIRTY)
  //difficult conditions influences this field, only when all data for user set this field is set
  private boolean dirty;

  //todo make university list???
  @Column(name = COLUMN_COUNT_HOURS_PERSONAL_THERAPY)
  private Integer hoursPersonalTherapy;

  //separate personal and with clients??? should it be shown???
  @Column(name = COLUMN_COUNT_HOURS_PRACTICE)
  private Integer hoursOfPractice;

  @Column(name = COLUMN_COUNT_HOURS_GROUP_THERAPY)
  private Integer hoursOfGroupTherapy;

  @Column(name = COLUMN_UNIVERSITY_APPROVED, columnDefinition = "BIT", length = 1)
  private boolean universityApproved;

  @ManyToMany
  @JoinColumn(table = "psy_psychologist_way", name = "way_id", referencedColumnName = "psychologist_id")
  private Set<WithWhomWorks> withWhomWorkses;

  //todo: make storage entities
  //private video entity
  //private image entity

  public PsychologistEntity() {
  }

  public UserEntity getUserEntity() {
    return userEntity;
  }

  public void setUserEntity(UserEntity userEntity) {
    this.userEntity = userEntity;
  }

  public AttachmentImageEntity getPhoto() {
    return photo;
  }

  public void setPhoto(AttachmentImageEntity photo) {
    this.photo = photo;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getSiteUrl() {
    return siteUrl;
  }

  public void setSiteUrl(String siteUrl) {
    this.siteUrl = siteUrl;
  }

  public String getEducation() {
    return education;
  }

  public void setEducation(String education) {
    this.education = education;
  }

  public String getUniversity() {
    return university;
  }

  public void setUniversity(String university) {
    this.university = university;
  }

  public String getSkype() {
    return skype;
  }

  public void setSkype(String skype) {
    this.skype = skype;
  }

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public Integer getHoursPersonalTherapy() {
    return hoursPersonalTherapy;
  }

  public void setHoursPersonalTherapy(Integer hoursPersonalTherapy) {
    this.hoursPersonalTherapy = hoursPersonalTherapy;
  }

  public Integer getHoursOfPractice() {
    return hoursOfPractice;
  }

  public void setHoursOfPractice(Integer hoursOfPractice) {
    this.hoursOfPractice = hoursOfPractice;
  }

  public Integer getHoursOfGroupTherapy() {
    return hoursOfGroupTherapy;
  }

  public void setHoursOfGroupTherapy(int hoursOfGroupTherapy) {
    this.hoursOfGroupTherapy = hoursOfGroupTherapy;
  }

  public boolean isUniversityApproved() {
    return universityApproved;
  }

  public void setUniversityApproved(boolean universityApproved) {
    this.universityApproved = universityApproved;
  }

  public Set<WithWhomWorks> getWithWhomWorkses() {
    return withWhomWorkses;
  }

  public void setWithWhomWorkses(Set<WithWhomWorks> withWhomWorkses) {
    this.withWhomWorkses = withWhomWorkses;
  }

  public AttachmentVideoEntity getVideo() {
    return video;
  }

  public void setVideo(AttachmentVideoEntity video) {
    this.video = video;
  }

  public EmbedHtmlEntity getEmbedVideo() {
    return embedVideo;
  }

  public void setEmbedVideo(EmbedHtmlEntity embedVideo) {
    this.embedVideo = embedVideo;
  }

  @Override
  public String toString() {
    return userEntity.getFirstLastName();
  }

  public List<AttachmentImageEntity> getGalleryItems() {
    return galleryItems;
  }

  public void setGalleryItems(List<AttachmentImageEntity> galleryItems) {
    this.galleryItems = galleryItems;
  }

  public boolean isDirty() {
    return dirty;
  }

  public void setDirty(boolean dirty) {
    this.dirty = dirty;
  }
}
