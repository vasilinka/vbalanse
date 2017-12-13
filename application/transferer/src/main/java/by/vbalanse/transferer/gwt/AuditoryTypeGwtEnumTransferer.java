package by.vbalanse.transferer.gwt;

import by.vbalanse.gwt.portal.common.rpc.gwt.dto.article.AuditoryTypeGwtEnum;
import by.vbalanse.model.article.AuditoryTypeEnum;
import by.vbalanse.transferer.common.AbstractTransferer;
import by.vbalanse.transferer.common.ObjectTransferer;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public class AuditoryTypeGwtEnumTransferer extends AbstractTransferer<AuditoryTypeGwtEnum> {
  {
    registerTransferer(AuditoryTypeEnum.class, new ObjectTransferer<AuditoryTypeGwtEnum, AuditoryTypeEnum>() {
      public AuditoryTypeEnum transfer(AuditoryTypeGwtEnum source) {
        if (source.equals(AuditoryTypeGwtEnum.FOR_BUSINESS_LADY_MEN)) {
          return AuditoryTypeEnum.FOR_BUSINESS_LADY_MEN;
        } else if (source.equals(AuditoryTypeGwtEnum.FOR_GROWNUPS)) {
          return AuditoryTypeEnum.FOR_GROWNUPS;
        } else if (source.equals(AuditoryTypeGwtEnum.FOR_MEN_WOMEN)) {
          return AuditoryTypeEnum.FOR_MEN_WOMEN;
        } else if (source.equals(AuditoryTypeGwtEnum.FOR_PARENTS)) {
          return AuditoryTypeEnum.FOR_PARENTS;
        }
        throw new IllegalArgumentException("Such Status of import is not supported");
      }
    });
  }
}
