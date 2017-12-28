package by.vbalanse.vaadin;

import by.vbalanse.vaadin.article.ArticleListView;
import by.vbalanse.vaadin.article.category.CategoriesEditor;
import by.vbalanse.vaadin.component.ClickLabel;
import by.vbalanse.vaadin.template.EmailListView;
import by.vbalanse.vaadin.training.TrainingListView;
import by.vbalanse.vaadin.user.UsersListView;
import by.vbalanse.vaadin.user.psychologist.PsychologistListView;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.event.Action;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.LayoutEvents;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Component
@Scope("prototype")
//@Scope(value = "session")
public class AdminView extends CssLayout implements ComponentContainer {
  public static final String USERS_URL_FRAGMENT = "users";
  public static final String ARTICLES_URL_FRAGMENT = "articles";
  public static final String CATEGORIES_URL_FRAGMENT = "categories";
  public static final String PSYCHOLOGISTS_URL_FRAGMENT = "psychologists";
  public static final String TRAINING_URL_FRAGMENT = "training_list";
  public static final String EMAIL_URL_FRAGMENT = "email_list";
  //  private static AdminView portalView;
  VerticalLayout verticalLayout;

  @Autowired
  private ArticleListView articleListView;

  @Autowired
  private CategoriesEditor categoriesEditor;

  @Autowired
  private UsersListView userListFlow;

  @Autowired
  private TrainingListView trainingListView;

  @Autowired
  private PsychologistListView psychologistListView;

  @Autowired
  private EmailListView emailListView;

  private HorizontalSplitPanel mainHorizontalSplitPanel;

  private HorizontalSplitPanel centralSplitPanel;

  public AdminView() {
    buildMainArea();
  }

//  public static AdminView getInstance() {
//    if (portalView == null) {
//      portalView = new AdminView();
//    }
//    return portalView;
//  }

  private void buildMainArea() {
//    VerticalLayout menu = new VerticalLayout();
//    menu.addStyleName("vertical-menu");
//    ClickLabel users = new ClickLabel("Пользователи");
//    users.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
//      public void layoutClick(LayoutEvents.LayoutClickEvent event) {
//        executeUsers();
//      }
//    });
//    menu.addComponent(users);
    Tree treeMenu = new Tree();
    treeMenu.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
    final HierarchicalContainer hierarchicalContainer = new HierarchicalContainer();
    List<MenuItem> menuItems = new MenuItems().findMenuItems();
    hierarchicalContainer.addContainerProperty("title",
        String.class, null);
    hierarchicalContainer.addContainerProperty("value",
        MenuItem.class, null);
    addChildren(hierarchicalContainer, menuItems, null, null);
    treeMenu.addItemClickListener(new ItemClickEvent.ItemClickListener() {
      @Override
      public void itemClick(ItemClickEvent event) {
        Item item = hierarchicalContainer.getItem(event.getItemId());
        MenuItem value = (MenuItem) item.getItemProperty("value").getValue();
        value.getMenuCommand().onItemClick();
      }
    });
    treeMenu.setItemCaptionPropertyId("title");
    treeMenu.setContainerDataSource(hierarchicalContainer);
    for (Object itemId : treeMenu.rootItemIds())
      treeMenu.expandItemsRecursively(itemId);
//    ClickLabel popupViewTest = new ClickLabel("Test popups");
//    popupViewTest.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
//      public void layoutClick(LayoutEvents.LayoutClickEvent event) {
//        PopupViewExample component = new PopupViewExample();
//        component.init("composition");
//        setMainFlow(component);
//      }
//    });
//    menu.addComponent(popupViewTest);

    Link logoutLink = new Link("Logout", new ExternalResource("j_spring_security_logout"));
    logoutLink.setStyleName("logoutArea");
    addComponent(logoutLink);
    mainHorizontalSplitPanel = new HorizontalSplitPanel();
    addComponent(mainHorizontalSplitPanel);
    mainHorizontalSplitPanel.setFirstComponent(treeMenu);
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
    centralSplitPanel = new HorizontalSplitPanel();
    mainHorizontalSplitPanel.setSecondComponent(centralSplitPanel);
    centralSplitPanel.setSplitPosition(50);
    centralSplitPanel.setSizeFull();
//    verticalLayout = new VerticalLayout();
//    setSecondComponent(verticalLayout);
//    HorizontalLayout toolbar = new HorizontalLayout();
//    verticalLayout.addComponent(toolbar);
//    toolbar.setWidth("100%");
//    verticalLayout.setSizeFull();

//    toolbar.setExpandRatio(searchField, 1);
//    toolbar.setComponentAlignment(searchField, Alignment.TOP_RIGHT);
//    verticalLayout.addComponent(personTable);
//    verticalLayout.setExpandRatio(personTable, 1);
    mainHorizontalSplitPanel.setSplitPosition(150, Unit.PIXELS);
//    logoutLink.setHeight("20px");
//    mainHorizontalSplitPanel.setHeight("100%");
//    setExpandRatio(logoutLink, 0.01f);
//    setExpandRatio(mainHorizontalSplitPanel, 1f);
    //setSizeFull();
    mainHorizontalSplitPanel.setSizeFull();
    setSizeFull();

  }

  private void addChildren(HierarchicalContainer hierarchicalContainer, List<MenuItem> menuItems2, Item parentItem, MenuItem parentMenuItem) {
    for (MenuItem menuItem: menuItems2) {
      Item item;
      String itemId = menuItem.getTitle();
      item = hierarchicalContainer.addItem(itemId);
      if (parentItem != null) {
        String parentId = parentMenuItem.getTitle();
        hierarchicalContainer.setParent(itemId, parentId);
      }
      item.getItemProperty("title").setValue(itemId);
      item.getItemProperty("value").setValue(menuItem);
      if (!menuItem.getChildren().isEmpty()) {
        hierarchicalContainer.setChildrenAllowed(itemId, true);
        addChildren(hierarchicalContainer, menuItem.getChildren(), item, menuItem);
      }


    }
  }

  private void executeArticles() {
    setMainFlow(articleListView);
    Page.getCurrent().setUriFragment(ARTICLES_URL_FRAGMENT);
  }

  private void enter(String uriFragment) {
    if (uriFragment == null || uriFragment.isEmpty()) {
      executeUsers();
      return;
    }
    if (uriFragment.equalsIgnoreCase(USERS_URL_FRAGMENT)) {
      executeUsers();
    } else if (uriFragment.equalsIgnoreCase(ARTICLES_URL_FRAGMENT)) {
      executeArticles();
    } else if (uriFragment.equalsIgnoreCase(CATEGORIES_URL_FRAGMENT)) {
      executeCategories();
    } else if (uriFragment.equalsIgnoreCase(PSYCHOLOGISTS_URL_FRAGMENT)) {
      executePsychologists();
    } else if (uriFragment.equalsIgnoreCase(TRAINING_URL_FRAGMENT)) {
      executeTrainingList();
    } else if (uriFragment.equalsIgnoreCase(EMAIL_URL_FRAGMENT)) {
      executeEmailTemplateList();
    }
  }

  private void executeTrainingList() {
    setMainFlow(trainingListView);
    Page.getCurrent().setUriFragment(TRAINING_URL_FRAGMENT);
  }

  private void executeEmailTemplateList() {
    setMainFlow(emailListView);
    Page.getCurrent().setUriFragment(EMAIL_URL_FRAGMENT);
  }

  private void executeCategories() {
    setMainFlow(categoriesEditor);
    Page.getCurrent().setUriFragment(CATEGORIES_URL_FRAGMENT);
  }

  private void executePsychologists() {
    setMainFlow(psychologistListView);
    Page.getCurrent().setUriFragment(PSYCHOLOGISTS_URL_FRAGMENT);
  }

  public void setMainFlow(com.vaadin.ui.Component component) {
    clearEditFlow();

    //setSecondComponent(component);
    centralSplitPanel.setFirstComponent(component);
  }

  private void clearEditFlow() {
    centralSplitPanel.setSecondComponent(new Label("Выберите одну из записей таблицы"));
  }

  public void setEditFlow(com.vaadin.ui.Component component) {
    //setSecondComponent(component);
    centralSplitPanel.setSecondComponent(component);
  }

  public void executeUsers() {
    setMainFlow(userListFlow);
    Page.getCurrent().setUriFragment(USERS_URL_FRAGMENT);
  }

  @PostConstruct
  public void init() {
    userListFlow.setAdminView(this);
    psychologistListView.setAdminView(this);
    articleListView.setAdminView(this);
    emailListView.setAdminView(this);
    trainingListView.setAdminView(this);
  }

  public class MenuItems {

    private List<MenuItem> menuItems = new ArrayList<>();

    public MenuItems() {
      MenuItem topUsersMenu = new MenuItem("Пользователи", USERS_URL_FRAGMENT, new MenuCommand() {
        @Override
        public void onItemClick() {
          executeUsers();
        }
      });
      MenuItem topArticlesMenu = new MenuItem("Статьи", ARTICLES_URL_FRAGMENT, new MenuCommand() {
        @Override
        public void onItemClick() {
          executeArticles();
        }
      });
      MenuItem categoriesMenu = new MenuItem("Категории", CATEGORIES_URL_FRAGMENT, new MenuCommand() {
        @Override
        public void onItemClick() {
          executeCategories();
        }
      });
      topArticlesMenu.addChild(categoriesMenu);
      MenuItem topTemplateMenu = new MenuItem("Шаблоны писем", EMAIL_URL_FRAGMENT, new MenuCommand() {
        @Override
        public void onItemClick() {
          executeEmailTemplateList();
        }
      });
      MenuItem topPsychologistMenu = new MenuItem("Психологи", PSYCHOLOGISTS_URL_FRAGMENT, new MenuCommand() {
        @Override
        public void onItemClick() {
          executePsychologists();
        }
      });
      menuItems.add(topUsersMenu);
      menuItems.add(topArticlesMenu);
      menuItems.add(topPsychologistMenu);
      menuItems.add(topTemplateMenu);
      MenuItem trainingMenu = new MenuItem("Тренинги", TRAINING_URL_FRAGMENT, new MenuCommand() {
        @Override
        public void onItemClick() {
          executeTrainingList();
        }
      });
      menuItems.add(trainingMenu);
    }

    public List<MenuItem> findMenuItems() {
      return menuItems;
    }
  }

  public class MenuItem {
    private MenuItem parent;
    private List<MenuItem> children = new ArrayList<>();
    private String title;
    private String url;
    private MenuCommand menuCommand;

    public MenuItem(String title, String url, MenuCommand menuCommand) {
      this.title = title;
      this.url = url;
      this.menuCommand = menuCommand;
    }

    public void setParent(MenuItem parent) {
      this.parent = parent;
    }

    public List<MenuItem> getChildren() {
      return children;
    }

    public void addChild(MenuItem child) {
      if (!children.contains(child)) {
        child.setParent(this);
        children.add(child);
      }
    }

    public void setMenuCommand(MenuCommand menuCommand) {
      this.menuCommand = menuCommand;
    }

    public String getTitle() {
      return title;
    }

    public String getUrl() {
      return url;
    }

    public MenuCommand getMenuCommand() {
      return menuCommand;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      MenuItem menuItem = (MenuItem) o;

      if (title != null ? !title.equals(menuItem.title) : menuItem.title != null) return false;

      return true;
    }

    @Override
    public int hashCode() {
      return title != null ? title.hashCode() : 0;
    }
  }

  interface MenuCommand {
    void onItemClick();
  }
}
