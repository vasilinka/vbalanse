package by.vbalanse.model.training;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public enum TrainingTypeEnum {
  TRAINING("training", "Тренинг"),
  PSY_GROUP("psy_group", "Группова терапия"),
  GROUP_OF_HELP("help_group", "Групппа поддержки");

  private String code;
  private String title;

  private TrainingTypeEnum(String code, String title) {
    this.code = code;
    this.title = title;
  }

  public String getCode() {
    return code;
  }

  public String getTitle() {
    return title;
  }

  public static TrainingTypeEnum parseTrainingType(String type) {
    if (type == null) {
      return null;
    }
    for (TrainingTypeEnum trainingTypeEnum : TrainingTypeEnum.values()) {
      if (trainingTypeEnum.getCode().equals(type)) {
        return trainingTypeEnum;
      }
    }
    return null;
  }

}
