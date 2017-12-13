package by.vbalanse.rest;

import by.vbalanse.dao.article.DepartmentDao;
import by.vbalanse.dao.article.TagDao;
import by.vbalanse.dao.geography.CityDao;
import by.vbalanse.dao.jpa.UserInfoKeyValueDaoImpl;
import by.vbalanse.dao.user.UserDao;
import by.vbalanse.dao.user.UserInfoKeyValueDao;
import by.vbalanse.dao.user.psychologist.TherapyDimensionDao;
import by.vbalanse.dao.user.psychologist.WithWhomWorksDao;
import by.vbalanse.facade.article.BaseCodeTitleInfo;
import by.vbalanse.facade.article.BonusInfo;
import by.vbalanse.facade.article.ContentFacade;
import by.vbalanse.facade.storage.StorageFileFacade;
import by.vbalanse.facade.user.UserFacade;
import by.vbalanse.model.article.DepartmentEntity;
import by.vbalanse.model.content.PropertyForListKeyValueEntity;
import by.vbalanse.model.geography.CityEntity;
import by.vbalanse.model.psychologist.BonusEntity;
import by.vbalanse.model.psychologist.TherapyDimensionEntity;
import by.vbalanse.model.psychologist.WithWhomWorks;
import by.vbalanse.model.user.UserInfoKeyValueEntity;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.devlib.schmidt.imageinfo.ImageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Controller
@RequestMapping("/psy/data")
public class DataService {
  public static final Long NULL_VALUE = -1l;
  public static final String BONUS_DESCRIPTION = "bonus_description";
  public static final String BONUS = "bonus";
  public static final String BONUS_ICON = "icon";
  @Autowired
  WithWhomWorksDao withWhomWorksDao;

  public Map<String, Class> classNames = new HashMap<>();

  {
    classNames.put(BonusEntity.class.getSimpleName(), BonusEntity.class);
    classNames.put(PropertyForListKeyValueEntity.class.getSimpleName(), PropertyForListKeyValueEntity.class);
    classNames.put(UserInfoKeyValueEntity.class.getSimpleName(), UserInfoKeyValueEntity.class);
  }

  static class Tester {
    public static void main(String[] args) {
      System.out.println(new DataService().classNames);

    }
  }


  @Autowired
  CityDao cityDao;

  @Autowired
  DepartmentDao departmentDao;

  @Autowired
  UserFacade userFacade;

  @Autowired
  private StorageFileFacade storageFileFacade;

  @Autowired
  private TherapyDimensionDao therapyDimensionDao;

  @Autowired
  private TagDao tagDao;

  @RequestMapping(value = "workWith", method = RequestMethod.GET)
  public
  @ResponseBody
  List<WithWhomWorks> workWith() {
    return withWhomWorksDao.findAll();
  }

  @RequestMapping(value = "cities", method = RequestMethod.GET)
  public
  @ResponseBody
  List<CityEntity> findCities() {
    return cityDao.findAll();
  }

  @Autowired
  ServletContext servletContext;

  @Autowired
  ContentFacade contentFacade;

  @Autowired
  UserInfoKeyValueDao userInfoKeyValueDao;

  @Autowired
  UserDao userDao;

  @RequestMapping(value = "departments", method = RequestMethod.GET)
  public
  @ResponseBody
  List<DepartmentEntity> findDepartments() {
    //log
    return departmentDao.findAll();
  }

  @RequestMapping(value = "therapyDimensions", method = RequestMethod.GET)
  public
  @ResponseBody
  List<TherapyDimensionEntity> findTherapyDimensions() {
    return therapyDimensionDao.findAll();
  }

  @RequestMapping(value = "allTags", method = RequestMethod.GET)
  public
  @ResponseBody
  List<BaseCodeTitleInfo> findAllTags() {
    return transferBaseCodeTitle(tagDao.findAll());
  }

  @RequestMapping(value = "tags", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public
  @ResponseBody
  List<BaseCodeTitleInfo> findTags(@RequestBody FilterParams filterParams) {
    return transferBaseCodeTitle(tagDao.findTags(filterParams.getFilter(), filterParams.getIds()));
  }

  List<BaseCodeTitleInfo> transferBaseCodeTitle(List<?> objects) {
    ArrayList<BaseCodeTitleInfo> baseCodeTitleInfos = new ArrayList<>();
    for (Object someObject : objects) {
      BaseCodeTitleInfo baseCodeTitleInfo = new BaseCodeTitleInfo();
      //for (Field field : someObject.getClass().getDeclaredFields()) {
      try {
        Method id = someObject.getClass().getMethod("getId");
        Method code = someObject.getClass().getMethod("getCode");
        Method title = someObject.getClass().getMethod("getTitle");
        //field.setAccessible(true); // You might want to set modifier to public first.
        Object value = null;
        Object idValue = id.invoke(someObject, null);
        baseCodeTitleInfo.setId((Long) idValue);
        Object codeValue = code.invoke(someObject, null);
        baseCodeTitleInfo.setCode((String) codeValue);
        Object titleValue = title.invoke(someObject, null);
        baseCodeTitleInfo.setTitle((String) titleValue);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
      baseCodeTitleInfos.add(baseCodeTitleInfo);
    }
    return baseCodeTitleInfos;
  }


//  protected static File getOrCreateResourceTypeDir(final String baseDir,
//                                                   final ResourceType type) {
//    File dir = new File(baseDir, type.getPath());
//    if (!dir.exists())
//      dir.mkdirs();
//    return dir;
//  }

  @RequestMapping(value = "images", method = RequestMethod.GET)
  public
  @ResponseBody
  List<FileInfo> getFiles(HttpServletRequest request) throws IOException {
//    String absolutePath = getRealUserFilesAbsolutePath(RequestCycleHandler
//        .getUserFilesAbsolutePath(ThreadLocalData.getRequest()));
//    File typeDir = getOrCreateResourceTypeDir(absolutePath, type);
//    File currentDir = new File(typeDir, currentFolder);
//    if (!currentDir.exists() || !currentDir.isDirectory())
//      throw new InvalidCurrentFolderException();
//
    // collect files
    File currentDir = new File("files");
    String realPathFromServletContext = servletContext.getRealPath("files");
    List<FileInfo> files;
    Map<String, Object> fileMap;
//    File[] fileList = currentDir
//        .listFiles((FileFilter) FileFileFilter.FILE);
    ArrayList<File> files1 = new ArrayList<>();
    collectAllImageFiles(realPathFromServletContext, files1);
    files = new ArrayList<>();
    String startPath = request.getScheme() + "://" +   // "http" + "://
        request.getServerName() +       // "myhost"
        ":" +                           // ":"
        request.getServerPort() +       // "8080"
        request.getContextPath() + "/" + "files";
    for (File file : files1) {
      //fileMap = new HashMap<String, Object>(2);
      //fileMap.put(Connector.KEY_NAME, file.getName());
      //fileMap.put(Connector.KEY_SIZE, file.length());
      FileInputStream fileInputStream = new FileInputStream(file);
      ImageInfo ii = new ImageInfo();
      ii.setInput(fileInputStream);
      if (ii.check()) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setWidth(ii.getWidth());
        fileInfo.setHeight(ii.getHeight());
        fileInfo.setName(file.getName());
        String absolutePath = file.getAbsolutePath();
        String folderPath1 = absolutePath.substring(absolutePath.indexOf("files") + 5).replace("\\", "/");
        fileInfo.setAbsolutePath(absolutePath);
        fileInfo.setFullUrl(startPath + folderPath1);

        //fileInfo.setFullUrl(storageFileFacade.getRealFilePath());
        files.add(fileInfo);
      }
    }
    return files;
  }

  private void collectAllImageFiles(String folder, ArrayList<File> files) {
    File currentDir = new File(folder);
    File[] fileList = currentDir
        .listFiles((FileFilter) FileFileFilter.FILE);
    if (fileList != null) {
      files.addAll(Arrays.asList(fileList));
    }
    String[] foldersList = currentDir
        .list(DirectoryFileFilter.DIRECTORY);
    if (foldersList != null) {
      for (String folderName : foldersList) {
        collectAllImageFiles(folder + File.separator + folderName, files);
      }
    }
  }

//  public List<String> getFolders(final  ResourceType type,
//                                 final String currentFolder) throws IOException {
//    String absolutePath = getRealUserFilesAbsolutePath(RequestCycleHandler
//        .getUserFilesAbsolutePath(ThreadLocalData.getRequest()));
//    File typeDir = getOrCreateResourceTypeDir(absolutePath, type);
//    File currentDir = new File(typeDir, currentFolder);
//    if (!currentDir.exists() || !currentDir.isDirectory())
//      throw new IOException("no current dir");
//      //throw new InvalidCurrentFolderException();
//
//    String[] fileList = currentDir.list(DirectoryFileFilter.DIRECTORY);
//    return Arrays.asList(fileList);
//  }

  @RequestMapping(value = "saveBonus", method = RequestMethod.POST)
  public
  @ResponseBody
  IdJsonResult saveBonus(@RequestParam String name, @RequestParam String value, @RequestParam Long pk,
                            @RequestParam(required = false) String className) {

    Long id = contentFacade.mergeContent(className, name, pk, value);
    IdJsonResult idJsonResult = new IdJsonResult();
    idJsonResult.setId(id);
    return idJsonResult;
  }


  @RequestMapping(value = "savePsyCharacteristics", method = RequestMethod.POST)
  public
  @ResponseBody
  IdJsonResult savePsyCharacteristics(@RequestParam String name, @RequestParam String value, @RequestParam Long pk) {
    if (UserInfoKeyValueDaoImpl.getUserKeys().contains(name)) {
      //todo: refactor to service
      /*if (pk == -1) {
        pk = null;
      }*/
      UserInfoKeyValueEntity userInfoKeyValueEntity = userFacade.saveUserMeta(name, value, pk);
      IdJsonResult idJsonResult = new IdJsonResult();
      idJsonResult.setId(userInfoKeyValueEntity.getId());
      return idJsonResult;
    }
    IdJsonResult result = new IdJsonResult();
    result.setSuccess(false);
    return result;
  }


  @RequestMapping(value = "removeContent", method = RequestMethod.GET)
  public
  @ResponseBody
  void removeArticle(@RequestParam Long contentId, @RequestParam String contentName) {
    if (contentName.equals("elementGallery")) {
      userFacade.removeItemGallery(contentId);
    }
  }


}

