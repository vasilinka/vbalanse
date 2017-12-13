package by.vbalanse.vaadin.portal;

import by.vbalanse.model.article.ArticleCategoryEntity;
import by.vbalanse.model.geography.CityEntity;
import by.vbalanse.model.training.TrainingEntity;
import by.vbalanse.vaadin.AdminUI;
import by.vbalanse.vaadin.article.HierarchicalArticleCategoryContainer;
import by.vbalanse.vaadin.component.ClickLabel;
import by.vbalanse.vaadin.hibernate.utils.SpringContextHelper;
import by.vbalanse.vaadin.portal.component.AlsoTabSection;
import by.vbalanse.vaadin.portal.component.CategoryBlockMainPage;
import by.vbalanse.vaadin.portal.component.TrainingComponent;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Property;
import com.vaadin.event.LayoutEvents;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.hibernate.SessionFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class PortalView extends CssLayout {

  private static final String MAIN_PAGE_URL_FRAGMENT = "main_page";
  private static final String OLD_MAIN_PAGE_URL_FRAGMENT = "old_main_page";
//  private static final String OLD_MAIN_PAGE_URL_FRAGMENT = "old_main_page";
  private static final String TRAININGS_URL_FRAGMENT = "trainings";

  private HorizontalLayout welcomeRegion;
  JPAContainer<ArticleCategoryEntity> categoriesContainerJpa;
  JPAContainer<CityEntity> cityContainer;
  JPAContainer<TrainingEntity> trainingContainer;
  VerticalLayout mainPart;

  public PortalView() {

    SpringContextHelper springContextHelper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
    SessionFactory sessionFactory = (SessionFactory) springContextHelper.getBean("sessionFactory");
    categoriesContainerJpa = new HierarchicalArticleCategoryContainer(ArticleCategoryEntity.class);
    cityContainer = JPAContainerFactory.make(CityEntity.class, AdminUI.PERSISTENCE_UNIT);
    trainingContainer = JPAContainerFactory.make(TrainingEntity.class, AdminUI.PERSISTENCE_UNIT);
    buildMainArea();

  }

  private void buildMainArea() {

    //CssLayout bodyContainer = new CssLayout();
    VerticalSplitPanel inBodyContainer = new VerticalSplitPanel();
    setHeight("100%");
    inBodyContainer.setHeight("100%");
    setWidth("1000px");
    setStyleName("in-body-container");
    HorizontalSplitPanel topRegion = new HorizontalSplitPanel();
    topRegion.setSizeFull();
    topRegion.setSplitPosition(180, Unit.PIXELS);
    //topRegion.addStyleName("blue-style");
    topRegion.setLocked(true);

    ThemeResource resource = new ThemeResource("img/logo.png");
    Image logotip = new Image("Логотип", resource);
    logotip.setWidth(130, Unit.PIXELS);
    logotip.addClickListener(new MouseEvents.ClickListener() {
      public void click(MouseEvents.ClickEvent event) {
        //executeOldMainPage();
        executeMainPageVersionMikola1();
      }
    });
    //logotip.setHeight(100, Unit.PIXELS);
    logotip.setStyleName("logotip");
    topRegion.setFirstComponent(logotip);

    HorizontalSplitPanel emptyRegion = new HorizontalSplitPanel();
    emptyRegion.setLocked(true);
    Panel тест = new Panel();
    //тест.addStyleName("red-style");
    тест.setHeight("100%");
    final HorizontalLayout cityCurrent = new HorizontalLayout();
    final Label content = new Label("Ваш город не задан");
    cityCurrent.addComponent(content);
    final ComboBox citySelect = showCitySelect(content);
    ClickLabel изменить = new ClickLabel("&nbsp;Изменить");
    изменить.setStyleName("link");
    изменить.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
      public void layoutClick(LayoutEvents.LayoutClickEvent event) {
        citySelect.setVisible(true);
      }
    });
    cityCurrent.addComponent(изменить);

    cityCurrent.addComponent(citySelect);
    тест.setContent(cityCurrent);
    emptyRegion.setFirstComponent(тест);

    VerticalLayout loginArea = new VerticalLayout();
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = null;
    if (authentication != null) {
      userName = authentication.getName();
//          if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
//              userName = "гость";
//          }
    }
    Label welcomeLabel = new Label("Добро пожаловать, Гость");
    loginArea.addComponent(welcomeLabel);
    //loginArea.addComponent(new Label(userName == null ? "Гость" : userName));
//    Image socialLoginImage = new Image("Войти через", new ThemeResource("img/login_social.png"));
//    socialLoginImage.setWidth(100, Unit.PIXELS);
//    loginArea.addComponent(socialLoginImage);
    emptyRegion.setSecondComponent(loginArea);
    loginArea.setWidth(200, Unit.PIXELS);
    emptyRegion.setSplitPosition(200, Unit.PIXELS, true);
    topRegion.setSecondComponent(emptyRegion);


    inBodyContainer.setFirstComponent(topRegion);
    inBodyContainer.setSplitPosition(48, Unit.PIXELS);
    final CssLayout centralPart = new CssLayout();
    //centralPart.addStyleName("pink-color");
    centralPart.setHeight("100%");
    inBodyContainer.setSecondComponent(centralPart);
    inBodyContainer.setLocked(true);
    addComponent(inBodyContainer);

    final MenuBar menu = new MenuBar();
    menu.setWidth(100.0f, Unit.PERCENTAGE);
    //menu.setHeight(34, Unit.PIXELS);
    menu.setId("menu");
    final MenuBar статьиBar = new MenuBar();
    статьиBar.setWidth("100%");
    статьиBar.addItem("О мужчинах", null);
    статьиBar.addItem("О женщинах", null);
    статьиBar.addItem("Жизнь в сексе", null);
    статьиBar.setVisible(false);
    menu.addItem("Статьи", new MenuBar.Command() {
      public void menuSelected(MenuBar.MenuItem selectedItem) {
        статьиBar.setVisible(true);
      }
    });
    //menu.addListener(Hover);
    menu.addItem("Тренинги", new MenuBar.Command() {
      public void menuSelected(MenuBar.MenuItem selectedItem) {
        Notification.show("Тренинги");
        executeTrainingsPage();
        статьиBar.setVisible(false);
      }
    });
    menu.addItem("Спросить психолога", new MenuBar.Command() {
      public void menuSelected(MenuBar.MenuItem selectedItem) {
        Notification.show("Спросить психолога");
        статьиBar.setVisible(false);
      }
    });
    menu.addItem("Психологи", new MenuBar.Command() {
      public void menuSelected(MenuBar.MenuItem selectedItem) {
        Notification.show("Психологи");
        статьиBar.setVisible(false);
      }
    });
    menu.addItem("old main page", new MenuBar.Command() {
      @Override
      public void menuSelected(MenuBar.MenuItem selectedItem) {
        executeOldMainPage();
        статьиBar.setEnabled(false);
      }
    });
    centralPart.addComponent(menu);
    mainPart = new VerticalLayout();
    mainPart.setWidth("100%");
    mainPart.setHeight("100%");
    centralPart.addComponent(mainPart);
    //centralPart.addComponent(статьиBar);

    HorizontalSplitPanel mainPageComponent = mainPage();
    mainPart.addComponent(mainPageComponent);

    addAttachListener(new AttachListener() {
      public void attach(AttachEvent event) {
        String fragment = Page.getCurrent().getUriFragment();
        enter(fragment);
        Page.getCurrent().addUriFragmentChangedListener(
            new Page.UriFragmentChangedListener() {
              public void uriFragmentChanged(
                  Page.UriFragmentChangedEvent source) {
                enter(source.getUriFragment());
              }
            });
      }
    });
  }

  private void enter(String uriFragment) {
    if (uriFragment == null || uriFragment.isEmpty()) {
      //executeOldMainPage();
      executeMainPageVersionMikola1();
      //статьиBar.setEnabled(false);
      return;
    }
    if (uriFragment.equalsIgnoreCase(MAIN_PAGE_URL_FRAGMENT)) {
      //executeOldMainPage();
      executeMainPageVersionMikola1();
    } else if (uriFragment.equalsIgnoreCase(TRAININGS_URL_FRAGMENT)) {
      executeTrainingsPage();
    }
  }

  private void executeTrainingsPage() {
    setMainFlow(trainingPage());
    Page.getCurrent().setUriFragment(TRAININGS_URL_FRAGMENT);
  }

  private void executeMainPageVersionMikola1() {
    setMainFlow(new TestThemeView());
    Page.getCurrent().setUriFragment(MAIN_PAGE_URL_FRAGMENT);
  }

  private AbstractComponent trainingPage() {
    //UI.
    //UI.getCurrent().
    HorizontalSplitPanel trainingArea = new HorizontalSplitPanel();
    CssLayout leftPart = new CssLayout();
    //leftPart.addStyleName("green-style");
    leftPart.setHeight("100%");
    trainingArea.setFirstComponent(leftPart);
    leftPart.setStyleName("left-categories");
    leftPart.addComponent(new ClickLabel("<font color='blue'>Отношения М+Ж</font>"));
    leftPart.addComponent(new ClickLabel("<font color='blue'>Родителям о детях</font>"));
    leftPart.addComponent(new ClickLabel("<font color='blue'>Дела семейные</font>"));
    leftPart.addComponent(new ClickLabel("<font color='blue'>Бизнес</font>"));
//    leftPart.addComponent(new ClickLabel(">> Коучинг"));
//    leftPart.addComponent(new ClickLabel(">> Планирования времени"));
    leftPart.addComponent(new ClickLabel("<font color='blue'>Личностного роста</font>"));
    leftPart.addComponent(new ClickLabel("<font color='blue'>Группы поддержки</font>"));
    leftPart.addComponent(new ClickLabel(""));
    leftPart.addComponent(new ClickLabel("Любовь"));
    leftPart.addComponent(new ClickLabel("Родители и дети"));
    leftPart.addComponent(new ClickLabel("Будущим мамам"));
    leftPart.addComponent(new ClickLabel("Кризисы"));
    leftPart.addComponent(new ClickLabel("Жизнь в сексе"));
    leftPart.addComponent(new ClickLabel("Страхи и фобии"));
    leftPart.addComponent(new ClickLabel("Зависимость"));
    leftPart.addComponent(new ClickLabel("Смерть близкого"));
    leftPart.addComponent(new ClickLabel("Выбор профессии"));
    leftPart.addComponent(new ClickLabel("Карьера и деньги"));
    leftPart.addComponent(new ClickLabel("Конфликты на работе"));
    leftPart.addComponent(new ClickLabel("Здоровье"));
    leftPart.addComponent(new ClickLabel("Поиск себя"));
//    leftPart.addComponent(new ClickLabel(">> семейное насилие"));
//    leftPart.addComponent(new ClickLabel(">> потерявшие близких"));
//    leftPart.addComponent(new ClickLabel(">> онкобольным"));
//    leftPart.addComponent(new ClickLabel(">> больным гепатитом, СПИДом"));
//    leftPart.addComponent(new ClickLabel(">> зависимость (алкоголизм, наркомания)"));
//    leftPart.addComponent(new ClickLabel(">> зависимость пищевая (булемия, анорексия)"));

    CssLayout centralCategories = new CssLayout();
    //centralCategories.addStyleName("pink-color");
    //centralCategories.setHeight("100%");
    trainingArea.setSecondComponent(centralCategories);
    trainingArea.setSplitPosition(154, Unit.PIXELS);

    //centralCategories.setSplitPosition(100, Unit.PIXELS);
    HorizontalLayout fastFilters = new HorizontalLayout();
    //fastFilters.setWidth("100%");
    fastFilters.setStyleName("fast-filters");

    CssLayout trainingType = new CssLayout();
    trainingType.setStyleName("filter-column");
    ClickLabel titleLabel1 = new ClickLabel("Тип");
    titleLabel1.setStyleName("title-label");
    trainingType.addComponent(titleLabel1);
    trainingType.addComponent(new ClickLabel("Тренинг"));
    trainingType.addComponent(new ClickLabel("Группа поддержки"));
    trainingType.addComponent(new ClickLabel("Групповая терапия"));
    fastFilters.addComponent(trainingType);

    CssLayout trainingPeriod = new CssLayout();
    trainingPeriod.setStyleName("filter-column");
    ClickLabel titleLabel2 = new ClickLabel("Скоро");
    trainingPeriod.addComponent(titleLabel2);
    titleLabel2.setStyleName("title-label");
    trainingPeriod.addComponent(new ClickLabel("Ближайшая неделя"));
    trainingPeriod.addComponent(new ClickLabel("Ближайший месяц "));
    trainingPeriod.addComponent(new ClickLabel("Ближайшие полгода"));
    trainingPeriod.addComponent(new ClickLabel(""));
    trainingPeriod.addComponent(new ClickLabel("По мере набора"));
    fastFilters.addComponent(trainingPeriod);

    CssLayout timeType = new CssLayout();
    timeType.setStyleName("filter-column");
    ClickLabel titleLabel3 = new ClickLabel("Время проведения");
    titleLabel3.setStyleName("title-label");
    timeType.addComponent(titleLabel3);
    timeType.addComponent(new ClickLabel("Выходного дня"));
    timeType.addComponent(new ClickLabel("Будни день"));
    timeType.addComponent(new ClickLabel("Будни вечер"));
    fastFilters.addComponent(timeType);

    CssLayout city = new CssLayout();
    city.setStyleName("filter-column");
    ClickLabel titleLabel4 = new ClickLabel("Город");
    titleLabel4.setStyleName("title-label");
    city.addComponent(titleLabel4);
    Collection<?> itemIds = cityContainer.getItemIds();
    for (Object cityId : itemIds) {
      EntityItem<CityEntity> item = cityContainer.getItem(cityId);
      city.addComponent(new ClickLabel(item.getEntity().getTitle()));
    }
    fastFilters.addComponent(city);

    //fastFilters.setExpandRatio(trainingType, 0.1f);
    trainingType.setWidth(150, Unit.PIXELS);
    //fastFilters.setExpandRatio(trainingPeriod, 0.1f);
    trainingPeriod.setWidth(150, Unit.PIXELS);
    //fastFilters.setExpandRatio(timeType, 0.1f);
    timeType.setWidth(150, Unit.PIXELS);
    //fastFilters.setExpandRatio(city, 0.1f);
    city.setWidth(150, Unit.PIXELS);

    centralCategories.addComponent(fastFilters);
    Collection<Object> trainingIds = trainingContainer.getItemIds();
    VerticalLayout trainingList = new VerticalLayout();
    for (Object trainingId : trainingIds) {
      EntityItem<TrainingEntity> item = trainingContainer.getItem(trainingId);
      trainingList.addComponent(new TrainingComponent(item.getEntity()));
    }
    HorizontalLayout trainingListAlso = new HorizontalLayout();
    trainingListAlso.setWidth("100%");
    trainingListAlso.addComponent(trainingList);
    trainingListAlso.setExpandRatio(trainingList, 0.8f);
    CssLayout also = new CssLayout();
    Image advertImage = new Image("", new ThemeResource("img/advert2.jpg"));
    advertImage.setWidth("180px");
    also.setStyleName("right-column");
    also.addComponent(advertImage);
    //TabSheet статьиTab = new TabSheet();
    //статьиTab.addStyleName(Reindeer.TABSHEET_BORDERLESS);
    also.addComponent(new ClickLabel("<h3>Может быть интересно</h3>"));

    ArrayList<String> articleTitleList = new ArrayList<>();
    articleTitleList.add("Статья про женственность 1");
    articleTitleList.add("Статья про женственность 2");
    articleTitleList.add("Статья про женственность 3");
    AlsoTabSection статьиSection = new AlsoTabSection("Статьи", articleTitleList);
    also.addComponent(статьиSection);

    ArrayList<String> booksTitleList = new ArrayList<>();
    booksTitleList.add("Мужчины с Марса, женщины с Венеры");
    booksTitleList.add("Как стать Стервой");
    booksTitleList.add("Советы домохозяйке");
    AlsoTabSection bookSection = new AlsoTabSection("Книги", booksTitleList);
    also.addComponent(bookSection);

    trainingListAlso.addComponent(also);
    trainingListAlso.setComponentAlignment(also, Alignment.TOP_LEFT);
    trainingListAlso.setExpandRatio(also, 0.3f);
    centralCategories.addComponent(trainingListAlso);

    return trainingArea;
  }

  private void setMainFlow(AbstractComponent abstractComponent) {
    mainPart.removeAllComponents();
    mainPart.addComponent(abstractComponent);
  }

  private void executeOldMainPage() {
    setMainFlow(mainPage());
    Page.getCurrent().setUriFragment(OLD_MAIN_PAGE_URL_FRAGMENT);
  }

  private HorizontalSplitPanel mainPage() {
    HorizontalSplitPanel centralPart2 = new HorizontalSplitPanel();
    VerticalLayout leftPart = new VerticalLayout();
    //leftPart.addStyleName("green-style");
    leftPart.setHeight("100%");
    centralPart2.setFirstComponent(leftPart);

    VerticalLayout centralCategories = new VerticalLayout();
    //centralCategories.addStyleName("pink-color");
    //centralCategories.setHeight("100%");
    centralPart2.setSecondComponent(centralCategories);
    HorizontalLayout categoriesFirstRow = new HorizontalLayout();
    categoriesFirstRow.setHeight("220px");


    //categoriesContainer.addContainerFilter("category", "null", true, true);
    //Collection<?> itemIds = categoriesContainer.getItemIds();
    Collection<?> children1 = categoriesContainerJpa.getChildren(null);
    ArrayList children = new ArrayList(children1);

    List firstRowCategories = children.subList(0, 3);
    for (Object topCategoryId : firstRowCategories) {
      EntityItem item = categoriesContainerJpa.getItem(topCategoryId);
      ArticleCategoryEntity articleCategoryEntity = (ArticleCategoryEntity) item.getEntity();
      categoriesFirstRow.addComponent(new CategoryBlockMainPage(articleCategoryEntity));
    }

    HorizontalLayout categoriesSecondRow = new HorizontalLayout();
    //categoriesSecondRow.addStyleName("orange-style");
    categoriesSecondRow.setHeight("200px");
    categoriesSecondRow.setWidth("100%");
    List secondRowCateories = children.subList(3, 5);
    for (Object topCategoryId : secondRowCateories) {
      EntityItem item = categoriesContainerJpa.getItem(topCategoryId);
      ArticleCategoryEntity articleCategoryEntity = (ArticleCategoryEntity) item.getEntity();
      categoriesSecondRow.addComponent(new CategoryBlockMainPage(articleCategoryEntity));
    }
    categoriesSecondRow.addComponent(new Panel());
    categoriesFirstRow.setWidth("100%");
    centralCategories.addComponent(categoriesFirstRow);
    centralCategories.addComponent(categoriesSecondRow);
    centralPart2.setSplitPosition(237, Unit.PIXELS);
    return centralPart2;
  }

  private ComboBox showCitySelect(final Label content) {
    final ComboBox citySelector = new ComboBox("Город", cityContainer);
    citySelector.setNullSelectionAllowed(false);
    citySelector.setItemCaptionPropertyId("title");
    citySelector.setImmediate(true);
    citySelector.setStyleName("city");
    citySelector.setVisible(false);
    citySelector.addValueChangeListener(new Property.ValueChangeListener() {
      public void valueChange(Property.ValueChangeEvent event) {
        Object id = citySelector.getValue();
        if (id != null) {
          content.setValue("Ваш город " + citySelector.getItem(id).getItemProperty("title"));
        }
        citySelector.setVisible(false);
      }
    });
    return citySelector;
  }
}

