package by.vbalanse.facade.article;

import by.vbalanse.facade.article.BaseTitleDescriptionInfo;
import by.vbalanse.facade.article.HasIdInfo;

/**
 * Created by Vasilina on 28.03.2015.
 */
public class BonusInfo extends BaseTitleDescriptionInfo {
  private String iconName;
  //used for partial set bonus, if bonus is dirty it wont be shown to user
  //bonus is marked as dirty when no data from user set
  private boolean dirty;

  public String getIconName() {
    return iconName;
  }

  public void setIconName(String iconName) {
    this.iconName = iconName;
  }
}
