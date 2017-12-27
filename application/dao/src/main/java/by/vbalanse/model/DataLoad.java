/*
 * This Learning Management System (“Software”) is the exclusive and sole property of Baja Education. Inc. (“Baja”).
 * Baja has the sole rights to copy the software, create derivatives or modified versions of it, distribute copies
 * to End Users by license, sale or otherwise. Anyone exercising any of these exclusive rights which also includes
 * indirect copying  such as unauthorized translation of the code into a different programming language without
 * written explicit permission from Baja is an infringer and subject to liability for damages or statutory fines.
 * Interested parties may contact bpozos@gmail.com.
 *
 * (c) 2012 Baja Education
 */
package by.vbalanse.model;

import by.vbalanse.model.article.*;
import by.vbalanse.model.geography.CityEntity;
import by.vbalanse.model.psychologist.TherapyDimensionEntity;
import by.vbalanse.model.psychologist.WithWhomWorks;
import by.vbalanse.model.psychologist.PsychologistEntity;
import by.vbalanse.model.storage.AbstractStorageFileEntity;
import by.vbalanse.model.storage.StorageFolderEntity;
import by.vbalanse.model.template.EmailTemplateEntity;
import by.vbalanse.model.user.RoleEntity;
import by.vbalanse.model.user.RoleTypeEnum;
import by.vbalanse.model.user.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManager;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class DataLoad extends AbstractDataLoad {

  public static final String EMAIL_CHANGE_LINK = "emailChangeLink";
  static String propertiesFileName = "datasource";
  static String propertiesFolder = "spring-properties";

  public DataLoad() {
    super(propertiesFileName, propertiesFolder);
  }

  public DataLoad(String propertiesFileName, String propertiesFolder) {
    super(propertiesFileName, propertiesFolder);
  }

  public static void main(String[] args) {
    if (args.length > 0) {
      propertiesFileName = args[0];
    }
    if (args.length > 1) {
      propertiesFolder = args[1];
    }
    DataLoad ddl = new DataLoad(propertiesFileName, propertiesFolder);

    recreateTablesCurrent();

    ddl.recreateTables();
  }

  private static void recreateTablesCurrent() {
    java.sql.Connection connection = null;
    try {
      connection = DriverManager.getConnection(
          "jdbc:mysql://localhost:3306",
          "root", ""
      );
      java.sql.Statement st = connection.createStatement();
      st.executeUpdate("DROP DATABASE vbalanse");
      st.execute("CREATE DATABASE vbalanse");
      st.close();
    } catch (SQLException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
  }

  protected String[] getSpringConfigLocations() {
    return new String[]{
        "spring-configuration/common-config-datasource.xml",
        //"spring-configuration/common-config-hibernate.xml",
        "spring-configuration/common-config-jpa.xml"//,
        //"spring-configuration/hibernate-model.xml"
    };
  }

  protected String getHibernateEntityManagerFactoryBeanName() {
    return "entityManagerFactory";
  }

  protected void createDatabaseData(EntityManager entityManager) {
    createArticleCategories();
    //createPsychologist();

    createUsers();
    createDepartments();
    createOneArticle();
    createTags();
    createCities();
    createArticleCategories();
    createWorkWithWays();
    createTherapyDimensions();
   createTempFolder();
    createTrainings();
    createEmailTemplates();
  }

  private void createTags() {
    entityManager.persist(createTag("Бизнес", "business"));
    entityManager.persist(createTag("Семья", "family"));
    entityManager.persist(createTag("Любимые", "love"));
    entityManager.persist(createTag("Отношения", "relation"));
    entityManager.persist(createTag("Развод", "divorce"));
  }

  private TagEntity createTag(String title, String code) {
    return new TagEntity(title, code);
  }


  private void createDepartments() {
    entityManager.persist(new DepartmentEntity("О детях", "children"));
    entityManager.persist(new DepartmentEntity("Отношения", "relation"));
    entityManager.persist(new DepartmentEntity("Бизнес", "business"));
    entityManager.persist(new DepartmentEntity("Здоровье", "health"));
    entityManager.persist(new DepartmentEntity("Бессознательное", "uncon"));
  }

  private void createEmailTemplates() {
    EmailTemplateEntity successRegister = new EmailTemplateEntity();
    successRegister.setTitle(EmailTemplateEntity.SUCCESS_REGISTER_CODE);
    successRegister.setTheme("Вы успешно зарегистрированы!");
    successRegister.setContent("Добро пожаловать в лучшую систему блогов [[имя]]! Ваш емейл: [[email]]. " +
        "Для подтверждениея, пожалуйста, перейдите по ссылке [[activationLink]].");
    entityManager.persist(successRegister);

    EmailTemplateEntity forgotPassword = new EmailTemplateEntity();
    forgotPassword.setTitle(EmailTemplateEntity.PASSWORD_CHANGE_CODE);
    forgotPassword.setTheme("Замена пароля!");
    forgotPassword.setContent("Для изменения пароля перейдите, пожалуйста? по ссылке [[emailChangeLink]].");
    entityManager.persist(forgotPassword);
  }

  private void createTempFolder() {
    StorageFolderEntity folderEntity = new StorageFolderEntity();
    folderEntity.setCode(AbstractStorageFileEntity.TEMP_FOLDER);
    folderEntity.setFullPath(false);
    folderEntity.setPath("files/temp");
    entityManager.persist(folderEntity);

    StorageFolderEntity folderImageEntity = new StorageFolderEntity();
    folderImageEntity.setCode(AbstractStorageFileEntity.IMAGE_FOLDER);
    folderImageEntity.setFullPath(false);
    folderImageEntity.setPath("files/img");
    entityManager.persist(folderImageEntity);
//    for (ModuleStorageFolderEnum folder : ModuleStorageFolderEnum.values()) {
//      StorageFolderEntity folderEntity = new StorageFolderEntity();
//      folderEntity.setCode(folder.getCode());
//      folderEntity.setFullPath(false);
//      folderEntity.setPath(folder.getReativePath());
//      entityManager.save(folderEntity);
//
//    }
  }

  private void createArticleCategories() {
    ArticleCategoryEntity отношенияCategory = createCategory("Отношения М+Ж", null);
    createCategory("О мужчинах", отношенияCategory);
    createCategory("О женщинах", отношенияCategory);
    createCategory("Жизнь в сексе", отношенияCategory);
    ArticleCategoryEntity семьяCategory = createCategory("Семья", null);
    createCategory("Дети", семьяCategory);
    createCategory("Подростки", семьяCategory);
    createCategory("Жду малыша", семьяCategory);
    createCategory("Отношения с родителями", семьяCategory);
    ArticleCategoryEntity здоровьеCategory = createCategory("Здоровье", null);
    createCategory("Забочусь о себе", здоровьеCategory);
    createCategory("Красота??", здоровьеCategory);
    createCategory("Суицид", здоровьеCategory);
    createCategory("Зависимость", здоровьеCategory);
    ArticleCategoryEntity поискСебяCategory = createCategory("Поиск себя", null);
    createCategory("Самопознание", поискСебяCategory);
    createCategory("Досуг", поискСебяCategory);
  }

  private void createCities() {
    entityManager.persist(new CityEntity("Минск", "Minsk"));
    entityManager.persist(new CityEntity("Гродно", "Grodno"));
    entityManager.persist(new CityEntity("Витебск", "Vitebsk"));
    entityManager.persist(new CityEntity("Гомель", "Gomel"));
    entityManager.persist(new CityEntity("Могилёв", "Mogilev"));
    entityManager.persist(new CityEntity("Брест", "Brest"));
  }

  private void createTrainings() {
    TrainingImport trainingImport = new TrainingImport();
    try {
      trainingImport.importTrainings(entityManager);
    } catch (Exception e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }
    //new TrainingEntity("")
  }

  private ArticleCategoryEntity createCategory(String title, ArticleCategoryEntity parent) {
    ArticleCategoryEntity articleCategoryEntity = new ArticleCategoryEntity();
    articleCategoryEntity.setTitle(title);
    articleCategoryEntity.setCategory(parent);
    entityManager.persist(articleCategoryEntity);
    return articleCategoryEntity;
  }

  private void createWorkWithWays() {
    WithWhomWorks личная = new WithWhomWorks("Личная", "Person");
    entityManager.persist(личная);
    WithWhomWorks бизнес = new WithWhomWorks("Семейная", "Family");
    entityManager.persist(бизнес);
    WithWhomWorks детская = new WithWhomWorks("Дети", "Сhild");
    entityManager.persist(детская);
    WithWhomWorks семейная = new WithWhomWorks("Организация", "Company");
    entityManager.persist(семейная);
    WithWhomWorks подростки = new WithWhomWorks("Подростки", "Adults");
    entityManager.persist(подростки);
  }

  private void createTherapyDimensions() {
    TherapyDimensionEntity личная = new TherapyDimensionEntity("Гештальт", "Gestalt");
    entityManager.persist(личная);
    TherapyDimensionEntity psihoanaliz = new TherapyDimensionEntity("Психоанализ", "Psihoanaliz");
    entityManager.persist(psihoanaliz);
    TherapyDimensionEntity ungianstvo = new TherapyDimensionEntity("Юнгианство", "Ungianstvo");
    entityManager.persist(ungianstvo);
    TherapyDimensionEntity cognitiveBihevioral = new TherapyDimensionEntity("Когнитивно-бихевиорральное", "Cognitive-Bihevioral");
    entityManager.persist(cognitiveBihevioral);
    TherapyDimensionEntity system = new TherapyDimensionEntity("Системный подход", "System");
    entityManager.persist(system);
  }

  private void createOneArticle() {
//    UserEntity userEntity = createUsers(entityManager);
    UserEntity userEntity = getAdminUser();
    ArticleEntity articleEntity = createArticle(userEntity);
    DepartmentEntity departmentEntity = getDepartment("relation");
    articleEntity.setDepartment(departmentEntity);

    //TargetAuditoryEntity forParentsAuditory = createAuditories(entityManager);
    //createArticleCategory(entityManager);
    //ArrayList<TargetAuditoryEntity> auditories = new ArrayList<TargetAuditoryEntity>();
    //auditories.add(forParentsAuditory);
    //articleEntity.setAuditories(auditories);

    entityManager.persist(articleEntity);
  }

  public static DepartmentEntity getDepartment(String title) {
    return (DepartmentEntity) entityManager.createQuery("select de from DepartmentEntity de where de.code = '"+title+"'").getSingleResult();
  }

  public static UserEntity getAdminUser() {
    return (UserEntity) entityManager.createQuery("select ue from UserEntity ue where ue.email = 'admin@vbalanse.by'").getSingleResult();
  }

  private void createArticleCategory() {
    ArticleCategoryEntity articleCategoryEntity = new ArticleCategoryEntity();
    articleCategoryEntity.setTitle("Родителям");
    entityManager.persist(articleCategoryEntity);
  }

  private TargetAuditoryEntity createAuditories() {
    TargetAuditoryEntity forParentsAuditory = null;
    for (AuditoryTypeEnum auditoryTypeEnum : AuditoryTypeEnum.values()) {
      TargetAuditoryEntity targetAuditoryEntity = new TargetAuditoryEntity();
      targetAuditoryEntity.setCode(auditoryTypeEnum.getCode());
      targetAuditoryEntity.setDescription(auditoryTypeEnum.getDescription());
      if (auditoryTypeEnum.equals(AuditoryTypeEnum.FOR_PARENTS)) {
        forParentsAuditory = targetAuditoryEntity;
      }
      entityManager.persist(targetAuditoryEntity);
    }
    return forParentsAuditory;
  }

  public ArticleEntity createArticle(UserEntity userEntity) {
    ArticleEntity articleEntity = new ArticleEntity();
    articleEntity.setTitle("Моего ребёнка обижают в школе");
    articleEntity.setDateOfPublish(new Date());
    articleEntity.setText("Моего ребёнка обижают в школе");
    articleEntity.setAuthor(userEntity);
    return articleEntity;
  }

  private UserEntity createUsers() {
    createUser("Admin", "Terehova", "admin@vbalanse.by", RoleTypeEnum.ROLE_ADMINISTRATOR);
    return createUser("Vasilina", "Terehova", "test@vbalanse.by", RoleTypeEnum.ROLE_PSYCHOLOGIST);
  }


  private UserEntity createUser(String firstName, String lastName, String email, RoleTypeEnum roleCode) {
    RoleEntity psichologistRole = null;
    for (RoleTypeEnum role : RoleTypeEnum.values()) {
      RoleEntity roleEntity = new RoleEntity(role.getCode(), role.getDescription());
      if (role.equals(roleCode)) {
        psichologistRole = roleEntity;
      }
      entityManager.persist(roleEntity);
    }
    BCryptPasswordEncoder md5PasswordEncoder = new BCryptPasswordEncoder();
    UserEntity userEntity = new UserEntity();
    userEntity.setRole(psichologistRole);
    userEntity.setFirstName(firstName);
    userEntity.setLastName(lastName);
    userEntity.setEmail(email);
    userEntity.setConfirmed(true);
    userEntity.setPasswordMD5hash(md5PasswordEncoder.encode("11111"));
    entityManager.persist(userEntity);
    if (roleCode.equals(RoleTypeEnum.ROLE_PSYCHOLOGIST)) {
      createPsychologist(userEntity);
    }
    return userEntity;
  }

  private void createPsychologist(UserEntity userEntity) {
    PsychologistEntity psychologistEntity = new PsychologistEntity();
    psychologistEntity.setUserEntity(userEntity);
    psychologistEntity.setSkype("vasillina");
    entityManager.persist(psychologistEntity);
  }


}
