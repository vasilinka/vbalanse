package by.vbalanse.dao.psychologist;

import by.vbalanse.dao.common.AbstractManagedEntityDao;
import by.vbalanse.model.psychologist.BonusEntity;

import java.util.List;

/**
 * writeme: Should be the description of the class
 *
 * @author <a href="v.terehova@itision.com">Vasilina Terehova</a>
 */
public interface BonusDao extends AbstractManagedEntityDao<BonusEntity> {

  List<BonusEntity> findBy(Long userId);

}