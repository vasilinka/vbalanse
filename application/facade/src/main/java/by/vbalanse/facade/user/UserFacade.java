package by.vbalanse.facade.user;

import by.vbalanse.facade.ExpiredResetPasswordParameter;
import by.vbalanse.facade.ValidationException;
import by.vbalanse.facade.article.ImageInfo;
import by.vbalanse.model.psychologist.PsychologistEntity;
import by.vbalanse.model.storage.attachment.AttachmentImageEntity;
import by.vbalanse.model.user.UserEntity;
import by.vbalanse.model.user.UserInfoKeyValueEntity;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author mikolasusla@gmail.com
 */
public interface UserFacade {

  //todo: return dto
  public UserEntity findByEmail(String email);

  public UserEntity saveUser(UserEntity userEntity) throws NoSuchAlgorithmException, SQLException;

  PsychologistEntity savePsychologist(PsychologistEntity psychologistEntity);

  boolean saveActivate(String activationParameter) throws UserActivateWrongUrl;

  boolean saveAskToResetPassword(String email) throws ValidationException, NoSuchAlgorithmException;

  boolean saveChangePassword(String password, String token) throws ExpiredResetPasswordParameter, NoSuchAlgorithmException;

  AttachmentImageEntity saveAvatar(Long imageId);

  UserInfoKeyValueEntity saveUserMeta(String key, String value, Long id);

  List<AttachmentImageEntity> saveGallery(List<ImageInfo> imageIds);

  void removeItemGallery(Long contentId);
}
