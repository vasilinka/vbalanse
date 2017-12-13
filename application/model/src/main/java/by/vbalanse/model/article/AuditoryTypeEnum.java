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

package by.vbalanse.model.article;

public enum AuditoryTypeEnum {

  FOR_PARENTS("P", "Для родителей"),
  FOR_BUSINESS_LADY_MEN("BLM", "Для бизнес леди и мужчин"),
  FOR_MEN_WOMEN("MW", "Для мужчин и женщин"),
  FOR_GROWNUPS("G", "Для девушек и юношей"),
  ;

  private String code;
  private String description;

  private AuditoryTypeEnum(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  public static AuditoryTypeEnum parse(String code) {
    for (AuditoryTypeEnum role : values()) {
      if (role.getCode().equals(code)) {
        return role;
      }
    }

    throw new IllegalArgumentException(
        "No enum const " + AuditoryTypeEnum.class + "." + code);
  }

}
