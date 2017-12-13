package by.vbalanse.model.common;


import javax.persistence.Column;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="mailto:e.terehov@itision.com">Eugene Terehov</a>
 */
public abstract class AbstractVersionedEntity<ID extends Serializable> extends AbstractEntity<ID> {

  @NotNull
  @Version
  @Column(name = COLUMN_OBJ_VERSION)
  private long objVersion = -1;

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

    AbstractVersionedEntity that = (AbstractVersionedEntity) o;

    return objVersion == that.objVersion;

  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (int) (objVersion ^ (objVersion >>> 32));
    return result;
  }

}
