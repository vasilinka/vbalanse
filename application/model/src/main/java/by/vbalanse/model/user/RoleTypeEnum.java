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

package by.vbalanse.model.user;

public enum RoleTypeEnum {

  ROLE_ADMINISTRATOR(Constants.ROLE_ADMINISTATOR_CODE, "Administrator"),
  ROLE_PSYCHOLOGIST(Constants.ROLE_PSYCHOLOGIST_CODE, "Psychologist"),
  ROLE_REGISTERED_USER(Constants.ROLE_REGISTERED_USER_CODE, "Registered user");

  private String code;
  private String description;

  private RoleTypeEnum(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  public static RoleTypeEnum parse(String code) {
    for (RoleTypeEnum role : values()) {
      if (role.getCode().equals(code)) {
        return role;
      }
    }

    throw new IllegalArgumentException(
        "No enum const " + RoleTypeEnum.class + "." + code);
  }

  public static class Constants {
    public static final String ROLE_ADMINISTATOR_CODE = "A";
    public static final String ROLE_PSYCHOLOGIST_CODE = "P";
    public static final String ROLE_REGISTERED_USER_CODE = "RU";
  }
}
