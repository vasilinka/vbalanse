package by.vbalanse.model.common;


import javax.persistence.Column;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public abstract class AbstractCompositeVersionedManagedEntity<ID extends Serializable>
    extends AbstractCompositeKeyEntity<ID> {

  @NotNull
  @Version
  @Column(name = COLUMN_OBJ_VERSION)
  private long objVersion = -1;

  protected AbstractCompositeVersionedManagedEntity() {
  }

  protected AbstractCompositeVersionedManagedEntity(ID id) {
    super(id);
  }

  public long getObjVersion() {
    return objVersion;
  }

  public void setObjVersion(long param) {
    objVersion = param;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    AbstractCompositeVersionedManagedEntity that = (AbstractCompositeVersionedManagedEntity) o;

    return objVersion == that.objVersion;

  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (int) (objVersion ^ (objVersion >>> 32));
    return result;
  }

}
