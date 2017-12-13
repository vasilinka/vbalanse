package by.vbalanse.model.geography;

import by.vbalanse.model.common.AbstractManagedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
@Entity(name = CityEntity.TABLE_NAME)
public class CityEntity extends AbstractManagedEntity {
  public static final String TABLE_NAME = "geo_city";
  public static final String COLUMN_CITY_NAME = "title_";
  public static final String COLUMN_CITY_CODE = "code_";
  //??we need region to show city in order of regions???  not region name but separate entity for regions
  public static final String COLUMN_REGION_NAME = "region_";

  @Column(name = COLUMN_CITY_NAME)
  @NotNull
  private String title;

  @Column(name = COLUMN_CITY_CODE)
  @NotNull
  private String code;

  public CityEntity() {
  }

  public CityEntity(String title, String code) {
    this.title = title;
    this.code = code;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return title;
  }
}
