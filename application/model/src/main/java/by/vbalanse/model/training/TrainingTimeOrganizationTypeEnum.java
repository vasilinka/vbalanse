package by.vbalanse.model.training;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public enum TrainingTimeOrganizationTypeEnum {
  PO_MERE_NABORA("nabor", "По мере набора"),
  PO_DATE("podate", "По дате");

  private String code;
  private String title;

  private TrainingTimeOrganizationTypeEnum(String code, String title) {
    this.code = code;
    this.title = title;
  }

  public String getCode() {
    return code;
  }

  public String getTitle() {
    return title;
  }

  public static TrainingTimeOrganizationTypeEnum parseTrainingTimeOrganizationType(String type) {
    if (type == null) {
      return null;
    }
    for (TrainingTimeOrganizationTypeEnum trainingTimeOrganizationTypeEnum : TrainingTimeOrganizationTypeEnum.values()) {
      if (trainingTimeOrganizationTypeEnum.getCode().equals(type)) {
        return trainingTimeOrganizationTypeEnum;
      }
    }
    return null;
  }
}
