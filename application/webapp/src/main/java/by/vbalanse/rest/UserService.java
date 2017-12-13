package by.vbalanse.rest;

import by.vbalanse.convert.Transferer;
import by.vbalanse.dao.psychologist.BonusDao;
import by.vbalanse.dao.user.RoleDao;
import by.vbalanse.dao.user.UserDao;
import by.vbalanse.dao.user.UserInfoKeyValueDao;
import by.vbalanse.dao.user.psychologist.PsychologistDao;
import by.vbalanse.facade.ExpiredResetPasswordParameter;
import by.vbalanse.facade.ValidationException;
import by.vbalanse.facade.article.ArticleFacade;
import by.vbalanse.facade.article.ArticleInfo;
import by.vbalanse.facade.article.BonusInfo;
import by.vbalanse.facade.article.ImageInfo;
import by.vbalanse.facade.storage.StorageFileFacade;
import by.vbalanse.facade.user.UserFacade;
import by.vbalanse.facade.user.UserFacadeImpl;
import by.vbalanse.model.article.ArticleEntity;
import by.vbalanse.model.psychologist.BonusEntity;
import by.vbalanse.model.psychologist.PsychologistEntity;
import by.vbalanse.model.storage.attachment.AttachmentImageEntity;
import by.vbalanse.model.user.UserEntity;
import by.vbalanse.model.user.UserInfoKeyValueEntity;
import by.vbalanse.servlet.TokenTransfer;
import by.vbalanse.servlet.TokenUtils;
import by.vbalanse.rest.error.ValidationExceptionJSONInfo;
import com.mchange.rmi.NotAuthorizedException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Controller
@RequestMapping("/psy/user")
public class UserService {
  @Autowired
  private UserDetailsService userService;

  @Autowired
  private StorageFileFacade storageFileFacade;


  @Autowired
  @Qualifier("authenticationManager")
  private AuthenticationManager authManager;

  @Autowired
  private UserFacade userFacade;

  @Autowired
  private PsychologistDao psychologistDao;

  @Autowired
  private BonusDao bonusDao;

  @Autowired
  private UserDao userDao;

  @Autowired
  private RoleDao roleDao;

  @Autowired
  private ArticleFacade articleFacade;

  @Autowired
  private Transferer transferer;

  @Autowired
  UserInfoKeyValueDao userInfoKeyValueDao;

  /**
   * Retrieves the currently logged in user.
   *
   * @return A transfer containing the username and the roles.
   */

  @RequestMapping(value = "exception1")
  public String exception1() {
    throw new NullPointerException("Exception1 as plain text with <strong>html</strong> tags");
  }

  @RequestMapping(value = "getUser", method = RequestMethod.GET)
  public
  @ResponseBody
  UserTransfer getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal = authentication.getPrincipal();
    if (principal instanceof String && ((String) principal).equals("anonymousUser")) {
      //throw new WebApplicationException(401);
      Map<String, Boolean> roles = new HashMap<String, Boolean>();
      roles.put("Anonymous", true);
      return new UserTransfer(null, createRoleMap(authentication.getAuthorities()));
    }
    UserDetails userDetails = (UserDetails) principal;

    return new UserTransfer(userDetails.getUsername(), this.createRoleMap(userDetails.getAuthorities()));
  }

  @RequestMapping(value = "register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  PsychologistEntity savePsychologist(@RequestBody PsychologistEntity psychologistEntity) {
    return userFacade.savePsychologist(psychologistEntity);
  }

  @RequestMapping(value = "saveAvatar", method = RequestMethod.GET)
  public
  @ResponseBody
  ImageInfo saveAvatar(@RequestParam Long imageId) {
    AttachmentImageEntity imageEntity = userFacade.saveAvatar(imageId);
    ImageInfo imageInfo = new ImageInfo();
    imageInfo.setName(imageEntity.getName());
    imageInfo.setUrl(storageFileFacade.getRealFileUrl(imageEntity.getImageFile()));
    return imageInfo;
  }

  @RequestMapping(value = "saveGallery", method = RequestMethod.POST, consumes =  MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  List<ImageInfo> saveImageGallery(@RequestBody List<ImageInfo> imageIds) {
    List<AttachmentImageEntity> imageEntities = userFacade.saveGallery(imageIds);
    ArrayList<ImageInfo> imageInfos = new ArrayList<>();
    for (AttachmentImageEntity imageEntity : imageEntities) {
      ImageInfo imageInfo = new ImageInfo();
      imageInfo.setId(imageEntity.getId());
      imageInfo.setName(imageEntity.getName());
      imageInfo.setUrl(storageFileFacade.getRealFileUrl(imageEntity.getImageFile()));
      imageInfos.add(imageInfo);
    }
    return imageInfos;
  }


  @RequestMapping(value = "getPsychologist", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  PsychologistInfo getPsychologist(@RequestParam(defaultValue = ValueConstants.DEFAULT_NONE) Long psychologistId) throws NotAuthorizedException {
    Long userId = null;
    PsychologistEntity psychologistEntity;
    if (psychologistId == -1) {
      userId = UserFacadeImpl.getUserId();
      if (userId == null) {
        throw new NotAuthorizedException();
      }
      psychologistEntity = psychologistDao.findPsychologist(userId);
    } else {
      psychologistEntity = psychologistDao.find(psychologistId);
      userId = psychologistEntity.getUserEntity().getId();
    }
    PsychologistInfo psychologistInfo = new PsychologistInfo();
    psychologistInfo.setUserInfo(transferer.<UserEntity, BaseUserInfo>transfer(UserEntity.class, BaseUserInfo.class, psychologistEntity.getUserEntity()));
    List<BonusEntity> bonuses = bonusDao.findBy(userId);
    psychologistInfo.setBonuses(transferer.<BonusEntity,BonusInfo>transferList(BonusEntity.class, BonusInfo.class, bonuses));
    List<ArticleEntity> articles = articleFacade.findArticles(psychologistId);
    List<ArticleInfo> articleInfos = new ArrayList<>(articles.size());
    for (ArticleEntity article : articles) {
      ArticleInfo transfer = transferer.<ArticleEntity, ArticleInfo>transfer(ArticleEntity.class, ArticleInfo.class, article);
      Document parse = Jsoup.parse(article.getText());
      String text = parse.body().text();
      transfer.setExcerpt(getExcerpt(text, 301));
      articleInfos.add(transfer);
    }
    psychologistInfo.setArticles(articleInfos);
    //psychologistInfo.setAvatar(transferer.<AttachmentImageEntity, ImageInfo>transfer(AttachmentImageEntity.class, ImageInfo.class, psychologistEntity.getPhoto()));
    if (psychologistEntity.getPhoto() != null) {
      psychologistInfo.setAvatar(transferImageInfo(psychologistEntity.getPhoto()));
    }
    psychologistInfo.setGallery(transferImageInfo(psychologistEntity.getGalleryItems()));
    Map<String, UserInfoKeyValueEntity> userMeta = userInfoKeyValueDao.getUserMeta(userId);
    Map<String, NameIdJsonInfo> stringNameIdJsonInfoHashMap = new HashMap<>();
    for (Map.Entry<String, UserInfoKeyValueEntity> entry :  userMeta.entrySet()) {
      stringNameIdJsonInfoHashMap.put(entry.getKey(), new NameIdJsonInfo(entry.getValue().getId(), entry.getValue().getValue()));
    }
    psychologistInfo.setUserMeta(stringNameIdJsonInfoHashMap);
    return psychologistInfo;
    //return userFacade.savePsychologist(psychologistEntity);
  }

  private List<ImageInfo> transferImageInfo(List<AttachmentImageEntity> imageEntities) {
    ArrayList<ImageInfo> imageInfos = new ArrayList<>();
    for (AttachmentImageEntity imageEntity : imageEntities) {
      imageInfos.add(transferImageInfo(imageEntity));
    }
    return imageInfos;
  }

  private ImageInfo transferImageInfo(AttachmentImageEntity imageEntity) {
    ImageInfo imageInfo = new ImageInfo();
    imageInfo.setId(imageEntity.getId());
    imageInfo.setName(imageEntity.getName());
    imageInfo.setUrl(storageFileFacade.getRealFileUrl(imageEntity.getImageFile()));
    return imageInfo;
  }

  private String getExcerpt(String text, int maxSymbol) {
    if (text.isEmpty() || text.length() <maxSymbol) {
      return text;
    }
    String cut = text.substring(0, maxSymbol);
    if (Character.isWhitespace(text.charAt(maxSymbol))) {
      //space symbol exactly where we cut
      return cut + " ...";
    }
    int index = maxSymbol - 1;
    while (!Character.isWhitespace(text.charAt(index)) && index > 0) {
      index--;
    }
    if (index > 0) {
      cut = text.substring(0, index + 1);
    }
    return cut + " ...";
  }

  @RequestMapping(value = "test", method = RequestMethod.GET)
  public
  @ResponseBody
  ValidationExceptionJSONInfo test() {
    Map<String, String> fieldErrors = new HashMap<>();
    fieldErrors.put("username", "Емейл должен быть уникальным.");
    ValidationExceptionJSONInfo validationExceptionJSONInfo = new ValidationExceptionJSONInfo(fieldErrors);
    validationExceptionJSONInfo.setStatus(400);
    return validationExceptionJSONInfo;
  }

  @RequestMapping(value = "fastRegister", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  UserEntity saveUser(@RequestBody UserInfo userInfo) throws NoSuchAlgorithmException, SQLException, ValidationException {
    UserEntity byEmail = null;
    byEmail = userFacade.findByEmail(userInfo.getEmail());
    if (byEmail != null) {
      ValidationException validationException = new ValidationException();
      validationException.addFieldError("username", "Данный емейл уже зарегистрирован в системе.");
      throw validationException;
    }
    UserEntity userEntity1 = new UserEntity();
    userEntity1.setEmail(userInfo.getEmail());
    userEntity1.setPasswordMD5hash(userInfo.getPassword());

    return userFacade.saveUser(userEntity1);
  }

  @RequestMapping(value = "changePassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  JsonResult changePassword(@RequestBody UserPasswordInfo userPasswordInfo) throws ExpiredResetPasswordParameter, NoSuchAlgorithmException {
    boolean result = userFacade.saveChangePassword(userPasswordInfo.getPassword(), userPasswordInfo.getToken());
    return new JsonResult(result);
  }

  @RequestMapping(value = "askToResetPassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  JsonResult askToResetPassword(@RequestParam(value = "username") String email) throws by.vbalanse.facade.ValidationException, NoSuchAlgorithmException {
    boolean result = userFacade.saveAskToResetPassword(email);
    return new JsonResult(result);
  }

  /**
   * Authenticates a user and creates an authentication token.
   *
   * @param username The name of the user.
   * @param password The password of the user.
   * @return A transfer containing the authentication token.
   */
  @RequestMapping(value = "authenticate", method = RequestMethod.POST)
  public
  @ResponseBody
  TokenTransfer authenticate(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, HttpServletRequest request, HttpServletResponse response) {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(username, password);
    Authentication authentication = this.authManager.authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

		/*
     * Reload user as password of authentication principal will be null after authorization and
		 * password is needed for token generation
		 */
    UserDetails userDetails = this.userService.loadUserByUsername(username);

    return new TokenTransfer(TokenUtils.createToken(userDetails));
  }


  private Map<String, Boolean> createRoleMap(Collection<? extends GrantedAuthority> authorities) {
    Map<String, Boolean> roles = new HashMap<String, Boolean>();
    for (GrantedAuthority authority : authorities) {
      roles.put(authority.getAuthority(), Boolean.TRUE);
    }

    return roles;
  }


}
