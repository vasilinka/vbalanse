package by.vbalanse.model.common;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractCompositeKeyEntity<ID extends Serializable> extends AbstractEntity<ID> {

  @Id
  @NotNull
  private ID id;

  protected AbstractCompositeKeyEntity() {
  }

  protected AbstractCompositeKeyEntity(ID id) {
    this.id = id;
  }

  /**
   * The id attribute used to uniquelly identify the instance of the subclass
   *
   * @return the unique id (primary key) of the instance
   */
  public ID getId() {
    return this.id;
  }

}