package by.vbalanse.facade.user;

import by.vbalanse.dao.storage.StorageFileDao;
import by.vbalanse.dao.storage.attachment.AttachmentDao;
import by.vbalanse.dao.template.TemplateDao;
import by.vbalanse.dao.user.RoleDao;
import by.vbalanse.dao.user.UserDao;
import by.vbalanse.dao.user.UserInfoKeyValueDao;
import by.vbalanse.dao.user.psychologist.PsychologistDao;
import by.vbalanse.facade.ExpiredResetPasswordParameter;
import by.vbalanse.facade.ValidationException;
import by.vbalanse.facade.article.ImageInfo;
import by.vbalanse.facade.mail.MailFacade;
import by.vbalanse.facade.security.UserDetailsImpl;
import by.vbalanse.facade.storage.StorageFileFacade;
import by.vbalanse.facade.storage.attachment.AttachmentFacade;
import by.vbalanse.model.DataLoad;
import by.vbalanse.model.common.AbstractManagedEntity;
import by.vbalanse.model.psychologist.PsychologistEntity;
import by.vbalanse.model.storage.attachment.AttachmentImageEntity;
import by.vbalanse.model.template.EmailTemplateEntity;
import by.vbalanse.model.user.RoleTypeEnum;
import by.vbalanse.model.user.UserEntity;
import by.vbalanse.model.user.UserInfoKeyValueEntity;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;

/**
 * @author mikolasusla@gmail.com
 */
@Service("userFacade")
public class UserFacadeImpl implements UserFacade {

  public static final String ACTIVATE_SUBURL = "activate";
  public static final String RESET_PASSWORD_SUBURL = "reset";
  public static final String ACTIVATE_LINK_PARAMETER = "activateLink";
  public static final String CHANGE_PASSWORD_LINK_PARAMETER = "changePassword";
  public static final short THUMBNAIL_WIDTH = (short) 300;

  @Override
  public UserEntity findByEmail(String email) {
    return userDao.getByEmail(email);
  }

  @Autowired
  private PsychologistDao psychologistDao;

  @Autowired
  private StorageFileDao storageFileDao;

  @Autowired
  private StorageFileFacade storageFileFacade;

  @Autowired
  private AttachmentFacade attachmentFacade;

  @Autowired
  private UserInfoKeyValueDao userInfoKeyValueDao;

  @Autowired
  private AttachmentDao attachmentDao;

  @Autowired
  private UserDao userDao;

  @Autowired
  private RoleDao roleDao;

  @Autowired
  private TemplateDao templateDao;

  @Autowired
  private MailFacade mailFacade;

  @Autowired
  private HttpServletRequest request;

  @Override
  public UserEntity saveUser(UserEntity userEntity) throws NoSuchAlgorithmException, SQLException {
    userEntity.setRole(roleDao.getByCode(RoleTypeEnum.ROLE_REGISTERED_USER.getCode()));

    String hash = sendActivateUserEmail(userEntity);
    userEntity.setPasswordMD5hash(getMd5Code(userEntity.getPasswordMD5hash()));
    userEntity.setConfirmed(false);
    userEntity.setConfirmLink(hash);
    userDao.save(userEntity);
    return userEntity;
  }

  private String sendActivateUserEmail(UserEntity userEntity) throws NoSuchAlgorithmException {
    EmailTemplateEntity emailTemplateEntity = templateDao.getByCode(EmailTemplateEntity.SUCCESS_REGISTER_CODE);
    String content = emailTemplateEntity.getContent();
    String subject = emailTemplateEntity.getTheme();
    Map<String, String> replaces = new HashMap<>();
    replaces.put("имя", "психолог");
    String email = userEntity.getEmail();
    replaces.put("email", email);
    String hash = getMd5CodeUser(userEntity);
    String url = request.getScheme()
        + "://"
        + request.getServerName()
        + ":"
        + request.getServerPort()
        + "/servlet/user/" + ACTIVATE_SUBURL + "?" + ACTIVATE_LINK_PARAMETER + "=" + hash;
    replaces.put("activationLink", url);
    for (String replaceFrom : replaces.keySet()) {
      content = content.replace("[[" + replaceFrom + "]]", replaces.get(replaceFrom));
    }
    mailFacade.addMailToQueue(email, subject, content, true);
    return hash;
  }

  public static Long getUserId() {
    Long userId;Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal = authentication.getPrincipal();
    if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
      //here not logged user
      return null;
    }
    UserDetailsImpl userDetails = (UserDetailsImpl) principal;
    userId = userDetails.getId();
    return userId;
  }

  private String getMd5CodeUser(UserEntity userEntity) throws NoSuchAlgorithmException {
    return getMd5Code(userEntity.getPasswordMD5hash() + userEntity.getEmail() + new Date());
  }

  @Override
  public PsychologistEntity savePsychologist(PsychologistEntity psychologistEntity) {
    UserEntity userEntity = psychologistEntity.getUserEntity();
    userEntity.setPasswordMD5hash("111");
    userEntity.setRole(roleDao.getByCode(RoleTypeEnum.ROLE_PSYCHOLOGIST.getCode()));
    userDao.save(userEntity);
    psychologistEntity.setSkype("skype");
    psychologistDao.save(psychologistEntity);
    return psychologistEntity;

  }

  @Override
  public boolean saveActivate(String activateParameter) throws UserActivateWrongUrl {
    UserEntity userEntity = userDao.getByConfirmLink(activateParameter);
    if (userEntity != null) {
      userEntity.setConfirmLink(null);
      userEntity.setConfirmed(true);
      userDao.saveOrUpdate(userEntity);
      return true;
    } else {
      return false;
    }
  }

  public static UserDetailsImpl getUserDetails() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetailsImpl) {
      return (UserDetailsImpl) principal;
    }
    return null;
  }

  @Override
  public boolean saveAskToResetPassword(String email) throws ValidationException, NoSuchAlgorithmException {
    UserEntity byEmail = null;
    try {
      byEmail = userDao.getByEmail(email);
    } catch (NoResultException ex) {
      throw new ValidationException("username", "Данный емейл не найден в базе");
    }
    if (byEmail == null) {
      throw new ValidationException("username", "Данный емейл не найден в базе");
    }
    sendChangePasswordEmail(byEmail);
    userDao.save(byEmail);
    return true;
  }

  @Override
  public boolean saveChangePassword(String password, String token) throws ExpiredResetPasswordParameter, NoSuchAlgorithmException {
    UserEntity byEmailResetLink = null;
    try {
      byEmailResetLink = userDao.getByEmailResetLink(token);
    } catch (NoResultException ex) {
      throw new ExpiredResetPasswordParameter();
    }
    byEmailResetLink.setPasswordMD5hash(getMd5Code(password));
    byEmailResetLink.setChangeEmailLink(null);
    userDao.save(byEmailResetLink);
    return true;
  }

  @Override
  public AttachmentImageEntity saveAvatar(Long imageId) {
    PsychologistEntity psychologistEntity = psychologistDao.findPsychologist(getUserId());
    AttachmentImageEntity imageEntity = attachmentFacade.saveImageAttachment(imageId, psychologistEntity.getPhoto());
    psychologistEntity.setPhoto(imageEntity);
    return imageEntity;

  }

  private void sendChangePasswordEmail(UserEntity byEmail) throws NoSuchAlgorithmException {
    String md5CodeUser = getMd5CodeUser(byEmail);
    byEmail.setChangeEmailLink(md5CodeUser);
    EmailTemplateEntity emailTemplateEntity = templateDao.getByCode(EmailTemplateEntity.PASSWORD_CHANGE_CODE);
    String content = emailTemplateEntity.getContent();
    String subject = emailTemplateEntity.getTheme();
    Map<String, String> replaces = new HashMap<>();
    replaces.put("имя", "психолог");
    String email = byEmail.getEmail();
    replaces.put("email", email);
    String url = request.getScheme()
        + "://"
        + request.getServerName()
        + ":"
        + request.getServerPort()
        + "/index.html#!/changePassword?" + CHANGE_PASSWORD_LINK_PARAMETER + "=" + md5CodeUser;
    replaces.put(DataLoad.EMAIL_CHANGE_LINK, url);
    for (String replaceFrom : replaces.keySet()) {
      content = content.replace("[[" + replaceFrom + "]]", replaces.get(replaceFrom));
    }
    mailFacade.addMailToQueue(email, subject, content, true);
    byEmail.setChangeEmailLink(md5CodeUser);
  }

  //todo: replace with md5 generation
  private String getMd5Code(String message) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    return new String(Hex.encodeHex(md.digest(message.getBytes())));

  }

  public UserInfoKeyValueEntity saveUserMeta(String key, String value, Long id) {
    return userInfoKeyValueDao.saveUserMeta(userDao.find(UserFacadeImpl.getUserId()), key, value, id);
  }

  @Override
  public List<AttachmentImageEntity> saveGallery(List<ImageInfo> imageIds) {
    PsychologistEntity psychologist = psychologistDao.findPsychologist(getUserId());
    //List<AttachmentImageEntity> galleryItems = psychologist.getGalleryItems();
    List<AttachmentImageEntity>images =  new ArrayList<>();
    for (ImageInfo imageInfo : imageIds) {
      if (imageInfo.getNewImageId() != null) {
        //save image info
        AttachmentImageEntity imageEntity = attachmentFacade.saveImageAttachment(imageInfo, null);
        psychologist.getGalleryItems().add(imageEntity);
        images.add(imageEntity);
      }
    }
    psychologistDao.saveOrUpdate(psychologist);
    return images;
  }

  @Override
  public void removeItemGallery(Long contentId) {
    PsychologistEntity psychologist = psychologistDao.findPsychologist(getUserId());
    for (AttachmentImageEntity image : psychologist.getGalleryItems()) {
      if (image.getId().equals(contentId)) {
        Set<Long> ids = new HashSet<>();
        Long id = image.getId();
        ids.add(id);
        attachmentFacade.deleteAttachments(ids);
        psychologist.getGalleryItems().remove(image);
        break;
      }
    }

    //todo: check on security delete!!!! also url security
    //attachmentDao.delete(attachmentDao.find(contentId));
  }

  private boolean contains(List<AbstractManagedEntity> entities, Long id ) {
    for (AbstractManagedEntity abstractManagedEntity : entities) {
      if (abstractManagedEntity.getId().equals(id)) {
        return true;
      }
    }
    return false;
  }

}
