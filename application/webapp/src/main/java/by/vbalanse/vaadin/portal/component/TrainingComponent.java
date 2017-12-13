package by.vbalanse.vaadin.portal.component;

import by.vbalanse.model.psychologist.PsychologistEntity;
import by.vbalanse.model.training.TrainingEntity;
import by.vbalanse.vaadin.component.ClickLabel;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;

import java.util.Set;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class TrainingComponent extends CustomComponent {
  private final TrainingEntity trainingEntity;

  public TrainingComponent(TrainingEntity entity) {
    this.trainingEntity = entity;
    buildMainView();
  }

  private void buildMainView() {
    GridLayout container = new GridLayout(2, 1);
    container.setStyleName("training-row");
    CssLayout imageRegion = new CssLayout();
    Image c = new Image("", new ThemeResource("img/otnoshenia.jpg"));
    c.setWidth(90, Unit.PIXELS);
    imageRegion.addComponent(c);
    imageRegion.setWidth(110, Unit.PIXELS);
    Set<PsychologistEntity> authors = trainingEntity.getAuthors();
    String authorsString = "";
    for (PsychologistEntity author : authors) {
      authorsString+= author.getUserEntity().getFirstLastName() + " ";
    }
    ClickLabel authorTraining = new ClickLabel("Ведущие: " + authorsString);
    authorTraining.setStyleName("author-name");
    imageRegion.addComponent(authorTraining);
    imageRegion.setStyleName("image-region");
    container.addComponent(imageRegion, 0, 0);
    //container.setExpandRatio(imageRegion, 0.3f);
    container.setComponentAlignment(imageRegion, Alignment.TOP_LEFT);
    //container.setSplitPosition(120, Unit.PIXELS);
    //container.setLocked(true);
//    VerticalLayout ratings = new VerticalLayout();
//    ratings.setWidth(150, Unit.PIXELS);

    CssLayout descriptionTraining = new CssLayout();
    descriptionTraining.setStyleName("description-my");
    descriptionTraining.setSizeUndefined();
    ClickLabel trainingTitle = new ClickLabel(trainingEntity.getTitle());
    trainingTitle.setStyleName("training-style");
    descriptionTraining.addComponent(trainingTitle);
    ClickLabel textDescription = new ClickLabel(trainingEntity.getDescription());
    textDescription.setStyleName("description-training");
    descriptionTraining.addComponent(textDescription);
    ClickLabel timeDescription = new ClickLabel("Время проведения " + trainingEntity.getHoursOfTraining());
    //timeDescription.setStyleName("description-training");
    descriptionTraining.addComponent(timeDescription);
    ClickLabel priceLabel = new ClickLabel("Цена <span class='price'>" + trainingEntity.getPrice() + "</span>");
    //priceLabel.setStyleName("description-training");
    descriptionTraining.addComponent(priceLabel);

    HorizontalLayout rating = new HorizontalLayout();
    ClickLabel reitingTitle = new ClickLabel("<strong>Рейтинг:</strong>");
    reitingTitle.addStyleName("reiting");
    rating.addComponent(reitingTitle);
    Embedded c1 = new Embedded(null, new ThemeResource("img/reiting.png"));
    c1.setWidth(100, Unit.PIXELS);
    c1.setWidth(71, Unit.PIXELS);
    rating.addComponent(c1);
    ClickLabel voices = new ClickLabel("3 голоса");
    voices.addStyleName("voices");
    rating.addComponent(voices);
    descriptionTraining.addComponent(rating);

    HorizontalLayout trainingInfo = new HorizontalLayout(descriptionTraining);
    trainingInfo.addComponent(descriptionTraining);
    //trainingInfo.addComponent(ratings);
    container.addComponent(trainingInfo, 1, 0);
    container.setWidth("100%");
    //container.setExpandRatio(trainingInfo, 0.7f);
    setCompositionRoot(container);
  }
}
